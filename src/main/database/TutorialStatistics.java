package main.database;

import java.text.DecimalFormat;

/**
 * This class saves all tutorial statistics of the player
 * who is currently logged in.
 * 
 * @author frajwa
 *
 */
public class TutorialStatistics extends ProfileStatistics {

  /**
   * Cunstructor for the statistics of the tutorial mode.
   * @param stats tutorial statistics from the database
   */
  public TutorialStatistics(int[] stats) {
    super(stats);
  }

  /**
   * Transforms the integer database data into the statistics for the tutorial mode.
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
    statsData[7] = "-";
    statsData[8] = "-";

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
}
