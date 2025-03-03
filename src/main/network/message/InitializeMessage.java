package main.network.message;

import main.model.Scrabble;

/**
 * Message for the initialization of a game.
 * 
 * @author lknothe
 *
 */

public class InitializeMessage extends Message {

  /**
   * Default serial version.
   */

  private static final long serialVersionUID = 1L;

  /**
   * Scrabble object.
   */

  private Scrabble scrabble;

  /**
   * Constructor.
   * 
   * @param type type of message
   * @param from origin of the message
   * @param turnstate Turnstate object
   */

  public InitializeMessage(MessageType type, String from, Scrabble scrabble) {
    super(type, from);
    this.scrabble = scrabble;
  }

  /**
   * Returns scrabble obkect.
   * 
   * @return Scrabble object
   */

  public Scrabble getScrabble() {
    return scrabble;
  }
}
