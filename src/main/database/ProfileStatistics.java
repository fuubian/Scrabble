package main.database;

/**
 * This class saves all statistics of the player
 * who is currently logged in.
 * 
 * @author frajwa
 * 
 */
public abstract class ProfileStatistics {

  /**
   * Array of all statistics for one mode.
   */
  protected int[] stats;
  
  /**
   * Constructor that initializes the statistics.
   * @param stats of the game mode.
   */
  public ProfileStatistics(int[] stats) { 
    this.stats = stats;
  }

  /**
   * Transforms the integer database data into the statistics for the network mode.
   * 
   * @return array of network statistics
   */
  public abstract String[] getStats();

  /**
   * This method transfers the current data of the game mode into the database.
   */
  public abstract void updateStats();

  /**
   * Increases the amount of games played in one game mode.
   */
  public void increaseGamesPlayed() {
    this.stats[0]++;
  }

  /**
   * Increases the amount of wins in one game mode.
   */
  public void increaseWins() {
    this.stats[1]++;
  }

  /**
   * Increases the amount of games where the player was the worst player.
   */
  public void increaseLastPlace() {
    this.stats[2]++;
  }

  /**
   * Checks if the old high score was improved and updates it.
   * 
   * @param points points that were reached in one game
   */
  public void testHighscore(int points) {
    if (this.stats[3] < points) {
      this.stats[3] = points;
    }
  }

  /**
   * Increases the overall points reached in a game mode.
   * 
   * @param points points reached in one game
   */
  public void increasePoints(int points) {
    this.stats[4] += points;
  }

  /**
   * Checks if the old word highscore was improved and updates it.
   * 
   * @param points points of the word
   */
  public void testBestWord(int points) {
    if (this.stats[5] < points) {
      this.stats[5] = points;
    }
  }
}
