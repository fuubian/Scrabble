package main.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import main.network.message.ChatMessage;
import main.network.message.DisconnectMessage;
import main.network.message.Message;
import main.network.message.MessageType;
import main.network.message.NewNameMessage;
import main.network.message.UpdateGameMessage;

/**
 * This class represents the connection (server-side) between server and client.
 * It receives and sends messages.
 * 
 * @author frajwa
 * 
 */
public class ServerProtocol extends Thread {

  /**
   * Connection to the client.
   */
  private Socket client;

  /**
   * Pointer to the server.
   */
  private Server host;

  /**
   * InputStream to receive messages from client.
   */
  private ObjectInputStream in;

  /**
   * OutputStream to send messages to client.
   */
  private ObjectOutputStream out;

  /**
   * Boolean variable that determines if the connection to the client is active.
   */
  private boolean running = true;

  /**
   * Username of the client.
   */
  private String userName;

  /**
   * Constructs the protocol.
   * 
   * @param client socket to client
   * @param server pointer to Server
   */
  public ServerProtocol(Socket client, Server server) {
    this.client = client;
    this.host = server;

    try {
      this.out = new ObjectOutputStream(this.client.getOutputStream());;
      this.in = new ObjectInputStream(this.client.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Receives new messages and handles first ConnectMessage.
   */
  public void run() {
    try {
      Message mgReceived = (Message) in.readObject();

      /**
       * First message must be a ConnectMessage.
       * Accept connection and send lobby details.
       */
      if (mgReceived.getmType() == MessageType.CONNECT) {
        if (host.getAmountPlayer() == 4) {
          Message mgRefuesed = new DisconnectMessage(MessageType.DISCONNECT, 
              "host", "Lobby is full.");
          this.out.writeObject(mgRefuesed);
          this.running = false;
        } else {
          this.userName = mgReceived.getFrom();

          /**
           * Check if username is already used and send new username to client.
           */
          int i = 0;
          while (this.host.checkName(this.userName)) {
            i++;
            this.userName = mgReceived.getFrom() + " (" + i + ")";
          }

          Message mgName = new NewNameMessage(MessageType.NEWNAME, "host", this.userName);
          this.out.writeObject(mgName);
          this.host.addClient(this, this.userName);

          /**
           * TODO: Receive stats from client and update in GUI.
           */
        }
      } else {
        this.running = false;
      }

      while (this.running) {
        mgReceived = (Message) in.readObject();
        switch (mgReceived.getmType()) {
          case DISCONNECT:
            this.running = false;
            if (!((DisconnectMessage) mgReceived).getReason().equals("Connection closed")) {
              this.host.removeClient(this.userName);
            }
            if (((DisconnectMessage) mgReceived).getReason().equals("Disconnect")) {
              this.out.writeObject(mgReceived);
            }
            break;
          case CHAT:
            if (((ChatMessage) mgReceived).getLocation().equals("lobby")) {
              host.getNetController().receiveChatLobby((ChatMessage) mgReceived);
              this.host.sendToAllBut(mgReceived, mgReceived.getFrom());
            } else {
              host.getNetController().receiveChatGame((ChatMessage) mgReceived);
              this.host.sendToAllBut(mgReceived, mgReceived.getFrom());
            }
            break;
          case CHANGETILES:
            // TODO: Put player tiles in bag and sends new tiles.
            break;
          case READY:
            this.host.setReady(mgReceived.getFrom());
            break;
          case UPDATEGAME:
            this.host.getNetController().receiveMove(((UpdateGameMessage) mgReceived).getGame());
            this.host.sendToAllBut(mgReceived, mgReceived.getFrom());
            break;
          case LEAVEGAME:
            this.host.getNetController().receiveBackToLobby(mgReceived.getFrom());
            this.host.sendToAllBut(mgReceived, mgReceived.getFrom());
            break;
          default:
        }
      }

      this.client.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sends a DisconnectMessage to the client.
   */
  public void closeConnection() {
    if (this.running) {
      this.running = false;
      try {
        Message mgClose = new DisconnectMessage(MessageType.DISCONNECT, "host", "Connection closed");
        out.writeObject(mgClose);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Sends a message to the client.
   * 
   * @param mgSend Message that is sent
   */
  public void sendMessage(Message mgSend) {
    try {
      this.out.writeObject(mgSend);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
