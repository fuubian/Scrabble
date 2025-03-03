package main.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Represents an error message.
 * 
 * @author ceho
 *
 */

public class ErrorBox {
  /**
   * Individual width of the screen the application is being displayed on.
   */
  private int width;

  /**
   * Individual height of the screen the application is being displayed on.
   */
  // private int height;

  /**
   * Stage object to display the scene.
   */
  private Stage stage;

  /**
   * Base/background for error text.
   */
  private Rectangle rect;

  /**
   * Label that displays error message for player.
   */
  private Label error;

  /**
   * Button to close the error-box.
   */
  private Button cancel;

  /**
   * Stores the error message that is being displayed.
   */
  private String errorMsg;

  /**
   * Returns the cancel button.
   * 
   * @return cancel.
   */
  public Button getOkButton() {
    return this.cancel;
  }

  /**
   * Initializes the base/background.
   */
  private void initRectangle() {
    rect = new Rectangle();
    rect.setHeight(width * 0.13194); // 190-(width * 0.07639)
    rect.setWidth(width * 0.25); // 360-width * 0.20833
    rect.setFill(Color.WHITE);
    rect.setStroke(GuiSettings.RED);
  }

  /**
   * Initializes the label and sets the error message that is going the be displayed.
   * 
   * @param message the error message.
   */
  private void setErrorMsg(String message) {
    error = new Label(message);
    error.setAlignment(Pos.CENTER_LEFT);
    error.setStyle("-fx-font-family: Futura;" + "-fx-font-size: 18px;" + "-fx-text-fill: #ff605c;");
    // error.setPrefSize(width * 0.19444, width * 0.020833); // 280/30
    error.setPrefWidth(width * 0.22221); // 320-width * 0.19444
    error.setWrapText(true);
  }

  /**
   * Initializes and styles the button.
   */
  private void initButtons() {
    cancel = new Button("OK");
    cancel.setStyle("-fx-background-color: #D9D9D9;" + "-fx-text-fill: black;"
        + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
        + "-fx-background-radius: 15px;" + "-fx-font-size: 13px;");
    cancel.setPrefWidth(width * 0.04861); // 70
    cancel.setPrefHeight(width * 0.020833); // 30
  }

  /**
   * Sets all ActionEvents of the button cancel.
   */
  private void setActionEvents() {
    cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        stage.close();
      }
    });

    cancel.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        cancel.setStyle("-fx-background-color: #E3E3E3;" + "-fx-text-fill: black;"
            + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
            + "-fx-background-radius: 15px;" + "-fx-font-size: 14px;");
      }
    });

    cancel.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        cancel.setStyle("-fx-background-color: #D9D9D9;" + "-fx-text-fill: black;"
            + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
            + "-fx-background-radius: 15px;" + "-fx-font-size: 13px;");
      }
    });
  }

  /**
   * Initializes the Stage and sets the Scene.
   */
  private void initStage() {
    stage = new Stage();
    stage.setTitle("Error");
    stage.initModality(Modality.APPLICATION_MODAL);

    setErrorMsg(errorMsg);

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setVgap(width * 0.006944); // 10
    grid.add(error, 0, 0);
    grid.add(cancel, 0, 1);
    GridPane.setHalignment(cancel, HPos.RIGHT);

    StackPane root = new StackPane();

    root.getChildren().add(rect);
    root.getChildren().add(grid);

    Scene scene;
    scene = new Scene(root, width * 0.25694, width * 0.13889);
    // 370/200-(width * 0.21528-width * 0.08334
    stage.setScene(scene);
    stage.showAndWait();
  }

  /**
   * Displays the error-box.
   * 
   * @param message ErrorMsg that is going to be displayed.
   */
  public void displayError(String message) {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    width = gd.getDisplayMode().getWidth();
    // height = gd.getDisplayMode().getHeight();

    errorMsg = message;
    initRectangle();
    initButtons();
    setActionEvents();
    initStage();
  }
}
