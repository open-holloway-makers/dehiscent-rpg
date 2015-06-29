package core;

import enemies.Enemy;
import items.Item;
import items.Rating;
import items.SlotType;
import items.Weapon;

import java.util.List;
import java.util.stream.Collectors;

public class CombatResolver {

  // Gosh what a big method
  public static boolean resolveCombat(Player player, Enemy enemy) {
    // Get possible hands
    List<String> hands = getAvailableHands(player);

    while (player.getHp() > 0 && enemy.getHp() > 0) {
      printCombatBanner(player, enemy);

      // Player turn
      printHandsEquip(player, hands);

      Weapon weaponToAttackWith = null;
      while (weaponToAttackWith == null) {
        String decision = IO.getDecision("Which hand do you attack with? ");
        IO.println("");
        int d = -1;
        try {
          d = Integer.parseInt(decision);
        } catch (NumberFormatException exception) {
          // Don't set d, let d carry through to next error handling if block
        }
        if (d == -1 || d >= hands.size()) {
          IO.printf("Please enter a number between 0 and %d\n\n", (hands.size() - 1));
        } else {
          Item item = player.getEquipSlots().get(hands.get(d)).getItem();
          if (item instanceof Weapon) {
            weaponToAttackWith = (Weapon) player.getEquipSlots().get(hands.get(d)).getItem();
          } else {
            IO.println("That won't make a very good weapon...\n");
            weaponToAttackWith = new Weapon("Bogus weapon", 0, SlotType.HAND, null, 5, 0, Rating.U, Rating.U, Rating.U);
          }
        }
      }
      int playerDamage = weaponToAttackWith.getAttackRating(player);
      IO.println("You attacked for " + playerDamage + " damage!");
      enemy.subHp(playerDamage);

      // Enemy turn
      // At the moment physDef is pretty much straight up deducted from damage, probably no good
      int enemyDamage = (enemy.getAttackRating() - (player.getPhysDef() / 8));
      IO.printf("The %s attacked you for %d damage!\n\n", enemy.getName(), enemyDamage);
      player.subHp(enemyDamage);
    }

    printCombatBanner(player, enemy);
    boolean outcome = false;
    if (player.getHp() <= 0) {
      IO.println("You died...\n");
      player.die();
    } else {
      IO.println("You are victorious!");
      player.addGold(enemy.getGoldReward());
      player.addXp(enemy.getXpReward());
      outcome = true;
    }
    return outcome;
  }

  private static void printCombatBanner(Player player, Enemy enemy) {
    IO.print(IO.formatBanner(IO.BOX_WIDTH));
    IO.print(IO.formatColumns(IO.BOX_WIDTH, false, true,
            String.format("Player %d/%d", player.getHp(), player.getMaxHp()),
            String.format("%d/%d %s", enemy.getHp(),
                    enemy.getMaxHp(), enemy.getName())));
    IO.print(IO.formatBanner(IO.BOX_WIDTH));
  }

  public static List<String> getAvailableHands(Player player) {
    return player.getEquipSlots().keySet()
            .parallelStream()
            .filter(k -> k.toLowerCase().contains("hand"))
            .collect(Collectors.toList());
  }

  public static void printHandsEquip(Player player, List<String> hands) {
    for (int i = 0; i < hands.size(); i++) {
      String hand = hands.get(i);
      String holding = "(empty)";
      if (!player.getEquipSlots().get(hands.get(i)).isFree()) {
        holding = player.getEquipSlots().get(hands.get(i)).getItem().getName();
      }
      IO.print(IO.formatColumns(IO.BOX_WIDTH, i + ": " + hand, holding));
    }
    IO.println(IO.formatBanner(IO.BOX_WIDTH));
  }
}
