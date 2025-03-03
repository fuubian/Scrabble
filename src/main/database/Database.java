package main.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class represents the connection to the database that saves all player profiles and player
 * statistics.
 * 
 * @author frajwa
 * 
 */
public class Database {

  /**
   * Connection to database.
   */
  private Connection con;

  /**
   * Constructor for the database. Creates the connection to database.
   */
  public Database() {
    try {
      Class.forName("org.sqlite.JDBC");
      this.con = DriverManager.getConnection("jdbc:sqlite:res/database/PlayerProfiles.db");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a profile to the database.
   * 
   * @param name username
   * @param password secret password of profile
   */
  public void addProfile(String name, String password) {
    try {
      Statement stm = this.con.createStatement();
      stm.executeUpdate("INSERT INTO Profiles (Name, Password) " + "VALUES ('" + name + "', '"
          + password + "');");
      stm.execute("INSERT INTO NetworkStatistics (Name, Played, Wins, Last, Highscore, Points, "
          + "Word, Messages, Moves, Movetime) VALUES ('" + name + "', 0, 0, 0, 0, 0, 0, 0, 0, 0);");
      stm.execute("INSERT INTO TutorialStatistics (Name, Played, Wins, Last, Highscore, Points, "
          + "Word) VALUES ('" + name + "', 0, 0, 0, 0, 0, 0);");
      stm.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Updates the name of a profile in the database.
   * 
   * @param oldName the old name of a profile
   * @param newName the new name of a profile
   */
  public void editProfileName(String oldName, String newName) {
    try {
      Statement stm = this.con.createStatement();
      stm.executeUpdate("UPDATE Profiles SET Name='" + newName + "' WHERE Name='" + oldName + "';");
      stm.executeUpdate("UPDATE NetworkStatistics SET Name='" + newName + "' WHERE Name='" + oldName + "';");
      stm.executeUpdate("UPDATE TutorialStatistics SET Name='" + newName + "' WHERE Name='" + oldName + "';");
      stm.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Updates the password of a profile in the database.
   * 
   * @param name username
   * @param newPassword new password of the profile
   */
  public void editProfilePassword(String name, String newPassword) {
    try {
      Statement stm = this.con.createStatement();
      stm.executeUpdate(
          "UPDATE Profiles SET Password='" + newPassword + "' WHERE Name='" + name + "';");
      stm.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Updates the network statistics of a profile in the database.
   * 
   * @param name username
   * @param stats object that contains all statistics
   */
  public void updateProfileStatsNetwork(String name, int[] stats) {
    try {
      Statement stm = this.con.createStatement();
      stm.executeUpdate("UPDATE NetworkStatistics SET Played=" + stats[0] + ", Wins="
          + stats[1] + ", Last=" + stats[2] + ", Highscore=" + stats[3] + ", Points=" 
          + stats[4] + ", Word=" + stats[5] + ", Messages="  + stats[6] + ", Moves=" 
          + stats[7] + ", Movetime=" + stats[8] + " WHERE Name='" + name + "';");
      stm.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Updates the tutorial statistics of a profile in the database.
   * 
   * @param name username
   * @param stats object that contains all statistics
   */
  public void updateProfileStatsTutorial(String name, int[] stats) {
    try {
      Statement stm = this.con.createStatement();
      stm.executeUpdate("UPDATE NetworkStatistics SET Played=" + stats[0] + ", Wins="
          + stats[1] + ", Last=" + stats[2] + ", Highscore=" + stats[3] + ", Points=" 
          + stats[4] + ", Word=" + stats[5] + " WHERE Name='" + name + "';");
      stm.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deletes a profile of the database.
   * 
   * @param name username
   */
  public void deleteProfile(String name) {
    try {
      Statement stm = this.con.createStatement();
      stm.executeUpdate("DELETE FROM Profiles WHERE Name='" + name + "';");
      stm.executeUpdate("DELETE FROM NetworkStatistics WHERE Name='" + name + "';");
      stm.executeUpdate("DELETE FROM TutorialStatistics WHERE Name='" + name + "';");
      stm.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks if a profile with a given name exists.
   * 
   * @param name username that is checked
   * @return boolean that indicates if the profile exists
   */
  public boolean checkName(String name) {
    boolean check = false;

    try {
      Statement stm = this.con.createStatement();
      ResultSet rs = stm.executeQuery("SELECT Name FROM Profiles WHERE Name='" + name + "';");
      check = rs.next();
      rs.close();
      stm.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return check;
  }

  /**
   * Reads a player's password from the database.
   * 
   * @param name username
   * @return found password
   */
  public String getPassword(String name) {
    String password = null;

    try {
      Statement stm = this.con.createStatement();
      ResultSet rs = stm.executeQuery("SELECT Password FROM Profiles WHERE Name='" + name + "';");
      password = rs.getString(1);
      rs.close();
      stm.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return password;
  }

  /**
   * Reads a player's network statistics from the database.
   * 
   * @param name username
   * @return array of network statistics
   */
  public int[] getNetworkStats(String name) {
    int[] netStats = new int[9];

    try {
      Statement stm = this.con.createStatement();
      ResultSet rs = stm.executeQuery("SELECT Played, Wins, Last, Highscore, "
          + "Points, Word, Messages, Moves, Movetime FROM NetworkStatistics WHERE Name='" + name + "';");
      for (int i = 0; i < netStats.length; i++) {
        netStats[i] = rs.getInt(i + 1);
      }
      rs.close();
      stm.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return netStats;
  }

  /**
   * Reads a player's network statistics from the database.
   * 
   * @param name username
   * @return array of tutorial statistics
   */
  public int[] getTutorialStats(String name) {
    int[] tutStats = new int[6];

    try {
      Statement stm = this.con.createStatement();
      ResultSet rs = stm.executeQuery("SELECT Played, Wins, Last, Highscore, "
          + "Points, Word FROM TutorialStatistics WHERE Name='" + name + "';");
      for (int i = 0; i < tutStats.length; i++) {
        tutStats[i] = rs.getInt(i + 1);
      }
      rs.close();
      stm.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return tutStats;
  }

  /**
   * Closes the connection to the database.
   */
  public void disconnect() {
    try {
      this.con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
