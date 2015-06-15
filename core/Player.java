package core;

import items.EquipSlot;
import items.Item;
import items.Modifier;
import items.SlotType;
import map.WorldPoint;

import java.util.*;

public class Player {

  private int hp, xp, gold;
  private int physicalDefence, physicalDamage;
  private int vitality, strength, dexterity, intelligence;
  private int tempVitality, tempStrength, tempDexterity, tempIntelligence;
  private Map<String, EquipSlot> equipSlots;

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

    inventory = new ArrayList<Item>();
    hiddenInventory = new ArrayList<Item>();

    equipSlots = new HashMap<String, EquipSlot>();
    equipSlots.put("Left Hand", new EquipSlot(SlotType.HAND, null));
    equipSlots.put("Right Hand", new EquipSlot(SlotType.HAND, null));
    equipSlots.put("Head", new EquipSlot(SlotType.HEAD, null));
    equipSlots.put("Chest", new EquipSlot(SlotType.CHEST, null));
    equipSlots.put("Arms", new EquipSlot(SlotType.ARMS, null));
    equipSlots.put("Legs", new EquipSlot(SlotType.LEGS, null));
    equipSlots.put("Feet", new EquipSlot(SlotType.FEET, null));
    equipSlots.put("Accessory (1)", new EquipSlot(SlotType.ACCESSORY, null));
    equipSlots.put("Accessory (2)", new EquipSlot(SlotType.ACCESSORY, null));
    equipSlots.put("Accessory (3)", new EquipSlot(SlotType.ACCESSORY, null));
    equipSlots.put("Accessory (4)", new EquipSlot(SlotType.ACCESSORY, null));
  }

  public void initBaseStats() {
    this.vitality = 6;
    this.dexterity = 6;
    this.strength = 6;
    this.intelligence = 6;
    this.physicalDefence = 0;
    this.physicalDamage = 0;
  }

  public void initVitals() {
    this.hp = this.getMaxHp();
    this.xp = 0;
    this.gold = 0;
  }

  public void addHp(int x) {
    hp = java.lang.Math.min(hp + x, getMaxHp());
  }

  public void subHp(int x) {
    hp -= x;
  }

  public void addGold(int x) { gold += x; }

  public void subGold(int x) { gold -= x; }

  public void addXp(int x) { xp += x; }

  public void subXp(int x) { xp -= x; }

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

  public void addPhysDef(int x) { physicalDefence += x; }

  public void addPhysDmg(int x) { physicalDamage += x; }

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

  public int getPhysDef() { return physicalDefence; }

  public int getPhysDmg() { return physicalDamage; }

  public WorldPoint getPosition() {
    return position;
  }

  public List<WorldPoint> getVisited() {
    return visitedPoints;
  }

  public Map<String, EquipSlot> getEquipSlots() {
    return equipSlots;
  }

  public List<Item> getInventory() {
    return inventory;
  }

  public List<Item> getHiddenInventory() {
    return hiddenInventory;
  }

  public void obtain(Item i) {
    IO.println(i.getName() + " added to inventory.");
    inventory.add(i);
  }

  public void lose(Item i) {
    IO.println(i.getName() + " removed from inventory.");
    inventory.remove(i);
  }

  public void obtainHidden(Item i) {
    hiddenInventory.add(i);
  }

  public void loseHidden(Item i) {
    hiddenInventory.remove(i);
  }

  public void die() {
    visitedPoints = new ArrayList<WorldPoint>();
    setPosition(0, 0);
    fullHeal();
    gold /= 2;
  }

  public void attemptToInspect(String itemName) {
    Item itemToInspect = null;
    for (Item i : inventory) {
      if (i.getName().equalsIgnoreCase(itemName)) {
        itemToInspect = i;
        break;
      }
    }
    if (itemToInspect == null) {
      for (String key : equipSlots.keySet()) {
        EquipSlot e = equipSlots.get(key);
        if (e.item != null && e.item.getName().equalsIgnoreCase(itemName)) {
          itemToInspect = e.item;
          break;
        }
      }
    }
    if (itemToInspect != null) {
      IO.println(itemToInspect.toString());
    }
  }

  public boolean attemptToEquip(String s) {
    Item itemToEquip = null;
    for (Item i : inventory) {
      if (i.getName().equalsIgnoreCase(s)) {
        itemToEquip = i;
        break;
      }
    }
    if (!itemToEquip.isEquippable()) {
      IO.println("Item is not equippable");
      return false;
    }
    if (itemToEquip == null) {
      IO.println("No item found");
      return false;
    }
    List<String> possibleEquipSlots = new ArrayList<>();
    for (String key : equipSlots.keySet()) {
      EquipSlot e = equipSlots.get(key);
      if (e.slotType == itemToEquip.getSlotType()) {
        possibleEquipSlots.add(key);
      }
    }
    Collections.sort(possibleEquipSlots);
    EquipSlot slotToEquipTo = null;
    if (possibleEquipSlots.size() == 1) {
      slotToEquipTo = equipSlots.get(possibleEquipSlots.get(0));
    } else if (possibleEquipSlots.size() > 1) {
      for (int i = 0; i < possibleEquipSlots.size(); i++) {
        IO.println(i + ": " + possibleEquipSlots.get(i));
      }
      String decision = IO.getDecision("Which slot would you like to equip to? ");
      int d = -1;
      try {
        d = Integer.parseInt(decision);
      } catch (NumberFormatException e) {}
      if (d == -1 || d >= possibleEquipSlots.size()) {
        IO.println("Please enter a number between 0 and " + (possibleEquipSlots.size() - 1));
      } else {
        slotToEquipTo = equipSlots.get(possibleEquipSlots.get(d));
      }
    }
    return equip(slotToEquipTo, itemToEquip);
  }

  public boolean attemptToUnequip(String itemName) {
    EquipSlot slotToUnequipFrom = null;
    for (String key : equipSlots.keySet()) {
      EquipSlot e = equipSlots.get(key);
      if (e.item != null && e.item.getName().equalsIgnoreCase(itemName)) {
        slotToUnequipFrom = e;
        break;
      }
    }
    return unequip(slotToUnequipFrom);
  }

  public boolean equip(EquipSlot slotToEquipTo, Item itemToEquip) {
    if (slotToEquipTo != null) {
      unequip(slotToEquipTo);
      Modifier.apply(itemToEquip.getModifiers(), this);
      slotToEquipTo.item = itemToEquip;
      inventory.remove(itemToEquip);
      IO.println(itemToEquip.getName() + " equipped.");
      return true;
    }
    return false;
  }

  public boolean unequip(EquipSlot slotToUnequipFrom) {
    if (slotToUnequipFrom.item != null) {
      Modifier.remove(slotToUnequipFrom.item.getModifiers(), this);
      inventory.add(slotToUnequipFrom.item);
      IO.println(slotToUnequipFrom.item.getName() + " unequipped.");
      slotToUnequipFrom.item = null;
      return true;
    } else {
      return false;
    }
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
    return "\nHP: " + hp + "/" + getMaxHp() + " | XP: " + xp + " | Gold: " + gold + "\n";
  }

  public String baseStatsToString() {
    return "\nVIT: " + getBaseVit() + " | DEX:" + getBaseDex() + " | STR: " + getBaseStr() + " | INT: " + getBaseInt() + "\n";
  }

  public String statsToString() {
    return "\nVIT: " + getVit() + " | DEX:" + getDex() + " | STR: " + getStr() + " | INT: " + getInt() +
            "\n\nPHYS DEF: " + getPhysDef() + " | PHYS DMG: " + getPhysDmg() + "\n";
  }

  public String equippedToString() {
    List<String> sortedKeys = new ArrayList<String>(equipSlots.keySet());
    Collections.sort(sortedKeys);
    String output;
    String outputHands = "";
    String outputBody = "";
    String outputAccessories = "";
    for (String s : sortedKeys) {
      EquipSlot e = equipSlots.get(s);
      String slotStr = String.format("%1$-20s", s + ":");
      String itemStr = (e.item == null) ? "(empty)" : e.item.getName();
      if (e.item != null) {
        itemStr += String.format("%1$25s", e.item.modifiersToString());
      }
      output = (slotStr + itemStr + "\n");
      if (s.contains("Hand")) {
        outputHands += output;
      } else if (s.contains("Accessory")) {
        outputAccessories += output;
      } else {
        outputBody += output;
      }
    }
    return "\n" + outputHands + "\n" + outputBody + "\n" + outputAccessories;
  }

  public String inventoryToString() {
    String output = "";
    for (Item i : inventory) {
      output += i.getName() + "\n";
    }
    return output;
  }
}
