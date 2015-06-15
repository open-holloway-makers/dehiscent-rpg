package map.cells;

import core.Player;

public interface Cell {

  public default char getMapIcon() { return '?'; }

  public void explore(Player p);
  public void event(Player p);
  
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
