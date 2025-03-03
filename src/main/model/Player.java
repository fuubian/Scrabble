package main.model;

import java.io.Serializable;

/**
 * The Player class represents a player.
 * 
 * @author sekeller
 */
public abstract class Player implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The name of this player.
   */
  protected final String name;

  /**
   * The player type of this player.
   */
  protected final PlayerType playerType;

  /**
   * The rack of this player.
   */
  protected final Rack rack;

  /**
   * The score of this player.
   */
  protected int score;

  /**
   * Constructs a player with the specified name and specified player type.
   * 
   * @param name the specified name
   * @param playerType the specified player type
   */
  public Player(String name, PlayerType playerType) {
    this.name = name;
    this.playerType = playerType;
    this.rack = new Rack();
    this.score = 0;
  }

  /**
   * Constructs a copy of the specified player.
   * 
   * @param player the specified player
   */
  Player(Player player) {
    this.name = player.name;
    this.playerType = player.playerType;
    this.rack = new Rack(player.rack);
    this.score = player.score;
  }

  /**
   * Returns the name of this player.
   * 
   * @return the name of this player
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the player type of this player.
   * 
   * @return the player type of this player
   */
  public PlayerType getPlayerType() {
    return this.playerType;
  }

  /**
   * Returns the rack of this player.
   * 
   * @return the rack of this player
   */
  public Rack getRack() {
    return this.rack;
  }

  /**
   * Returns the score of this player.
   * 
   * @return the score of this player
   */
  public int getScore() {
    return this.score;
  }

  /**
   * Adds the specified score to the score of this player.
   * 
   * @param score the specified score
   */
  void addScore(int score) {
    this.score += score;
  }

}
