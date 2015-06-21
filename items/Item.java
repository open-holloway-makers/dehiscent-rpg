package items;

import core.IO;

import java.util.ArrayList;

public class Item {

  private String name;
  private SlotType slotType;
  private ArrayList<Modifier> modifiers;
  private String loreText;
  private int value;

  public Item(String name, int value) {
    this.name = name;
    this.value = value;
    this.modifiers = new ArrayList<>();
    this.slotType = null;
  }

  public Item(String name, int value, SlotType slotType, Modifier modifier) {
    this.name = name;
    this.value = value;
    this.slotType = slotType;
    modifiers = new ArrayList<>();
    if (modifier != null) this.modifiers.add(modifier);
  }

  public Item(String name, int value, SlotType slotType, ArrayList<Modifier> modifiers) {
    this.name = name;
    this.value = value;
    this.slotType = slotType;
    this.modifiers = modifiers;
  }

  public String getName() {
    return name;
  }

  public SlotType getSlotType() {
    return slotType;
  }

  public ArrayList<Modifier> getModifiers() {
    return modifiers;
  }

  public boolean isEquippable() {
    return slotType != null;
  }

  public void setLoreText(String loreText) {
    this.loreText = loreText;
  }

  public String getLoreText() {
    return (loreText != null) ? loreText : "";
  }

  @Override
  public String toString() {
    final int len = IO.BOX_WIDTH;
    return "\n" +
            IO.formatBanner(len) +
            IO.formatColumns(len, getName(), getValue() + " gold") +
            IO.formatBanner(len) +
            IO.formatColumns(len, "Equip to:", (isEquippable()) ? getSlotType().getValue().toString() : "n/a") +
            IO.formatColumns(len, "Modifier:", (getModifiers().size() > 0) ? modifiersToString() : "n/a") +
            IO.formatAsBox(getLoreText(), len, true);
  }

  public String modifiersToString() {
    return modifiers.stream()
            .map(Modifier::toString)
            .reduce("", (a, b) -> " " + a.concat(b));
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
