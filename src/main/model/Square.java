package main.model;

import java.io.Serializable;

/**
 * The Square class represents a square on the game board.
 * 
 * @author sekeller
 */
public class Square implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The square type of this square.
   */
  private final SquareType squareType;

  /**
   * The tile placed on this square.
   */
  private Tile tile;

  /**
   * Constructs a square with the specified square type.
   * 
   * @param squareType the specified square type
   */
  Square(SquareType squareType) {
    this.squareType = squareType;
    this.tile = null;
  }

  /**
   * Constructs a copy of the specified square.
   * 
   * @param original the specified square
   */
  Square(Square original) {
    this.squareType = original.squareType;

    if (!original.isEmpty()) {
      this.tile = new Tile(original.getTile());
    }
  }

  /**
   * Returns the square type of this square.
   * 
   * @return the square type of this square
   */
  public SquareType getSquareType() {
    return this.squareType;
  }

  /**
   * Returns true if this square is empty.
   * 
   * @return true if this square is empty
   */
  public boolean isEmpty() {
    return this.tile == null;
  }

  /**
   * Returns the tile placed on this square. If the square is empty this method will return null.
   * 
   * @return the tile placed on this square
   */
  public Tile getTile() {
    return this.tile;
  }

  /**
   * Sets this tile to the specified tile.
   * 
   * @param tile the specified tile
   */
  void setTile(Tile tile) {
    this.tile = tile;
  }

}
