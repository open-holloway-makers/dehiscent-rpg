package map.cells;

import core.IO;
import core.Player;
import core.TransactionResolver;
import items.Consumable;
import npcs.Merchant;

public class LittleGrocerShop implements Cell {

  Merchant polliver;

  @Override
  public char getMapIcon() {
    return '\u00A5';
  }

  @Override
  public void explore(Player player) {
    IO.println("\"The harvest has been hard, but I hope there's something you like\"");
    TransactionResolver.resolveTransaction(player, polliver);
  }

  @Override
  public void event(Player player) {

    polliver = new Merchant("Polliver", 200, 0.8, 1.2);
    polliver.setStoreName("The Little Grocery");

    Consumable carrots = new Consumable(
            "Carrots", 3, 5, "You feel a little better.\n", (p) -> (p).addHp(5));

    Consumable magicTurnip = new Consumable(
            "Magic Turnip", 20, 1, "What an extraordinary turnip.\n", Player::fullHeal);

    Consumable beef = new Consumable("Beef", 10, 1, "Now that's a proper meal.\n", p -> {
      p.addHp(40);
      p.addXp(1);
    });

    polliver.obtain(carrots, Integer.MAX_VALUE);
    polliver.obtain(magicTurnip, 1);
    polliver.obtain(beef, 1);


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
