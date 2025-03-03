package main.model;

/**
 * The SquareType class represents the type of a square.
 * 
 * @author sekeller
 */
public enum SquareType {

  /**
   * Standard.
   */
  STD(1, 1),

  /**
   * Double letter score.
   */
  DLS(2, 1),

  /**
   * Triple letter score.
   */
  TLS(3, 1),

  /**
   * Double word score.
   */
  DWS(1, 2),

  /**
   * Triple word score.
   */
  TWS(1, 3);

  /**
   * The factor for the letter score.
   */
  private final int letterFactor;

  /**
   * The factor for the word score.
   */
  private final int wordFactor;

  /**
   * Constructs a square type with the specified letter factor and word factor.
   * 
   * @param letterFactor the specified letter factor
   * @param wordFactor the specified word factor
   */
  private SquareType(int letterFactor, int wordFactor) {
    this.letterFactor = letterFactor;
    this.wordFactor = wordFactor;
  }

  /**
   * Returns the letter factor of this square type.
   * 
   * @return the letter factor of this square type
   */
  public int getLetterFactor() {
    return this.letterFactor;
  }

  /**
   * Returns the word factor of this square type.
   * 
   * @return the word factor of this square type
   */
  public int getWordFactor() {
    return this.wordFactor;
  }

}
