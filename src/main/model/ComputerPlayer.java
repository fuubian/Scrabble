package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ComputerPlayer class represents a computer player.
 * 
 * @author sekeller
 */
public class ComputerPlayer extends Player {

  private static final long serialVersionUID = 1L;

  /**
   * A constant for the minimum turn time in milliseconds.
   */
  private static final long MIN_TURN_TIME = 3_000;

  /**
   * The difficulty of the computer player.
   */
  private Difficulty difficulty;

  /**
   * Constructs a computer player with the specified name and the specified difficulty.
   * 
   * @param name the specified name
   * @param difficulty the specified difficulty
   */
  public ComputerPlayer(String name, Difficulty difficulty) {
    super(name, PlayerType.COM);

    this.difficulty = difficulty;
  }

  /**
   * Constructs a copy of the specified computer player.
   * 
   * @param original the specified computer player
   */
  ComputerPlayer(ComputerPlayer original) {
    super(original);

    this.difficulty = original.difficulty;
  }

  /**
   * Returns the difficulty of this computer player.
   * 
   * @return the difficulty of this computer player
   */
  public Difficulty getDifficulty() {
    return this.difficulty;
  }

  /**
   * Returns a move for the specified game.
   * 
   * @param game the specified game
   * @return a move for the specified game
   */
  public Move chooseMove(Scrabble game) {
    // finish the game when you win
    Move move = null;

    if (game.getScorelessMoveCount() >= Scrabble.MIN_SCORELESS_MOVE_COUNT_TO_FINISH_GAME) {
      boolean won = true;
      int ownScore = game.getCurrentPlayer().getScore();
      int ownIndex = game.getCurrentPlayerIndex();

      for (int i = 0; i < game.getPlayerCount(); i++) {
        if (i != ownIndex && game.getPlayer(i).getScore() > ownScore) {
          won = false;
          break;
        }
      }

      if (won) {
        move = new FinishGame(game);
      }
    }

    // pass with a certain probability
    if (move == null) {
      if (Math.random() < this.difficulty.getPassRate()) {
        move = new Pass(game);
      }
    }

    // search for a valid move
    if (move == null) {
      move = this.getValidPlayWordMove(game);
    }

    // change tiles when there is no valid move or ...
    if (move == null) {
      // TODO
    }

    // pass when there is no other option
    if (move == null) {
      move = new Pass(game);
    }

    // Simulate thinking ...
    long elapsedTime = Scrabble.TIME_LIMIT - game.getRemainingTime();
    long sleepTime = MIN_TURN_TIME - elapsedTime;

    if (game.getGameState() == GameState.PLAY && sleepTime > 0) {
      try {
        Thread.sleep(sleepTime);
      } catch (InterruptedException e) {
      }
    }

    return move;
  }

  /**
   * Returns a valid play word move for the specified game.
   * 
   * @param game the specified game
   * @return a valid play word move for the specified game
   */
  private PlayWord getValidPlayWordMove(Scrabble game) {
    int minRow = Gameboard.squareIdToRow(Gameboard.CENTER_SQUARE_ID);
    int maxRow = Gameboard.squareIdToCol(Gameboard.CENTER_SQUARE_ID);
    int minCol = Gameboard.squareIdToRow(Gameboard.CENTER_SQUARE_ID);
    int maxCol = Gameboard.squareIdToCol(Gameboard.CENTER_SQUARE_ID);

    for (int row = 0; row < Gameboard.ROWS; row++) {
      for (int col = 0; col < Gameboard.COLS; col++) {
        if (!game.getGameboard().getSquareAt(row, col).isEmpty()) {
          minRow = Math.min(minRow, row);
          maxRow = Math.max(maxRow, row);
          minCol = Math.min(minCol, col);
          maxCol = Math.max(maxCol, col);
        }
      }
    }

    List<String> words = game.getDictionary().getAllWords();
    Collections.shuffle(words);
    PlayWord bestMove = null;

    for (String word : words) {
      for (int col : this.shuffledValues(0, Gameboard.COLS - 1)) {
        for (int row : this.shuffledValues(0, Gameboard.ROWS - 1)) {
          String squareId = Gameboard.computeSquareId(row, col);

          for (Direction direction : Direction.values()) {
            if (game.getGameState() == GameState.GAME_OVER) {
              return bestMove;
            }

            if (direction == Direction.HORIZONTAL
                && (row < minRow - 1 || row > maxRow + 1 || word.length() > Gameboard.COLS - col)) {
              continue;
            }

            if (direction == Direction.VERTICAL
                && (col < minCol - 1 || col > maxCol + 1 || word.length() > Gameboard.ROWS - row)) {
              continue;
            }

            PlayWord move = new PlayWord(game, word, squareId, direction);

            if (move.isValid() && move.getScore() <= this.difficulty.getMaxScore()) {
              if (bestMove == null || move.getScore() > bestMove.getScore()) {
                bestMove = move;

                if (bestMove.getScore() >= this.difficulty.getMinScore()) {
                  return bestMove;
                }
              }
            }
          }
        }
      }
    }

    return bestMove;
  }

  /**
   * Returns a shuffled list with the values from the specified minimum to the specified maximum
   * value.
   * 
   * @param min the specified minimum value
   * @param max the specified maximum value
   * @return a shuffled list with the values from the specified minimum to the specified maximum
   *         value
   */
  private List<Integer> shuffledValues(int min, int max) {
    List<Integer> values = new ArrayList<>();

    for (int i = min; i <= max; i++) {
      values.add(i);
    }

    Collections.shuffle(values);

    return values;
  }

}
