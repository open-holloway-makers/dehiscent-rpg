package map.cells;

import core.IO;
import core.Player;
import core.Stat;
import items.Item;
import items.Modifier;
import items.Slot;

public class HelmCell implements Cell {

  public char getMapIcon() {
    return '.';
  }

  public boolean isVisited = false;
  public boolean isExplored = false;

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
  public boolean goWest() {
    return goNorth();
  }

  @Override
  public boolean goSouth() {
    return goNorth();
  }

  @Override
  public void explore(Player p) {
    if (!isExplored) {
      IO.println("You find a bronze helm, it looks old but of good craftsmanship.");
      Item helmet = new Item("Helmet", Slot.HEAD, new Modifier(Stat.PHYS_DEF, +5));
      p.obtain(helmet);
      isExplored = true;
    } else {
      IO.print("There's nothing left to see here.");
    }
  }

  @Override
  public void event(Player p) {
    if (!isExplored) {
      IO.println("Something shiny catches your attention.");
      isVisited = true;
    } else {
      IO.println("What use is there returning here to be reminded of your woes..?");
    }
  }
}
