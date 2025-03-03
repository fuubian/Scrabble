package main.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import main.model.GameState;
import main.model.Scrabble;

/**
 * Represents a countdown timer.
 * 
 * @author ceho
 * @author sekeller
 *
 */

public class CountdownTimer {
  /**
   * Milliseconds per second.
   */
  private static final int MILLISECONDS_PER_SECOND = 1000;

  /**
   * Seconds per minute.
   */
  private static final int SECONDS_PER_MINUTE = 60;

  /**
   * Label on which the countdown timer is going to be displayed.
   */
  private Label timerLabel;

  /**
   * Timeline object for the animation.
   */
  private Timeline timeline;

  /**
   * The Scrabble game object.
   */
  private Scrabble game;

  /**
   * The constructor of the CountdownTimer class. Initializes the Label.
   * 
   * @param label on which the timer is going to be displayed.
   */
  public CountdownTimer(Label label) {
    this.timerLabel = label;
  }

  /**
   * Sets the game object.
   * 
   * @param game object.
   */
  public void setGame(Scrabble game) {
    this.game = game;
  }

  /**
   * Starts the countdown timer.
   */
  public void start() {
    timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> printTime()));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  /**
   * Displays the remaining time on the timer label.
   */
  private void printTime() {
    if (game != null) {
      long time = game.getRemainingTime();
      if (time < 0) {
        time = 0;
      }

      if (game.getGameState() == GameState.GAME_OVER) {
        timeline.stop();
      }

      long sec = time / MILLISECONDS_PER_SECOND % SECONDS_PER_MINUTE;
      long min = time / MILLISECONDS_PER_SECOND / SECONDS_PER_MINUTE;

      String timeText = String.format("%02d:%02d", min, sec);

      timerLabel.setText(timeText);

      if (game.getGameState() == GameState.GAME_OVER) {
        new GameOverBox().displayGameOver(game.getPlayers(), game.getTotalTime());
      }
    }
  }
}
