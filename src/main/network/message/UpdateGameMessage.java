package main.network.message;

import main.model.TurnState;

/**
 * Message to update the game status.
 * 
 * @author lknothe
 *
 */

public class UpdateGameMessage extends Message {

  /**
   * Default serial version.
   */

  private static final long serialVersionUID = 1L;
  
  /**
   * An object that contains the game information.
   */
  private TurnState game;

  /**
   * Constructor of the message.
   * 
   * @param typ type of the message
   * @param from name of the sender
   * @param game Scrabble object
   */
  public UpdateGameMessage(MessageType typ, String from, TurnState game) {
    super(typ, from);
    this.game = game;
  }
  
  /**
   * Returns an object of the class Scrabble.
   * @return Scrabble object
   */
  public TurnState getGame()
  {
    return this.game;
  }
}
