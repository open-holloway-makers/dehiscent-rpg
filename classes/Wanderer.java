package classes;

import core.Player;
import core.Stat;
import items.Item;
import items.Modifier;
import items.SlotType;

public class Wanderer extends Player {

  public Wanderer() {
    super();
    initEquipped();
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

  //public Item(String name, int value, SlotType slotType, Modifier modifier) {
  public void initEquipped() {
    Item ruggedTabbard = new Item("Rugged Tabbard", 2, SlotType.CHEST, new Modifier(Stat.PHYS_DEF, 5));
    Item leatherGreaves = new Item("Leather Greaves", 4, SlotType.LEGS, new Modifier(Stat.PHYS_DEF, 4));
    Item leatherGloves = new Item("Leather Gloves", 4, SlotType.ARMS, new Modifier(Stat.PHYS_DEF, 4));
    obtain(ruggedTabbard);
    obtain(leatherGreaves);
    obtain(leatherGloves);
    attemptToEquip(ruggedTabbard.getName());
    attemptToEquip(leatherGreaves.getName());
    attemptToEquip(leatherGloves.getName());
  }
}
