package main.network.message;

/**
 * The message class for chatmessages.
 * 
 * @author lknothe
 *
 */

public class ChatMessage extends Message {

  /**
   * Default serial version.
   */

  private static final long serialVersionUID = 1L;

  /**
   * The text of the chatmessage.
   */

  private String text;

  /**
   * String object for the location of the chat.
   */

  private String location;

  /**
   * Constructor.
   * 
   * @param type type of message
   * @param from origin of the message
   */

  public ChatMessage(MessageType type, String from, String text, String location) {
    super(type, from);
    this.text = text;
    this.location = location;
  }

  /**
   * Returns the text.
   * 
   * @return text of the chatmessage
   */

  public String getText() {
    return text;
  }

  /**
   * Returns location either lobby or ingame.
   * 
   * @return String object
   */

  public String getLocation() {
    return location;
  }

}
