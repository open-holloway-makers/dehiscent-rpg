package map.cells;

import core.Player;

public abstract class Cell {

  public char mapIcon = '?';

  public Cell() {}

  public boolean goNorth() {
    return true;
  }

  public boolean goEast() {
    return true;
  }

  public boolean goWest() {
    // Life is peaceful there
    // In the open air
    // Where the skies are blue
    // This is what we're gonna do
    return true;
  }

  public boolean goSouth() {
    return true;
  }

  public abstract Player explore(Player p);
  public abstract Player event(Player p);
}
