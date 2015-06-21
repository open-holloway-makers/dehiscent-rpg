package map.cells;

import core.CombatResolver;
import core.Player;
import enemies.Enemy;

public class FourTest implements Cell {

  public char getMapIcon() { return '4'; }

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
  public void explore(Player player) {
    System.out.println("There's nothing of interest here...");
  }

  @Override 
  public void event(Player player) {
    Enemy e = new Enemy("Imp", 30, 20, 20, 20);
    CombatResolver.resolveCombat(player, e);
    System.out.println("There's nothing here...");
  }
}
