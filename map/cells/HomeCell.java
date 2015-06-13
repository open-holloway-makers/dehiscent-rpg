package map.cells;

import core.Player;

public class HomeCell extends Cell {

  public HomeCell() { super('O'); };

  @Override
  public boolean goNorth() {
    System.out.println("You look over at the blank wall and think, what is my life?");
    return false;
  }

  @Override
  public boolean goEast() {
    return goNorth();
  }

  @Override
  public boolean goWest() {
    return goNorth();
  }

  @Override
  public boolean goSouth() {
    System.out.println("You open the cabin door and go out into the big wide world.");
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
