package main.network.message;

/**
 * This message is sent after receiving a broadcast message. 
 * The searching client receives the IP address of an open server.
 * 
 * @author frajwa
 *
 */
public class BroadcastResponseMessage extends Message {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * IP address of an open server.
   */
  private String address;
  
  public BroadcastResponseMessage(MessageType type, String from, String address) {
    super(type, from);
    this.address = address;
  }
  
  public String getAddress() {
    return this.address;
  }

}
