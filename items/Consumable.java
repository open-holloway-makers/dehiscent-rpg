package items;

import core.IO;
import core.Player;

public class Consumable extends Item {

  private Useable u;
  private int uses;
  private String messageOnUse;

  public Consumable(String name, int value, int uses, Useable u) {
    super(name, value);
    this.u = u;
    this.uses = uses;
    this.messageOnUse = "";
  }

  public Consumable(String name, int value, int uses, String messageOnUse, Useable u) {
    super(name, value);
    this.u = u;
    this.uses = uses;
    this.messageOnUse = messageOnUse;
  }

  public void use(Player player) {
    IO.print(messageOnUse);
    u.use(player);
    if (--uses <= 0) {
      player.lose(this);
    }
  }


}
