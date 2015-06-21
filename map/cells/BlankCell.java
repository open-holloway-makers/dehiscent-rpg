package map.cells;

import core.IO;
import core.Player;

public class BlankCell implements Cell {
  
  public BlankCell() { 
  };

  public char getMapIcon() { return '.'; }

  @Override
  public boolean goNorth() {
    IO.println("You continue wandering.");
    return true;
  }

  @Override
  public boolean goEast() {
    return goNorth();
  }

  @Override
  public boolean goSouth() {
    return goNorth();
  }

  @Override
  public boolean goWest() {
    return goNorth();
  }

  @Override
  public void explore(Player player) {
    IO.println("There's nothing of interest here...");
  }

  @Override
  public void event(Player player) {
    IO.println("There's nothing here...");
  }
}
