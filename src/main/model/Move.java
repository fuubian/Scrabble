package main.model;

import java.io.Serializable;

/**
 * The Move class represents an abstract move.
 * 
 * @author sekeller
 */
public abstract class Move implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * A constant for the milliseconds per second.
   */
  private static final int MILLISECONDS_PER_SECOND = 1000;

  /**
   * A constant for the seconds per minute.
   */
  private static final int SECONDS_PER_MINUTE = 60;

  /**
   * The game in which the move is to be executed.
   */
  protected final Scrabble game;

  /**
   * The exception which is caused by the move.
   */
  protected GameException gameException;

  /**
   * The player of the move.
   */
  protected String player;

  /**
   * The time of the move in milliseconds.
   */
  protected long time;

  /**
   * Constructs a Move for the specified game.
   * 
   * @param game the specified game
   */
  Move(Scrabble game) {
    this.game = game;
    this.player = game.getCurrentPlayer().getName();
  }

  /**
   * Constructs a Move.
   */
  Move() {
    this.game = null;
  }

  /**
   * Returns true if this move is valid.
   * 
   * @return true if this move is valid
   */
  public boolean isValid() {
    return this.gameException == null;
  }

  /**
   * Returns the time of this move in milliseconds.
   * 
   * @return the time of this move in milliseconds
   */
  public long getTime() {
    return this.time;
  }

  /**
   * Returns the player of this move.
   * 
   * @return the player of this move
   */
  public String getPlayer() {
    return this.player;
  }

  /**
   * Sets the time to the specified time.
   * 
   * @param time the specified time
   */
  void setTime(long time) {
    this.time = time;
  }

  /**
   * Returns a formatted string of the time of this move.
   * 
   * @return a formatted string of the time of this move
   */
  public String getFormattedTimeString() {
    long mil = this.time % MILLISECONDS_PER_SECOND;
    long sec = this.time / MILLISECONDS_PER_SECOND % SECONDS_PER_MINUTE;
    long min = this.time / MILLISECONDS_PER_SECOND / SECONDS_PER_MINUTE;

    return String.format("%02d:%02d:%03d", min, sec, mil);
  }

  /**
   * Returns the score of this move.
   * 
   * @return the score of this move
   */
  public abstract int getScore();

  /**
   * Executes this move.
   * 
   * @throws GameException
   */
  abstract void execute() throws GameException;

}
