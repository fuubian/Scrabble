package main.controller;

import main.gui.TilePane;
import main.model.Direction;
import main.model.Gameboard;

/**
 * Supporting class to find the word on the board that was being placed.
 * 
 * @author ceho
 * @author sekeller
 *
 */
public class PlayedWord {
  /**
   * Stores the direction of the played word.
   */
  private Direction direction;

  /**
   * Stores the played word.
   */
  private String word;

  /**
   * Indicates the newly placed tiles from the current round.
   */
  private boolean[][] playedTiles;

  /**
   * Stores the row index of the first letter of the word.
   */
  private int startRow;

  /**
   * Stores the column index of the first letter of the word.
   */
  private int startCol;

  /**
   * The current board with the placed tiles.
   */
  private TilePane[][] board;

  /**
   * Constructor for PlayedWord. Invoked the find method.
   * 
   * @param board the current gameboard.
   * @param playedTiles the tiles that have been placed in the current round.
   */
  public PlayedWord(TilePane[][] board, boolean[][] playedTiles) {
    this.board = board;
    this.playedTiles = playedTiles;
    find();
  }

  /**
   * Returns the direction of the played word.
   * 
   * @return direction.
   */
  public Direction getDirection() {
    return direction;
  }

  /**
   * Sets the direction to find the word to either horizontal or vertical.
   * 
   * @param direction of the word.
   */
  public void setDirection(Direction direction) {
    this.direction = direction;
    findWord(direction);
  }

  /**
   * Returns the placed word.
   * 
   * @return word.
   */
  public String getWord() {
    return word;
  }

  /**
   * Sets the current word.
   * 
   * @param word that was being set.
   */
  public void setWord(String word) {
    this.word = word;
  }

  /**
   * Returns the start positiong from the placed word.
   * 
   * @return start position.
   */
  public String getStartPosition() {
    return Gameboard.computeSquareId(startRow, startCol);
  }

  /**
   * Finds the placed word by finding start position, direction first.
   */
  public void find() {
    findStart();
    findDirection();
    correctStart(this.direction);
    findWord(this.direction);
  }

  /**
   * Finds the placed word with the set direction.
   * 
   * @param direction of the word.
   */
  public void find(Direction direction) {
    findStart();
    correctStart(direction);
    findWord(direction);
  }

  /**
   * Finds the direction of the played word.
   */
  private void findDirection() {
    int total = 0;

    for (int i = 0; i < playedTiles.length; i++) {
      for (int j = 0; j < playedTiles.length; j++) {
        if (playedTiles[i][j]) {
          total++;
        }
      }
    }

    if (total == 1) {
      if (startRow > 0 && !board[startRow - 1][startCol].isEmpty()) {
        direction = Direction.VERTICAL;
        return;
      }
      if (startCol > 0 && !board[startRow][startCol - 1].isEmpty()) {
        direction = Direction.HORIZONTAL;
        return;
      }
      if (startRow < Gameboard.ROWS - 1 && !board[startRow + 1][startCol].isEmpty()) {
        direction = Direction.VERTICAL;
        return;
      }
      if (startCol < Gameboard.COLS - 1 && !board[startRow][startCol + 1].isEmpty()) {
        direction = Direction.HORIZONTAL;
        return;
      }
    }

    for (int i = 0; i < playedTiles.length; i++) {
      int count = 0;
      for (int j = 0; j < playedTiles.length; j++) {
        if (playedTiles[i][j]) {
          count++;
        }
      }
      if (count > 1) {
        direction = Direction.HORIZONTAL;
        return;
      }
    }
    direction = Direction.VERTICAL;
  }

  /**
   * Finds the index for the current first placed letter.
   */
  private void findStart() {
    for (int i = 0; i < playedTiles.length; i++) {
      for (int j = 0; j < playedTiles.length; j++) {
        if (playedTiles[i][j]) {
          startRow = i;
          startCol = j;
          return;
        }
      }
    }
  }

  /**
   * Corrects the start position if the new placed word appends to an already placed tile.
   * 
   * @param direction of the word.
   */
  private void correctStart(Direction direction) {
    if (direction == Direction.HORIZONTAL) {
      while (startCol >= 0 && !board[startRow][startCol].isEmpty()) {
        startCol--;
      }
      startCol++;
    } else {
      while (startRow >= 0 && !board[startRow][startCol].isEmpty()) {
        startRow--;
      }
      startRow++;
    }
  }

  /**
   * Finds the word with the direction and the start position.
   * 
   * @param direction of the word.
   */
  private void findWord(Direction direction) {
    word = "";
    if (direction == Direction.HORIZONTAL) {
      int j = startCol;
      while (j < playedTiles[startRow].length && !board[startRow][j].isEmpty()) {
        word += board[startRow][j].getLetter();
        j++;
      }
    } else {
      int i = startRow;
      while (i < playedTiles.length && !board[i][startCol].isEmpty()) {
        word += board[i][startCol].getLetter();
        i++;
      }
    }
  }

  /**
   * String output for test purposes,
   */
  public String toString() {
    return "Startposition: " + getStartPosition() + "\nDirection: " + getDirection() + "\nWord: "
        + getWord();
  }
}
