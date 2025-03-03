package main.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class represents the option pane with with the application can be closed.
 * 
 * @author raydin
 *
 */
public class AlertBox {
  /**
   * Individual width of the screen the application is being displayed on.
   */
  private int width;

  /**
   * The StackPane object of this class.
   */
  private StackPane root;

  /**
   * The stage object to display the scene.
   */
  private Stage stage;

  /**
   * The base/background for alert text.
   */
  private Rectangle rect;

  /**
   * The alert message, can be edited based on alert type.
   */
  private String alert;

  /**
   * The label that constaints the message for the user.
   */
  private Label alertLabel;

  /**
   * The buttons with which the user can exit the alert-box.
   */
  private Button proceed;

  /**
   * The button with which the user can close the application.
   */
  private Button cancel;

  /**
   * HBox which stores cancel and proceed in a row.
   */
  private HBox buttons;

  /**
   * The flag to decide if the application is going to be closed.
   */
  private boolean answer = false;

  /**
   * Returns the proceed Button-object.
   * 
   * @return the proceed-Button object.
   */
  public Button getProceed() {
    return proceed;
  }

  /**
   * Returns cancel Button-object.
   * 
   * @return the cancel-Button object.
   */
  public Button getCancel() {
    return this.cancel;
  }

  /**
   * Returns the answer of the user.
   * 
   * @return the answer of the user.
   */
  public boolean getAnswer() {
    return this.answer;
  }

  /**
   * Returns the Stage object of this class.
   * 
   * @return the Stage object of this class.
   */
  public Stage getStage() {
    return this.stage;
  }

  /**
   * Returns the StackPane object of this class.
   * 
   * @return the StackPane object of this class.
   */
  public StackPane getStackPane() {
    return this.root;
  }

  /**
   * Initializes the base/background.
   */
  public void initRectangle() {
    rect = new Rectangle();
    rect.setHeight(width * 0.104167); // 150
    rect.setWidth(width * 0.27778); // 400
    rect.setFill(Color.WHITE);
    rect.setStroke(GuiSettings.BLUE);
  }

  /**
   * Sets the message that is going to be displayed.
   */
  public void setAlert() {
    alertLabel = new Label(alert);
    // alertLabel.setPrefSize(width * 0.26389, width * 0.027778); // 380/40
    alertLabel.setPrefWidth(width * 0.26389);
    alertLabel.setAlignment(Pos.CENTER);
    alertLabel.setStyle("-fx-text-fill: #2e75b6; -fx-font-family: Futura; "
        + "-fx-font-weight: bold; -fx-font-size: 18px; -fx-background-radius: 10px;");
    alertLabel.setWrapText(true);
  }

  /**
   * Initializes the Button-objects and sets their Event-Handlers.
   */
  public void initButtons() {

    proceed = new Button("Yes");
    cancel = new Button("Cancel");

    proceed.setStyle("-fx-background-color: #00ca4e; -fx-text-fill: white; "
        + "-fx-font-weight: bold; -fx-font-family: Futura; -fx-border-radius: 15px; "
        + "-fx-background-radius: 15px; -fx-font-size: 13px;");

    proceed.setPrefSize(width * 0.069443, width * 0.020833); // 100/30

    cancel.setStyle("-fx-background-color: #ff605c; -fx-text-fill: white; "
        + "-fx-font-weight: bold; -fx-font-family: Futura; -fx-border-radius: 15px; "
        + "-fx-background-radius: 15px; -fx-font-size: 13px;");

    cancel.setPrefSize(width * 0.069443, width * 0.020833); // 100/30

    buttons = new HBox();
    buttons.setSpacing(width * 0.05556); // 80
    buttons.setAlignment(Pos.CENTER);
    buttons.getChildren().add(proceed);
    buttons.getChildren().add(cancel);
  }

  /**
   * Sets all ActionEvents for the buttons cancel and proceed.
   */
  public void setActionEvents() {
    proceed.setOnAction(actionEvent -> {
      answer = true;
      stage.close();
    });

    cancel.setOnAction(actionEvent -> {
      answer = false;
      stage.close();
    });

    proceed.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        proceed.setStyle("-fx-background-color: #1AD75F; -fx-text-fill: white; "
            + "-fx-font-weight: bold; -fx-font-family: Futura; -fx-border-radius: 15px; "
            + "-fx-background-radius: 15px; -fx-font-size: 14px;");
      }
    });

    proceed.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        proceed.setStyle("-fx-background-color: #00ca4e; -fx-text-fill: white; "
            + "-fx-font-weight: bold; -fx-font-family: Futura; -fx-border-radius: 15px; "
            + "-fx-background-radius: 15px; -fx-font-size: 13px;");
      }
    });

    cancel.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        cancel.setStyle("-fx-background-color: #FF7369; -fx-text-fill: white; "
            + "-fx-font-weight: bold; -fx-font-family: Futura; -fx-border-radius: 15px; "
            + "-fx-background-radius: 15px; -fx-font-size: 14px;");
      }
    });

    cancel.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        cancel.setStyle("-fx-background-color: #ff605c; -fx-text-fill: white; "
            + "-fx-font-weight: bold; -fx-font-family: Futura; "
            + "-fx-border-radius: 15px; -fx-background-radius: 15px; -fx-font-size: 13px;");
      }
    });
  }

  /**
   * Initializes the Stage and sets the Scene.
   */
  public void initStage() {

    stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Alert");

    setAlert();

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setVgap(width * 0.01389); // 10-width * 0.006944/20
    grid.add(alertLabel, 0, 0);
    grid.add(buttons, 0, 1);

    root = new StackPane();
    root.setAlignment(Pos.CENTER);

    root.getChildren().add(rect);
    root.getChildren().add(grid);

    Scene scene = new Scene(root, width * 0.28472, width * 0.11111); // 410/160

    stage.setScene(scene);
    stage.showAndWait();
  }

  /**
   * Displays the alert-Window.
   * 
   * @param alertMsg the alert message-text on the alert box.
   */
  public void displayAlert(String alertMsg) {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    width = gd.getDisplayMode().getWidth();

    answer = false;

    alert = alertMsg;
    initRectangle();
    initButtons();
    setActionEvents();
    initStage();
  }
}
