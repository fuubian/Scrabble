package main.gui;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import main.model.Gameboard;

/**
 * The class sets the dragAndDrop events for the tiles.
 * 
 * @author ceho
 *
 */

public class DragAndDrop {
  /**
   * Row index of the square that got occupied by a tile.
   */
  private int rowChanged;

  /**
   * Column index of the square that got occupied by a tile.
   */
  private int colChanged;

  /**
   * Indicates whether the squares is still available for a new tile or is already occupied by a
   * different tile.
   */
  private boolean[][] available = new boolean[Gameboard.ROWS][Gameboard.COLS];

  /**
   * Indicates whether the tiles placed on the gameboard can still be changed or not. Tiles can
   * still be changed if they were placed in the current game round.
   */
  private boolean[][] editable = new boolean[Gameboard.ROWS][Gameboard.COLS];

  /**
   * Indicates whether the square is valid for a new tile or not. At the beginning of every round
   * every available square is valid. If you place one tile on the board only the row and column of
   * the placed tiles are valid. If you place further tiles only the according horizontal row or
   * vertical column is valid.
   */
  private boolean[][] valid = new boolean[Gameboard.ROWS][Gameboard.COLS];

  /**
   * Indicated whether the tile is a joker or not.
   */
  private boolean[][] joker = new boolean[Gameboard.ROWS][Gameboard.COLS];

  /**
   * Indicated whether a tile already placed on the board is being drag&dropped to another square on
   * the board or back to the rack.
   */
  private boolean backOnRack = false;

  /**
   * The letter on the tile.
   */
  private String content;

  /**
   * The tiles on the rack.
   */
  private TilePane[] tiles;

  /**
   * The GameboardGui object that displays the gameboard.
   */
  private GameboardGui gameGui;

  /**
   * The Scrabble game object.
   */
  private Gameboard board;

  /**
   * Counts the tiles that are placed on the board in the current round.
   */
  private int countTile = 0;

  /**
   * Indicates the row of the first placed tile in the current round.
   */
  private int firstTileRow;

  /**
   * Indicates the column of the first placed tile in the current round.
   */
  private int firstTileCol;

  /**
   * Indicates whether a joker is being dragged.
   */
  private boolean dragJoker = false;

  /**
   * Indicates whether drag and drop is active or not.
   */
  private boolean active = false;

  /**
   * The constructor of the DragAndDrop class. Initializes static fields.
   * 
   * @param tiles the tiles on the rack.
   * @param gameGui the current gameboard.
   * @param game the current game object.
   */
  public DragAndDrop(TilePane[] tiles, GameboardGui gameGui, Gameboard board) {
    this.tiles = tiles;
    this.gameGui = gameGui;
    this.board = board;

    for (int i = 0; i < Gameboard.ROWS; i++) {
      for (int j = 0; j < Gameboard.COLS; j++) {
        available[i][j] = true;
        editable[i][j] = false;
        valid[i][j] = true;
        joker[i][j] = false;
      }
    }
  }

  /**
   * Returns whether the tiles placed on the gameboard can still be changed or not. Tiles can still
   * be changed if they were placed in the current game round.
   * 
   * @return boolean[][] editable.
   */
  public boolean[][] getEditable() {
    return this.editable;
  }

  /**
   * Returns if the specified square is editable. Editable indicates whether the squares is still
   * available for a new tile or is already occupied by a different tile.
   * 
   * @param row of the square
   * @param col of the square
   * @return boolean value of specified square
   */
  public boolean getEditable(int row, int col) {
    return this.editable[row][col];
  }

  /**
   * Returns true if a tile was being placed in the current round and false if no tile was being
   * placed in the current round.
   * 
   * @return boolean edited.
   */
  public boolean getEdited() {
    boolean edited = false;
    for (int row = 0; row < editable.length; row++) {
      for (int col = 0; col < editable[row].length; col++) {
        if (editable[row][col] == true) {
          edited = true;
          break;
        }
      }
    }
    return edited;
  }

  /**
   * Sets the value for the available array.
   * 
   * @param row of square.
   * @param col of square.
   * @param val of square.
   */
  public void setAvailable(int row, int col, boolean val) {
    available[row][col] = val;
  }

  /**
   * Returns the available value of the specified square.
   * 
   * @param row Row of square.
   * @param col Column of square.
   * @return available value of square.
   */
  public boolean getAvailable(int row, int col) {
    return available[row][col];
  }

  /**
   * Returns the number of new placed tiles from the current round.
   * 
   * @return countTile.
   */
  public int getCountTile() {
    return countTile;
  }

  /**
   * Reverts the last placed tiles.
   */
  public void revert() {
    countTile = 0;

    for (int i = 0; i < Gameboard.ROWS; i++) {
      for (int j = 0; j < Gameboard.COLS; j++) {
        if (editable[i][j]) {
          editable[i][j] = false;
          available[i][j] = true;
          gameGui.getSquare(i, j).clear();
          gameGui.colorSquares(board.getSquareAt(i, j).getSquareType(), i, j,
              "-fx-border-color: white;");
          if (joker[i][j]) {
            joker[i][j] = false;
          }
        }
        valid[i][j] = true;
      }
    }
  }

  /**
   * Sets the current placed word.
   */
  public void set() {
    countTile = 0;

    for (int i = 0; i < editable.length; i++) {
      for (int j = 0; j < editable[i].length; j++) {
        editable[i][j] = false;
      }
    }

    for (int i = 0; i < valid.length; i++) {
      for (int j = 0; j < valid[i].length; j++) {
        valid[i][j] = available[i][j];
      }
    }
  }

  /**
   * Resets the whole gameboard.
   */
  public void reset() {
    countTile = 0;

    for (int i = 0; i < Gameboard.ROWS; i++) {
      for (int j = 0; j < Gameboard.COLS; j++) {
        available[i][j] = true;
        editable[i][j] = false;
        valid[i][j] = true;
        joker[i][j] = false;
      }
    }
  }

  /**
   * Sets drag and drop on active.
   * 
   * @param active.
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Returns true if drag and drop is active.
   * 
   * @return active.
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Executes the drag and drop actions.
   */
  public void start() {
    // countTile = 0;


    /*
     * for (int i = 0; i < editable.length; i++) { for (int j = 0; j < editable[i].length; j++) {
     * editable[i][j] = false; } }
     * 
     * for (int i = 0; i < valid.length; i++) { for (int j = 0; j < valid[i].length; j++) {
     * valid[i][j] = available[i][j]; } }
     */


    for (TilePane tileSource : tiles) {
      for (TilePane tileTarget : tiles) {
        for (int row1 = 0; row1 < Gameboard.ROWS; row1++) {
          for (int col1 = 0; col1 < Gameboard.COLS; col1++) {
            for (int row2 = 0; row2 < Gameboard.ROWS; row2++) {
              for (int col2 = 0; col2 < Gameboard.COLS; col2++) {
                int rowSource = row1;
                int colSource = col1;
                int rowTarget = row2;
                int colTarget = col2;

                // Drag detected of a tile on the rack.
                tileSource.setOnDragDetected(new EventHandler<MouseEvent>() {
                  public void handle(MouseEvent event) {

                    Dragboard db = tileSource.startDragAndDrop(TransferMode.COPY);
                    db.setDragView(tileSource.snapshot(null, null), event.getX(), event.getY());

                    // tileSource.setStyle("-fx-font-family: Futura;" + "-fx-font-size: 30px;"
                    // + "-fx-background-color: #F2F2F2; " + "-fx-border-color: #A6A6A6;"
                    // + "-fx-text-fill: #A6A6A6;");

                    tileSource.setStyle(TilePane.BACKGROUND_RACK_EMPTY + TilePane.BORDER_RACK);
                    // System.out.println("tileSource drag detected");

                    tileSource.setFontStyle(TilePane.LETTER_STYLE_RACK_DRAGGED,
                        TilePane.SCORE_STYLE_DRAGGED);

                    // if (tileSource.getText() != null) {
                    if (!tileSource.isEmpty()) {
                      tileSource.setCursor(Cursor.CLOSED_HAND);
                      // gameGui.getSquare(rowSource, colSource).setCursor(Cursor.CLOSED_HAND);
                    } else {
                      tileSource.setCursor(Cursor.DEFAULT);
                    }

                    ClipboardContent content = new ClipboardContent();
                    // content.putString(tileSource.getText());
                    content.putString(tileSource.getContent());

                    db.setContent(content);

                    event.consume();
                  }
                });

                // Drag detected of a tile on the board.
                gameGui.getSquare(rowSource, colSource)
                    .setOnDragDetected(new EventHandler<MouseEvent>() {
                      public void handle(MouseEvent event) {

                        if (editable[rowSource][colSource]) {
                          Dragboard db = gameGui.getSquare(rowSource, colSource)
                              .startDragAndDrop(TransferMode.COPY);
                          db.setDragView(
                              gameGui.getSquare(rowSource, colSource).snapshot(null, null),
                              event.getX(), event.getY());

                          gameGui.setSquareStyle(rowSource, colSource,
                              "-fx-background-color: #A6A6A6;"
                                  + "-fx-border-color: white; -fx-font-family: Futura;"
                                  + "-fx-font-size: 20px;" + "-fx-text-fill: #F2F2F2;");

                          // tileSource.setCursor(Cursor.CLOSED_HAND);
                          gameGui.getSquare(rowSource, colSource).setCursor(Cursor.CLOSED_HAND);

                          ClipboardContent content = new ClipboardContent();

                          if (!gameGui.getSquare(rowSource, colSource).isEmpty()) {
                            content.putString(gameGui.getSquare(rowSource, colSource).getContent());
                            db.setContent(content);
                          }

                          if (joker[rowSource][colSource]) {
                            dragJoker = true;
                            joker[rowSource][colSource] = false;
                          }

                          countTile--;
                          editable[rowSource][colSource] = false;

                          if (countTile == 1) {
                            for (int i = 0; i < valid.length; i++) {
                              for (int j = 0; j < valid[i].length; j++) {
                                valid[i][j] = true;
                              }
                            }

                            for (int i = 0; i < valid.length; i++) {
                              for (int j = 0; j < valid[i].length; j++) {
                                if (editable[i][j]) {
                                  firstTileRow = i;
                                  firstTileCol = j;
                                }
                              }
                            }

                            for (int i = 0; i < valid.length; i++) {
                              for (int j = 0; j < valid[i].length; j++) {
                                if (i != firstTileRow && j != firstTileCol) {
                                  valid[i][j] = false;
                                }
                              }
                            }
                          } else if (countTile == 0) {
                            for (int i = 0; i < valid.length; i++) {
                              for (int j = 0; j < valid[i].length; j++) {
                                valid[i][j] = true;
                              }
                            }
                          }

                          for (int i = 0; i < valid.length; i++) {
                            for (int j = 0; j < valid[i].length; j++) {
                              // System.out.print(joker[i][j] ? 1 : 0);
                            }
                            // System.out.println();
                          }
                          // System.out.println(countTile);
                          // System.out.println();

                        } else {
                          gameGui.getSquare(rowSource, colSource).setCursor(Cursor.DEFAULT);
                        }
                        event.consume();
                      }
                    });

                // Drag over to a square on the board.
                gameGui.getSquare(rowTarget, colTarget)
                    .setOnDragOver(new EventHandler<DragEvent>() {
                      public void handle(DragEvent event) {

                        if (event.getGestureSource() != gameGui.getSquare(rowTarget, colTarget)
                            && event.getDragboard().hasString() && available[rowTarget][colTarget]
                            && valid[rowTarget][colTarget] && active) {
                          event.acceptTransferModes(TransferMode.COPY);

                          // tileSource.setCursor(Cursor.CLOSED_HAND);
                          // gameGui.getSquare(rowSource, colSource).setCursor(Cursor.CLOSED_HAND);
                          gameGui.getSquare(rowTarget, colTarget).setCursor(Cursor.CLOSED_HAND);
                        } else {
                          gameGui.getSquare(rowTarget, colTarget).setCursor(Cursor.DEFAULT);
                        }
                        event.consume();
                      }
                    });

                // Drag over to the rack.
                tileTarget.setOnDragOver(new EventHandler<DragEvent>() {
                  public void handle(DragEvent event) {

                    if (event.getGestureSource() != tileTarget
                        && event.getDragboard().hasString()) { // && tileTarget.getText() == null
                      event.acceptTransferModes(TransferMode.COPY);

                      tileTarget.setCursor(Cursor.CLOSED_HAND);
                    } else {
                      tileTarget.setCursor(Cursor.DEFAULT);
                    }
                    event.consume();
                  }
                });

                // Drag entered a square on the board.
                gameGui.getSquare(rowTarget, colTarget)
                    .setOnDragEntered(new EventHandler<DragEvent>() {
                      public void handle(DragEvent event) {
                        if (event.getGestureSource() != gameGui.getSquare(rowTarget, colTarget)
                            && event.getDragboard().hasString() && active) {
                          if (countTile > 0) {
                            for (int i = 0; i < valid.length; i++) {
                              for (int j = 0; j < valid[i].length; j++) {
                                if (valid[i][j] && !editable[i][j] && available[i][j]) {
                                  gameGui.getSquare(i, j).setOpacity(0.65);
                                }
                              }
                            }
                          }
                          if (available[rowTarget][colTarget] && valid[rowTarget][colTarget]) {
                            gameGui.colorSquares(
                                board.getSquareAt(rowTarget, colTarget).getSquareType(), rowTarget,
                                colTarget, "-fx-border-color: red;");

                            // tileSource.setCursor(Cursor.CLOSED_HAND);
                            // gameGui.getSquare(rowSource,
                            // colSource).setCursor(Cursor.CLOSED_HAND);
                            gameGui.getSquare(rowTarget, colTarget).setCursor(Cursor.CLOSED_HAND);
                          }
                        } else {
                          gameGui.getSquare(rowTarget, colTarget).setCursor(Cursor.DEFAULT);
                        }

                        event.consume();
                      }
                    });

                // Drag entered the rack.
                tileTarget.setOnDragEntered(new EventHandler<DragEvent>() {
                  public void handle(DragEvent event) {
                    if (event.getGestureSource() != tileTarget
                        && event.getDragboard().hasString()) {
                      // if (tileTarget.getText() == null) {
                      if (tileTarget.isEmpty()) {
                        // tileTarget.setStyle("-fx-font-family: Futura;" + "-fx-font-size: 30px;"
                        // + "-fx-background-color: #F2F2F2; " + "-fx-border-color: #2e75b6;"
                        // + "-fx-text-fill: #A6A6A6;");
                        tileTarget.setStyle(
                            TilePane.BACKGROUND_RACK_EMPTY + TilePane.BORDER_RACK_SELECTED);
                        // System.out.println("tileTarget drag entered");

                      }
                      // else {
                      // tileTarget.setOpacity(0.75);
                      // }
                      content = tileTarget.getContent();
                      // letter = tileTarget
                      tileTarget.setCursor(Cursor.CLOSED_HAND);
                    } else {
                      tileTarget.setCursor(Cursor.DEFAULT);
                    }

                    event.consume();
                  }
                });

                // Drag exited a square on the board.
                gameGui.getSquare(rowTarget, colTarget)
                    .setOnDragExited(new EventHandler<DragEvent>() {
                      public void handle(DragEvent event) {
                        if (countTile > 0) {
                          for (int i = 0; i < valid.length; i++) {
                            for (int j = 0; j < valid[i].length; j++) {
                              if (valid[i][j] && !editable[i][j] && available[i][j]) {
                                gameGui.getSquare(i, j).setOpacity(1);
                              }
                            }
                          }
                        }
                        if (available[rowTarget][colTarget] && valid[rowTarget][colTarget]
                            && active) {
                          gameGui.colorSquares(
                              board.getSquareAt(rowTarget, colTarget).getSquareType(), rowTarget,
                              colTarget, "-fx-border-color: white;");
                          // tileSource.setCursor(Cursor.CLOSED_HAND);
                          // gameGui.getSquare(rowSource, colSource).setCursor(Cursor.CLOSED_HAND);
                          gameGui.getSquare(rowTarget, colTarget).setCursor(Cursor.CLOSED_HAND);
                        }

                        event.consume();
                      }
                    });

                // Drag exited the rack.
                tileTarget.setOnDragExited(new EventHandler<DragEvent>() {
                  public void handle(DragEvent event) {
                    // if (tileTarget.getText() == null) {
                    if (tileTarget.isEmpty()) {
                      // tileTarget.setStyle("-fx-font-family: Futura;" + "-fx-font-size: 30px;"
                      // + "-fx-background-color: #F2F2F2; " + "-fx-border-color: #A6A6A6;"
                      // + "-fx-text-fill: #A6A6A6;");

                      tileTarget.setStyle(TilePane.BACKGROUND_RACK_EMPTY + TilePane.BORDER_RACK);
                      // System.out.println("tileTarget drag exited");

                    }
                    // else {
                    // tileTarget.setOpacity(1);
                    // }
                    tileTarget.setCursor(Cursor.CLOSED_HAND);

                    event.consume();
                  }
                });

                // Drag dropped on a square on the board.
                gameGui.getSquare(rowTarget, colTarget)
                    .setOnDragDropped(new EventHandler<DragEvent>() {
                      public void handle(DragEvent event) {
                        backOnRack = false;
                        Dragboard db = event.getDragboard();
                        boolean success = false;

                        if (db.hasString()) {
                          String content = db.getString();

                          if (content.charAt(0) == '*') {
                            ChooseJokerBox jokerBox = new ChooseJokerBox();
                            jokerBox.displayJoker(Character.toString(content.charAt(0)));
                            content = jokerBox.getLetter() + content.substring(1);
                            joker[rowTarget][colTarget] = true;
                          }

                          if (dragJoker) {
                            // System.out.println("Dropped");
                            dragJoker = false;
                            joker[rowTarget][colTarget] = true;
                          }

                          for (int i = 0; i < valid.length; i++) {
                            for (int j = 0; j < valid[i].length; j++) {
                              // System.out.print(joker[i][j] ? 1 : 0);
                            }
                            // System.out.println();
                          }
                          // System.out.println(countTile);
                          // System.out.println();

                          gameGui.getSquare(rowTarget, colTarget).setContent(content);

                          rowChanged = rowTarget;
                          colChanged = colTarget;

                          success = true;
                        }
                        gameGui.getSquare(rowTarget, colTarget).setCursor(Cursor.OPEN_HAND);

                        event.setDropCompleted(success);
                        event.consume();
                      }
                    });

                // Drag dropped on the rack.
                tileTarget.setOnDragDropped(new EventHandler<DragEvent>() {
                  public void handle(DragEvent event) {
                    backOnRack = true;
                    Dragboard db = event.getDragboard();
                    boolean success = false;

                    if (db.hasString() && tileTarget.isEmpty()) {
                      // if (tileTarget.isEmpty()) {
                      if (dragJoker) {
                        // tileTarget.setText("*");
                        tileTarget.setContent("*", 0);
                        dragJoker = false;
                      } else {
                        // tileTarget.setText(db.getString());
                        tileTarget.setContent(db.getString());
                      }
                      // tileTarget.setStyle("-fx-font-family: Futura;" + "-fx-font-size: 30px;"
                      // + "-fx-background-color: #2e75b6; " + "-fx-border-color: #A6A6A6;"
                      // + "-fx-text-fill: white;");
                      tileTarget.setStyle(TilePane.BACKGROUND_OCCUPIED + TilePane.BORDER_RACK);
                      tileTarget.setFontStyle(TilePane.LETTER_STYLE_RACK, TilePane.SCORE_STYLE);
                      // System.out.println("tileTarget drag dropped");

                      success = true;
                      // }
                    }
                    tileTarget.setCursor(Cursor.OPEN_HAND);
                    event.setDropCompleted(success);
                    event.consume();
                  }
                });

                // Drag done from a tile on the rack.
                tileSource.setOnDragDone(new EventHandler<DragEvent>() {
                  public void handle(DragEvent event) {

                    if (event.getTransferMode() == TransferMode.COPY) {

                      if (!backOnRack) { // TODO
                        if (joker[rowChanged][colChanged]) {
                          gameGui.setSquareStyle(rowChanged, colChanged,
                              "-fx-background-color: #7471C3;"
                                  + "-fx-border-color: white; -fx-font-family: Futura;"
                                  + "-fx-font-size: 20px;" + "-fx-text-fill: #D9D9D9;");
                        } else {
                          gameGui.setSquareStyle(rowChanged, colChanged,
                              "-fx-background-color: #2E8CCD;"
                                  + "-fx-border-color: white; -fx-font-family: Futura;"
                                  + "-fx-font-size: 20px;" + "-fx-text-fill: #D9D9D9;");
                        }

                        available[rowChanged][colChanged] = false;
                        editable[rowChanged][colChanged] = true;

                        countTile++;

                        if (countTile == 1) {
                          firstTileRow = rowChanged;
                          firstTileCol = colChanged;
                        }

                        for (int i = 0; i < valid.length; i++) {
                          for (int j = 0; j < valid[i].length; j++) {
                            if (i != rowChanged && j != colChanged) {
                              valid[i][j] = false;
                            }
                          }
                        }

                        for (int i = 0; i < valid.length; i++) {
                          for (int j = 0; j < valid[i].length; j++) {
                            // System.out.print(valid[i][j] ? 1 : 0);
                          }
                          // System.out.println();
                        }
                        // System.out.println(countTile);
                        // System.out.println();

                        // tileSource.setText(null);
                        // tileSource.clear();
                      }
                      // else {
                      // tileSource.setContent(content);
                      // // tileSource.set
                      // }

                      tileSource.clear();

                      // if (tileSource.getText() == null) {
                      if (tileSource.isEmpty()) {
                        // tileSource.setStyle(
                        // "-fx-background-color: #F2F2F2; " + "-fx-border-color: #A6A6A6;");
                        tileSource.setStyle(TilePane.BACKGROUND_RACK_EMPTY + TilePane.BORDER_RACK);
                        // System.out.println("tileSource drag done and empty");
                      } else {
                        // tileSource.setStyle("-fx-font-family: Futura;" + "-fx-font-size: 30px;"
                        // + "-fx-background-color: #2e75b6; " + "-fx-border-color: #A6A6A6;"
                        // + "-fx-text-fill: white;");
                        tileSource.setStyle(TilePane.BACKGROUND_OCCUPIED + TilePane.BORDER_RACK);
                        tileSource.setFontStyle(TilePane.LETTER_STYLE_RACK, TilePane.SCORE_STYLE);
                        // System.out.println("tileSource drag done and not empty");
                      }

                      tileSource.setCursor(Cursor.OPEN_HAND);
                    } else {
                      // tileSource.setStyle("-fx-font-family: Futura;" + "-fx-font-size: 30px;"
                      // + "-fx-background-color: #2e75b6; " + "-fx-border-color: #A6A6A6;"
                      // + "-fx-text-fill: white;");
                      tileSource.setStyle(TilePane.BACKGROUND_OCCUPIED + TilePane.BORDER_RACK);
                      tileSource.setFontStyle(TilePane.LETTER_STYLE_RACK, TilePane.SCORE_STYLE);
                      // System.out.println("tileSource drag done nothing dragged");
                    }

                    event.consume();
                  }
                });

                // Drag done from a tile on the board.
                gameGui.getSquare(rowSource, colSource)
                    .setOnDragDone(new EventHandler<DragEvent>() {
                      public void handle(DragEvent event) {
                        if (event.getTransferMode() == TransferMode.COPY) {

                          if (!backOnRack) {
                            if (joker[rowChanged][colChanged]) { // TODO
                              gameGui.setSquareStyle(rowChanged, colChanged,
                                  "-fx-background-color: #7471C3;"
                                      + "-fx-border-color: white; -fx-font-family: Futura;"
                                      + "-fx-font-size: 20px;" + "-fx-text-fill: #D9D9D9;");
                            } else {
                              gameGui.setSquareStyle(rowChanged, colChanged,
                                  "-fx-background-color: #2E8CCD;"
                                      + "-fx-border-color: white; -fx-font-family: Futura;"
                                      + "-fx-font-size: 20px;" + "-fx-text-fill: #D9D9D9;");
                            }

                            available[rowChanged][colChanged] = false;
                            editable[rowChanged][colChanged] = true;
                            countTile++;
                          }
                          // else {
                          // if (joker[rowSource][colSource]) {
                          //
                          // }
                          // }


                          available[rowSource][colSource] = true;
                          editable[rowSource][colSource] = false;

                          for (int i = 0; i < valid.length; i++) {
                            for (int j = 0; j < valid[i].length; j++) {
                              if (i != rowChanged && j != colChanged) {
                                valid[i][j] = false;
                              }
                            }
                          }

                          if (countTile == 1) {
                            for (int i = 0; i < valid.length; i++) {
                              for (int j = 0; j < valid[i].length; j++) {
                                valid[i][j] = true;
                              }
                            }

                            for (int i = 0; i < valid.length; i++) {
                              for (int j = 0; j < valid[i].length; j++) {
                                if (editable[i][j]) {
                                  firstTileRow = i;
                                  firstTileCol = j;
                                }
                              }
                            }

                            for (int i = 0; i < valid.length; i++) {
                              for (int j = 0; j < valid[i].length; j++) {
                                if (i != firstTileRow && j != firstTileCol) {
                                  valid[i][j] = false;
                                }
                              }
                            }
                          } else if (countTile == 0) {
                            for (int i = 0; i < valid.length; i++) {
                              for (int j = 0; j < valid[i].length; j++) {
                                valid[i][j] = true;
                              }
                            }
                          }

                          for (int i = 0; i < valid.length; i++) {
                            for (int j = 0; j < valid[i].length; j++) {
                              // System.out.print(valid[i][j] ? 1 : 0);
                            }
                            // System.out.println();
                          }
                          // System.out.println(countTile);
                          // System.out.println();

                          // if (rowSource == Gameboard.squareIdToRow(Gameboard.CENTER_SQUARE_ID)
                          // && colSource == Gameboard.squareIdToCol(Gameboard.CENTER_SQUARE_ID)) {
                          // gameGui.getSquare(rowSource, colSource).clear();
                          // gameGui.getSquare(rowSource, colSource).setLetter("\u2605");
                          // } else {
                          gameGui.getSquare(rowSource, colSource).clear();
                          // }

                          gameGui.colorSquares(
                              board.getSquareAt(rowSource, colSource).getSquareType(), rowSource,
                              colSource, "-fx-border-color: white;");

                          gameGui.getSquare(rowSource, colSource).setCursor(Cursor.OPEN_HAND);
                        } else {
                          // System.out.println("Done");
                          if (dragJoker) {
                            dragJoker = false;
                            joker[rowSource][colSource] = true;
                            gameGui.setSquareStyle(rowSource, colSource,
                                "-fx-background-color: #7471C3;"
                                    + "-fx-border-color: white; -fx-font-family: Futura;"
                                    + "-fx-font-size: 20px;" + "-fx-text-fill: #D9D9D9;");
                            // System.out.println("yay joker");
                          } else {
                            gameGui.setSquareStyle(rowSource, colSource,
                                "-fx-background-color: #2E8CCD;"
                                    + "-fx-border-color: white; -fx-font-family: Futura;"
                                    + "-fx-font-size: 20px;" + "-fx-text-fill: #D9D9D9;");
                            // System.out.println("oh no");
                          }

                          countTile++;
                          editable[rowSource][colSource] = true;

                          for (int i = 0; i < valid.length; i++) {
                            for (int j = 0; j < valid[i].length; j++) {
                              if (i != rowSource && j != colSource) {
                                valid[i][j] = false;
                              }
                            }
                          }

                          if (countTile == 1) {
                            for (int i = 0; i < valid.length; i++) {
                              for (int j = 0; j < valid[i].length; j++) {
                                valid[i][j] = true;
                              }
                            }

                            for (int i = 0; i < valid.length; i++) {
                              for (int j = 0; j < valid[i].length; j++) {
                                if (editable[i][j]) {
                                  firstTileRow = i;
                                  firstTileCol = j;
                                }
                              }
                            }

                            for (int i = 0; i < valid.length; i++) {
                              for (int j = 0; j < valid[i].length; j++) {
                                if (i != firstTileRow && j != firstTileCol) {
                                  valid[i][j] = false;
                                }
                              }
                            }
                          }

                          for (int i = 0; i < valid.length; i++) {
                            for (int j = 0; j < valid[i].length; j++) {
                              // System.out.print(joker[i][j] ? 1 : 0);
                            }
                            // System.out.println();
                          }
                          // System.out.println(countTile);
                          // System.out.println();
                        }
                        event.consume();
                      }
                    });

                // if (editable[rowSource][colSource]) {
                gameGui.getSquare(rowSource, colSource)
                    .setOnMouseClicked(new EventHandler<MouseEvent>() {
                      public void handle(MouseEvent me) {
                        if (editable[rowSource][colSource] && joker[rowSource][colSource]) {
                          ChooseJokerBox jokerBox = new ChooseJokerBox();
                          jokerBox
                              .displayJoker(gameGui.getSquare(rowSource, colSource).getLetter());
                          gameGui.getSquare(rowSource, colSource).setLetter(jokerBox.getLetter());
                        }
                      }
                    });
                // }
              }
            }
          }
        }
      }
    }
  }
}
