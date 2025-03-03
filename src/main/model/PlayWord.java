package main.model;

/**
 * The PlayWord class represents a move to play a word.
 * 
 * @author sekeller
 */
public class PlayWord extends Move {

  private static final long serialVersionUID = 1L;

  /**
   * The word to be laid.
   */
  private final String word;

  /**
   * The square id where the word should be placed.
   */
  private final String squareId;

  /**
   * The direction in which the word is to be placed.
   */
  private final Direction direction;

  /**
   * The score of the move.
   */
  private int score;

  /**
   * Constructs a play word move with the specified word, the specified squareId and the specified
   * direction for the specified game.
   * 
   * @param word word the specified word
   * @param squareId the specified squareId
   * @param direction the specified direction
   * @param game the specified game
   */
  PlayWord(Scrabble game, String word, String squareId, Direction direction) {
    super(game);
    this.word = word;
    this.squareId = squareId;
    this.direction = direction;

    this.initMoveException();
    this.initScore();
  }

  /**
   * Constructs a copy of the specified play word move.
   * 
   * @param original the specified play word move
   */
  PlayWord(PlayWord original) {
    super();

    this.player = original.player;
    this.gameException = new GameException(original.gameException);
    this.time = original.time;

    this.word = original.word;
    this.squareId = original.squareId;
    this.direction = original.direction;
    this.score = original.score;
  }

  /**
   * Returns the word of this play word move.
   * 
   * @return the word of this play word move
   */
  public String getWord() {
    return this.word;
  }

  /**
   * Returns the square id of this play word move.
   * 
   * @return the square id of this play word move
   */
  public String getSquareId() {
    return this.squareId;
  }

  /**
   * Returns the direction of this play word move.
   * 
   * @return the direction of this play word move
   */
  public Direction getDirection() {
    return this.direction;
  }

  @Override
  public int getScore() {
    return this.score;
  }

  @Override
  void execute() throws GameException {
    if (this.gameException != null) {
      throw this.gameException;
    }

    int min_row = Gameboard.squareIdToRow(this.squareId);
    int min_col = Gameboard.squareIdToCol(this.squareId);

    Rack rack = this.game.getCurrentPlayer().getRack();

    for (int i = 0; i < this.word.length(); i++) {
      if (this.direction.equals(Direction.VERTICAL)) {
        Square square = this.game.getGameboard().getSquareAt(min_row + i, min_col);

        if (square.isEmpty()) {
          Tile tile = rack.getTile(this.word.charAt(i));

          if (tile.getLetter() == '*') {
            tile.setLetter(this.word.charAt(i));
          }

          rack.removeTile(tile);
          square.setTile(tile);
        }
      } else {
        Square square = this.game.getGameboard().getSquareAt(min_row, min_col + i);

        if (square.isEmpty()) {
          Tile tile = rack.getTile(this.word.charAt(i));

          if (tile.getLetter() == '*') {
            tile.setLetter(this.word.charAt(i));
          }

          rack.removeTile(tile);
          square.setTile(tile);
        }
      }
    }

    this.game.getCurrentPlayer().addScore(this.getScore());
    this.game.resetScorelessMoveCount();

    while (!rack.isFull() && !this.game.getTileBag().isEmpty()) {
      Tile tile = this.game.getTileBag().removeTile();
      rack.addTile(tile);
    }

    if (rack.isEmpty()) {
      this.game.setGameState(GameState.GAME_OVER);
    } else {
      this.game.updateCurrentPlayer();
    }
  }

  @Override
  public String toString() {
    return "PlayWord(" + this.word + "," + this.squareId + "," + this.direction + ")";
  }

  /**
   * Initializes the moveException.
   */
  private void initMoveException() {
    if (this.game.getGameState() == GameState.PREPERATION) {
      this.gameException = new GameException("The game has not started yet!");
      return;
    }

    if (this.game.getGameState() == GameState.GAME_OVER) {
      this.gameException = new GameException("The game is over!");
      return;
    }

    if (this.word == null || this.word.isEmpty()) {
      this.gameException = new GameException("You have to play at least one tile on the board!");
      return;
    }

    if (this.squareId == null || !Gameboard.isSquareIdValid(this.squareId)) {
      this.gameException = new GameException("The square id is not valid: " + this.squareId);
      return;
    }

    if (this.direction == null) {
      this.gameException = new GameException("A direction unequal to null must be selected!");
      return;
    }

    int minRow = Gameboard.squareIdToRow(this.squareId);
    int minCol = Gameboard.squareIdToCol(this.squareId);
    int maxRow = minRow + (this.direction == Direction.VERTICAL ? this.word.length() - 1 : 0);
    int maxCol = minCol + (this.direction == Direction.HORIZONTAL ? this.word.length() - 1 : 0);

    if (maxRow >= Gameboard.ROWS || maxCol >= Gameboard.COLS) {
      this.gameException = new GameException("Some tiles are outside of the gameboard!");
      return;
    }

    if (!this.game.getDictionary().contains(this.word)) {
      this.gameException =
          new GameException("The dictionary does not contain the word: " + this.word);
      return;
    }

    Gameboard gameboard = this.game.getGameboard();

    if (this.direction.equals(Direction.VERTICAL)) {
      if (minRow - 1 >= 0 && !gameboard.getSquareAt(minRow - 1, minCol).isEmpty()) {
        this.gameException =
            new GameException("The word does not start at row " + (minRow + 1) + "!");
        return;
      }

      if (maxRow + 1 < Gameboard.ROWS && !gameboard.getSquareAt(maxRow + 1, minCol).isEmpty()) {
        this.gameException =
            new GameException("The word does not end at row " + (maxRow + 1) + "!");
        return;
      }
    } else {
      if (minCol - 1 >= 0 && !gameboard.getSquareAt(minRow, minCol - 1).isEmpty()) {
        this.gameException =
            new GameException("The word does not start at col " + (char) (minCol + 1) + "!");
        return;
      }

      if (maxCol + 1 < Gameboard.ROWS && !gameboard.getSquareAt(minRow, maxCol + 1).isEmpty()) {
        this.gameException =
            new GameException("The word does not end at col " + (char) (maxCol + 1) + "!");
        return;
      }
    }

    for (int i = 0; i < this.word.length(); i++) {
      if (this.direction.equals(Direction.VERTICAL)) {
        Square square = this.game.getGameboard().getSquareAt(minRow + i, minCol);
        if (!square.isEmpty() && square.getTile().getLetter() != this.word.charAt(i)) {
          this.gameException =
              new GameException("There is a conflict with tiles on the gameboard!");
          return;
        }
      } else {
        Square square = this.game.getGameboard().getSquareAt(minRow, minCol + i);
        if (!square.isEmpty() && square.getTile().getLetter() != this.word.charAt(i)) {
          this.gameException =
              new GameException("There is a conflict with tiles on the gameboard!");
          return;
        }
      }
    }

    String neededLetters = "";

    for (int i = 0; i < this.word.length(); i++) {
      if (this.direction.equals(Direction.VERTICAL)) {
        Square square = this.game.getGameboard().getSquareAt(minRow + i, minCol);
        if (square.isEmpty()) {
          neededLetters += this.word.charAt(i);
        }
      } else {
        Square square = this.game.getGameboard().getSquareAt(minRow, minCol + i);
        if (square.isEmpty()) {
          neededLetters += this.word.charAt(i);
        }
      }
    }

    if (neededLetters.isEmpty()) {
      this.gameException = new GameException("The word is already on the gameboard!");
      return;
    }

    String ownedLetters = "";
    Rack rack = this.game.getCurrentPlayer().getRack();

    for (int i = 0; i < rack.size(); i++) {
      ownedLetters += rack.getTile(i).getLetter();
    }

    if (!new LetterHistogram(neededLetters).isLessOrEqualTo(new LetterHistogram(ownedLetters))) {
      this.gameException =
          new GameException("The rack does not contain the needed letters to play the word!");
      return;
    }

    if (gameboard.isEmpty()) {
      int center_row = Gameboard.squareIdToRow(Gameboard.CENTER_SQUARE_ID);
      int center_col = Gameboard.squareIdToCol(Gameboard.CENTER_SQUARE_ID);

      if (this.direction.equals(Direction.HORIZONTAL)
          && (minRow != center_row || minCol > center_col || maxCol < center_col)) {
        this.gameException = new GameException("The first word must cover the center square!");
        return;
      }

      if (this.direction.equals(Direction.VERTICAL)
          && (minCol != center_col || minRow > center_row || maxRow < center_row)) {
        this.gameException = new GameException("The first word musst cover the center square!");
        return;
      }
    } else {
      boolean verticalAdjacentTile = false;
      boolean horizontalAdjacentTile = false;

      if (this.direction == Direction.VERTICAL) {
        for (int row = minRow; row <= maxRow; row++) {
          if (minCol > 0) {
            Square leftSquare = gameboard.getSquareAt(row, minCol - 1);

            if (!leftSquare.isEmpty()) {
              horizontalAdjacentTile = true;
              break;
            }
          }

          if (minCol < Gameboard.COLS - 1) {
            Square rightSquare = gameboard.getSquareAt(row, minCol + 1);

            if (!rightSquare.isEmpty()) {
              horizontalAdjacentTile = true;
              break;
            }
          }
        }
      } else {
        for (int col = minCol; col <= maxCol; col++) {
          if (minRow > 0) {
            Square upperSquare = gameboard.getSquareAt(minRow - 1, col);

            if (!upperSquare.isEmpty()) {
              verticalAdjacentTile = true;
              break;
            }
          }

          if (minRow < Gameboard.ROWS - 1) {
            Square lowerSquare = gameboard.getSquareAt(minRow + 1, col);

            if (!lowerSquare.isEmpty()) {
              verticalAdjacentTile = true;
              break;
            }
          }
        }
      }

      boolean useExistingLetter = !neededLetters.equals(this.word);

      if (!useExistingLetter && !verticalAdjacentTile && !horizontalAdjacentTile) {
        this.gameException = new GameException("The main word must either use the letters of one"
            + " or more previously played words or else have at least one of its tiles horizontally"
            + " or vertically adjacent to an already played word!");
        return;
      }

      int row = Gameboard.squareIdToRow(this.squareId);
      int col = Gameboard.squareIdToCol(this.squareId);

      if (this.direction == Direction.HORIZONTAL) {
        for (int i = 0; i < this.word.length(); i++) {
          Square square = gameboard.getSquareAt(row, col + i);

          if (square.isEmpty()) {
            String word = this.getSideWord(row, col + i, this.word.charAt(i));

            if (word.length() > 1) {

              if (!this.game.getDictionary().contains(word)) {
                this.gameException =
                    new GameException("The dictionary does not contain the word: " + word);
                return;
              }
            }
          }
        }
      } else {
        for (int i = 0; i < this.word.length(); i++) {
          Square square = gameboard.getSquareAt(row + i, col);

          if (square.isEmpty()) {
            String word = this.getSideWord(row + i, col, this.word.charAt(i));

            if (word.length() > 1) {
              if (!this.game.getDictionary().contains(word)) {
                this.gameException =
                    new GameException("The dictionary does not contain the word: " + word);
                return;
              }
            }
          }
        }
      }
    }
  }

  /**
   * Returns the orthogonal word to the main word which includes the specified letter of the main
   * word at the specified row and the specified column.
   * 
   * @param mainRow the specified row of the letter from the main word
   * @param mainCol the specified column of the letter from the main word
   * @param mainChar the specified letter from the main word
   * @return the orthogonal word to the main word which includes the specified letter of the main
   *         word at the specified row and the specified column
   */
  private String getSideWord(int mainRow, int mainCol, char mainChar) {
    Gameboard gameboard = this.game.getGameboard();
    Square square = gameboard.getSquareAt(mainRow, mainCol);

    String word = Character.toString(mainChar);

    int row = mainRow - (this.direction == Direction.HORIZONTAL ? 1 : 0);
    int col = mainCol - (this.direction == Direction.HORIZONTAL ? 0 : 1);

    while (col >= 0 && row >= 0) {
      square = gameboard.getSquareAt(row, col);

      if (square.isEmpty()) {
        break;
      }

      word = square.getTile().getLetter() + word;

      row = row - (this.direction == Direction.HORIZONTAL ? 1 : 0);
      col = col - (this.direction == Direction.HORIZONTAL ? 0 : 1);
    }

    row = mainRow + (this.direction == Direction.HORIZONTAL ? 1 : 0);
    col = mainCol + (this.direction == Direction.HORIZONTAL ? 0 : 1);

    while (row < Gameboard.ROWS && col < Gameboard.COLS) {
      square = gameboard.getSquareAt(row, col);

      if (square.isEmpty()) {
        break;
      }

      word = word + square.getTile().getLetter();

      row = row + (this.direction == Direction.HORIZONTAL ? 1 : 0);
      col = col + (this.direction == Direction.HORIZONTAL ? 0 : 1);
    }

    return word;
  }

  /**
   * Initializes the score of this move.
   */
  private void initScore() {
    if (this.gameException != null) {
      return;
    }

    this.score = this.scoreOfMainWord();
    this.score += this.scoreOfAllSideWords();
    this.score += this.bonusScore();
  }

  /**
   * Returns the score of the main word of this play word move.
   * 
   * @return the score of the main word of this play word move
   */
  private int scoreOfMainWord() {
    int score = 0;

    int row = Gameboard.squareIdToRow(this.squareId);
    int col = Gameboard.squareIdToCol(this.squareId);

    int wordFactor = 1;

    for (int i = 0; i < this.word.length(); i++) {
      Gameboard gameboard = this.game.getGameboard();
      Square square = gameboard.getSquareAt(row, col);

      if (square.isEmpty()) {
        Rack rack = this.game.getCurrentPlayer().getRack();
        Tile tile = rack.getTile(this.word.charAt(i));
        SquareType squareType = gameboard.getSquareAt(row, col).getSquareType();
        score += tile.getScore() * squareType.getLetterFactor();
        wordFactor *= squareType.getWordFactor();
      } else {
        Tile tile = gameboard.getSquareAt(row, col).getTile();
        score += tile.getScore();
      }

      row += (this.direction == Direction.HORIZONTAL ? 0 : 1);
      col += (this.direction == Direction.HORIZONTAL ? 1 : 0);
    }

    score *= wordFactor;

    return score;
  }

  /**
   * Returns the score of all side words of this play word move.
   * 
   * @return the score of all side words of this play word move
   */
  private int scoreOfAllSideWords() {
    int row = Gameboard.squareIdToRow(this.squareId);
    int col = Gameboard.squareIdToCol(this.squareId);
    int score = 0;

    for (int i = 0; i < this.word.length(); i++) {
      if (this.game.getGameboard().getSquareAt(row, col).isEmpty()) {
        Tile tile = this.game.getCurrentPlayer().getRack().getTile(this.word.charAt(i));
        score = score + this.scoreOfSideWord(row, col, tile);
      }

      row = row + (this.direction == Direction.HORIZONTAL ? 0 : 1);
      col = col + (this.direction == Direction.HORIZONTAL ? 1 : 0);
    }

    return score;
  }

  /**
   * Returns the score of the side word which includes the specified letter of the main word at the
   * specified row and the specified column.
   * 
   * @param mainRow the specified row of the letter from the main word
   * @param mainCol the specified column of the letter from the main word
   * @param mainChar the specified letter from the main word
   * @return the score of the side word which includes the specified letter of the main word at the
   *         specified row and the specified column
   */
  private int scoreOfSideWord(int mainRow, int mainCol, Tile mainTile) {
    Gameboard gameboard = this.game.getGameboard();
    Square square = gameboard.getSquareAt(mainRow, mainCol);

    int score = 0;

    int row = mainRow - (this.direction == Direction.HORIZONTAL ? 1 : 0);
    int col = mainCol - (this.direction == Direction.HORIZONTAL ? 0 : 1);

    while (col >= 0 && row >= 0) {
      square = gameboard.getSquareAt(row, col);

      if (square.isEmpty()) {
        break;
      }

      score = score + square.getTile().getScore();

      row = row - (this.direction == Direction.HORIZONTAL ? 1 : 0);
      col = col - (this.direction == Direction.HORIZONTAL ? 0 : 1);
    }

    row = mainRow + (this.direction == Direction.HORIZONTAL ? 1 : 0);
    col = mainCol + (this.direction == Direction.HORIZONTAL ? 0 : 1);

    while (row < Gameboard.ROWS && col < Gameboard.COLS) {
      square = gameboard.getSquareAt(row, col);

      if (square.isEmpty()) {
        break;
      }

      score = score + square.getTile().getScore();

      row = row + (this.direction == Direction.HORIZONTAL ? 1 : 0);
      col = col + (this.direction == Direction.HORIZONTAL ? 0 : 1);
    }

    if (score == 0) {
      return 0;
    }

    SquareType squareType = square.getSquareType();
    score += mainTile.getScore() * squareType.getLetterFactor();
    score *= squareType.getWordFactor();

    return score;
  }

  /**
   * Returns the bonus score of this play word move.
   * 
   * @return the bonus score of this play word move
   */
  private int bonusScore() {
    int count = 0;

    int row = Gameboard.squareIdToRow(this.squareId);
    int col = Gameboard.squareIdToCol(this.squareId);

    for (int i = 0; i < this.word.length(); i++) {
      if (this.game.getGameboard().getSquareAt(row, col).isEmpty()) {
        count++;
      }

      row += (this.direction == Direction.HORIZONTAL ? 0 : 1);
      col += (this.direction == Direction.HORIZONTAL ? 1 : 0);
    }

    return (count == Rack.TILE_CAPACITY ? 50 : 0);
  }

}
