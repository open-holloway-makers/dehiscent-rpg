package map.cells;

import core.Player;

public class LittleTatShop implements Cell {
  @Override
  public char getMapIcon() {
    return 0;
  }

  @Override
  public void explore(Player p) {

  }

  @Override
  public void event(Player p) {

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
