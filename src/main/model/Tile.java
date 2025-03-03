package main.model;

import java.io.Serializable;

/**
 * The Tile class represents a tile.
 * 
 * @author sekeller
 */
public class Tile implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The tile id for the next tile.
   */
  private static int NEXT_ID = 0;

  /**
   * The tile id of this tile.
   */
  private final int tileId;

  /**
   * The letter of this tile.
   */
  private char letter;

  /**
   * The score of this tile.
   */
  private final int score;

  /**
   * Constructs a tile with the specified letter and score.
   * 
   * @param letter the specified letter
   * @param score the specified score
   */
  Tile(char letter, int score) {
    this.letter = letter;
    this.score = score;

    this.tileId = NEXT_ID++;
  }

  /**
   * Constructs a copy of the specified tile.
   * 
   * @param original the specified tile
   */
  Tile(Tile original) {
    this.letter = original.letter;
    this.score = original.score;
    this.tileId = original.tileId;
  }

  /**
   * Sets the letter of this tile to the specified letter.
   * 
   * @param letter the specified letter
   */
  void setLetter(char letter) {
    this.letter = letter;
  }

  /**
   * Returns the letter of this tile.
   * 
   * @return the letter of this tile
   */
  public char getLetter() {
    return this.letter;
  }

  /**
   * Returns the score of this tile.
   * 
   * @return the score of this tile
   */
  public int getScore() {
    return this.score;
  }

  /**
   * Returns the tile id of this tile.
   * 
   * @return the tile id of this tile
   */
  int getTileId() {
    return this.tileId;
  }

  @Override
  public boolean equals(Object object) {
    return (object instanceof Tile) && (this.tileId == ((Tile) object).tileId);
  }

  @Override
  public String toString() {
    return Character.toString(this.letter) + "(" + Integer.toString(this.score) + ")";
  }

}
