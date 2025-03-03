package main.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import main.controller.ServerController;
import main.database.Profile;
import main.model.Scrabble;
import main.network.message.ChatMessage;
import main.network.message.LeaveGameMessage;
import main.network.message.Message;
import main.network.message.MessageType;

/**
 * This class starts the server and waits for clients to connect.
 * All information about the clients are handled here.
 * 
 * @author frajwa
 *
 */
public class Server extends Thread {

  /**
   * The ServerSocket creates the server and opens it.
   */
  private ServerSocket host;

  /**
   * Controller between Network and GUI.
   */
  private ServerController netController;
  
  /**
   * Object that receives a broadcast message for the automatic host detection.
   */
  private BroadcastReceiver bcReceiver;

  /**
   * Username of the host.
   */
  private String userName = Profile.getName();

  /**
   * Boolean variable that determines if the server is open for new clients to connect.
   */
  private boolean running = true;

  /**
   * HashMap with all connected clients and their usernames.
   */
  private HashMap<String, ServerProtocol> clients = new HashMap<>();

  /**
   * Integer with amount of players (host, connected clients, AIs).
   */
  private int amountPlayer = 1;

  /**
   * Constructs the server.
   * 
   * @param nc Controller between Network and GUI
   */
  public Server(ServerController nc) {
    this.netController = nc;
    this.bcReceiver = new BroadcastReceiver();
    this.bcReceiver.start();
    try {
      this.host = new ServerSocket(ServerSettings.port);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Accepts new players connecting.
   */
  public void run() {
    try {
      while (running) {
        Socket client = host.accept();

        if (!running) {
          break;
        }
        
        ServerProtocol clientConnection = new ServerProtocol(client, this);
        clientConnection.start();
      }
      this.host.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Closes the connection to every client.
   */
  public void closeServer() {
    if (this.running) {
      this.bcReceiver.closeSockets();
      for (String clientName : this.clients.keySet()) {
        this.clients.get(clientName).closeConnection();
      }
      this.running = false;
      try {
        /**
         * Connect to itself to close the loop. 
         */
        Socket s = new Socket("127.0.0.1", ServerSettings.port);
        s.close();
      } catch (UnknownHostException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Adds new client to the client hashmap and to the GUI.
   * 
   * @param client ServerProtocol to the client
   * @param name client's username
   */
  public void addClient(ServerProtocol client, String name) {
    this.clients.put(name, client);
    this.amountPlayer++;
    this.netController.addPlayer(name, Profile.getNetworkStats().getStats());
    this.netController.sendLobbyDetails();
  }

  /**
   * Removes client from the client hashmap and from the GUI.
   * 
   * @param name client's username
   */
  public void removeClient(String name) {
    this.clients.remove(name);
    this.amountPlayer--;
    this.netController.removePlayer(name);
  }

  /**
   * Adds a new AI to the lobby.
   * 
   * @param difficulty strength of the AI
   */
  public void addAi() {
    this.amountPlayer++;
  }

  /**
   * Removes an AI from the lobby.
   */
  public void removeAi() {
    this.amountPlayer--;
  }

  /**
   * Creates a ChatMessage when the host wants to send one.
   * 
   * @param text content of the ChatMessage
   */
  public void createChatMessage(String text, String lobby) {
    Message mgChat = new ChatMessage(MessageType.CHAT, this.userName, text, lobby);
    this.sendToAll(mgChat);
  }

  /**
   * Sends a Message to all clients but one chosen.
   * 
   * @param mgSend Message that is sent
   * @param exception client that doesn't receive the Message
   */
  public void sendToAllBut(Message mgSend, String exception) {
    for (String clientName : this.clients.keySet()) {
      if (!clientName.equals(exception)) {
        this.clients.get(clientName).sendMessage(mgSend);
      }
    }
  }
  
  /**
   * Sends a message to only one client.
   * 
   * @param mgSend message
   * @param player client who should receive message
   */
  public void sendToOne(Message mgSend, String player) {
    for (String clientName : this.clients.keySet()) {
      if (clientName.equals(player)) {
        this.clients.get(clientName).sendMessage(mgSend);
      }
    }
  }

  /**
   * Sends a Message to all clients.
   * 
   * @param mgSend Message that is sent
   */
  public void sendToAll(Message mgSend) {
    for (String clientName : this.clients.keySet()) {
      this.clients.get(clientName).sendMessage(mgSend);
    }
  }

  /**
   * Sends a received ChatMessage to the GUI.
   * 
   * @param mgChat received ChatMessage
   */
  public void receiveChatMessage(Message mgChat) {
    if (((ChatMessage) mgChat).getLocation().equals("lobby")) {
      this.netController.receiveChatLobby(((ChatMessage) mgChat));
    } else {
      this.netController.sendChatGame(((ChatMessage) mgChat).getText());
    }
  }

  /**
   * Returns the amount of players who are currently connected to the game + the host.
   * 
   * @return amount of players
   */
  public int getAmountPlayer() {
    return this.amountPlayer;
  }

  /**
   * Checks if a username is already used by a player.
   * 
   * @param name client that wants to connect
   * @return boolean variable that indicates if the username is used
   */
  public boolean checkName(String name) {
    return this.clients.containsKey(name) || this.userName.equals(name);
  }
  
  public void setReady(String name) {
    this.netController.changeReady(name);
  }
  
  public ServerController getNetController() {
    return this.netController;
  }
}