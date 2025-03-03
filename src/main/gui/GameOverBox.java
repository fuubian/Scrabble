package main.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.List;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.model.Player;

public class GameOverBox {
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
  private Label gameOver;

  /**
   * Array to store the players names.
   */
  private Player[] players;

  /**
   * Label that displays the total play time.
   */
  private Label time;

  /**
   * Button to close the game over box.
   */
  private Button proceed;

  /**
   * GridPane to sort the players.
   */
  private GridPane rankGrid;

  /**
   * Stores the height of the box.
   */
  private int boxHeight;

  /**
   * Returns the next button.
   * 
   * @return next.
   */
  public Button getOkButton() {
    return this.proceed;
  }

  /**
   * Initializes the players and sorts them in a descending order,
   * 
   * @param players of the current game.
   */
  private void initPlayers(List<Player> players) {
    this.players = new Player[players.size()];
    for (int i = 0; i < players.size(); i++) {
      this.players[i] = players.get(i);
    }

    for (int i = 0; i < this.players.length - 1; i++) {
      for (int j = 0; j < (this.players.length - i - 1); j++) {
        if (this.players[j].getScore() < this.players[j + 1].getScore()) {
          Player help = this.players[j];
          this.players[j] = this.players[j + 1];
          this.players[j + 1] = help;
        }
      }
    }
  }

  /**
   * Initializes the base/background.
   */
  private void initRectangle() {
    rect = new Rectangle();
    switch (players.length) {
      case 3:
        boxHeight = 360;
        rect.setHeight(boxHeight);
        break;
      case 4:
        boxHeight = 420;
        rect.setHeight(boxHeight);
        break;
      default:
        boxHeight = 300;
        rect.setHeight(boxHeight);
    }
    rect.setWidth(500);
    rect.setFill(Color.WHITE);
    rect.setStroke(GuiSettings.BLUE);
  }

  /**
   * Initializes the label and sets the GAME OVER label and the total game time.
   */
  private void initLabels(long totalTime) {
    gameOver = new Label("GAME OVER");
    gameOver.setAlignment(Pos.CENTER);
    gameOver.setTextAlignment(TextAlignment.CENTER);
    gameOver.setStyle("-fx-font-family: Futura;" + "-fx-font-size: 40px;"
        + "-fx-text-fill: #C3ADD4;" + "-fx-font-weight: bold;");

    long minutes = (totalTime / 1000) / 60;

    long seconds = (totalTime / 1000) % 60;

    time = new Label("Time: " + minutes + "m " + seconds + "s");
    // time = new Label("Time: " + totalTime + "ms");
    time.setAlignment(Pos.CENTER);
    time.setTextAlignment(TextAlignment.CENTER);
    time.setStyle("-fx-font-family: Futura;" + "-fx-font-size: 18px;" + "-fx-text-fill: #7F7F7F");
  }

  /**
   * Initializes the scoreboard.
   */
  private void initScoreboard() {
    rankGrid = new GridPane();
    rankGrid.setAlignment(Pos.CENTER);
    rankGrid.setHgap(40);
    rankGrid.setVgap(20);
    // rankGrid.setGridLinesVisible(true);

    Label[] numbers = new Label[4];
    Circle[] circles = new Circle[4];
    Label[] playerLabel = new Label[4];
    Label[] scores = new Label[4];
    Label[] rank = new Label[4];

    for (int i = 0; i < players.length; i++) {
      circles[i] = new Circle(20);

      rank[i] = new Label();
      rank[i].setAlignment(Pos.CENTER);
      rank[i].setTextAlignment(TextAlignment.CENTER);

      switch (i) {
        case 0:
          circles[i].setFill(Color.GOLD);

          rank[i].setText("DESTROYER");
          rank[i].setStyle("-fx-font-family: Futura;" + "-fx-font-size: 20px;"
              + "-fx-text-fill: linear-gradient(to right, red, orange, #e7eb00, green,"
              + "blue, indigo, violet);" + "-fx-background-color: transparent;");
          break;
        case 1:
          circles[i].setFill(Color.SILVER);

          // rank[i].setText("KEK");
          rank[i].setStyle("-fx-font-family: Futura;" + "-fx-font-size: 20px;"
              + "-fx-text-fill: #7F7F7F;" + "-fx-background-color: transparent;");
          break;
        case 2:
          circles[i].setFill(Color.ROSYBROWN);

          // rank[i].setText("MEH");
          rank[i].setStyle("-fx-font-family: Futura;" + "-fx-font-size: 20px;"
              + "-fx-text-fill: #7F7F7F;" + "-fx-background-color: transparent;");
          break;
        case 3:
          circles[i].setFill(Color.BLACK);

          rank[i].setText("LOOSER");
          rank[i].setStyle("-fx-font-family: Futura;" + "-fx-font-size: 20px;"
              + "-fx-text-fill: black;" + "-fx-background-color: transparent;");
          break;
      }

      rankGrid.add(circles[i], 0, i);
      GridPane.setHalignment(circles[i], HPos.CENTER);

      rankGrid.add(rank[i], 3, i);
      GridPane.setHalignment(rank[i], HPos.CENTER);

      numbers[i] = new Label(Integer.toString(i + 1));
      numbers[i].setAlignment(Pos.CENTER);
      numbers[i].setTextAlignment(TextAlignment.CENTER);
      numbers[i].setStyle("-fx-font-family: Futura;" + "-fx-font-size: 20px;"
          + "-fx-text-fill: white;" + "-fx-background-color: transparent;");

      rankGrid.add(numbers[i], 0, i);
      GridPane.setHalignment(numbers[i], HPos.CENTER);

      playerLabel[i] = new Label(players[i].getName());
      playerLabel[i].setAlignment(Pos.CENTER);
      playerLabel[i].setTextAlignment(TextAlignment.CENTER);
      playerLabel[i].setStyle("-fx-font-family: Futura;" + "-fx-font-size: 20px;"
          + "-fx-text-fill: #7F7F7F;" + "-fx-background-color: transparent;");

      rankGrid.add(playerLabel[i], 1, i);
      GridPane.setHalignment(playerLabel[i], HPos.LEFT);

      scores[i] = new Label(Integer.toString(players[i].getScore()));
      scores[i].setAlignment(Pos.CENTER);
      scores[i].setTextAlignment(TextAlignment.CENTER);
      scores[i].setStyle("-fx-font-family: Futura;" + "-fx-font-size: 20px;"
          + "-fx-text-fill: #2e75b6;" + "-fx-background-color: transparent;");

      rankGrid.add(scores[i], 2, i);
      GridPane.setHalignment(scores[i], HPos.CENTER);
    }
  }

  /**
   * Initializes and styles the button.
   */
  private void initButtons() {
    proceed = new Button("Next");
    proceed.setStyle("-fx-background-color: #00ca4e; -fx-text-fill: white; "
        + "-fx-font-weight: bold; -fx-font-family: Futura; -fx-border-radius: 15px; "
        + "-fx-background-radius: 15px; -fx-font-size: 13px;");

    proceed.setPrefSize(width * 0.069443, width * 0.020833); // 100/30
  }

  /**
   * Sets all ActionEvents of the button cancel.
   */
  private void setActionEvents() {
    proceed.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        stage.close();
      }
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
  }

  /**
   * Initializes the Stage and sets the Scene.
   */
  private void initStage() {
    stage = new Stage();
    stage.setTitle("Game over");
    stage.initModality(Modality.APPLICATION_MODAL);

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    // grid.setGridLinesVisible(true);
    grid.setVgap(20); // 10
    grid.add(gameOver, 0, 0);
    GridPane.setHalignment(gameOver, HPos.CENTER);
    grid.add(time, 0, 1);
    GridPane.setHalignment(time, HPos.CENTER);
    grid.add(rankGrid, 0, 2);
    grid.add(proceed, 0, 3);
    GridPane.setHalignment(proceed, HPos.RIGHT);

    StackPane root = new StackPane();

    root.getChildren().add(rect);
    root.getChildren().add(grid);

    Scene scene;
    scene = new Scene(root, 510, boxHeight + 10); // 310/120-(width * 0.076389)
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Displays the game over box.
   * 
   * @param players of the current game.
   */
  public void displayGameOver(List<Player> players, long time) {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    width = gd.getDisplayMode().getWidth();
    // height = gd.getDisplayMode().getHeight();

    initPlayers(players);
    initRectangle();
    initLabels(time);
    initScoreboard();
    initButtons();
    setActionEvents();
    initStage();
  }
}
