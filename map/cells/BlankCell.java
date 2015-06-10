package map.cells;

import core.Player;

public class BlankCell extends Cell {
  
  public BlankCell() { 
    super(' '); 
  };

  @Override
  public boolean goNorth() {
    System.out.println("You continue wandering.");
    return true;
  }

  @Override
  public boolean goEast() {
    System.out.println("You continue wandering.");
    return true;
  }

  @Override
  public boolean goSouth() {
    System.out.println("You continue wandering.");
    return true;
  }

  @Override
  public boolean goWest() {
    System.out.println("You continue wandering.");
    return true;
  }

  @Override
  public Player explore(Player p) {
    System.out.println("There's nothing of interest here...");
    return p;
  }

  @Override
  public Player event(Player p) {
    System.out.println("There's nothing here...");
    return p;
  }
}
