package main.model;

/**
 * The FinishGame class represents a move to finish a game.
 * 
 * @author sekeller
 */
public class FinishGame extends Move {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a finish game move for the specified game.
   * 
   * @param game the specified game
   */
  FinishGame(Scrabble game) {
    super(game);

    this.init();
  }

  /**
   * Constructs a copy of the specified finish game move.
   * 
   * @param original the specified finish game move
   */
  FinishGame(FinishGame original) {
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

    this.game.setGameState(GameState.GAME_OVER);
  }

  @Override
  public String toString() {
    return "FinishGame()";
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

    if (this.game.getScorelessMoveCount() < Scrabble.MIN_SCORELESS_MOVE_COUNT_TO_FINISH_GAME) {
      this.gameException = new GameException("The scoreless move count is too small!");
      return;
    }
  }

}
