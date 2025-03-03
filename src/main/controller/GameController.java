package main.controller;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.database.Profile;
import main.gui.AlertBox;
import main.gui.DragAndDrop;
import main.gui.ErrorBox;
import main.gui.ExchangeTilesBox;
import main.gui.GameOverBox;
import main.gui.GameboardGui;
import main.gui.InGame;
import main.gui.TilePane;
import main.model.ComputerPlayer;
import main.model.GameException;
import main.model.GameState;
import main.model.Gameboard;
import main.model.Move;
import main.model.Player;
import main.model.PlayerType;
import main.model.Rack;
import main.model.Scrabble;
import main.model.Tile;

/**
 * Controller for the game.
 * 
 * @author ceho
 *
 */
public class GameController {
  /**
   * DragAndDrop object to drag and drop the tiles.
   */
  private DragAndDrop dragAndDrop;

  /**
   * GameboardGui object to display the gameboard.
   */
  private GameboardGui gameGui;

  /**
   * Scrabble game object that represents a game.
   */
  private Scrabble game;

  /**
   * Labels to display the player's username.
   */
  private Label[] players = new Label[Scrabble.MAX_PLAYER_COUNT];

  /**
   * Labels to display the current score of the players.
   */
  private Label[] scores = new Label[Scrabble.MAX_PLAYER_COUNT];

  /**
   * Labels for the seven tiles.
   */
  private TilePane[] tiles = new TilePane[Rack.TILE_CAPACITY];

  /**
   * Label to display the remaining amount of tiles in the bag.
   */
  private Label bagOfTiles;

  /**
   * ErrorBox object to display errors.
   */
  private ErrorBox error = new ErrorBox();

  /**
   * AlerBox object to display alerts.
   */
  private AlertBox alert = new AlertBox();

  /**
   * Button to confirm a move.
   */
  private Button confirm;

  /**
   * Button to pass the round.
   */
  private Button pass;

  /**
   * Button to exchange tiles.
   */
  private Button change;

  /**
   * Stores the current player.
   */
  private Player currentPlayer;

  /**
   * Connection to the Server-/ClientController.
   */
  private NetworkGameController netController;

  /**
   * Shows if the current game is a network game.
   */
  private boolean isNetwork = false;

  /**
   * Shows if the player is the host.
   */
  private boolean isHost = false;

  /**
   * Constructor of the class GameController.
   * 
   * @param inGame object.
   */
  public GameController(InGame inGame, Scrabble game) {
    this.dragAndDrop = inGame.getDragAndDrop();
    this.gameGui = inGame.getGameGui();
    this.game = game;
    this.players = inGame.getPlayers();
    this.scores = inGame.getScores();
    this.tiles = inGame.getTiles();
    this.bagOfTiles = inGame.getBagOfTiles();
    this.confirm = inGame.getConfirm();
    this.pass = inGame.getPass();
    this.change = inGame.getChange();
    this.currentPlayer = game.getCurrentPlayer();
  }

  /**
   * Updates the game when a network game is played.
   * 
   * @param game
   */
  public void setGame(Scrabble game) {
    StatisticController.resetState();
    this.game = game;
  }

  /**
   * Sets the NetworkGameController and marks the current game as a network game.
   * 
   * @param netController a ServerController or ClientController
   * @param isHost boolean that determines if the player hosts the game
   */
  public void setNetwork(NetworkGameController netController, boolean isHost) {
    this.netController = netController;
    this.isNetwork = true;
    this.isHost = isHost;
  }

  /**
   * Returns the game object for the NetworkGameController.
   * 
   * @return running Scrabble game
   */
  public Scrabble getGame() {
    return this.game;
  }

  private boolean isContiguous() {
    boolean[][] playedTiles = dragAndDrop.getEditable();
    int startRow = Gameboard.ROWS - 1;
    int startCol = Gameboard.COLS - 1;
    int endRow = 0;
    int endCol = 0;

    for (int row = 0; row < Gameboard.ROWS; row++) {
      for (int col = 0; col < Gameboard.COLS; col++) {
        if (playedTiles[row][col]) {
          startRow = Math.min(row, startRow);

          startCol = Math.min(col, startCol);

          endRow = Math.max(row, endRow);

          endCol = Math.max(col, endCol);
        }
      }
    }

    if (startRow == endRow) {
      for (int i = startCol + 1; i < endCol; i++) {
        if (!playedTiles[startRow][i] && game.getGameboard().getSquareAt(startRow, i).isEmpty()) {
          return false;
        }
      }
    } else {
      for (int i = startRow + 1; i < endRow; i++) {
        if (!playedTiles[i][startCol] && game.getGameboard().getSquareAt(i, startCol).isEmpty()) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Sets one move after clicking on confirm.
   * 
   * Searches for the word with playedWord. Model checks if word exists and proceeds accordingly.
   */
  public void setMove() {
    boolean result = false;

    if (isContiguous()) {
      PlayedWord playedWord = new PlayedWord(gameGui.getSquares(), dragAndDrop.getEditable());
      // System.out.println(playedWord);

      try {
        game.playWord(playedWord.getWord(), playedWord.getStartPosition(),
            playedWord.getDirection());
        result = true;
      } catch (GameException e1) {
        error.displayError(e1.getMessage());
      }
    } else {
      error.displayError("Your tiles are not contiguous.");
    }

    if (!result) {
      dragAndDrop.revert();
    }

    if (result && this.isNetwork) {
      Scrabble copyGame = new Scrabble(this.game);
      this.netController.sendMove(copyGame.getTurnState());
    }

    updateAll();
    dragAndDrop.set();
  }

  /**
   * Pass the round.
   */
  public void pass() {
    alert.displayAlert("Are you sure you want to pass this round?");
    if (alert.getAnswer()) {
      try {
        game.pass();
        if (this.isNetwork) {
          Scrabble copyGame = new Scrabble(this.game);
          this.netController.sendMove(copyGame.getTurnState());
        }
        dragAndDrop.revert();
        updateAll();
      } catch (GameException e1) {
        error.displayError(e1.getMessage());
      }
    }
  }

  /**
   * Exchange the tiles.
   */
  public void exchangeTiles() {
    if (dragAndDrop.getEdited()) {
      error.displayError("You need to remove your tiles first to proceed.");
    } else {
      alert.displayAlert("If you exchange your tiles you won't be able "
          + "to play this round.\nDo you want to proceed?");
      if (alert.getAnswer()) {
        ExchangeTilesBox changeBox = new ExchangeTilesBox();
        changeBox.displayExchange(game);
        if (changeBox.getAnswer()) {
          if (this.isNetwork) {
            Scrabble copyGame = new Scrabble(this.game);
            this.netController.sendMove(copyGame.getTurnState());
          }
          updateAll();
        }
      }
    }
  }

  /**
   * The current player is marked with a blue border and text color. UpdatePlayer changes the
   * current player to the next player and marks them as the new current player.
   */
  private void updatePlayer() {
    for (int i = 0; i < Scrabble.MAX_PLAYER_COUNT; i++) {
      players[i].setStyle("-fx-font-family: Futura; -fx-background-radius: 10px;"
          + "-fx-font-size: 20px; -fx-background-color: white; -fx-border-color: #A6A6A6;"
          + "-fx-text-fill: #7F7F7F; -fx-border-radius: 10px;");

      scores[i].setStyle("-fx-font-family: Futura; -fx-background-radius: 10px;"
          + "-fx-font-size: 20px; -fx-background-color: white; -fx-border-color: #A6A6A6;"
          + "-fx-text-fill: #7F7F7F; -fx-border-radius: 10px;");
    }

    int currentPlayer = game.getCurrentPlayerIndex();

    players[currentPlayer].setStyle("-fx-font-family: Futura; -fx-background-radius: 10px;"
        + "-fx-font-size: 20px; -fx-background-color: white; -fx-border-color: #2e75b6;"
        + "-fx-text-fill: #2e75b6; -fx-border-radius: 10px;");

    scores[currentPlayer].setStyle("-fx-font-family: Futura; -fx-background-radius: 10px;"
        + "-fx-font-size: 20px; -fx-background-color: white; -fx-border-color: #2e75b6;"
        + "-fx-text-fill: #2e75b6; -fx-border-radius: 10px;");
  }

  /**
   * Updates the tiles on the rack.
   */
  private void updateRack(Player me) {
    for (int i = 0; i < tiles.length; i++) {
      if (i < me.getRack().size()) {
        tiles[i].setStyle(TilePane.BACKGROUND_OCCUPIED + TilePane.BORDER_RACK);
        tiles[i].setFontStyle(TilePane.LETTER_STYLE_RACK, TilePane.SCORE_STYLE);

        tiles[i].setLetter(me.getRack().getTile(i).getLetter());
        tiles[i].setScore(me.getRack().getTile(i).getScore());
      } else {
        tiles[i].setStyle(TilePane.BACKGROUND_RACK_EMPTY + TilePane.BORDER_RACK);
        tiles[i].clear();
      }
    }
  }

  /**
   * Updates the gameboard.
   */
  private void updateGameboard() {
    for (int row = 0; row < Gameboard.ROWS; row++) {
      for (int col = 0; col < Gameboard.COLS; col++) {
        Tile tile = game.getGameboard().getSquareAt(row, col).getTile();
        if (tile != null) {
          gameGui.getSquare(row, col).setLetter(tile.getLetter());
          gameGui.getSquare(row, col).setScore(tile.getScore());

          dragAndDrop.setAvailable(row, col, false);
        } else {
          gameGui.getSquare(row, col).clear();
        }

        if (!dragAndDrop.getAvailable(row, col)) {
          gameGui.setSquareStyle(row, col,
              "-fx-background-color: #2e75b6;" + "-fx-border-color: white;"
                  + "-fx-font-family: Futura;" + "-fx-font-size: 20px;" + "-fx-text-fill: white;");
        }
      }
    }
    // int row = Gameboard.squareIdToRow(Gameboard.CENTER_SQUARE_ID);
    // int col = Gameboard.squareIdToCol(Gameboard.CENTER_SQUARE_ID);
    // if (gameGui.getSquare(row, col).isEmpty()) {
    // gameGui.getSquare(row, col).clear();
    // gameGui.getSquare(row, col).setLetter("\u2605");
    // }
  }

  /**
   * Updates the scoreboard.
   */
  private void updateScore() {
    for (int i = 0; i < game.getPlayerCount(); i++) {
      scores[i].setText(Integer.toString(game.getPlayer(i).getScore()));
    }
  }

  /**
   * Updates the amount of tiles left in the bag.
   */
  private void updateBagOfTiles() {
    bagOfTiles.setText(Integer.toString(game.getTileBag().size()));
  }

  private void executeMove() {
    ComputerPlayer com = (ComputerPlayer) game.getCurrentPlayer();
    Move move = com.chooseMove(game);
    try {
      game.executeMove(move);
    } catch (GameException e) {
      error.displayError(e.getMessage());
    }
  }

  /**
   * Updates the GUI of the InGame session by updating the players, the rack, the gameboard, the
   * scores and the tiles in bag.
   */
  public void updateAll() {
    if (!this.currentPlayer.getName().equals(game.getCurrentPlayer().getName())) {
      this.currentPlayer = game.getCurrentPlayer();
    }

    Player me = null;
    dragAndDrop.setActive(false);
    confirm.setDisable(true);
    pass.setDisable(true);
    change.setDisable(true);

    for (int i = 0; i < game.getPlayerCount(); i++) {
      if (game.getPlayer(i).getName().equals(Profile.getOnlineName())) {
        me = game.getPlayer(i);
        break;
      }
    }
    if (game.getCurrentPlayer().getName().equals(Profile.getOnlineName())) {
      dragAndDrop.setActive(true);
      confirm.setDisable(false);
      pass.setDisable(false);
      change.setDisable(false);
    }

    updateRack(me);
    updateGameboard();
    updateScore();
    updateBagOfTiles();
    updatePlayer();

    if (game.getGameState() == GameState.GAME_OVER) {
      StatisticController.updateStats(this.game, this.isNetwork);
      new GameOverBox().displayGameOver(game.getPlayers(), game.getTotalTime());
    } else {
      if (game.getCurrentPlayer().getPlayerType() == PlayerType.COM) {
        if (!this.isNetwork || this.isHost) {
          Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
              executeMove();
              return null;
            }
          };
          task.setOnSucceeded(taskFinishEvent -> {
            updateAll();
            if (this.isNetwork) {
              Scrabble copyGame = new Scrabble(this.game);
              this.netController.sendMove(copyGame.getTurnState());
            }
          });
          new Thread(task).start();
        }
      } else if (this.currentPlayer.getName().equals(me.getName())) {
        if (game.getScorelessMoveCount() >= Scrabble.MIN_SCORELESS_MOVE_COUNT_TO_FINISH_GAME) {
          alert.displayAlert("There has been " + game.getScorelessMoveCount()
              + " scoreless moves.\nDo you want to end the game?");
          if (alert.getAnswer()) {
            try {
              game.finishGame();
              updateAll();
              if (this.isNetwork) {
                Scrabble copyGame = new Scrabble(this.game);
                this.netController.sendMove(copyGame.getTurnState());
              }
            } catch (GameException e) {
              error.displayError(e.getMessage());
            }
          }
        }
      }
    }


  }
}
