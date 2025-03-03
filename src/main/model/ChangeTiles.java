package main.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The ChangeTiles class represents a move to exchange tiles.
 * 
 * @author sekeller
 */
public class ChangeTiles extends Move {

  private static final long serialVersionUID = 1L;

  /**
   * The tiles which are to be exchanged.
   */
  private final Collection<Tile> tiles;

  /**
   * Constructs a change tiles move with the specified tiles for the specified game.
   * 
   * @param game the specified game
   * @param tiles the specified tiles
   */
  ChangeTiles(Scrabble game, Collection<Tile> tiles) {
    super(game);
    this.tiles = tiles;

    this.init();
  }

  /**
   * Constructs a copy of the specified change tiles move.
   * 
   * @param original the specified change tiles move
   */
  ChangeTiles(ChangeTiles original) {
    super();

    this.player = original.player;
    this.gameException = new GameException(original.gameException);
    this.time = original.time;

    this.tiles = new ArrayList<>(original.tiles);
  }

  /**
   * Returns the tiles which are to be exchanged.
   * 
   * @return the tiles which are to be exchanged
   */
  public Collection<Tile> getTiles() {
    return new ArrayList<Tile>(this.tiles);
  }

  @Override
  public int getScore() {
    return 0;
  }

  @Override
  void execute() throws GameException {
    if (this.gameException != null) {
      throw this.gameException;
    }

    TileBag tileBag = this.game.getTileBag();
    Rack rack = this.game.getCurrentPlayer().getRack();
    rack.removeAllTiles(this.tiles);

    while (!rack.isFull() && !tileBag.isEmpty()) {
      Tile tile = tileBag.removeTile();
      rack.addTile(tile);
    }

    tileBag.addAllTiles(this.tiles);

    this.game.increaseScorelessMoveCount();
    this.game.updateCurrentPlayer();
  }

  @Override
  public String toString() {
    return "ChangeTiles(" + this.tiles + ")";
  }

  /**
   * Initializes the moveException.
   */
  private void init() {
    if (this.game.getGameState() == GameState.PREPERATION) {
      this.gameException = new GameException("The game has not started yet!");
      return;
    }

    if (this.game.getGameState() == GameState.GAME_OVER) {
      this.gameException = new GameException("The game is over!");
      return;
    }

    if (this.tiles == null) {
      this.gameException = new GameException("There are no tiles selected!");
      return;
    }

    if (this.tiles.size() == 0) {
      this.gameException = new GameException("There are no tiles selected!");
      return;
    }

    Rack rack = this.game.getCurrentPlayer().getRack();

    if (this.tiles.size() > rack.size()) {
      this.gameException =
          new GameException("There are more tiles selected than there are on the rack!");
      return;
    }

    if (!rack.containsAllTiles(this.tiles)) {
      this.gameException = new GameException("The current player does not contain all tiles!");
      return;
    }

    if (this.game.getTileBag().size() < this.tiles.size()) {
      this.gameException = new GameException("There are not enaugh tiles in the tile bag!");
      return;
    }
  }

}
