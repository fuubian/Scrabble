package main.database;

import java.text.DecimalFormat;

/**
 * This class saves all network statistics of the player
 * who is currently logged in.
 * 
 * @author frajwa
 *
 */
public class NetworkStatistics extends ProfileStatistics {
  
  /**
   * Constructor for network statistics.
   * @param stats statistics of the network
   */
  public NetworkStatistics(int[] stats) { 
    super(stats);
  }
  
  /**
   * Transforms the integer database data into the statistics for the network mode.
   * 
   * @return array of network statistics
   */
  public String[] getStats() {
    DecimalFormat df = new DecimalFormat("0.0 %");
    DecimalFormat df2 = new DecimalFormat("0.0");
    String[] statsData = new String[9];

    statsData[0] = Integer.toString(this.stats[0]);
    statsData[1] = Integer.toString(this.stats[1]);
    statsData[2] = this.stats[0] != 0 ? df.format((double) this.stats[1]
        / (double) this.stats[0]) : df.format(0);
    statsData[3] = this.stats[0] != 0 ? df.format((double) this.stats[2]
        / (double) this.stats[0]) : df.format(0);
    statsData[4] = Integer.toString(this.stats[3]);
    statsData[5] = this.stats[0] != 0 ? df2.format((double) this.stats[4]
        / (double) this.stats[0]) : df2.format(0);
    statsData[6] = Integer.toString(this.stats[5]);
    statsData[7] = Integer.toString(this.stats[6]);
    int avgSeconds = this.stats[7] != 0 ? this.stats[8] / this.stats[7] : 0;
    statsData[8] = Integer.toString(avgSeconds / 60) + ":" + new DecimalFormat("00").format(avgSeconds % 60);

    return statsData;
  }
  
  /**
   * This method transfers the current data of the network mode into the database.
   */
  public void updateStats() {
    Database db = new Database();
    db.updateProfileStatsNetwork(Profile.getName(), this.stats);
    db.disconnect();
  }
  
  /**
   * Increments the amount of sent messages.
   */
  public void increaseMessagesSent() {
    this.stats[6]++;
  }
  
  /**
   * Increments the amount of moves in a network game.
   */
  public void increaseMoves() {
    this.stats[7]++;
  }

  /**
   * Increases the overall move time.
   * 
   * @param time in seconds
   */
  public void increaseMoveTime(int time) {
    this.stats[8] += time;
  }
}
