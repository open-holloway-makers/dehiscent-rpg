package core;

import items.EquipSlot;
import items.Item;
import items.Modifier;
import items.Slot;
import map.WorldPoint;

import java.util.List;
import java.util.ArrayList;

public class Player {

  private int hp, xp, gold;
  private int physicalDefence;
  private int vitality, strength, dexterity, intelligence;
  private int tempVitality, tempStrength, tempDexterity, tempIntelligence;
  private List<EquipSlot> equipSlots;

  private WorldPoint position;
  private List<WorldPoint> visitedPoints;

  private List<Item> inventory;
  private List<Item> hiddenInventory;

  public Player() {
    initBaseStats();
    initVitals();
    position = new WorldPoint(0, 0);
    visitedPoints = new ArrayList<WorldPoint>();
    visitedPoints.add(position);

    equipSlots = new ArrayList<EquipSlot>();
    equipSlots.add(new EquipSlot(Slot.ACCESSORY, null));
    equipSlots.add(new EquipSlot(Slot.ACCESSORY, null));
    equipSlots.add(new EquipSlot(Slot.ACCESSORY, null));
    equipSlots.add(new EquipSlot(Slot.ACCESSORY, null));
    equipSlots.add(new EquipSlot(Slot.HEAD, null));
    equipSlots.add(new EquipSlot(Slot.CHEST, null));
    equipSlots.add(new EquipSlot(Slot.ARMS, null));
    equipSlots.add(new EquipSlot(Slot.LEGS, null));
    equipSlots.add(new EquipSlot(Slot.FEET, null));
    equipSlots.add(new EquipSlot(Slot.HAND, null));
    equipSlots.add(new EquipSlot(Slot.HAND, null));
  }

  public void addHp(int x) {
    hp = java.lang.Math.min(hp + x, getMaxHp());
  }

  public void subHp(int x) {
    hp -= x;
  }

  public void fullHeal() {
    setHp(getMaxHp());
  }

  public void setHp(int x) {
    hp = java.lang.Math.min(x, getMaxHp());
  }

  public void setBaseVit(int x) {
    vitality = x;
  }

  public void setBaseStr(int x) {
    strength = x;
  }

  public void setBaseDex(int x) {
    dexterity = x;
  }

  public void setBaseInt(int x) {
    intelligence = x;
  }

  public void addVit(int x) {
    tempVitality += x;
  }

  public void addDex(int x) {
    tempDexterity += x;
  }

  public void addStr(int x) {
    tempStrength += x;
  }

  public void addInt(int x) {
    tempIntelligence += x;
  }

  public void setPosition(int x, int y) {
    position = new WorldPoint(x, y);
    visitedPoints.add(position);
  }

  // This should probably be changed in the future!
  public int getMaxHp() {
    return (int) (100 * (this.vitality / 10.0));
  }

  public int getHp() {
    return hp;
  }

  public int getXp() {
    return xp;
  }

  public int getGold() {
    return gold;
  }

  public int getBaseVit() {
    return vitality;
  }

  public int getBaseStr() {
    return strength;
  }

  public int getBaseDex() {
    return dexterity;
  }

  public int getBaseInt() {
    return intelligence;
  }

  public int getVit() {
    return vitality + tempVitality;
  }

  public int getStr() {
    return strength + tempStrength;
  }

  public int getDex() {
    return dexterity + tempDexterity;
  }

  public int getInt() {
    return intelligence + tempIntelligence;
  }

  public WorldPoint getPosition() {
    return position;
  }

  public List<WorldPoint> getVisited() {
    return visitedPoints;
  }

  public List<EquipSlot> getEquipSlots() {
    return equipSlots;
  }

  public List<Item> getInventory() { return inventory; }

  public List<Item> getHiddenInventory() { return hiddenInventory; }

  // This is all pretty terrible
  public void equip(Item i) {
    if (i.isEquippable()) {
      Slot s = i.getSlot();
      if (i.getSlot().isUnique(equipSlots)) {
        for (EquipSlot e : equipSlots) {
          if (e.slot == s) {
            equip(e, i);
          }
        }
      } else {
        int count = 0;
        IO.println("Which slot would you like to equip to?");
        for (EquipSlot e : equipSlots) {
          if (e.slot == s) {
            count++;
            IO.print(count + ": " + e.item.getName());
          }
        }
        String decision = IO.getDecision("\n");
        int d = 0;
        try {
          d = Integer.parseInt(decision);
        } catch (NumberFormatException e) {
        }
        for (EquipSlot e : equipSlots) {
          if (e.slot == s) {
            count++;
            if (count == d) {
              equip(e, i);
            }
          }
        }
      }
      inventory.remove(i);
    } else {
      IO.println("Cannot equip " + i.getName());
    }
  }

  public void equip(EquipSlot e, Item i) {
    unequip(e);
    e.item = i;
    Modifier.apply(i.getModifiers(), this);
  }

  public void unequip(EquipSlot e) {
    if (!e.isFree()) unequip(e.item.getName());
  }

  public void unequip(String s) {
    for (EquipSlot e : equipSlots) {
      if (!e.isFree() && e.item.getName().equalsIgnoreCase(s)) {
        Modifier.remove(e.item.getModifiers(), this);
        inventory.add(e.item);
        e.item = null;
      }
    }
  }

  public void initBaseStats() {
    this.vitality = 6;
    this.dexterity = 6;
    this.strength = 6;
    this.intelligence = 6;
  }

  public void initVitals() {
    this.hp = this.getMaxHp();
    this.xp = 0;
    this.gold = 0;
  }

  public void goNorth() {
    setPosition(position.x(), position.y() - 1);
  }

  public void goEast() {
    setPosition(position.x() + 1, position.y());
  }

  public void goSouth() {
    setPosition(position.x(), position.y() + 1);
  }

  public void goWest() {
    setPosition(position.x() - 1, position.y());
  }

  public String vitalsToString() {
    return "HP: " + hp + "/" + getMaxHp() + " | XP: " + xp + " | Gold: " + gold;
  }

  public String statsToString() {
    return "VIT: " + getVit() + " | DEX:" + getDex() + " | STR: " + getStr() + " | INT: " + getInt();
  }

  public String equippedToString() {
    String output = "";
    for (EquipSlot e : equipSlots) {
      String slotStr = e.slot.toString();
      String itemStr = (e.item == null) ? "(empty)" : e.item.toString();
      output += (slotStr + ": " + itemStr + "\n");
    }
    return output;
  }

  public String inventoryToString() {
    String output = "";
    for (Item i : inventory) {
      output += i.getName() + "\n";
    }
    return output;
  }
}
