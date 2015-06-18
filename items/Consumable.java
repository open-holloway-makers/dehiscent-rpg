package items;

import core.Player;

public class Consumable extends Item {

  public Consumable(String name, int value) {
    super(name, value);
  }

  public void takeEffect(ConsumableEffect effect, Player p) {
    effect.begin(p);
  }

  public void use(Player player) {
    player.addGold(50);
  }

  public interface ConsumableEffect {
    void begin(Player player);
  }

}
