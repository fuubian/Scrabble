package main.model;

/**
 * The Player class represents a human player.
 * 
 * @author sekeller
 */
public class HumanPlayer extends Player {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a human player with the specified name.
   * 
   * @param name the specified name
   */
  public HumanPlayer(String name) {
    super(name, PlayerType.MAN);
  }

  /**
   * Constructs a copy of the specified human player.
   * 
   * @param original the specified human player
   */
  HumanPlayer(HumanPlayer original) {
    super(original);
  }

}
