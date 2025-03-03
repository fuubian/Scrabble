package main.network.message;

import java.io.Serializable;

/**
 * Main message file, superclass for all message types.
 * 
 * @author lknothe
 *
 */

public class Message implements Serializable {

  /**
   * Default serial version.
   */

  private static final long serialVersionUID = 1L;

  /**
   * Type of Message (specified in MessageType).
   */

  private MessageType messageType;

  /**
   * Origin (Clients name or so) of a message.
   */

  private String from;

  /**
   * Constructor for all Messages.
   * 
   * @param type type of message
   * @param from origin of the message
   */

  public Message(MessageType type, String from) {
    this.messageType = type;
    this.from = new String(from);
  }

  /**
   * Returns the Type of message.
   * 
   * @return message type
   */

  public MessageType getmType() {
    return messageType;
  }

  /**
   * Returns the origin of the message.
   * 
   * @return origin of the message
   */

  public String getFrom() {
    return from;
  }


}
