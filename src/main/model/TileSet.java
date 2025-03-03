package main.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

/**
 * The TileSet class represents a set of tiles.
 * 
 * @author sekeller
 */
public class TileSet {

  /**
   * The mapping for the the count and the score of a letter.
   */
  private final TreeMap<Character, CountScorePair> map;

  /**
   * Constructs a set of tiles with the specified set of letters.
   * 
   * @param letterSet the specified set of letters
   */
  public TileSet() {
    this.map = new TreeMap<>();
  }

  /**
   * Returns the count of the specified letter.
   * 
   * @param letter the specified letter
   * @return the count of the specified letter
   */
  public int getCount(char letter) {
    return this.map.get(letter).getCount();
  }

  /**
   * Returns the count of the specified letter.
   * 
   * @param letter the specified letter
   * @return the count of the specified letter
   */
  public int getScore(char letter) {
    return this.map.get(letter).getScore();
  }

  /**
   * Sets the count of the specified letter to the specified count.
   * 
   * @param letter the specified letter
   * @param count the specified count
   */
  public void setCount(char letter, int count) {
    if (!this.map.containsKey(letter)) {
      this.map.put(letter, new CountScorePair());
    }

    this.map.get(letter).setCount(count);
  }

  /**
   * Sets the score of the specified letter to the specified score.
   * 
   * @param letter the specified letter
   * @param score the specified score
   */
  public void setScore(char letter, int score) {
    if (!this.map.containsKey(letter)) {
      this.map.put(letter, new CountScorePair());
    }

    if (letter != '*') {
      this.map.get(letter).setScore(score);
    }
  }

  /**
   * Sets the count and the score of the specified letter to the specified count and the specified
   * score.
   * 
   * @param letter the specified letter
   * @param count the specified count
   * @param score the specified score
   */
  public void set(char letter, int count, int score) {
    this.setCount(letter, count);
    this.setScore(letter, score);
  }

  /**
   * Returns a collection of the tiles which are specified by this tile set object.
   * 
   * @return a collection of the tiles which are specified by this tile set object
   */
  // TODO
  // rename createTiles
  public Collection<Tile> create() {
    Collection<Tile> tiles = new ArrayList<Tile>();

    for (Character letter : this.map.keySet()) {
      int count = this.getCount(letter);
      int score = this.getScore(letter);

      for (int i = 0; i < count; i++) {
        Tile tile = new Tile(letter, score);
        tiles.add(tile);
      }
    }

    return tiles;
  }

  /**
   * Returns the standard tile set for the English version of this game.
   * 
   * @return the standard tile set for the English version of this game
   */
  public static TileSet getStandard() {
    TileSet factory = new TileSet();

    factory.set('*', 2, 0);
    factory.set('A', 9, 1);
    factory.set('B', 2, 3);
    factory.set('C', 2, 3);
    factory.set('D', 4, 2);
    factory.set('E', 12, 1);
    factory.set('F', 2, 4);
    factory.set('G', 3, 2);
    factory.set('H', 2, 4);
    factory.set('I', 9, 1);
    factory.set('J', 1, 8);
    factory.set('K', 1, 5);
    factory.set('L', 4, 1);
    factory.set('M', 2, 3);
    factory.set('N', 6, 1);
    factory.set('O', 8, 1);
    factory.set('P', 2, 3);
    factory.set('Q', 1, 10);
    factory.set('R', 6, 1);
    factory.set('S', 4, 1);
    factory.set('T', 6, 1);
    factory.set('U', 4, 1);
    factory.set('V', 2, 4);
    factory.set('W', 2, 4);
    factory.set('X', 1, 8);
    factory.set('Y', 2, 4);
    factory.set('Z', 1, 10);

    return factory;
  }

  /**
   * The CountScorePair class represents a pair of count and score.
   */
  private class CountScorePair {

    /**
     * The count of the count score pair.
     */
    private int count;

    /**
     * The score of the count score pair.
     */
    private int score;

    /**
     * Constructs a pair of a count and a pair.
     */
    public CountScorePair() {
      this.count = 0;
      this.score = 0;
    }

    /**
     * Returns the count of this object.
     * 
     * @return the count of this object
     */
    public int getCount() {
      return this.count;
    }

    /**
     * Returns the score of this object.
     * 
     * @return the score of this object
     */
    public int getScore() {
      return this.score;
    }

    /**
     * Sets the count to the specified count.
     * 
     * @param count the specified count
     */
    public void setCount(int count) {
      this.count = count;
    }

    /**
     * Sets the score to the specified score.
     * 
     * @param score the specified score
     */
    public void setScore(int score) {
      this.score = score;
    }

  }

}
