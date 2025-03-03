package main.network.message;

/**
 * Message for all lobby details.
 * 
 * @author lknothe
 *
 */

public class LobbyDetailsMessage extends Message {

  /**
   * Default serial version.
   */

  private static final long serialVersionUID = 1L;

  /**
   * Names of the players in the lobby.
   */

  private String[] names;

  /**
   * Name of the lobby.
   */

  private String lobbyName;

  /**
   * Connection Details (ip-adress).
   */

  private String conDet;

  /**
   * Statistics.
   */

  private String[][] stats;

  /**
   * Players ready.
   */

  private boolean[] ready = {false, false, false};
  
  /**
   * Players that took place in the last game
   */
  private String[] gamePlayers;

  /**
   * Constructor.
   * 
   * @param type type of message
   * @param from origin of the message
   * @param names Names of the players
   * @param lobbyName Name of the lobby
   * @param connectionDetails connection details (IP)
   * @param statisitcs statistics
   * @param ready ready players
   */

  public LobbyDetailsMessage(MessageType type, String from, String[] names, String lobbyName,
      String connectionDetails, String[][] statistics, boolean[] ready, String[] gamePlayers) {
    super(type, from);
    this.names = names;
    this.lobbyName = lobbyName;
    this.conDet = connectionDetails;
    this.stats = statistics;
    this.ready = ready;
    this.gamePlayers = gamePlayers;
  }

  /**
   * Returns the names of the players connected.
   * 
   * @return names of the players
   */

  public String[] getNames() {
    return names;
  }

  /**
   * Returns lobbyname.
   * 
   * @return lobbyname
   */

  public String getLobbyName() {
    return lobbyName;
  }

  /**
   * Returns connection details.
   * 
   * @return connection details
   */

  public String getConDet() {
    return conDet;
  }

  /**
   * Returns stats.
   * 
   * @return stats String[][]
   */

  public String[][] getStats() {
    return stats;
  }

  /**
   * Returns whos ready.
   * 
   * @return boolean[]
   */
  public boolean[] getReady() {
    return ready;
  }
  
  public String[] getGamePlayers() {
    return this.gamePlayers;
  }

}
