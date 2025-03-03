package main.network;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * This class saves all needed settings for setting up a server.
 * 
 * @author frajwa
 */
public class ServerSettings {
  
  /**
   * Port that is used to connect to server.
   */
  public static int port = 8421;
  
  /**
   * Port that is used to look for active hosts.
   */
  public static int broadcastPort = 8422;
  
  /**
   * Port that is used to answer broadcast messages.
   */
  public static int responsePort = 8423;
  
  /**
   * Returns the local IP address of the host.
   * The method connects to google and gets his local address through the socket.
   * 
   * @return local IP address
   */ 
  public static String getIPAdress() {
    String ip = "IP couldn't be detected.";
    try{
      DatagramSocket socket = new DatagramSocket();
      socket.connect(InetAddress.getByName("google.com"), 80);
      ip = socket.getLocalAddress().getHostAddress().toString();
      socket.close();
    } catch (SocketException e) {
    } catch (UnknownHostException e) {
        try {
          ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e1) {
        }
    }
    
    return ip;
  }
}
