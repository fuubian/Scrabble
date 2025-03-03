package main.network.message;

/**
 * To disconnect or kick a player from a lobby/game.
 * 
 * @author lknothe
 *
 */

public class DisconnectMessage extends Message {


  /**
   * Default serial version.
   */

  private static final long serialVersionUID = 1L;

  /**
   * Reason for the disconnect or kick.
   */

  private String reason;

  /**
   * Constructor.
   * 
   * @param type type of message
   * @param from origin of the message
   * @param reason reason to be disconnected
   */

  public DisconnectMessage(MessageType type, String from, String reason) {
    super(type, from);
    this.reason = reason;
  }


  /**
   * Returns the reason for the disconnect.
   * 
   * @return reason for disconnect
   */

  public String getReason() {
    return reason;
  }

}
