package map.cells;

import core.CombatResolver;
import core.IO;
import core.Player;
import enemies.Enemy;

public class NuisancePig implements Cell {

  private int encounterValue = 0;

  @Override
  public char getMapIcon() {
    return '.';
  }

  @Override
  public void explore(Player player) {


  }

  @Override
  public void event(Player player) {
    if (encounterValue < 1) {
      IO.printAsBox(
              "A wild pig appeared! With a grunt and a snarl it lowers its head to " +
                      "charge at you."
      );

      Enemy pig = new Enemy("Nuisance Pig", 20, 10, 10, 10);

      if (CombatResolver.resolveCombat(player, pig)) {
        encounterValue++;
      }
    }
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
