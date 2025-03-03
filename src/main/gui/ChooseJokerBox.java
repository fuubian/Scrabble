package main.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Pop-up window to choose a letter for the joker.
 * 
 * @author ceho
 *
 */
public class ChooseJokerBox {
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
  private Stage dialog;

  /**
   * Base/background.
   */
  private Rectangle rect;

  /**
   * Label that displays information for player.
   */
  private Label infoLabel;

  /**
   * StackPane object for the rack.
   */
  private GridPane grid;

  /**
   * Label for the tile.
   */
  private Label tile;

  /**
   * The current letter on the joker tile.
   */
  private String letter;

  /**
   * ComboBox to choose a letter for the joker.
   */
  private ComboBox<Character> joker;

  /**
   * ErrorBox to display errors.
   */
  private ErrorBox error = new ErrorBox();

  /**
   * Flag to indicate whether user clicked on cancel or save.
   */
  // private boolean answer = true;

  /**
   * Button to close the popup window.
   */
  // private Button cancel;

  /**
   * Button to save the entered data and close the popup window.
   */
  private Button save;

  /**
   * Returns the word.
   * 
   * @return word.
   */
  public String getLetter() {
    return letter;
  }

  /**
   * Returns the answer of the user.
   * 
   * @return answer.
   */
  // public boolean getAnswer() {
  // return this.answer;
  // }

  /**
   * Initializes the base/background.
   */
  private void initRectangle() {
    rect = new Rectangle();
    rect.setHeight(width * 0.13889); // 200
    // rect.setWidth(width * 0.3125); // 450
    // rect.setWidth(boxWidth + 20);
    rect.setWidth(300);
    rect.setFill(Color.WHITE);
    rect.setStroke(GuiSettings.BLUE);
  }

  /**
   * Initializes the Label and sets the info that is going the be displayed.
   */
  private void initInfo() {
    infoLabel = new Label("Choose the letter for your joker.");
    infoLabel
        .setStyle("-fx-font-family: Futura;" + "-fx-font-size: 18px;" + "-fx-text-fill: #2e75b6;");
    infoLabel.setPrefSize(267, 27); // 280/30-width * 0.020833
    infoLabel.setAlignment(Pos.CENTER_LEFT);
  }


  private void initJoker() {
    grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    // rackGrid.setHgap(10);

    tile = new Label(letter);
    tile.setPrefSize(50, 45); // 40/40
    tile.setAlignment(Pos.CENTER);
    tile.setStyle(
        "-fx-font-family: Futura;" + "-fx-font-size: 25px;" + "-fx-background-color: #E988A6; "
            + "-fx-border-color: #A6A6A6;" + "-fx-text-fill: white;");
    grid.add(tile, 0, 0);

    if (tile.getText().equals("W") || tile.getText().equals("M") || tile.getText().equals("Q")) {
      tile.setPadding(new Insets(0, 0, 0, 2));
      tile.setAlignment(Pos.CENTER_LEFT);
    } else {
      tile.setPadding(new Insets(0, 20, 0, 0));
    }

    joker = new ComboBox<Character>();
    joker.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; "
        + "-fx-text-fill: transparent; " + "-fx-font-family: Futura; -fx-font-size: 16px;"
        + "-fx-text-base-color: transparent;");

    joker.setPrefHeight(45);
    joker.setMaxWidth(50);

    joker.setPromptText(letter);

    for (int j = 65; j < 91; j++) {
      Character c = Character.valueOf((char) j);
      joker.getItems().add(c);
    }

    grid.add(joker, 0, 0);
  }

  /**
   * Initializes and styles the buttons.
   */
  private void initButtons() {
    // cancel = new Button("Cancel");
    // cancel.setStyle("-fx-background-color: #ff605c;" + "-fx-text-fill: white;"
    // + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
    // + "-fx-background-radius: 15px;" + "-fx-font-size: 13px;");
    // cancel.setPrefSize(width * 0.069443, width * 0.020833); // 100/30

    save = new Button("Save");
    save.setStyle("-fx-background-color: #00ca4e;" + "-fx-text-fill: white;"
        + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
        + "-fx-background-radius: 15px;" + "-fx-font-size: 13px;");
    save.setPrefSize(width * 0.069443, width * 0.020833); // 100/30
  }

  /**
   * Sets all ActionEvents of the buttons.
   * 
   * @param type indicates whether it's a network game or a tutorial game.
   */
  private void setActionEvents() {
    save.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      public void handle(MouseEvent e) {
        if (tile.getText().equals("*")) {
          error.displayError("You have to set a letter for the joker.");
        } else {
          letter = tile.getText();
          // answer = true;
          dialog.close();
        }
      }
    });

    save.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        save.setStyle("-fx-background-color: #1AD75F;" + "-fx-text-fill: white;"
            + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
            + "-fx-background-radius: 15px;" + "-fx-font-size: 14px;");
      }
    });

    save.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        save.setStyle("-fx-background-color: #00ca4e;" + "-fx-text-fill: white;"
            + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
            + "-fx-background-radius: 15px;" + "-fx-font-size: 13px;");
      }
    });

    // cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
    // public void handle(MouseEvent e) {
    // if (tile.getText().equals("*")) {
    // // answer = false;
    // error.displayError("You have to set a letter for the joker.");
    // } else {
    // // answer = false;
    // answer = false;
    // dialog.close();
    // }
    // }
    // });
    //
    // cancel.setOnMouseEntered(new EventHandler<MouseEvent>() {
    // public void handle(MouseEvent me) {
    // cancel.setStyle("-fx-background-color: #FF7369;" + "-fx-text-fill: white;"
    // + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
    // + "-fx-background-radius: 15px;" + "-fx-font-size: 14px;");
    // }
    // });
    //
    // cancel.setOnMouseExited(new EventHandler<MouseEvent>() {
    // public void handle(MouseEvent me) {
    // cancel.setStyle("-fx-background-color: #ff605c;" + "-fx-text-fill: white;"
    // + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
    // + "-fx-background-radius: 15px;" + "-fx-font-size: 13px;");
    // }
    // });

    joker.setOnAction(e -> {
      tile.setText(Character.toString(joker.getValue()));

      if (tile.getText().equals("W") || tile.getText().equals("M") || tile.getText().equals("Q")) {
        tile.setPadding(new Insets(0, 0, 0, 2));
        tile.setAlignment(Pos.CENTER_LEFT);
      } else {
        tile.setPadding(new Insets(0, 20, 0, 0));
        tile.setAlignment(Pos.CENTER);
      }
    });
  }

  /**
   * Initializes the Stage and sets the Scene.
   */
  private void initStage() {
    dialog = new Stage();
    dialog.setTitle("Joker");
    dialog.initModality(Modality.APPLICATION_MODAL);


    dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        System.out.println("dialog close");
      }
    });

    GridPane pane = new GridPane();
    pane.setAlignment(Pos.CENTER);
    pane.setVgap(width * 0.01389); // 20
    pane.add(infoLabel, 0, 0, 2, 1);
    pane.add(grid, 0, 1, 2, 1);
    // pane.add(cancel, 0, 2);
    pane.add(save, 1, 2);

    GridPane.setHalignment(save, HPos.RIGHT);

    StackPane root = new StackPane();
    // rect.setWidth(boxWidth + 20);
    root.getChildren().add(rect);
    root.getChildren().add(pane);

    Scene dialogScene;
    // dialogScene = new Scene(root, boxWidth + 30, width * 0.14583);
    dialogScene = new Scene(root, 310, width * 0.14583);
    // 460/210; width * 0.31944/ width * 0.14583

    dialog.setScene(dialogScene);
    dialog.showAndWait();
  }

  /**
   * Displays the pop-up window.
   * 
   * @param letter the current letter on the joker.
   */
  public void displayJoker(String letter) {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    width = gd.getDisplayMode().getWidth();
    // height = gd.getDisplayMode().getHeight();

    this.letter = letter;
    // boxWidth = 0;

    initInfo();
    initJoker();
    initRectangle();
    initButtons();
    setActionEvents();
    initStage();
  }
}
