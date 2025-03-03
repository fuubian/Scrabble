package main.model;

/**
 * The Difficulty class represents a difficulty for a computer player.
 * 
 * @author sekeller
 */
public enum Difficulty {

  /**
   * Easy difficulty.
   */
  EASY(0.10, 10, 20),

  /**
   * Hard difficulty.
   */
  HARD(0.00, 20, Integer.MAX_VALUE);

  /**
   * The rate for a pass move.
   */
  private double passRate;

  /**
   * The minimum score for a play word move.
   */
  private int minScore;

  /**
   * The maximum score for a play word move.
   */
  private int maxScore;

  /**
   * Constructs a difficulty with the specified pass rate and minimum score.
   * 
   * @param passRate the specified pass rate
   * @param minScore the specified minimum score
   */
  private Difficulty(double passRate, int minScore, int maxScore) {
    this.passRate = passRate;
    this.minScore = minScore;
    this.maxScore = maxScore;
  }

  /**
   * Returns the pass rate of this difficulty.
   * 
   * @return the pass rate of this difficulty
   */
  public double getPassRate() {
    return this.passRate;
  }

  /**
   * Returns the minimum score of this difficulty.
   * 
   * @return the minimum score of this difficulty
   */
  public int getMinScore() {
    return this.minScore;
  }

  /**
   * Returns the maximum score of this difficulty.
   * 
   * @return the maximum score of this difficulty
   */
  public int getMaxScore() {
    return this.maxScore;
  }

}
