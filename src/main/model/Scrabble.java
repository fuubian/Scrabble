package main.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Scrabble implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * A constant for the minimum number of players of a game.
   */
  public static final int MIN_PLAYER_COUNT = 2;

  /**
   * A constant for the maximum number of players of a game.
   */
  public static final int MAX_PLAYER_COUNT = 4;

  /**
   * A constant for the time limit of a move in milliseconds.
   */
  public static final long TIME_LIMIT = 10 * 60 * 1_000;

  /**
   * A constant for the minimum number of scoreless moves to finish the game.
   */
  public static final int MIN_SCORELESS_MOVE_COUNT_TO_FINISH_GAME = 6;

  /**
   * The tile bag of the game.
   */
  private TileBag tileBag;

  /**
   * The dictionary of the game.
   */
  private Dictionary dictionary;

  /**
   * The gameboard of the game.
   */
  private Gameboard gameboard;

  /**
   * The players of the game.
   */
  private List<Player> players;

  /**
   * The moves of the game.
   */
  private List<Move> moves;

  /**
   * The index of the current player.
   */
  private int currentPlayerIndex;

  /**
   * The number of consecutive scoreless moves.
   */
  private int scorelessMoveCount;

  /**
   * The game state of the game.
   */
  private GameState gameState;

  /**
   * The remaining time for the move of the current player.
   */
  private long remainingTime;

  /**
   * The option for a time limit.
   */
  private boolean timeLimit;

  /**
   * The timer of the game.
   */
  private Thread timeThread;

  /**
   * Constructs a scrabble game with the specified set of tiles, the specified dictionary. When the
   * specified value for the time limit is true, the time limit is set to 10 minutes.
   * 
   * @param tileSet the specified set of tiles
   * @param dictionary the specified dictionary
   * @param timeLimit the specified value for the time limit
   */
  public Scrabble(Collection<Tile> tileSet, Dictionary dictionary, boolean timeLimit) {
    this.tileBag = new TileBag(tileSet);
    this.dictionary = dictionary;
    this.gameboard = new Gameboard();
    this.players = new ArrayList<>();
    this.moves = new ArrayList<>();

    this.currentPlayerIndex = 0;
    this.scorelessMoveCount = 0;
    this.gameState = GameState.PREPERATION;
    this.remainingTime = TIME_LIMIT;
    this.timeLimit = timeLimit;
  }

  /**
   * Constructs a copy of the specified scrabble game.
   * 
   * @param original the specified scrabble game
   */
  public Scrabble(Scrabble original) {
    this.tileBag = new TileBag(original.getTileBag());
    this.dictionary = new Dictionary(original.getDictionary());
    this.gameboard = new Gameboard(original.getGameboard());

    this.players = new ArrayList<>();
    for (Player player : original.players) {
      if (player instanceof HumanPlayer) {
        this.players.add(new HumanPlayer((HumanPlayer) player));
      } else if (player instanceof ComputerPlayer) {
        this.players.add(new ComputerPlayer((ComputerPlayer) player));
      }
    }

    this.moves = new ArrayList<>();
    for (Move move : original.moves) {
      if (move instanceof Pass) {
        this.moves.add(new Pass((Pass) move));
      } else if (move instanceof PlayWord) {
        this.moves.add(new PlayWord((PlayWord) move));
      } else if (move instanceof ChangeTiles) {
        this.moves.add(new ChangeTiles((ChangeTiles) move));
      } else if (move instanceof FinishGame) {
        this.moves.add(new FinishGame((FinishGame) move));
      }
    }

    this.currentPlayerIndex = original.currentPlayerIndex;
    this.scorelessMoveCount = original.scorelessMoveCount;
    this.gameState = original.gameState;

    this.remainingTime = original.remainingTime;
    this.timeLimit = original.timeLimit;

    // TODO delete
    if (this.timeLimit && this.gameState == GameState.PLAY) {
      this.timeThread = new TimeThread(this);
      this.timeThread.start();
    }

  }

  /**
   * Returns the current game state of this game.
   * 
   * @return the current game state of this game
   */
  public TurnState getTurnState() {
    return new TurnState(this);
  }

  /**
   * Sets the current game state of this game to the specified game state.
   * 
   * @param turnState the specified game state
   */
  public void setTurnState(TurnState turnState) {
    this.tileBag = turnState.getTileBag();
    this.gameboard = turnState.getGameboard();
    this.players = turnState.getPlayers();
    this.moves = turnState.getMoves();
    this.currentPlayerIndex = turnState.getCurrentPlayerIndex();
    this.scorelessMoveCount = turnState.getScorelessMoveCount();
    this.gameState = turnState.getGameState();
    this.remainingTime = turnState.getRemainingTime();
  }

  /**
   * Adds the specified player to this game.
   * 
   * @param player the specified player
   * @throws GameException ...
   */
  public void addPlayer(Player player) throws GameException {
    if (this.gameState == GameState.PLAY) {
      throw new GameException("No player can be added when the game is running.");
    }

    if (this.gameState == GameState.GAME_OVER) {
      throw new GameException("No player can be added when the game is finished.");
    }

    if (this.players.size() >= MAX_PLAYER_COUNT) {
      throw new GameException(
          "No player can be added when the maximum number of players has already been reached.");
    }

    this.players.add(player);
  }

  /**
   * Returns the tile bag of this game.
   * 
   * @return the tile bag of this game
   */
  public TileBag getTileBag() {
    return this.tileBag;
  }

  /**
   * Returns the dictionary of this game.
   * 
   * @return the dictionary of this game
   */
  public Dictionary getDictionary() {
    return this.dictionary;
  }

  /**
   * Returns the gameboard of this game.
   * 
   * @return the gameboard of this game
   */
  public Gameboard getGameboard() {
    return this.gameboard;
  }

  /**
   * Returns a list with all players of this game.
   * 
   * @returna list with all players of this game
   */
  public List<Player> getPlayers() {
    return new ArrayList<Player>(this.players);
  }

  /**
   * Returns the player count of this game.
   * 
   * @return the player count of this game
   */
  public int getPlayerCount() {
    return this.players.size();
  }

  /**
   * Returns the player with the specified index.
   * 
   * @param index the specified index
   * @return the player with the specified index
   */
  public Player getPlayer(int index) {
    return this.players.get(index);
  }

  /**
   * Returns the index of the current player.
   * 
   * @return the index of the current player
   */
  public int getCurrentPlayerIndex() {
    return this.currentPlayerIndex;
  }

  /**
   * Returns the current player.
   * 
   * @return the current player
   */
  public Player getCurrentPlayer() {
    return this.players.get(this.currentPlayerIndex);
  }

  /**
   * Returns the move count of this game.
   * 
   * @return the move count of this game
   */
  public int getMoveCount() {
    return this.moves.size();
  }

  /**
   * Returns the move with the specified index.
   * 
   * @param index the specified index
   * @return the move with the specified index
   */
  public Move getMove(int index) {
    return this.moves.get(index);
  }

  /**
   * Returns the scoreless move count.
   * 
   * @return the scoreless move count
   */
  public int getScorelessMoveCount() {
    return this.scorelessMoveCount;
  }

  /**
   * Returns the game state.
   * 
   * @return the game state
   */
  public GameState getGameState() {
    return this.gameState;
  }

  /**
   * Starts the game.
   * 
   * @throws GameException ...
   */
  public void startGame() throws GameException {
    if (this.gameState == GameState.PLAY) {
      throw new GameException("The game cannot be started if it is already running.");
    }

    if (this.gameState == GameState.GAME_OVER) {
      throw new GameException("The game cannot be started if it is already finished.");
    }

    if (this.players.size() < MIN_PLAYER_COUNT) {
      throw new GameException("There must be at least two players to start the game.");
    }

    for (Player player : this.players) {
      while (!player.getRack().isFull()) {
        Tile tile = this.getTileBag().removeTile();
        player.getRack().addTile(tile);
      }
    }

    this.gameState = GameState.PLAY;

    if (this.timeLimit) {
      this.timeThread = new TimeThread(this);
      this.timeThread.start();
    }
  }

  /**
   * Sets the game state of this game to game over.
   */
  public void stopGame() {
    this.gameState = GameState.GAME_OVER;
  }

  /**
   * Returns the remaining time for the current player.
   * 
   * @return the remaining time for the current player
   */
  public long getRemainingTime() {
    return this.remainingTime;
  }

  /**
   * Returns the total time of the game in milliseconds.
   */
  public long getTotalTime() {
    long totalTime = 0;

    for (Move move : this.moves) {
      totalTime += move.time;
    }

    return totalTime;
  }

  /**
   * Executes a pass move.
   * 
   * @throws GameException ...
   */
  public void pass() throws GameException {
    this.executeMove(new Pass(this));
  }

  /**
   * Executes a change tiles move with the specified tiles.
   * 
   * @param tiles the specified tiles.
   * @throws GameException ...
   */
  public void changeTiles(Collection<Tile> tiles) throws GameException {
    this.executeMove(new ChangeTiles(this, tiles));
  }

  /**
   * Executes a play word move with the specified word, the specified square id and the specified
   * direction.
   * 
   * @param word the specified word
   * @param squareId the specified square id
   * @param direction the specified direction
   * @throws GameException ...
   */
  public void playWord(String word, String squareId, Direction direction) throws GameException {
    this.executeMove(new PlayWord(this, word, squareId, direction));
  }

  /**
   * Executes a finish game move.
   * 
   * @throws GameException ...
   */
  public void finishGame() throws GameException {
    this.executeMove(new FinishGame(this));
  }

  /**
   * Executes the specified move.
   * 
   * @param move the specified move.
   * @throws GameException ...
   */
  public synchronized void executeMove(Move move) throws GameException {
    if (this.gameState == GameState.PLAY) {
      move.execute();
      move.setTime(TIME_LIMIT - this.remainingTime);
      this.moves.add(move);
      this.remainingTime = TIME_LIMIT;
      System.out.println(move);
    }
  }

  /**
   * Subtracts the specified elapsed time from the elapsed time.
   * 
   * @param elapsedTime the specified elapsed time.
   */
  synchronized void updateRemainingTime(long elapsedTime) {
    if (this.timeLimit) {
      this.remainingTime = this.remainingTime - elapsedTime;

      if (this.remainingTime < 0) {
        this.remainingTime = 0;
        this.gameState = GameState.GAME_OVER;
      }
    }
  }

  /**
   * Increases the scoreless move count by one.
   */
  void increaseScorelessMoveCount() {
    this.scorelessMoveCount++;
  }

  /**
   * Resets the scoreless move count.
   */
  void resetScorelessMoveCount() {
    this.scorelessMoveCount = 0;
  }

  /**
   * Updates the current player.
   */
  void updateCurrentPlayer() {
    this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.size();
  }

  /**
   * Sets the game state to the specified game state.
   * 
   * @param gameState the specified game state.
   */
  void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

}
