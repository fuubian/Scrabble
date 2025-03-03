package main.gui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class to start the application.
 * 
 * @author ceho
 *
 */

public class Main extends Application {
  /**
   * GuiController object to control the scenes.
   */
  AltGuiController controller;

  /**
   * Start method that starts the controller.
   * 
   * @param primaryStage the primary Stage that the scene is set on.
   */
  @Override
  public void start(Stage primaryStage) {
    controller = new AltGuiController(primaryStage);
    controller.start(primaryStage);
  }

  /**
   * Main method to start the application.
   * 
   * @param args: the arguments for javafx.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
