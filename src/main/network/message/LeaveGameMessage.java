package main.network.message;

/**
 * 
 * @author frajwa
 *
 */
public class LeaveGameMessage extends Message {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public LeaveGameMessage(MessageType type, String from) {
    super(type, from);
  }
}