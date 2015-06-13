package map.cells;

import core.Player;

public interface Cell {

  public default char getMapIcon() { return '?'; }

  public Player explore(Player p);
  public Player event(Player p);
  
  public default boolean goNorth() { return true; }
  public default boolean goSouth() { return true; }
  public default boolean goEast()  { return true; }
  public default boolean goWest()  {
    // Life is peaceful there
    // In the open air
    // Where the skies are blue
    // This is what we're gonna do
    return true;
  }
}
