package main.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import main.model.Gameboard;
import main.model.SquareType;

/**
 * Represents the gameboard of a game.
 * 
 * @author ceho
 *
 */

public class GameboardGui {
  /**
   * Two dimensional array for each square.
   */
  private TilePane[][] squares = new TilePane[Gameboard.ROWS][Gameboard.COLS];

  /**
   * Width of the screen.
   */
  private int height;

  /**
   * GridPane to arrange the squares.
   */
  private GridPane gameGrid;

  /**
   * Gameboard object of the game.
   */
  private Gameboard board;

  /**
   * The constructor of the GameboadGui class. Initializes the gameboardGui.
   */
  GameboardGui(Gameboard board, int height) {
    this.height = height;
    this.board = board;
    initGameboard();
  }

  /**
   * Returns the gameboard object.
   * 
   * @return Gameboard object as GridPane.
   */
  public GridPane getGameGui() {
    return gameGrid;
  }

  /**
   * Returns all squares of the gameboard.
   * 
   * @return Label[][] of squares.
   */
  public TilePane[][] getSquares() {
    return this.squares;
  }

  /**
   * Returns the specified square of the gameboard.
   * 
   * @param row the index of the row.
   * @param col the index of the column.
   * @return the specified square.
   */
  public TilePane getSquare(int row, int col) {
    return squares[row][col];
  }

  /**
   * Sets the style of the specified square.
   * 
   * @param row the index of the row.
   * @param col the index of the column.
   * @param style the css code as String for setStyle.
   */
  public void setSquareStyle(int row, int col, String style) {
    squares[row][col].setStyle(style);
  }

  /**
   * Initializes the squares and square colors and arranges them in the GridPane.
   */
  public void initGameboard() {
    gameGrid = new GridPane();
    gameGrid.setAlignment(Pos.CENTER);

    for (int row = 0; row < Gameboard.ROWS; row++) {
      for (int col = 0; col < Gameboard.COLS; col++) {
        SquareType squareType = board.getSquareAt(row, col).getSquareType();
        if (squareType == SquareType.STD) {
          this.squares[row][col] = new TilePane(height, TilePane.Type.BOARD_TILE);
        } else if (row == Gameboard.ROWS / 2 && col == Gameboard.COLS / 2) {
          this.squares[row][col] = new TilePane(height, TilePane.Type.BOARD_TILE, "\u2605");
        } else {
          this.squares[row][col] =
              new TilePane(height, TilePane.Type.BOARD_TILE, squareType.toString());
        }
        colorSquares(squareType, row, col, "-fx-border-color: white;");
        gameGrid.add(squares[row][col], col, row);
      }
    }
  }

  /**
   * Resets the whole gameboard.
   */
  public void reset() {
    for (int row = 0; row < Gameboard.ROWS; row++) {
      for (int col = 0; col < Gameboard.COLS; col++) {
        colorSquares(board.getSquareAt(row, col).getSquareType(), row, col,
            "-fx-border-color: white;");
      }
    }
  }

  /**
   * Colors the squares according to their squareType.
   * 
   * @param switchType the SquareType to switch.
   * @param row index of the row of the square that should get colored.
   * @param col index of the column of the square that should get colored.
   * @param bordercolor -fx-border-color as String
   */
  public void colorSquares(SquareType switchType, int row, int col, String bordercolor) {
    switch (switchType) {
      case DLS:
        this.squares[row][col].setStyle("-fx-background-color: #19C8FB;" + bordercolor);
        break;
      case DWS:
        this.squares[row][col].setStyle("-fx-background-color: #FE6AE9;" + bordercolor);
        break;
      case TLS:
        this.squares[row][col].setStyle("-fx-background-color: #0C58FA;" + bordercolor);
        break;
      case TWS:
        this.squares[row][col].setStyle("-fx-background-color: #FD0401;" + bordercolor);
        break;
      default:
        this.squares[row][col].setStyle("-fx-background-color: #056301;" + bordercolor);
    }
  }
}
