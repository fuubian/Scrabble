package main.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TurnState implements Serializable {

  private static final long serialVersionUID = 1L;

  private final TileBag tileBag;
  private final Gameboard gameboard;
  private final List<Player> players;
  private final List<Move> moves;

  private final int currentPlayerIndex;
  private final int scorelessMoveCount;
  private final GameState gameState;
  private final long remainingTime;

  public TurnState(Scrabble game) {
    this.tileBag = new TileBag(game.getTileBag());
    this.gameboard = new Gameboard(game.getGameboard());

    this.players = new ArrayList<>();
    for (int i = 0; i < game.getPlayerCount(); i++) {
      Player player = game.getPlayer(i);
      if (player instanceof HumanPlayer) {
        this.players.add(new HumanPlayer((HumanPlayer) player));
      } else if (player instanceof ComputerPlayer) {
        this.players.add(new ComputerPlayer((ComputerPlayer) player));
      }
    }

    this.moves = new ArrayList<>();
    for (int i = 0; i < game.getMoveCount(); i++) {
      Move move = game.getMove(i);
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

    this.currentPlayerIndex = game.getCurrentPlayerIndex();
    this.scorelessMoveCount = game.getScorelessMoveCount();
    this.gameState = game.getGameState();
    this.remainingTime = game.getRemainingTime();
  }

  public TileBag getTileBag() {
    return this.tileBag;
  }

  public Gameboard getGameboard() {
    return this.gameboard;
  }

  public List<Player> getPlayers() {
    return this.players;
  }

  public List<Move> getMoves() {
    return this.moves;
  }

  public int getCurrentPlayerIndex() {
    return this.currentPlayerIndex;
  }

  public int getScorelessMoveCount() {
    return this.scorelessMoveCount;
  }

  public GameState getGameState() {
    return this.gameState;
  }

  public long getRemainingTime() {
    return this.remainingTime;
  }

}
