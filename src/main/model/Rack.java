package main.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The Rack class represents a rack.
 * 
 * @author sekeller
 */
public class Rack implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The capacity of a rack.
   */
  public static final int TILE_CAPACITY = 7;

  /**
   * The tiles that are on the rack.
   */
  private ArrayList<Tile> tiles;

  /**
   * Constructs an empty rack.
   */
  Rack() {
    this.tiles = new ArrayList<>();
  }

  /**
   * Constructs a copy of the specified rack.
   * 
   * @param original the specified rack
   */
  Rack(Rack original) {
    this.tiles = new ArrayList<>();
    for (Tile tile : original.tiles) {
      this.tiles.add(new Tile(tile));
    }
  }

  /**
   * Returns the number of tiles in this rack.
   * 
   * @return the number of tiles in this rack
   */
  public int size() {
    return this.tiles.size();
  }

  public Tile getTile(int index) {
    return this.tiles.get(index);
  }

  /**
   * Returns true if the number of tiles in this rack is zero.
   * 
   * @return true if the number of tiles in this rack is zero
   */
  boolean isEmpty() {
    return this.tiles.isEmpty();
  }

  /**
   * Returns true if the number of tiles in this rack is seven.
   * 
   * @return true if the number of tiles in this rack is seven
   */
  boolean isFull() {
    return this.tiles.size() == TILE_CAPACITY;
  }

  /**
   * Adds the specified tile to this rack.
   * 
   * @param tile the tile to be add to this rack
   */
  void addTile(Tile tile) {
    if (this.tiles.size() < TILE_CAPACITY) {
      this.tiles.add(tile);
    }
  }

  /**
   * Returns true if the the specified tile is on this rack.
   * 
   * @param tile the specified tile
   * @return true if the the specified tile is on this rack
   */
  boolean containsTile(Tile tile) {
    return this.tiles.contains(tile);
  }

  /**
   * Returns true if all tiles of the specified collection are on this rack.
   * 
   * @param tiles the specified collection
   * @return true if all tiles of the specified collection are on this rack
   */
  boolean containsAllTiles(Collection<Tile> tiles) {
    return this.tiles.containsAll(tiles);
  }

  /**
   * Returns the first tile on this rack with the specified letter. Returns null if there is no tile
   * with the specified letter on this rack.
   * 
   * @param letter the specified letter
   * @return the first tile on this rack with the specified letter
   */
  Tile getTile(char letter) {
    for (Tile tile : this.tiles) {
      if (tile.getLetter() == letter) {
        return tile;
      }
    }

    for (Tile tile : this.tiles) {
      if (tile.getLetter() == '*') {
        return tile;
      }
    }

    return null;
  }

  /**
   * Removes the specified tile from this rack.
   * 
   * @param tile the tile to be removed from this rack
   */
  void removeTile(Tile tile) {
    this.tiles.remove(tile);
  }

  /**
   * Removes all tiles of the specified collection from this rack.
   * 
   * @param tiles the specified collection
   */
  void removeAllTiles(Collection<Tile> tiles) {
    this.tiles.removeAll(tiles);
  }

  @Override
  public String toString() {
    return this.tiles.toString();
  }

}
