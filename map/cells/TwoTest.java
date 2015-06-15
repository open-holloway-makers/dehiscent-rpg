package map.cells;

import core.Player;
import core.Stat;
import items.Modifier;

public class TwoTest implements Cell {

  public char getMapIcon() { return '2'; }

  @Override
  public boolean goNorth() {
    System.out.println("You look over at the blank wall and think, what is my life?");
    return true; 
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
  public void explore(Player p) {
    System.out.println("There's nothing of interest here...");
  }

  @Override 
  public void event(Player p) {
    System.out.println("There's nothing here...");
    Modifier testMod = new Modifier(Stat.DEX, 5);
    testMod.applyTo(p);
  }
}
