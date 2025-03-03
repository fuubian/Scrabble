package main.model;

/**
 * The Pass class represents a move to pass.
 * 
 * @author sekeller
 */
public class Pass extends Move {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a pass move for the specified game.
   * 
   * @param game the specified game
   */
  Pass(Scrabble game) {
    super(game);

    this.init();
  }

  /**
   * Constructs a copy of the specified pass move.
   * 
   * @param original the specified pass move
   */
  Pass(Pass original) {
    super();

    this.player = original.player;
    this.gameException = new GameException(original.gameException);
    this.time = original.time;
  }

  @Override
  public int getScore() {
    return 0;
  }

  @Override
  void execute() throws GameException {
    if (this.gameException != null) {
      throw this.gameException;
    }

    this.game.increaseScorelessMoveCount();
    this.game.updateCurrentPlayer();
  }

  @Override
  public String toString() {
    return "Pass()";
  }

  /**
   * Initializes the moveException.
   */
  private void init() {
    if (this.game.getGameState() == GameState.PREPERATION) {
      this.gameException = new GameException("The game has not started yet!");
      return;
    }

    if (this.game.getGameState() == GameState.GAME_OVER) {
      this.gameException = new GameException("The game is over!");
      return;
    }
  }

}
