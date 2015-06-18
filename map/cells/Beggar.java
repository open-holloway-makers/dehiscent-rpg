package map.cells;

import core.IO;
import core.Player;
import items.Consumable;

public class Beggar implements Cell {

  @Override
  public char getMapIcon() {
    return '\u00A5';
  }

  @Override
  public void explore(Player p) {
    IO.println("There's nobody else around except the beggar.");
  }

  @Override
  public void event(Player p) {
    IO.println("You found more soup!");
    Consumable soup = new Consumable("soup", 5) {
      public void use(Player p) {
        p.addXp(50);
      }
    };
    p.obtain(soup);
  }

  @Override
  public boolean goNorth() {
    return false;
  }

  @Override
  public boolean goSouth() {
    return false;
  }

  @Override
  public boolean goEast() {
    return false;
  }

  @Override
  public boolean goWest() {
    return false;
  }
}
