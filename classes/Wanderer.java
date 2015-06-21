package classes;

import core.Player;
import core.Stat;
import items.*;

public class Wanderer extends Player {

  public Wanderer() {
    super();
  }

  @Override
  public void initBaseStats() {
    this.vitality = 8;
    this.dexterity = 6;
    this.strength = 6;
    this.intelligence = 4;
    this.physicalDefence = 0;
  }

  @Override
  public void initVitals() {
    this.hp = this.getMaxHp();
    this.xp = 0;
    this.gold = 0;
  }

  @Override
  public void initEquipped() {
    Item leatherTabbard = new Item("Leather Tabbard", 4, SlotType.CHEST, new Modifier(Stat.PHYS_DEF, 5));
    Item ruggedGreaves = new Item("Rugged Greaves", 2, SlotType.LEGS, new Modifier(Stat.PHYS_DEF, 4));
    Item ruggedGloves = new Item("Rugged Gloves", 2, SlotType.ARMS, new Modifier(Stat.PHYS_DEF, 4));
    Item ruggedBoots = new Item("Rugged Boots", 2, SlotType.FEET, new Modifier(Stat.PHYS_DEF, 3));

    Weapon rock = new Weapon("Rock", 2, SlotType.HAND, null, 8, 0, Rating.U, Rating.U, Rating.U);

    ruggedBoots.setLoreText("Strange boots from the ancient land of Zena, birthplace of the curious dealer Domhnall." +
            "The inlaid silver rings symbolize an explorer.");

    rock.setLoreText("Strange boots from the ancient land of Zena, birthplace of the curious dealer Domhnall." +
            "The inlaid silver rings symbolize an explorer.");

    obtain(rock);
    attemptToEquip(leatherTabbard);
    attemptToEquip(ruggedGreaves);
    attemptToEquip(ruggedGloves);
    attemptToEquip(ruggedBoots);
  }
}
