package main.controller;

import main.database.Database;
import main.database.Profile;
import main.gui.ChangeDialogBox;
import main.gui.ErrorBox;
import main.gui.LoginPage;
import main.gui.PlayerProfile;

/**
 * Controller class for the database.
 * 
 * 
 * @author lknothe
 *
 */

public class DatabaseController {

  /**
   * Indicates whether username and password are correct while logging in.
   */

  public static boolean login = false;

  /**
   * Add a created profile to the database.
   * 
   * @param lp LoginPage object
   */

  public static void createProfile(LoginPage lp) {

    String pw1 = lp.getPassword().getText();
    String pw2 = lp.getRepeatPassword().getText();

    if (pw1.equals(pw2)) {
      if (pw1.isBlank() || lp.getUsername().getText().isBlank()) {
        lp.getErrorBox().displayError("Username/Password must not be empty.");
      } else {
        Database db = new Database();
        if (!db.checkName(lp.getUsername().getText())) {
          db.addProfile(lp.getUsername().getText(), pw1);
          Profile.setName(lp.getUsername().getText());
          login = true;
        } else {
          lp.getErrorBox().displayError("Username already exists.");
        }

        db.disconnect();
      }
    } else {
      lp.getErrorBox().displayError("Passwords do not match.");
    }


  }

  /**
   * Check if the login details are correct.
   * 
   * @param lp LoginPage object
   */

  public static void login(LoginPage lp) {
    String un = lp.getUsername().getText();
    String pw = lp.getPassword().getText();


    Database db = new Database();
    if (db.checkName(un)) {
      if (db.getPassword(un).equals(pw)) {
        login = true;
        Profile.setName(un);
      } else {
        lp.getErrorBox().displayError("Password incorrect.");
      }
    } else {
      lp.getErrorBox().displayError("Username non-existent.");
    }
    db.disconnect();
  }

  /**
   * Update the username.
   * 
   * @param cdb ChangeDialogBox object
   */

  public static void updateUsername(ChangeDialogBox cdb) {

    if (cdb.getUsername().equals(Profile.getName())) {
      ErrorBox error = new ErrorBox();
      error.displayError("Bro?");
    } else {
      Database db = new Database();
      if (!db.checkName(cdb.getUsername())) {
        db.editProfileName(Profile.getName(), cdb.getUsername());
        Profile.setName(cdb.getUsername());
      } else {
        ErrorBox error = new ErrorBox();
        error.displayError("Username already exists.");
      }
      db.disconnect();

    }

  }

  /**
   * Update Password.
   * 
   * @param cdb ChangeDialogBox object
   */

  public static void updatePassword(ChangeDialogBox cdb) {
    Database db = new Database();
    db.editProfilePassword(Profile.getName(), cdb.getPassword());
    db.disconnect();
  }

  /**
   * Delete Profile from the database.
   * 
   */

  public static void deleteProfile() {
    Database db = new Database();
    login = false;
    db.deleteProfile(Profile.getName());
    db.disconnect();
  }

  /**
   * Sets the statistics.
   * 
   * @param pp PlayerProfile object
   */

  public static void setStatistics(PlayerProfile pp) {
    String[] networkStats = Profile.getNetworkStats().getStats();
    String[] tutorialStats = Profile.getTutorialStats().getStats();

    for (int i = 0; i < networkStats.length; i++) {
      pp.setNetStats(i + 1, networkStats[i]);
      pp.setTutStats(i + 1, tutorialStats[i]);
    }

    pp.setCategoryStats(1, "Games Played");
    pp.setCategoryStats(2, "Wins");
    pp.setCategoryStats(3, "Win%");
    pp.setCategoryStats(4, "Last Place%");
    pp.setCategoryStats(5, "Highscore");
    pp.setCategoryStats(6, "Avg. Score");
    pp.setCategoryStats(7, "Highscore (Word)");
    pp.setCategoryStats(8, "Messages Typed");
    pp.setCategoryStats(9, "Avg. Move Time");
  }

  /**
   * Returns the password of a user.
   * 
   * @param userName user
   * @return password
   */
  public static String getPassword(String userName) {
    Database db = new Database();
    String result = db.getPassword(userName);
    db.disconnect();
    return result;
  }

  /**
   * Returns boolean if player is logged in.
   * 
   * @return if logged in
   */

  public static boolean isLogin() {
    return login;
  }

  /**
   * Set if logged in or out.
   * 
   * @param login boolean
   */

  public static void setLogin(boolean login) {
    DatabaseController.login = login;
  }

}
