package main.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Collection;
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
import main.model.GameException;
import main.model.Rack;
import main.model.Scrabble;
import main.model.Tile;

/**
 * Represents the pop-up window, when the player wants to exchange his tiles.
 * 
 * @author ceho
 *
 */

public class ExchangeTilesBox {
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
  private StackPane rackPane;

  /**
   * Labels for the seven tiles.
   */
  private Label[] tiles = new Label[Rack.TILE_CAPACITY];

  /**
   * Button to close the popup window.
   */
  private Button cancel;

  /**
   * Button to save the entered data and close the popup window.
   */
  private Button save;

  /**
   * Flag to indicate whether the tiles have been clicked or not.
   */
  private boolean[] clicked = {false, false, false, false, false, false, false};

  /**
   * The current Scrabble game object.
   */
  private Scrabble game;

  /**
   * Flag to indicate whether user clicked on save or on cancel button.
   */
  private boolean answer = false;

  /**
   * Return whether user clicked on cancel or save.
   * 
   * @return flag.
   */
  public boolean getAnswer() {
    return this.answer;
  }

  /**
   * Initializes the base/background.
   */
  private void initRectangle() {
    rect = new Rectangle();
    rect.setHeight(width * 0.13889); // 200
    rect.setWidth(width * 0.3125); // 450
    rect.setFill(Color.WHITE);
    rect.setStroke(GuiSettings.BLUE);
  }

  /**
   * Initializes the Label and sets the info that is going the be displayed.
   */
  private void initInfo() {
    infoLabel = new Label("Click the tiles you want to exchange.");
    infoLabel
        .setStyle("-fx-font-family: Futura;" + "-fx-font-size: 18px;" + "-fx-text-fill: #2e75b6");
    // infoLabel.setPrefSize(width * 0.19444, width * 0.020833); // 280/30
    infoLabel.setPrefHeight(width * 0.020833);
  }

  /**
   * Initializes and sets the panel on which the rack is going to be displayed.
   * 
   * @param r the current rack from the player.
   */
  private void initTiles(Rack r) {
    Rectangle rackRect = new Rectangle();
    rackRect.setWidth(width * 0.27778); // 400
    rackRect.setHeight(width * 0.038194); // 55
    rackRect.setArcWidth(30);
    rackRect.setArcHeight(30);
    rackRect.setFill(GuiSettings.GRAY_FILL);
    rackRect.setStroke(GuiSettings.GRAY_BORDER);

    for (int i = 0; i < Rack.TILE_CAPACITY; i++) {
      tiles[i] = new Label(Character.toString(r.getTile(i).getLetter()));
      tiles[i].setPrefSize(width * 0.027778, width * 0.027778); // 40/40
      tiles[i].setAlignment(Pos.CENTER);
      tiles[i].setStyle(
          "-fx-font-family: Futura;" + "-fx-font-size: 25px;" + "-fx-background-color: #2e75b6; "
              + "-fx-border-color: #A6A6A6;" + "-fx-text-fill: white;");
    }

    GridPane rackGrid = new GridPane();
    rackGrid.setAlignment(Pos.CENTER);
    rackGrid.setVgap(width * 0.010416); // 15
    rackGrid.setHgap(width * 0.010416); // 15
    rackGrid.add(tiles[0], 0, 0);
    rackGrid.add(tiles[1], 1, 0);
    rackGrid.add(tiles[2], 2, 0);
    rackGrid.add(tiles[3], 3, 0);
    rackGrid.add(tiles[4], 4, 0);
    rackGrid.add(tiles[5], 5, 0);
    rackGrid.add(tiles[6], 6, 0);

    rackPane = new StackPane();
    rackPane.getChildren().add(rackRect);
    rackPane.getChildren().add(rackGrid);
  }

  /**
   * Initializes and styles the buttons.
   */
  private void initButtons() {
    cancel = new Button("Cancel");
    cancel.setStyle("-fx-background-color: #ff605c;" + "-fx-text-fill: white;"
        + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
        + "-fx-background-radius: 15px;" + "-fx-font-size: 13px;");
    cancel.setPrefSize(width * 0.069443, width * 0.020833); // 100/30

    save = new Button("Save");
    save.setStyle("-fx-background-color: #00ca4e;" + "-fx-text-fill: white;"
        + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
        + "-fx-background-radius: 15px;" + "-fx-font-size: 13px;");
    save.setPrefSize(width * 0.069443, width * 0.020833); // 100/30
  }

  /**
   * Sets all ActionEvents of the buttons.
   */
  private void setActionEvents() {
    for (int i = 0; i < tiles.length; i++) {
      int indx = i;
      tiles[indx].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        public void handle(MouseEvent e) {
          if (!clicked[indx]) {
            clicked[indx] = true;
            tiles[indx].setStyle("-fx-font-family: Futura;" + "-fx-font-size: 25px;"
                + "-fx-background-color: #E988A6; " + "-fx-border-color: #A6A6A6;"
                + "-fx-text-fill: white;");
          } else {
            clicked[indx] = false;
            tiles[indx].setStyle("-fx-font-family: Futura;" + "-fx-font-size: 25px;"
                + "-fx-background-color: #2e75b6; " + "-fx-border-color: #A6A6A6;"
                + "-fx-text-fill: white;");
          }
        }
      });

      tiles[indx].setOnMouseEntered(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
          if (!clicked[indx]) {
            tiles[indx].setStyle("-fx-font-family: Futura;" + "-fx-font-size: 25px;"
                + "-fx-background-color: #2E8CCD; " + "-fx-border-color: #A6A6A6;"
                + "-fx-text-fill: white;");
          }
        }
      });

      tiles[indx].setOnMouseExited(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
          if (!clicked[indx]) {
            tiles[indx].setStyle("-fx-font-family: Futura;" + "-fx-font-size: 25px;"
                + "-fx-background-color: #2e75b6; " + "-fx-border-color: #A6A6A6;"
                + "-fx-text-fill: white;");
          }
        }
      });
    }

    save.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

      public void handle(MouseEvent e) {
        Collection<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < clicked.length; i++) {
          if (clicked[i]) {
            tiles.add(game.getCurrentPlayer().getRack().getTile(i));
          }
        }

        try {
          game.changeTiles(tiles);
          clicked = new boolean[] {false, false, false, false, false, false, false};
          answer = true;
          dialog.close();
        } catch (GameException e1) {
          ErrorBox error = new ErrorBox();
          error.displayError(e1.getMessage());
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

    cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      public void handle(MouseEvent e) {
        clicked = new boolean[] {false, false, false, false, false, false, false};
        answer = false;
        dialog.close();
      }
    });

    cancel.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        cancel.setStyle("-fx-background-color: #FF7369;" + "-fx-text-fill: white;"
            + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
            + "-fx-background-radius: 15px;" + "-fx-font-size: 14px;");
      }
    });

    cancel.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        cancel.setStyle("-fx-background-color: #ff605c;" + "-fx-text-fill: white;"
            + "-fx-font-weight: bold;" + "-fx-font-family: Futura;" + "-fx-border-radius: 15px;"
            + "-fx-background-radius: 15px;" + "-fx-font-size: 13px;");
      }
    });
  }

  /**
   * Initializes the Stage and sets the Scene.
   */
  private void initStage() {
    dialog = new Stage();
    dialog.setTitle("Exchange Tiles");
    dialog.initModality(Modality.APPLICATION_MODAL);

    GridPane pane = new GridPane();
    pane.setAlignment(Pos.CENTER);
    pane.setVgap(width * 0.01389); // 20
    pane.add(infoLabel, 0, 0, 2, 1);
    pane.add(rackPane, 0, 1, 2, 1);
    pane.add(cancel, 0, 2);
    pane.add(save, 1, 2);

    GridPane.setHalignment(save, HPos.RIGHT);

    StackPane root = new StackPane();

    root.getChildren().add(rect);
    root.getChildren().add(pane);

    Scene dialogScene;
    dialogScene = new Scene(root, width * 0.31944, width * 0.14583); // 460/210

    dialog.setScene(dialogScene);
    dialog.showAndWait();
  }

  /**
   * Displays the pop-up window.
   * 
   * @param game the current Scrabble game object.
   */
  public void displayExchange(Scrabble game) {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    width = gd.getDisplayMode().getWidth();
    // height = gd.getDisplayMode().getHeight();

    this.game = game;

    answer = false;

    initRectangle();
    initInfo();
    initTiles(game.getCurrentPlayer().getRack());
    initButtons();
    setActionEvents();
    initStage();
  }
}
