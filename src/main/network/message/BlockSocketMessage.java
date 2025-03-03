package main.network.message;

/**
 * This message is sent when a new game is started.
 * It blocks the socket of the client, so he can do no interaction 
 * while the host sends the game object.
 * 
 * @author frajwa
 *
 */
public class BlockSocketMessage extends Message {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   * 
   * @param type MessageType
   * @param from the sender
   */
  public BlockSocketMessage(MessageType type, String from) {
    super(type, from);
  }

}
