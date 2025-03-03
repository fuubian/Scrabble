package main.network.message;

import main.model.TurnState;

public class StartGameMessage extends Message {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private TurnState game;
  
  public StartGameMessage(MessageType type, String from, TurnState game) {
    super(type, from);
    this.game = game;
  }
  
  public TurnState getGame() {
    return this.game;
  }
  
}
