package main.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import main.network.message.BroadcastResponseMessage;

/**
 * This class sends broadcast messages and receives the responses.
 * It is used to find open Scrabble servers.
 * 
 * @author frajwa
 *
 */
public class BroadcastSender extends Thread {

  /**
   * Socket to send broadcast messages.
   */
  private DatagramSocket broadcastSocket;

  /**
   * Socket to receive responses.
   */
  private ServerSocket responseSocket;

  /**
   * List of addresses that responded to the broadcast message.
   */
  private ArrayList<String> addressList;

  /**
   * Determines if the sender is receiving responses.
   */
  private boolean running;

  /**
   * Constructor that initializes the sockets.
   * @throws SocketException 
   */
  public BroadcastSender() throws SocketException {
    this.addressList = new ArrayList<String>();
    this.broadcastSocket = new DatagramSocket(ServerSettings.broadcastPort);
    this.broadcastSocket.setBroadcast(true);
  }

  /**
   * Sends a message to every found broadcast address.
   */
  public void searchHosts() {
    try {
      List<InetAddress> broadcasts = this.listAllBroadcastAddresses();
      for (InetAddress address : broadcasts) {
        this.sendBroadcast(address);
      }
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }

  /**
   * Receives responses to the sent broadcast messages.
   */
  public void run() {
    try {
      this.responseSocket = new ServerSocket(ServerSettings.responsePort);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    this.running = true;
    try {
      while (this.running) {
        Socket client = responseSocket.accept();

        if (!this.running) {
          client.close();
          break;
        }
        
        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
        BroadcastResponseMessage msg = (BroadcastResponseMessage) in.readObject();

        this.addressList.add(msg.getAddress());
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns a list with IP addresses of open servers.
   * 
   * @return list with IP addresses of open servers
   */
  public ArrayList<String> getAddressList() {
    return this.addressList;
  }

  /**
   * Closes all running sockets after the job was done.
   */
  public void closeSockets() {
    if (running) {
      this.running = false;
      this.broadcastSocket.close();

      try {
        Socket tmpSocket = new Socket("localhost", ServerSettings.responsePort);
        tmpSocket.close();
        this.responseSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Sends a message to a broadcast address.
   * 
   * @param address broadcast address
   */
  private void sendBroadcast(InetAddress address) {
    try {
      byte[] content = ServerSettings.getIPAdress().getBytes();
      DatagramPacket packet = new DatagramPacket(content, content.length, address, ServerSettings.broadcastPort);
      this.broadcastSocket.send(packet);
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method finds the broadcast address.
   * Source: https://www.baeldung.com/java-broadcast-multicast (10.05.2021).
   * 
   * @return list that contains the broadcast address
   * @throws SocketException
   */
  private List<InetAddress> listAllBroadcastAddresses() throws SocketException {
    List<InetAddress> broadcastList = new ArrayList<>();
    Enumeration<NetworkInterface> interfaces 
    = NetworkInterface.getNetworkInterfaces();
    while (interfaces.hasMoreElements()) {
      NetworkInterface networkInterface = interfaces.nextElement();

      if (networkInterface.isLoopback() || !networkInterface.isUp()) {
        continue;
      }

      networkInterface.getInterfaceAddresses().stream() 
      .map(a -> a.getBroadcast())
      .filter(Objects::nonNull)
      .forEach(broadcastList::add);
    }
    return broadcastList;
  }

  /**
   * Test method.
   * 
   * @param args
   */
  public static void main(String[] args) {
    BroadcastSender bm;
    try {
      bm = new BroadcastSender();
      bm.searchHosts();
      bm.start();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      bm.closeSockets();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      ArrayList<String> list = bm.getAddressList();
      for (String s : list) {
        System.out.println(s);
      }
    } catch (SocketException e1) {
      e1.printStackTrace();
    }
  }
}
