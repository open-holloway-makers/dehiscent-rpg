package core;

import enemies.Enemy;
import items.Item;
import items.Rating;
import items.SlotType;
import items.Weapon;

import java.util.List;
import java.util.ArrayList;

public class CombatResolver {
  public static void resolveCombat(Player p, Enemy e) {
    // Get possible hands
    List<String> hands = new ArrayList<String>();
    for (String key : p.getEquipSlots().keySet()) {
      if (key.toLowerCase().contains("hand")) {
        hands.add(key);
      }
    }
    while (p.getHp() > 0 && e.getHp() > 0) {
      IO.println("==================================================");
      IO.println("Player " + p.getHp() + "/" + p.getMaxHp() + "\t\t\t\t" + e.getHp() + "/" + e.getMaxHp() + " " + e.getName());
      IO.println("==================================================");
      // Player turn
      for (int i = 0; i < hands.size(); i++) {
        IO.print(i + ": " + hands.get(i));
        if (p.getEquipSlots().get(hands.get(i)).isFree()) {
          IO.print("\t\t(empty)\n");
        } else {
          IO.print("\t\t" + p.getEquipSlots().get(hands.get(i)).item.getName() + "\n");
        }
      }
      Weapon weaponToAttackWith = null;
      while (weaponToAttackWith == null) {
        String decision = IO.getDecision("Which hand do you attack with? ");
        int d = -1;
        try {
          d = Integer.parseInt(decision);
        } catch (NumberFormatException exception) {
        }
        if (d == -1 || d >= hands.size()) {
          IO.println("Please enter a number between 0 and " + (hands.size() - 1));
        } else {
          Item item = p.getEquipSlots().get(hands.get(d)).item;
          if (item instanceof Weapon) {
            weaponToAttackWith = (Weapon) p.getEquipSlots().get(hands.get(d)).item;
          } else {
            IO.println("That won't make a very good weapon...");
            weaponToAttackWith = new Weapon("Bogus weapon", SlotType.HAND, null, 10, 0, Rating.U, Rating.U, Rating.U);
          }
        }
      }
      int playerDamage = weaponToAttackWith.getAttackRating(p);
      IO.println("You attacked for " + playerDamage + " damage!");
      e.subHp(playerDamage);

      // Enemy turn
      int enemyDamage = e.getAttackRating();
      IO.println("The " + e.getName() + " attacked you for " + enemyDamage + " damage!");
      p.subHp(enemyDamage);
    }
    if (p.getHp() <= 0) {
      IO.println("You died.");
      p.die();
    } else {
      IO.println("You are victorious!");
      IO.println("Obtained " + e.getGoldReward() + " gold.");
      IO.println("Obtained " + e.getXpReward() + " xp.");
      p.addGold(e.getGoldReward());
      p.addXp(e.getXpReward());
    }
  }
}
