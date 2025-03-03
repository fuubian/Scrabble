package main.model;

import java.io.Serializable;

/**
 * The TimeThread class represents the timer of a game.
 * 
 * @author sekeller
 */
class TimeThread extends Thread implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The game where the remaining time should be updated.
   */
  private final Scrabble game;

  /**
   * Constructs a time thread for the specified game.
   * 
   * @param game the specified game
   */
  public TimeThread(Scrabble game) {
    this.game = game;
  }

  @Override
  public void run() {
    long lastTime = System.currentTimeMillis();

    while (this.game.getGameState() == GameState.PLAY) {
      long currentTime = System.currentTimeMillis();
      long elapsedTime = currentTime - lastTime;
      this.game.updateRemainingTime(elapsedTime);
      lastTime = currentTime;

      try {
        sleep(10);
      } catch (InterruptedException interruptedException) {
      }
    }
  }

}
