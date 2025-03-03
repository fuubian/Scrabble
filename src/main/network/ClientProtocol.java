package main.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import main.controller.ClientController;
import main.database.Profile;
import main.network.message.ChatMessage;
import main.network.message.DisconnectMessage;
import main.network.message.InitializeMessage;
import main.network.message.LeaveGameMessage;
import main.network.message.LobbyDetailsMessage;
import main.network.message.Message;
import main.network.message.MessageType;
import main.network.message.NewNameMessage;
import main.network.message.StartGameMessage;
import main.network.message.UpdateGameMessage;

/**
 * This class represents the connection (client-side) between server and client. It receives and
 * sends messages.
 * 
 * @author frajwa
 * 
 */
public class ClientProtocol extends Thread {

  /**
   * Connection to the server.
   */
  private Socket clientSocket;

  /**
   * InputStream to receive messages from host.
   */
  private ObjectOutputStream out;

  /**
   * Outputstream to send messages to host.
   */
  private ObjectInputStream in;

  /**
   * Boolean variable that determines if the connection to the client is active.
   */
  private boolean running = true;

  /**
   * Username of the client.
   */
  private String userName = Profile.getName();

  /**
   * Controller between GUI and Network.
   */
  private ClientController netController;

  /**
   * Boolean that indicates if the client was accepted from the server.
   */
  private boolean accepted;

  /**
   * Boolean that indicates if he's allowed to send objects at the moment.
   */
  private boolean access = true;
  
  /**
   * Constructs protocol.
   * 
   * @param ipAdress address of host
   * @throws IOException
   */
  public ClientProtocol(String ipAdress, ClientController nc) throws IOException {
    this.netController = nc;
    try {
      this.clientSocket = new Socket(ipAdress, ServerSettings.port);
      this.out = new ObjectOutputStream(clientSocket.getOutputStream());
      this.in = new ObjectInputStream(clientSocket.getInputStream());
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      throw e;
    }
  }

  /**
   * Receiving new messages and handling first ConnectMessage.
   */
  public void run() {
    Message mgConnect = new Message(MessageType.CONNECT, this.userName);

    try {
      this.out.writeObject(mgConnect);

      while (this.running) {
        Message mgReceived = (Message) in.readObject();

        switch (mgReceived.getmType()) {
          case CHAT:
            if (((ChatMessage) mgReceived).getLocation().equals("lobby")) {
              netController.receiveChatLobby((ChatMessage) mgReceived);
            } else {
              netController.receiveChatGame((ChatMessage) mgReceived);
            }
            break;
          case LOBBYDETAILS:
            netController.updateLobby((LobbyDetailsMessage) mgReceived);
            break;
          case DISCONNECT:
            this.running = false;
            if (!((DisconnectMessage) mgReceived).getReason().equals("Disconnect")) {
              this.out.writeObject(mgReceived);
              this.netController.lostConnection(((DisconnectMessage) mgReceived).getReason());
              this.running = false;
            } else {
              this.netController.disconnect();
            }
            break;
          case INITIALIZE:
            this.netController.startGame(((InitializeMessage) mgReceived).getScrabble());
            break;
          case NEWNAME:
            this.userName = ((NewNameMessage) mgReceived).getNewName();
            this.accepted = true;
            Profile.setOnlineName(this.userName);
            break;
          case UPDATEGAME:
            this.netController.receiveMove(((UpdateGameMessage) mgReceived).getGame());
            break;
          case LEAVEGAME:
            this.netController.receiveBackToLobby(mgReceived.getFrom());
            break;
          case STARTGAME:
            this.access = true;
            this.netController.startGame(((StartGameMessage) mgReceived).getGame());
            break;
          case BLOCKSOCKET:
            this.access = false;
            this.netController.informPlayers(mgReceived.getFrom());
            break;
          default:
        }
      }

      this.clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

  }

  /**
   * Sends a ChatMessage to the server.
   * 
   * @param text what the player wrote in the chat
   */
  public void sendChat(String text, String lobby) {
    if (this.access) {
      try {
        Message mgChat = new ChatMessage(MessageType.CHAT, this.userName, text, lobby);
        this.out.writeObject(mgChat);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Returns if the client was accepted by the server.
   * 
   * @return boolean that indicates if the client was accepted
   */
  public boolean getAccepted() {
    return this.accepted;
  }

  /**
   * Requests a disconnect to the server.
   */
  public void disconnect() {
    if (running) {
      if (this.access) {
        this.accepted = false;
        this.running = false;
        try {
          Message mgDisconnect =
              new DisconnectMessage(MessageType.DISCONNECT, this.userName, "Disconnect");
          if (this.running) {
            out.writeObject(mgDisconnect);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        while (!this.access) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        Message msg = new LeaveGameMessage(MessageType.LEAVEGAME, Profile.getOnlineName());
        try {
          out.writeObject(msg);
        } catch (IOException e) {
          e.printStackTrace();
        }
        this.disconnect();
      }
    }
  }

  public void sendMessage(Message mgSend) throws IOException {
    if (this.access) {
      this.out.writeObject(mgSend);
    }
  }
}
