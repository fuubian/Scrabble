package main.database;

/**
 * This class saves the username and the statistics of 
 * the player who is currently logged in.
 * 
 * @author frajwa
 * 
 */
public class Profile {

  /**
   * Username of the profile. Static variable.
   */
  private static String userName = "NoName";
  
  /**
   * Name that is shown during the online game. 
   * It can change if another player has the same name.
   */
  private static String onlineName = "NoName";
  
  /**
   * Network statistics of the profile. Static variable.
   */
  private static ProfileStatistics networkStats;
  
  /**
   * Tutorial statistics of the profile. Static variable.
   */
  private static ProfileStatistics tutorialStats;
  
  /**
   * Returns the username of the profile.
   * 
   * @return username
   */
  public static String getName() {
    return Profile.userName;
  }
  
  /**
   * Sets a new username and gets the statistics from the database.
   * 
   * @param name username
   */
  public static void setName(String name) {
    Profile.userName = name;
    Profile.onlineName = name;
    
    Database db = new Database();
    Profile.networkStats = new NetworkStatistics(db.getNetworkStats(userName));
    Profile.tutorialStats = new TutorialStatistics(db.getTutorialStats(userName));
    db.disconnect();
  }
  
  /**
   * Returns the network statistics of the profile.
   * 
   * @return object that contains all statistics for the network game
   */
  public static ProfileStatistics getNetworkStats() {
    return Profile.networkStats;
  }
  
  /**
   * Returns the tutorial statistics of the profile.
   * 
   * @return object that contains all statistics for the tutorial mode
   */
  public static ProfileStatistics getTutorialStats() {
    return Profile.tutorialStats;
  }

  /**
   * Returns the online name.
   * 
   * @return online name
   */
  public static String getOnlineName() {
    return onlineName;
  }

  /**
   * Sets a new online name.
   * 
   * @param new online new
   */
  public static void setOnlineName(String onlineName) {
    Profile.onlineName = onlineName;
  }
  
}
