package main.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

public class TilePane extends StackPane {
  public static final String LETTER_STYLE_RACK =
      "-fx-font-family: Futura;" + "-fx-font-size: 30px;" + "-fx-text-fill: white;";

  public static final String LETTER_STYLE_RACK_DRAGGED =
      "-fx-font-family: Futura;" + "-fx-font-size: 30px;" + "-fx-text-fill: #A6A6A6;";

  public static final String LETTER_STYLE_BOARD =
      "-fx-font-family: Futura;" + "-fx-font-size: 25px;" + "-fx-text-fill: white;";

  public static final String SCORE_STYLE =
      "-fx-font-family: Futura;" + "-fx-font-size: 10px;" + "-fx-text-fill: white;";

  public static final String SCORE_STYLE_DRAGGED =
      "-fx-font-family: Futura;" + "-fx-font-size: 10px;" + "-fx-text-fill: #A6A6A6;";

  public static final String BACKGROUND_OCCUPIED = "-fx-background-color: #2e75b6;";

  public static final String BACKGROUND_RACK_EMPTY = "-fx-background-color: #F2F2F2;";

  public static final String BORDER_RACK = "-fx-border-color: #A6A6A6;";

  public static final String BORDER_RACK_SELECTED = "-fx-border-color: #2e75b6;";

  public static final String BACKGROUND_BOARD_STD = "-fx-background-color: #056301;";

  public static final String BACKGROUND_BOARD_DWS = "-fx-background-color: #FE6AE9;";

  public static final String BACKGROUND_BOARD_TWS = "-fx-background-color: #FD0401;";

  public static final String BACKGROUND_BOARD_DLS = "-fx-background-color: #19C8FB;";

  public static final String BACKGROUND_BOARD_TLS = "-fx-background-color: #0C58FA;";

  public static final String BORDER_BOARD_SELECTED = "-fx-border-color: red;";

  public static final String BORDER_BOARD = "-fx-border-color: white;";

  private static final String SQUARE_TYPE_STYLE =
      "-fx-font-family: Futura;" + "-fx-font-size: 12px;" + "-fx-text-fill: white;";

  private static final String SQUARE_TYPE_STYLE_CENTER =
      "-fx-font-family: Futura;" + "-fx-font-size: 25px;" + "-fx-text-fill: white;";

  public enum Type {
    RACK_TILE, BOARD_TILE;
  }

  /**
   * The type of the tile.
   */
  private Type type;

  /**
   * Label for the letter on the tile.
   */
  private Label letter;

  /**
   * Label for the score for this letter.
   */
  private Label score;

  /**
   * Label for the special squares.
   */
  private Label squareType;

  /**
   * Text of the special squares.
   */
  private String squareTypeText;

  /**
   * Constructs a new panel for the tile.
   * 
   * @param size of the screen.
   * @param type of tile.
   */
  public TilePane(int size, Type type) {
    this(size, type, "");
  }

  /**
   * Constructs a new panel for the tile.
   * 
   * @param size of the screen.
   * @param type of tile.
   * @param squareTypeText of special squares.
   */
  public TilePane(int size, Type type, String squareTypeText) {
    this.letter = new Label();
    this.score = new Label();
    this.squareType = new Label();
    this.type = type;
    this.squareTypeText = squareTypeText;
    this.init(size);
  }

  /**
   * Sets the letter for the tile.
   * 
   * @param letter.
   */
  public void setLetter(char letter) {
    this.letter.setText(Character.toString(letter));
    this.squareType.setText("");
  }

  /**
   * Sets the letter for the tile.
   * 
   * @param letter.
   */
  public void setLetter(String letter) {
    this.letter.setText(letter);
    this.squareType.setText("");
  }

  public String getLetter() {
    return this.letter.getText();
  }

  /**
   * Sets the score of the letter.
   * 
   * @param score.
   */
  public void setScore(int score) {
    this.score.setText(Integer.toString(score));
    this.squareType.setText("");
  }

  /**
   * Returns the letter and the according score.
   * 
   * @return the letter and the according score.
   */
  public String getContent() {
    return this.letter.getText() + " " + this.score.getText();
  }

  /**
   * Sets the letter and the score.
   * 
   * @param content letter and score as String.
   */
  public void setContent(String content) {
    String[] tokens = content.split(" ");
    this.setLetter(tokens[0]);
    this.setScore(Integer.parseInt(tokens[1]));
    this.squareType.setText("");
  }

  public void setContent(String letter, int score) {
    this.setLetter(letter);
    this.setScore(score);
    this.squareType.setText("");
  }

  public void clear() {
    this.letter.setText("");
    this.score.setText("");
    this.squareType.setText(this.squareTypeText);
  }

  /**
   * Returns true if the tile is empty.
   * 
   * @return true if the tile is empty.
   */
  public boolean isEmpty() {
    return this.letter.getText() == null || this.letter.getText().isEmpty();
  }

  public void setFontStyle(String letterFont, String scoreFont) {
    this.letter.setStyle(letterFont);
    this.score.setStyle(scoreFont);
  }

  private void init(int size) {
    switch (this.type) {
      case RACK_TILE:
        this.letter.setStyle(LETTER_STYLE_RACK);
        this.letter.setPrefSize(size * 0.05, size * 0.05);
        this.squareType.setPrefSize(size * 0.05, size * 0.05);
        this.setMaxSize(size * 0.05, size * 0.05);
        break;
      case BOARD_TILE:
        this.letter.setStyle(LETTER_STYLE_BOARD);
        this.letter.setPrefSize(size * 0.044443, size * 0.044443);
        this.squareType.setMaxSize(size * 0.044443, size * 0.044443);
        this.setMaxSize(size * 0.044443, size * 0.044443);
        break;
      default:
    }
    this.squareType.setText(squareTypeText);
    this.squareType.setStyle(SQUARE_TYPE_STYLE);
    this.squareType.setAlignment(Pos.CENTER);

    if (squareTypeText.equals("\u2605")) {
      this.squareType.setStyle(SQUARE_TYPE_STYLE_CENTER);
      this.squareType.setAlignment(Pos.TOP_CENTER);
    }

    Tooltip tooltip = new Tooltip();
    tooltip.setStyle("-fx-font-family: Calibri; -fx-font-size: 12px; "
        + "-fx-background-color: #f8c4c4; -fx-border-radius: 22px; "
        + "-fx-background-radius: 22px; -fx-text-fill: black; -fx-set-stroke: black;");
    switch (squareTypeText) {
      case "\u2605":
        tooltip.setText("Double Word Score");
        break;
      case "DWS":
        tooltip.setText("Double Word Score");
        break;
      case "TWS":
        tooltip.setText("Triple Word Score");
        break;
      case "DLS":
        tooltip.setText("Double Letter Score");
        break;
      case "TLS":
        tooltip.setText("Triple Letter Score");
        break;
      default:
        tooltip = null;
    }

    this.squareType.setTooltip(tooltip);

    this.letter.setAlignment(Pos.TOP_CENTER);
    this.letter.setTextAlignment(TextAlignment.CENTER);

    this.score.setStyle(SCORE_STYLE);
    this.score.setAlignment(Pos.BOTTOM_RIGHT);
    this.score.setTextAlignment(TextAlignment.RIGHT);

    this.setAlignment(Pos.BOTTOM_RIGHT);
    // this.setStyle(TILE_STYLE_OCCUPIED);
    this.getChildren().add(letter);
    this.getChildren().add(score);
    this.getChildren().add(squareType);
  }
}
