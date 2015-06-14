package map.cells;

import core.IO;
import core.Player;

public class BlankCell implements Cell {
  
  public BlankCell() { 
  }; 

  public char getMapIcon() { return ' '; }

  @Override
  public boolean goNorth() {
    IO.println("You continue wandering.");
    return true;
  }

  @Override
  public boolean goEast() {
    return goEast();
  }

  @Override
  public boolean goSouth() {
    return goEast();
  }

  @Override
  public boolean goWest() {
    return goEast();
  }

  @Override
  public Player explore(Player p) {
    IO.println("There's nothing of interest here...");
    return p;
  }

  @Override
  public Player event(Player p) {
    IO.println("There's nothing here...");
    return p;
  }
}
