package core;

import items.*;
import map.WorldPoint;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class Player {

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
    position = new WorldPoint(0, 0);
    visitedPoints = new ArrayList<>();
    visitedPoints.add(position);

    inventory = new ArrayList<>();
    hiddenInventory = new ArrayList<>();

    initEquipSlots();

    initBaseStats();
    initVitals();
    initEquipped();
  }

  public abstract void initBaseStats();

  public abstract void initVitals();

  public abstract void initEquipped();

  public void initEquipSlots() {
    equipSlots = new HashMap<>();
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

  public void addHp(int x) {
    hp = java.lang.Math.min(hp + x, getMaxHp());
  }

  public void subHp(int x) {
    hp -= x;
  }

  public void addGold(int x) {
    addGold(x, false);
  }

  public void subGold(int x) {
    subGold(x, false);
  }

  public void addXp(int x) {
    addXp(x, false);
  }

  public void addGold(int x, boolean suppress) {
    if (!suppress) IO.printf("You gained %d gold.\n", x);
    gold += x;
  }

  public void subGold(int x, boolean suppress) {
    if (!suppress) IO.printf("You lost %d gold.\n", x);
    gold -= x;
  }

  public void addXp(int x, boolean suppress) {
    if (!suppress) IO.printf("You gained %d xp.\n", x);
    xp += x;
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
    if (findInventoryItem(i.getName()).isPresent() || findEquippedItem(i.getName()).isPresent()) {
      if (findEquippedItem(i.getName()).isPresent())
        attemptToUnequip(i.getName());
      inventory.remove(i);
      if (!suppress) IO.println(i.getName() + " removed from inventory.");
    }
  }

  public void obtainHidden(Item i) {
    hiddenInventory.add(i);
  }

  public void loseHidden(Item i) {
    hiddenInventory.remove(i);
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

  public void die() {
    visitedPoints = new ArrayList<>();
    setPosition(0, 0);
    fullHeal();
    gold /= 2;
  }

  public boolean attemptToInspect(String itemName) {
    Optional<Item> itemToInspect = findItem(itemName);
    if (itemToInspect.isPresent() && itemToInspect.get() instanceof Weapon)
      IO.println(((Weapon) itemToInspect.get()).toString(this));
    else if (itemToInspect.isPresent())
      IO.println(itemToInspect.get().toString());
    return itemToInspect.isPresent();
  }

  public boolean attemptToUse(String itemName) {
    Optional<Item> itemToUse = findInventoryItem(itemName);
    if (itemToUse.isPresent()) {
      if (!(itemToUse.get() instanceof Consumable)) {
        IO.println("You can't use this item that way!");
        return false;
      } else {
        ((Consumable) itemToUse.get()).use(this);
        return true;
      }
    }
    IO.println("No item found");
    return false;
  }

  public Optional<Item> findItem(String itemName) {
    return findInventoryItem(itemName)
            .map(Optional::of)
            .orElse(findEquippedItem(itemName));
  }

  public List<Item> getAllKnownItems() {
    List<Item> equippedItems = getEquipSlots().values()
            .parallelStream()
            .map(EquipSlot::getItem)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    equippedItems.addAll(getInventory());
    return equippedItems;
  }

  public Optional<Item> findInventoryItem(String itemName) {
    return inventory.parallelStream()
            .filter(i -> i.getName().equalsIgnoreCase(itemName)).findAny();
  }

  public Optional<Item> findEquippedItem(String itemName) {
    return equipSlots.values().parallelStream()
            .map(e -> e.getItem())
            .filter(Objects::nonNull)
            .filter(i -> i.getName().equalsIgnoreCase(itemName))
            .findAny();
  }


  public Optional<EquipSlot> findEquippedItemSlot(String itemName) {
    return equipSlots.values().parallelStream()
            .filter(e -> Objects.nonNull(e.getItem())
                    && e.getItem().getName().equalsIgnoreCase(itemName))
            .findAny();
  }

  public boolean attemptToEquip(Item itemToEquip) {
    if (!itemToEquip.isEquippable()) {
      IO.println("Item is not equippable");
      return false;
    }
    List<String> possibleEquipSlots = equipSlots.entrySet().parallelStream()
            .filter(e -> e.getValue().getSlotType() == itemToEquip.getSlotType())
            .map(Map.Entry::getKey)
            .sorted()
            .collect(Collectors.toList());

    EquipSlot slotToEquipTo = null;
    if (possibleEquipSlots.size() == 1) {
      slotToEquipTo = equipSlots.get(possibleEquipSlots.get(0));
    } else if (possibleEquipSlots.size() > 1) {
      IntStream.range(0, possibleEquipSlots.size())
              .forEachOrdered(i -> IO.println(i + ": " + possibleEquipSlots.get(i)));

      double d = IO.getNumberWithinRange(
              "Which slot would you like to equip to? ",
              0, possibleEquipSlots.size() - 1);

      if (d > Double.NEGATIVE_INFINITY)
        slotToEquipTo = equipSlots.get(possibleEquipSlots.get((int) d));
    }
    return equip(slotToEquipTo, itemToEquip);
  }

  public boolean attemptToEquip(String s) {
    Optional<Item> itemOptional = findInventoryItem(s);
    if (!itemOptional.isPresent()) {
      IO.println("No item found");
      return false;
    }
    Item itemToEquip = itemOptional.get();
    return attemptToEquip(itemToEquip);
  }

  public boolean attemptToUnequip(String itemName) {
    return unequip(findEquippedItemSlot(itemName).orElse(null));
  }

  public boolean equip(EquipSlot slotToEquipTo, Item itemToEquip) {
    if (slotToEquipTo != null) {
      unequip(slotToEquipTo);
      Modifier.apply(itemToEquip.getModifiers(), this);
      slotToEquipTo.setItem(itemToEquip);
      inventory.remove(itemToEquip);
      IO.println(itemToEquip.getName() + " equipped.");
      return true;
    }
    return false;
  }

  public boolean unequip(EquipSlot slotToUnequipFrom) {
    if (slotToUnequipFrom.getItem() != null) {
      Modifier.remove(slotToUnequipFrom.getItem().getModifiers(), this);
      inventory.add(slotToUnequipFrom.getItem());
      IO.println(slotToUnequipFrom.getItem().getName() + " unequipped.");
      slotToUnequipFrom.setItem(null);
      return true;
    }
    return false;
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

    String[] sortedKeys = Stream.of(

            equipSlots.keySet().stream().filter(x -> x.contains("Hand")),
            equipSlots.keySet().stream().filter(x -> x.contains("Head")),
            equipSlots.keySet().stream().filter(x -> x.contains("Chest")),
            equipSlots.keySet().stream().filter(x -> x.contains("Arms")),
            equipSlots.keySet().stream().filter(x -> x.contains("Legs")),
            equipSlots.keySet().stream().filter(x -> x.contains("Feet")),
            equipSlots.keySet().stream().filter(x -> x.contains("Accessory")).sorted())
            .flatMap(x -> x)
            .distinct()
            .toArray(String[]::new);

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
      String itemStr = (e.getItem() == null) ? "(empty)" : e.getItem().getName();
      String dmgStr = "";
      String modStr = "";
      if (e.getItem() != null) {
        if (e.getItem() instanceof Weapon) {
          dmgStr = Integer.toString(((Weapon) e.getItem()).getAttackRating(this));
        }
        modStr = e.getItem().modifiersToString();
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
    return String.join("\n", "\n", title, outputHands, outputBody, outputAccessories);
  }

  public String inventoryToString() {
    Map<Item, Long> invCount = getInventory()
            .stream()
            .sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName()))
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    StringBuilder output = new StringBuilder();
    output.append(IO.formatBanner(IO.BOX_WIDTH));
    for (Map.Entry<Item, Long> e : invCount.entrySet()) {
      output.append(IO.formatColumns(IO.BOX_WIDTH, false, true,
              e.getKey().getName(),
              (e.getKey().isEquippable()) ? e.getKey().getSlotType().toString() : "",
              e.getKey().getValue() + "g",
              "x" + e.getValue()));
    }
    output.append(IO.formatBanner(IO.BOX_WIDTH));
    return output.toString();
  }

  public String verboseEquippedToString() {
    Item[] items = getEquipSlots().values().stream()
            .map(EquipSlot::getItem)
            .toArray(Item[]::new);

    StringBuilder output = new StringBuilder();
    for (Item i : items) {
      if (i == null)
        ;
      else if (i instanceof Weapon)
        output.append(((Weapon) i).toString(this));
      else
        output.append(i.toString());
    }
    return output.toString();
  }
}
