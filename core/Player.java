package core;

import items.*;
import map.WorldPoint;

import javax.swing.text.html.Option;
import java.util.*;

public class Player {

  protected int hp, xp, gold;
  protected int physicalDefence;
  protected int vitality, strength, dexterity, intelligence;
  protected int tempVitality, tempStrength, tempDexterity, tempIntelligence;
  protected Map<String, EquipSlot> equipSlots;

  private WorldPoint position;
  private List<WorldPoint> visitedPoints;

  protected List<Item> inventory;
  protected List<Item> hiddenInventory;

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

  public void addGold(int x) {
    gold += x;
  }

  public void subGold(int x) {
    gold -= x;
  }

  public void addXp(int x) {
    xp += x;
  }

  public void subXp(int x) {
    xp -= x;
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

  public void addPhysDef(int x) {
    physicalDefence += x;
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

  public int getPhysDef() {
    return physicalDefence;
  }

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
    obtain(i, false);
  }

  public void obtain(Item i, boolean suppress) {
    if (!suppress) IO.println(i.getName() + " added to inventory.");
    inventory.add(i);
  }

  public void lose(Item i) {
    lose(i, false);
  }

  public void lose(Item i, boolean suppress) {
    if (!suppress) IO.println(i.getName() + " removed from inventory.");
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

  public boolean attemptToInspect(String itemName) {
    Optional<Item> itemToInspect = findItem(itemName)
            .map(Optional::of)
            .orElse(findEquippedItem(itemName));
    if (itemToInspect.isPresent())
      IO.println(itemToInspect.toString());
    return itemToInspect.isPresent();
  }

  public Optional<Item> findItem(String itemName) {
    return inventory.parallelStream()
            .filter(i -> i.getName().equalsIgnoreCase(itemName)).findAny();
  }

  public Optional<Item> findEquippedItem(String itemName) {
    return equipSlots.values().parallelStream()
            .map(e -> e.item)
            .filter(Objects::nonNull)
            .filter(i -> i.getName().equalsIgnoreCase(itemName))
            .findAny();
  }

  public boolean attemptToEquip(String s) {
    Item itemToEquip = findItem(s);
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
      } catch (NumberFormatException e) {
      }
      if (d == -1 || d >= possibleEquipSlots.size()) {
        IO.println("Please enter a number between 0 and " + (possibleEquipSlots.size() - 1));
      } else {
        slotToEquipTo = equipSlots.get(possibleEquipSlots.get(d));
      }
    }
    return equip(slotToEquipTo, itemToEquip);
  }

  public boolean attemptToUse(String itemName) {
    Item itemToUse = findItem(itemName);
    if (itemToUse == null) {
      IO.println("No item found");
      return false;
    } else if (!(itemToUse instanceof Consumable)) {
      IO.println("You can't use this item that way!");
      return false;
    } else {
      ((Consumable) itemToUse).use(this);
      return true;
    }
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
    return String.format("\nHP: %d/%d | XP: %d | Gold: %d\n", hp, getMaxHp(), xp, gold);
  }

  public String baseStatsToString() {
    return String.format("\nVIT: %d | DEX: %d | STR: %d | INT: %d\n", getBaseVit(), getBaseDex(), getBaseStr(), getBaseInt());
  }

  public String statsToString() {
    return String.format("\nVIT: %d | DEX: %d | STR: %d | INT: %d\n\nPHYS DEF: %d\n", getVit(), getDex(), getStr(), getInt(), getPhysDef());
  }

  public String equippedToString() {
    List<String> sortedKeys = new ArrayList<String>(equipSlots.keySet());
    Collections.sort(sortedKeys);
    String formatString = "%-16s%-24s%-8s%8s\n";
    String output;

    String title = "------------------------------------------------------------\n";
    title += String.format(formatString, "Equip Slot", "Item Name", "Dmg", "Modifiers");
    title += "------------------------------------------------------------";
    String outputHands = "";
    String outputBody = "";
    String outputAccessories = "";
    for (String s : sortedKeys) {
      EquipSlot e = equipSlots.get(s);
      String slotStr = s;
      String itemStr = (e.item == null) ? "(empty)" : e.item.getName();
      String dmgStr = "";
      String modStr = "";
      if (e.item != null) {
        if (e.item instanceof Weapon) {
          dmgStr = Integer.toString(((Weapon) e.item).getAttackRating(this));
        }
        modStr = e.item.modifiersToString();

      }

      output = String.format(formatString, s, itemStr, dmgStr, modStr);

      if (s.contains("Hand")) {
        outputHands += output;
      } else if (s.contains("Accessory")) {
        outputAccessories += output;
      } else {
        outputBody += output;
      }
    }
    return "\n" + title + "\n" + outputHands + "\n" + outputBody + "\n" + outputAccessories;
  }

  public String inventoryToString() {
    String output = "";
    for (Item i : inventory) {
      output += i.getName() + "\n";
    }
    return output;
  }
}
