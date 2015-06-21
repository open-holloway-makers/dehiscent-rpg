package items;

import core.IO;
import core.Player;
import core.Stat;

import java.util.List;

public class Modifier {
  public Stat stat;
  public int mod;

  public Modifier(Stat stat, int mod) {
    this.stat = stat;
    this.mod = mod;
  }

  public void applyTo(Player player) {
    switch (stat) {
      case VIT:
        player.addVit(mod);
        break;
      case DEX:
        player.addDex(mod);
        break;
      case STR:
        player.addStr(mod);
        break;
      case INT:
        player.addInt(mod);
        break;
      case PHYS_DEF:
        player.addPhysDef(mod);
        break;
      default:
        IO.println("Stat not found...");
    }
  }

  public static void apply(Modifier modifier, Player player) {
    modifier.applyTo(player);
  }

  public static void apply(List<Modifier> modifiers, Player player) {
    modifiers.parallelStream().forEach(m -> m.applyTo(player));
  }

  public void removeFrom(Player player) {
    switch (stat) {
      case VIT:
        player.addVit(-mod);
        break;
      case DEX:
        player.addDex(-mod);
        break;
      case STR:
        player.addStr(-mod);
        break;
      case INT:
        player.addInt(-mod);
        break;
      case PHYS_DEF:
        player.addPhysDef(-mod);
        break;
      default:
        IO.println("Stat not found...");
    }
  }

  public static void remove(Modifier modifier, Player player) {
    modifier.removeFrom(player);
  }

  public static void remove(List<Modifier> modifiers, Player player) {
    modifiers.parallelStream().forEach(m -> m.removeFrom(player));
  }

  @Override
  public String toString() {
    String sign = (mod >= 0) ? " +" : " -";
    return stat + sign + java.lang.Math.abs(mod);
  }
}
