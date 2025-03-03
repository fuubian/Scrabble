package main.controller;

import java.text.DecimalFormat;
import main.database.NetworkStatistics;
import main.database.Profile;
import main.database.ProfileStatistics;
import main.model.Player;
import main.model.Scrabble;

/**
 * This class provides methods to read the data from games and to
 * update it into the database.
 * 
 * @author frajwa
 *
 */
public class StatisticController {
  
  /**
   * Indicates if the current game was already set into the database.
   */
  private static boolean updatesSet = false;
  
  /**
   * This method reads the data from a game and updates the statistics for the player.
   * 
   * @param game Scrabble game that was played
   * @param me the player
   * @param isNetwork boolean that shows if the game was a network game
   */
  public static void updateStats(Scrabble game, boolean isNetwork) {
    System.out.println("AUFRUF STATISTIK!");
    if (!StatisticController.updatesSet && game.getMoveCount() >= 5) {
      StatisticController.updatesSet = true;
      ProfileStatistics stats;
      
      if (isNetwork) {
        stats = Profile.getNetworkStats();
      } else {
        stats = Profile.getTutorialStats();
      }
      
      Player me = null;
      for (int i = 0; i < game.getPlayerCount(); i++) {
        if (game.getPlayer(i).getName().equals(Profile.getOnlineName())) {
          me = game.getPlayer(i);
          break;
        }
      }
      
      boolean firstPlace = true;
      boolean lastPlace = true;
      for (int i = 0; i < game.getPlayerCount(); i++) {
        if (!game.getPlayer(i).getName().equals(me.getName())) {
          if (game.getPlayer(i).getScore() > me.getScore()) {
            firstPlace = false;
          }
          if (game.getPlayer(i).getScore() < me.getScore()) {
            lastPlace = false;
          }
        }
      }
      stats.increaseGamesPlayed();
      if (firstPlace) {
        stats.increaseWins();
      }
      if (lastPlace) {
        stats.increaseLastPlace();
      }
      stats.testHighscore(me.getScore());
      stats.increasePoints(me.getScore());
      for (int i = 0; i < game.getMoveCount(); i++) {
        if (game.getMove(i).getPlayer().equals(me.getName())) {
          if (isNetwork) {
            stats.testBestWord(game.getMove(i).getScore());
            ((NetworkStatistics) stats).increaseMoves();
            ((NetworkStatistics) stats).increaseMoveTime((int) (game.getMove(i).getTime() / 1000));
          }
        }
      }
      stats.updateStats();
    }
  }

  /**
   * This method determines the statistics that shall be shown in the lobby.
   * 
   * @param game network game that was played
   * @param index number of the player
   * @return array of statistics
   */
  public static String[] getGameStats(Scrabble game, int index) {
    String[] stats = new String[4];
    
    Player me = game.getPlayer(index);
    
    stats[0] = Integer.toString(me.getScore());    
    double moves = 0;
    int maxScore = 0;
    long moveTime = 0;
    for (int i = 0; i < game.getMoveCount(); i++) {
      if(game.getMove(i).getPlayer().equals(me.getName())) {
        moves++;
        maxScore = game.getMove(i).getScore() > maxScore ? game.getMove(i).getScore() : maxScore;
        moveTime += game.getMove(i).getTime(); 
      }
    }
    stats[1] = Integer.toString(maxScore);
    stats[2] = new DecimalFormat("0.0").format(me.getScore() / moves);
    System.out.println(moveTime/1000 / moves);
    stats[3] = new DecimalFormat("0").format(moveTime / 1000 / moves / 60) + ":" +  new DecimalFormat("00").format((moveTime / moves / 1000) % 60);
    
    return stats;
  }
  
  /**
   * Resets the state of the StatisticController.
   */
  public static void resetState() {
    StatisticController.updatesSet = false;
  }
}
