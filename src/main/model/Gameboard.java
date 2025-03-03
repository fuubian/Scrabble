package main.model;

import java.io.Serializable;

/**
 * The Gameboard class represents a gameboard.
 * 
 * @author sekeller
 */
public class Gameboard implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The number of rows.
   */
  public static final int ROWS = 15;

  /**
   * The number of columns.
   */
  public static final int COLS = 15;

  /**
   * The id of the center square.
   */
  public static final String CENTER_SQUARE_ID = "H8";

  /**
   * The square ids of the triple word score squares.
   */
  private static final String SQUARE_IDS_TWS = "A1,A8,A15,H1,H15,O1,O8,O15";

  /**
   * The square ids of the triple letter score squares.
   */
  private static final String SQUARE_IDS_TLS = "B6,B10,F2,F6,F10,F14,J2,J6,J10,J14,N6,N10";

  /**
   * The square ids of the double word score squares.
   */
  private static final String SQUARE_IDS_DWS =
      "B2,B14,C3,C13,D4,D12,E5,E11,H8,K5,K11,L4,L12,M3,M13,N2,N14";

  /**
   * The square ids of the double letter score squares.
   */
  private static final String SQUARE_IDS_DLS =
      "A4,A12,C7,C9,D1,D8,D15,G3,G7,G9,G13,H4,H12,I3,I7,I9,I13,L1,L8,L15,M7,M9,O4,O12";

  /**
   * The squares of the gameboard.
   */
  private final Square[][] squares;

  /**
   * Constructs an empty gameboard.
   */
  Gameboard() {
    this.squares = new Square[ROWS][COLS];

    for (String squareId : SQUARE_IDS_TWS.split(",")) {
      this.setSquareAt(squareId, SquareType.TWS);
    }

    for (String squareId : SQUARE_IDS_TLS.split(",")) {
      this.setSquareAt(squareId, SquareType.TLS);
    }

    for (String squareId : SQUARE_IDS_DWS.split(",")) {
      this.setSquareAt(squareId, SquareType.DWS);
    }

    for (String squareId : SQUARE_IDS_DLS.split(",")) {
      this.setSquareAt(squareId, SquareType.DLS);
    }

    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        if (this.squares[row][col] == null) {
          this.setSquareAt(row, col, SquareType.STD);
        }
      }
    }
  }

  /**
   * Constructs a copy of the specified gameboard.
   * 
   * @param original the specified gameboard
   */
  Gameboard(Gameboard original) {
    this.squares = new Square[ROWS][COLS];

    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        this.squares[row][col] = new Square(original.squares[row][col]);
      }
    }
  }

  /**
   * Sets the square of this gameboard at the specified row and the specified column with a new
   * square with the specified square type.
   * 
   * @param row the specified row
   * @param col the specified column
   * @param squareType the specified square type
   */
  private void setSquareAt(int row, int col, SquareType squareType) {
    this.squares[row][col] = new Square(squareType);
  }

  /**
   * Sets the square of this gameboard at the specified square id with a new square with the
   * specified square type.
   * 
   * @param squareId the specified square id
   * @param squareType the specified square type
   */
  private void setSquareAt(String squareId, SquareType squareType) {
    int row = squareIdToRow(squareId);
    int col = squareIdToCol(squareId);

    this.squares[row][col] = new Square(squareType);
    this.setSquareAt(row, col, squareType);
  }

  /**
   * Returns the square at the specified row and the specified column.
   * 
   * @param row the specified row
   * @param col the specified column
   * @return the square at the specified row and the specified column
   */
  public Square getSquareAt(int row, int col) {
    return this.squares[row][col];
  }

  /**
   * Returns the square at the specified square id.
   * 
   * @param squareType the specified square type
   * @return the square at the specified square id
   */
  public Square getSquareAt(String squareId) {
    int row = squareIdToRow(squareId);
    int col = squareIdToCol(squareId);

    return this.getSquareAt(row, col);
  }

  /**
   * Returns true if the gameboard is empty.
   * 
   * @return true if the gameboard is empty
   */
  public boolean isEmpty() {
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        if (!this.squares[row][col].isEmpty()) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Returns true when the specified square id is a valid square id.
   * 
   * @param squareId the specified square id
   * @return true when the specified square id is a valid square id
   */
  public static boolean isSquareIdValid(String squareId) {
    return squareId.matches("[A-O]([1-9]|1[0-5])");
  }

  /**
   * Returns the square id of the specified row and the specified column.
   * 
   * @param row the specified row
   * @param col the specified column
   * @return the square id of the specified row and the specified column
   */
  public static String computeSquareId(int row, int col) {
    return Character.toString((char) ('A' + col)) + Integer.toString(row + 1);
  }

  /**
   * Returns the row of the specified square id.
   * 
   * @param squareId the specified square id
   * @return the row of the specified square id
   */
  public static int squareIdToRow(String squareId) {
    return Integer.parseInt(squareId.substring(1)) - 1;
  }

  /**
   * Returns the column of the specified square id.
   * 
   * @param squareId the specified square id
   * @return the column of the specified square id
   */
  public static int squareIdToCol(String squareId) {
    return squareId.charAt(0) - 'A';
  }

}
