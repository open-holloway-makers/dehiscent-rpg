package map.cells;

import core.IO;
import core.Player;
import items.Consumable;
import items.Useable;

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

    Consumable kedgeree = new Consumable("kedgeree", 5, 2,
            (player) -> player.subHp(10));

    Consumable onions = new Consumable("onions", 10, 2, "You feel revitalised!\n",
            (player) -> player.addVit(1)
    );

    Consumable potion = new Consumable("potion", 200, 1, "You're almost fully recovered!\n", new Useable() {
      @Override
      public void use(Player player) {
        player.addDex(1);
        player.fullHeal();
      }
    });

    p.obtain(kedgeree);
    p.obtain(onions);
    p.obtain(potion);
  }

  @Override
  public boolean goNorth() {
    return true;
  }

  @Override
  public boolean goSouth() {
    return true;
  }

  @Override
  public boolean goEast() {
    return true;
  }

  @Override
  public boolean goWest() {
    return true;
  }
}
