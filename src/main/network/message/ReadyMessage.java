package main.network.message;

/**
 * Message to pass a move.
 * 
 * @author lknothe
 *
 */

public class ReadyMessage extends Message {

  /**
   * Default serial version.
   */

  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   * 
   * @param type type of message
   * @param from origin of the message
   */

  public ReadyMessage(MessageType type, String from) {
    super(type, from);
  }


}
