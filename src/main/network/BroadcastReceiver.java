package main.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import main.database.Profile;
import main.network.message.BroadcastResponseMessage;
import main.network.message.Message;
import main.network.message.MessageType;


/**
 * This class receives broadcast messages and send a response.
 * It is used to signal that a server is open.
 * 
 * @author frajwa
 *
 */
public class BroadcastReceiver extends Thread {

  /**
   * Socket that is used to receive a broadcast message.
   */
  private DatagramSocket broadcastSocket;

  /**
   * Socket that is used to answer to a broadcast message.
   */
  private Socket responseSocket;

  /**
   * Determines if the receiver is active.
   */
  private boolean running;

  /**
   * Constructor that initializes the sockets.
   */
  public BroadcastReceiver() {
    try {
      this.broadcastSocket = new DatagramSocket(ServerSettings.broadcastPort);
      this.broadcastSocket.setBroadcast(true);
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Receiving broadcast messages.
   */
  public void run() {
    try {
      DatagramPacket packet = new DatagramPacket(new byte[512], 512);
      this.running = true;
      while (this.running) {
        this.broadcastSocket.receive(packet);

        if(!this.running) {
          break;
        }

        this.sendResponse(new String(packet.getData(), 0, packet.getLength()));
      }
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This closes the sockets when the server closes.
   */
  public void closeSockets() {
    if (this.running) {
      this.running = false;

      try {
        DatagramPacket packet = new DatagramPacket(new byte[512], 512, InetAddress.getLocalHost(), ServerSettings.broadcastPort);
        this.broadcastSocket.send(packet);
      } catch (UnknownHostException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      this.broadcastSocket.close();
    }
  }

  /**
   * This sends a response after a broadcast message.
   * 
   * @param address IP address of the broadcast sender
   */
  private void sendResponse(String address) {
    try {
      this.responseSocket = new Socket(address, ServerSettings.responsePort);
      Message msg = new BroadcastResponseMessage(MessageType.BROADCASTRESPONSE, Profile.getName(), 
          ServerSettings.getIPAdress());
      ObjectOutputStream out = new ObjectOutputStream(this.responseSocket.getOutputStream());
      out.writeObject(msg);
      this.responseSocket.close();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
