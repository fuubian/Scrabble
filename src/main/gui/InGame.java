package main.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.controller.GameController;
import main.model.Gameboard;
import main.model.Rack;
import main.model.Scrabble;
import main.model.TileSet;

/**
 * Abstract class for the InGame sessions.
 * 
 * @author ceho
 *
 */
public abstract class InGame {
  /**
   * Stage object to set the Scene.
   */
  Stage stage = new Stage();

  /**
   * Individual width of the screen the application is being displayed on.
   */
  int width;

  /**
   * Individual height of the screen the application is being displayed on.
   */
  int height;

  /**
   * ImageView object to store the background image in.
   */
  ImageView ivBackground;

  /**
   * Door icon, to leave the game.
   */
  Image door;

  /**
   * ImageView object to store the door icon in.
   */
  ImageView ivDoor;

  /**
   * StackPane object for displaying the Scene.
   */
  StackPane root;

  /**
   * Base rectangle behind the components.
   */
  Rectangle rectangle;

  /**
   * Button to leave the game.
   */
  Button logout;

  /**
   * "SCRABBLE" headline.
   */
  Label headline;

  /**
   * GameboardGui object to display the gameboard.
   */
  GameboardGui gameGui;

  // rack
  /**
   * StackPane object for the rack.
   */
  StackPane rackPane;

  /**
   * Labels for the seven tiles.
   */
  TilePane[] tiles = new TilePane[Rack.TILE_CAPACITY];

  /**
   * Button to confirm a move.
   */
  Button confirm;

  // letters, change, pass
  /**
   * Label to display "Letters".
   */
  Label letters;

  /**
   * Label to display the remaining amount of tiles in the bag.
   */
  Label bagOfTiles;

  /**
   * Button to pass the round.
   */
  Button pass;

  /**
   * Button to change tiles.
   */
  Button change;

  /**
   * CoundownTimer object for the Label timer.
   */
  CountdownTimer countdownTimer;

  // leaderboard
  /**
   * StackPane object for the leaderboard.
   */
  StackPane leadPane;

  /**
   * Labels to display the player's username.
   */
  Label[] players = new Label[Scrabble.MAX_PLAYER_COUNT];

  /**
   * Labels to display the current score of the players.
   */
  Label[] scores = new Label[Scrabble.MAX_PLAYER_COUNT];

  /**
   * DragAndDrop object to drag and drop the tiles.
   */
  DragAndDrop dragAndDrop;

  /**
   * GameController object for the game logic.
   */
  GameController gameCtrl;

  /**
   * The constructor of the InGame class. Invokes the init method.
   */
  public InGame() {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    width = gd.getDisplayMode().getWidth();
    height = gd.getDisplayMode().getHeight();

    init(this.stage);
  }

  /**
   * Returns the logout button.
   * 
   * @return the logout button.
   */
  public Button getLogout() {
    return this.logout;
  }

  /**
   * Returns the StackPane.
   * 
   * @return the StackPane.
   */
  public StackPane getStackPane() {
    return this.root;
  }

  /**
   * Returns the dragAndDrop object.
   * 
   * @return dragAndDrop.
   */
  public DragAndDrop getDragAndDrop() {
    return this.dragAndDrop;
  }

  /**
   * Returns the GameboardGui object.
   * 
   * @return gameGui.
   */
  public GameboardGui getGameGui() {
    return this.gameGui;
  }

  /**
   * Returns the array of players.
   * 
   * @return players.
   */
  public Label[] getPlayers() {
    return this.players;
  }

  /**
   * Returns the array of scores.
   * 
   * @return scores.
   */
  public Label[] getScores() {
    return this.scores;
  }

  /**
   * Returns the tiles.
   * 
   * @return tiles.
   */
  public TilePane[] getTiles() {
    return this.tiles;
  }

  /**
   * Returns the label for bagOfTiles.
   * 
   * @return bagOfTiles.
   */
  public Label getBagOfTiles() {
    return this.bagOfTiles;
  }

  /**
   * Returns the CountdownTimer object.
   * 
   * @return ct.
   */
  public CountdownTimer getCountdownTimer() {
    return this.countdownTimer;
  }

  /**
   * Returns the confirm button.
   * 
   * @return confirm.
   */
  public Button getConfirm() {
    return confirm;
  }

  /**
   * Returns the pass button.
   * 
   * @return pass.
   */
  public Button getPass() {
    return pass;
  }

  /**
   * Returns the change button.
   * 
   * @return change.
   */
  public Button getChange() {
    return change;
  }

  /**
   * Sets the Scrabble game object and the players, when a new game starts.
   * 
   * @param game the Scrabble object of the current game.
   */
  public void setGame(Scrabble game) {
    resetPlayers();
    setPlayers(game);
    gameCtrl = new GameController(this, game);
    gameCtrl.updateAll();
    gameGui.reset();
    dragAndDrop.reset();
  }

  /**
   * Sets the player's names.
   */
  public void setPlayers(Scrabble game) {
    for (int i = 0; i < game.getPlayerCount(); i++) {
      players[i].setText(game.getPlayer(i).getName());
    }
  }

  /**
   * Returns the GameController.
   * 
   * @return gameController
   */
  public GameController getGameController() {
    return this.gameCtrl;
  }

  /**
   * Initializes all components.
   * 
   * @param stage of the InGame session.
   */
  void init(Stage stage) {
    initObjects();
    initBackground();
    optionsBackground();
    initLogHead();
    initRack();
    initLeaderboard();
    initRest();
    setActionConfirm();
    setActionPass();
    setActionChange();
    setActionDoor();
    setActionTiles();
    setActionBoard();
    dragAndDrop.start();
  }

  /**
   * Initializes all necessary objects.
   */
  void initObjects() {
    Scrabble game = new Scrabble(TileSet.getStandard().create(), null, false);
    gameGui = new GameboardGui(game.getGameboard(), height);
    dragAndDrop = new DragAndDrop(tiles, gameGui, game.getGameboard());
  }

  /**
   * Initializes the Image-object "background" by using bckgrnd.png; bckgrnd.png is a free to use,
   * copyright free image from https://pixabay.com/images/id-691842/ saved on: 31st of March, 2021
   * at 10:32 pm.
   */
  void initBackground() {
    Image background = new Image("file:res/images/bckgrnd.png");
    ivBackground = new ImageView(background);
    ivBackground.setFitHeight(height);
    ivBackground.setFitWidth(width);
  }

  /**
   * Sets the base rectangle in the background.
   */
  void optionsBackground() {
    rectangle = new Rectangle();
    rectangle.setHeight(height * 0.83332); // 750
    rectangle.setWidth(width * 0.9028); // 1300
    rectangle.setArcWidth(30);
    rectangle.setArcHeight(30);

    rectangle.setFill(Color.WHITE);
    rectangle.setStroke(GuiSettings.BLUE);
  }

  /**
   * Initializes the Image-object "door" by using logout.png and the headline and styles them.
   */
  void initLogHead() {
    door = new Image("file:res/images/doorClosed.jpg", width * 0.035, width * 0.035, true, true);
    // 50.4/50.4
    ivDoor = new ImageView(door);
    logout = new Button();
    logout.setGraphic(ivDoor);
    logout.setStyle("-fx-background-color: transparent;");

    headline = new Label("S C R A B U R U");
    // headline.setStyle("-fx-font-family: Futura; -fx-text-fill: #2e75b6; -fx-font-size: 30px;");
    headline.setStyle(
        "-fx-font-family: Futura;" + "-fx-text-fill: linear-gradient(to right, #84A9E7 0%, "
            + "#DEB7EE 15%, #ee7898 30%, #e6ed8e 45%, #9ccdb4 60%, "
            + "#84A9E7 75%, #ed9ab0 100% );" + "-fx-font-size: 30px;" + "-fx-font-weight: bold;"); // 30px-2.308em
  }

  /**
   * Initializes and sets the panel on which the rack is going to be displayed.
   */
  void initRack() {
    Rectangle rackRect = new Rectangle();
    rackRect.setWidth(height * 0.66667); // 600
    rackRect.setHeight(height * 0.06667); // 60
    rackRect.setArcWidth(30);
    rackRect.setArcHeight(30);
    rackRect.setFill(GuiSettings.GRAY_FILL);
    rackRect.setStroke(GuiSettings.GRAY_BORDER);

    for (int i = 0; i < tiles.length; i++) {
      tiles[i] = new TilePane(height, TilePane.Type.RACK_TILE);
    }

    confirm = new Button("\u2713");
    confirm.setPrefSize(height * 0.05, height * 0.05); // 45/45
    confirm.setAlignment(Pos.CENTER);
    confirm.setStyle(
        "-fx-font-family: Futura;" + "-fx-font-size: 20px;" + "-fx-background-color: white; "
            + "-fx-border-color: #2e75b6;" + "-fx-text-fill: #2e75b6;");
    // radius-5px-0.385em; font-20px-1.538em

    GridPane rackGrid = new GridPane();
    rackGrid.setAlignment(Pos.CENTER);
    rackGrid.setVgap(height * 0.016667); // 15
    rackGrid.setHgap(width * 0.01736); // 25
    rackGrid.add(tiles[0], 0, 0);
    rackGrid.add(tiles[1], 1, 0);
    rackGrid.add(tiles[2], 2, 0);
    rackGrid.add(tiles[3], 3, 0);
    rackGrid.add(tiles[4], 4, 0);
    rackGrid.add(tiles[5], 5, 0);
    rackGrid.add(tiles[6], 6, 0);
    rackGrid.add(confirm, 7, 0);

    rackPane = new StackPane();
    rackPane.getChildren().add(rackRect);
    rackPane.getChildren().add(rackGrid);
  }

  /**
   * Initializes and sets the panel on which the leaderboard is going to be displayed.
   */
  void initLeaderboard() {
    Rectangle leadRect = new Rectangle();
    leadRect.setWidth(width * 0.20833); // 300
    leadRect.setHeight(height * 0.37778); // 340
    leadRect.setArcWidth(30);
    leadRect.setArcHeight(30);
    leadRect.setFill(GuiSettings.GRAY_FILL);
    leadRect.setStroke(GuiSettings.GRAY_BORDER);

    Label points = new Label("\u2605" + " POINTS " + "\u2605");
    points.setPrefSize(width * 0.17708, height * 0.05556); // 255/50
    points.setAlignment(Pos.CENTER);
    points.setStyle("-fx-font-family: Futura;" + "-fx-background-radius: 10px;"
        + "-fx-font-size: 20px;" + "-fx-background-color: #2e75b6; " + "-fx-border-color: #A6A6A6;"
        + "-fx-text-fill: white;" + "-fx-border-radius: 10px;");

    players[0] = new Label();
    players[0].setPrefSize(width * 0.12152, height * 0.05556); // 175/50
    players[0].setAlignment(Pos.CENTER);
    players[0].setStyle("-fx-font-family: Futura;" + "-fx-background-radius: 10px;"
        + "-fx-font-size: 20px;" + "-fx-background-color: white; " + "-fx-border-color: #2e75b6;"
        + "-fx-text-fill: #2e75b6;" + "-fx-border-radius: 10px;");

    for (int i = 1; i < Scrabble.MAX_PLAYER_COUNT; i++) {
      players[i] = new Label();
      players[i].setPrefSize(width * 0.12152, height * 0.05556); // 175/50
      players[i].setAlignment(Pos.CENTER);
      players[i].setStyle("-fx-font-family: Futura;" + "-fx-background-radius: 10px;"
          + "-fx-font-size: 20px;" + "-fx-background-color: white; " + "-fx-border-color: #A6A6A6;"
          + "-fx-text-fill: #7F7F7F;" + "-fx-border-radius: 10px;");
    }

    scores[0] = new Label();
    scores[0].setPrefSize(width * 0.04514, height * 0.05556); // 65/50
    scores[0].setAlignment(Pos.CENTER);
    scores[0].setStyle("-fx-font-family: Futura;" + "-fx-background-radius: 10px;"
        + "-fx-font-size: 20px;" + "-fx-background-color: white; " + "-fx-border-color: #2e75b6;"
        + "-fx-text-fill: #2e75b6;" + "-fx-border-radius: 10px;");

    for (int i = 1; i < scores.length; i++) {
      scores[i] = new Label();
      scores[i].setPrefSize(width * 0.04514, height * 0.05556); // 65/50
      scores[i].setAlignment(Pos.CENTER);
      scores[i].setStyle("-fx-font-family: Futura;" + "-fx-background-radius: 10px;"
          + "-fx-font-size: 20px;" + "-fx-background-color: white; " + "-fx-border-color: #A6A6A6;"
          + "-fx-text-fill: #7F7F7F;" + "-fx-border-radius: 10px;");
    }

    GridPane leadGrid = new GridPane();
    leadGrid.setAlignment(Pos.CENTER);
    leadGrid.setVgap(height * 0.016667); // 15
    leadGrid.setHgap(height * 0.016667);// 15
    leadGrid.add(points, 0, 0, 2, 1);
    leadGrid.add(players[0], 0, 1);
    leadGrid.add(scores[0], 1, 1);
    leadGrid.add(players[1], 0, 2);
    leadGrid.add(scores[1], 1, 2);
    leadGrid.add(players[2], 0, 3);
    leadGrid.add(scores[2], 1, 3);
    leadGrid.add(players[3], 0, 4);
    leadGrid.add(scores[3], 1, 4);

    leadPane = new StackPane();
    leadPane.getChildren().add(leadRect);
    leadPane.getChildren().add(leadGrid);
  }

  void initRest() {
    letters = new Label("LETTERS");
    letters.setPrefSize(width * 0.0833, height * 0.05556); // 120/50
    letters.setAlignment(Pos.CENTER);
    letters.setStyle("-fx-font-family: Futura;" + "-fx-background-radius: 10px;"
        + "-fx-font-size: 20px;" + "-fx-background-color: #2e75b6; " + "-fx-border-color: #A6A6A6;"
        + "-fx-text-fill: white;" + "-fx-border-radius: 10px;");

    bagOfTiles = new Label();
    bagOfTiles.setPrefSize(width * 0.0833, height * 0.05556); // 120/50
    bagOfTiles.setAlignment(Pos.CENTER);
    bagOfTiles.setStyle("-fx-font-family: Futura;" + "-fx-background-radius: 10px;"
        + "-fx-font-size: 20px;" + "-fx-background-color: white; " + "-fx-border-color: #2e75b6;"
        + "-fx-text-fill: #2e75b6;" + "-fx-border-radius: 10px;");

    change = new Button("CHANGE");
    change.setAlignment(Pos.CENTER);
    change.setStyle("-fx-font-family: Futura;" + "-fx-background-radius: 10px;"
        + "-fx-font-size: 20px;" + "-fx-background-color: white; " + "-fx-border-color: #2e75b6;"
        + "-fx-text-fill: #2E82C3;" + "-fx-border-radius: 10px;");

    pass = new Button("PASS");
    pass.setAlignment(Pos.CENTER);
    pass.setStyle("-fx-font-family: Futura;" + "-fx-background-radius: 10px;"
        + "-fx-font-size: 20px;" + "-fx-background-color: white; " + "-fx-border-color: #C00000;"
        + "-fx-text-fill: #C03C32;" + "-fx-border-radius: 10px;");
  }

  /**
   * Initializes the stage that sets the scene.
   * 
   * @param stage for the InGame session.
   */
  abstract void initStage(Stage stage);

  /**
   * Sets ActionEvents for the Button confirm.
   */
  void setActionConfirm() {
    confirm.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        gameCtrl.setMove();
      }
    });

    confirm.setOnMouseEntered(new EventHandler<MouseEvent>() {

      public void handle(MouseEvent me) {
        confirm.setCursor(Cursor.HAND);
      }
    });

    confirm.setOnMousePressed(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        confirm.setStyle(
            "-fx-font-family: Futura;" + "-fx-font-size: 20px;" + "-fx-background-color: #2e75b6; "
                + "-fx-border-color: #2e75b6;" + "-fx-text-fill: white;");
      }
    });

    confirm.setOnMouseReleased(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        confirm.setStyle(
            "-fx-font-family: Futura;" + "-fx-font-size: 20px;" + "-fx-background-color: white; "
                + "-fx-border-color: #2e75b6;" + "-fx-text-fill: #2e75b6;");
      }
    });
  }

  /**
   * Sets ActionEvents for the Button pass.
   */
  void setActionPass() {
    pass.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        gameCtrl.pass();
      }
    });

    pass.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        pass.setStyle(
            "-fx-font-family: Futura; -fx-background-radius: 10px;" + "-fx-font-size: 20px;"
                + "-fx-background-color: white; " + "-fx-border-color: #C00000;"
                + "-fx-text-fill: #C00000;" + "-fx-border-radius: 10px;");

        pass.setCursor(Cursor.HAND);
      }
    });

    pass.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        pass.setStyle(
            "-fx-font-family: Futura; -fx-background-radius: 10px;" + "-fx-font-size: 19px;"
                + "-fx-background-color: white; " + "-fx-border-color: #C00000;"
                + "-fx-text-fill: #C03C32;" + "-fx-border-radius: 10px;");
      }
    });
  }

  /**
   * Sets ActionEvents for the Button change.
   */
  void setActionChange() {
    change.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        gameCtrl.exchangeTiles();
      }
    });

    change.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        change.setStyle(
            "-fx-font-family: Futura; -fx-background-radius: 10px;" + "-fx-font-size: 20px;"
                + "-fx-background-color: white; " + "-fx-border-color: #2e75b6;"
                + "-fx-text-fill: #2e75b6;" + "-fx-border-radius: 10px;");

        change.setCursor(Cursor.HAND);
      }
    });

    change.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        change.setStyle(
            "-fx-font-family: Futura; -fx-background-radius: 10px;" + "-fx-font-size: 19px;"
                + "-fx-background-color: white; " + "-fx-border-color: #2e75b6;"
                + "-fx-text-fill: #2E82C3;" + "-fx-border-radius: 10px;");
      }
    });
  }

  /**
   * Sets ActionEvents for the door icon.
   */
  void setActionDoor() {
    logout.setOnMouseEntered(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        door = new Image("file:res/images/doorOpen.jpg", width * 0.035, width * 0.035, true, true);
        // 50.4/50.4
        ivDoor = new ImageView(door);
        logout.setGraphic(ivDoor);
        logout.setStyle("-fx-background-color: transparent;");

        logout.setCursor(Cursor.HAND);
      }
    });

    logout.setOnMouseExited(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent me) {
        door =
            new Image("file:res/images/doorClosed.jpg", width * 0.035, width * 0.035, true, true);
        // 50.4/50.4
        ivDoor = new ImageView(door);
        logout.setGraphic(ivDoor);
        logout.setStyle("-fx-background-color: transparent;");
      }
    });
  }

  /**
   * Sets the ActionEvents for the tiles.
   */
  void setActionTiles() {
    for (int i = 0; i < tiles.length; i++) {
      int indx = i;
      tiles[indx].setOnMouseEntered(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
          // if (tiles[indx].getText() != null) {
          if (!tiles[indx].isEmpty()) {
            tiles[indx].setStyle("-fx-font-family: Futura;" + "-fx-font-size: 30px;"
                + "-fx-background-color: #2E8CCD; " + "-fx-border-color: #A6A6A6;"
                + "-fx-text-fill: white;");

            tiles[indx].setCursor(Cursor.OPEN_HAND);
          } else {
            tiles[indx].setCursor(Cursor.DEFAULT);
          }
        }
      });


      tiles[indx].setOnMouseExited(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
          // if (tiles[indx].getText() != null) {
          if (!tiles[indx].isEmpty()) {
            tiles[indx].setStyle("-fx-font-family: Futura;" + "-fx-font-size: 30px;"
                + "-fx-background-color: #2e75b6; " + "-fx-border-color: #A6A6A6;"
                + "-fx-text-fill: white;");
          }
        }
      });


      tiles[indx].setOnMousePressed(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
          // if (tiles[indx].getText() != null) {
          if (!tiles[indx].isEmpty()) {
            tiles[indx].setCursor(Cursor.CLOSED_HAND);
          }
        }
      });
    }
  }

  /**
   * Sets ActionEvent for the board.
   */
  void setActionBoard() {
    for (int r = 0; r < Gameboard.ROWS; r++) {
      for (int c = 0; c < Gameboard.COLS; c++) {
        int row = r;
        int col = c;
        gameGui.getSquare(row, col).setOnMouseEntered(new EventHandler<MouseEvent>() {
          public void handle(MouseEvent me) {
            if (dragAndDrop.getEditable(row, col)) {
              gameGui.getSquare(row, col).setCursor(Cursor.OPEN_HAND);
            } else {
              gameGui.getSquare(row, col).setCursor(Cursor.DEFAULT);
            }
          }
        });
      }
    }
  }

  public void resetPlayers() {
    for (int i = 1; i < Scrabble.MAX_PLAYER_COUNT; i++) {
      players[i].setText("-");
      scores[i].setText("-");
    }
  }
}
