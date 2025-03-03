package main.network.message;

/**
 * Message that is sent to change a client's username.
 * 
 * @author frajwa
 */
public class NewNameMessage extends Message {
  
  /**
   * Default serial version.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * New name of a client.
   */
  private String newName;

  /**
   * Constructor.
   * 
   * @param type type of message
   * @param from origin of message
   * @param newName new name of a client
   */
  public NewNameMessage(MessageType type, String from, String newName) {
    super(type, from);
    this.newName = newName;
  }
  
  /**
   * Returns the new name of a client.
   * 
   * @return new name of a client
   */
  public String getNewName() {
    return this.newName;
  }
}
