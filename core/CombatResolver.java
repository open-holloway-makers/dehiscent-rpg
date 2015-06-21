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
  public static void resolveCombat(Player player, Enemy enemy) {
    // Get possible hands
    List<String> hands = player.getEquipSlots().keySet()
            .parallelStream()
            .filter(k -> k.toLowerCase().contains("hand"))
            .collect(Collectors.toList());

    while (player.getHp() > 0 && enemy.getHp() > 0) {
      IO.println("==================================================");
      IO.printf("Player %d/%d\t\t\t\t%d/%d %s\n",
              player.getHp(), player.getMaxHp(), enemy.getHp(),
              enemy.getMaxHp(), enemy.getName());
      IO.println("==================================================");
      // Player turn
      for (int i = 0; i < hands.size(); i++) {
        IO.print(i + ": " + hands.get(i));
        if (player.getEquipSlots().get(hands.get(i)).isFree()) {
          IO.print("\t\t(empty)\n");
        } else {
          IO.print("\t\t" + player.getEquipSlots().get(hands.get(i)).getItem().getName() + "\n");
        }
      }
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
    if (player.getHp() <= 0) {
      IO.println("You died...\n");
      player.die();
    } else {
      IO.println("You are victorious!");
      IO.printf("Obtained %d gold.\n", enemy.getGoldReward());
      IO.printf("Obtained %d xp.\n", enemy.getXpReward());
      player.addGold(enemy.getGoldReward());
      player.addXp(enemy.getXpReward());
    }
  }
}
