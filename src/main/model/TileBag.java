package main.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The TileBag class represents a bag of tiles.
 * 
 * @author sekeller
 */
public class TileBag implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The tiles that are in the bag.
   */
  private List<Tile> tiles;

  /**
   * Constructs an empty bag.
   */
  TileBag(Collection<Tile> tiles) {
    this.tiles = new ArrayList<>(tiles);
  }

  /**
   * Constructs a copy of the specified tile bag.
   * 
   * @param original the specified tile bag
   */
  TileBag(TileBag original) {
    this.tiles = new ArrayList<>();

    for (Tile tile : original.tiles) {
      this.tiles.add(new Tile(tile));
    }
  }

  /**
   * Returns the number of tiles in this bag.
   * 
   * @return the number of tiles in this bag
   */
  public int size() {
    return this.tiles.size();
  }

  /**
   * Returns true if this bag contains no tiles.
   * 
   * @return true if this bag contains no tiles
   */
  public boolean isEmpty() {
    return this.tiles.isEmpty();
  }

  /**
   * Adds all of the tiles in the specified collection to this bag.
   * 
   * @param tiles collection containing tiles to be added to this bag
   */
  void addAllTiles(Collection<Tile> tiles) {
    this.tiles.addAll(tiles);
  }

  /**
   * Removes a random tile from this bag. Returns the tile that was removed from the bag.
   * 
   * @return the tile that was removed from the bag
   */
  Tile removeTile() {
    int index = (int) (Math.random() * this.tiles.size());
    return this.tiles.remove(index);
  }

}
