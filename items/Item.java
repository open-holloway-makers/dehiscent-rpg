package items;

import java.util.ArrayList;

public class Item {

  private String name;
  private SlotType slotType;
  private ArrayList<Modifier> modifiers;
  private String loreText;
  private int value;

  public Item(String name, int value, SlotType slotType, Modifier modifier) {
    this.name = name;
    this.value = value;
    this.slotType = slotType;
    modifiers = new ArrayList<Modifier>();
    this.modifiers.add(modifier);
  }

  public Item(String name, SlotType slotType, ArrayList<Modifier> modifiers) {
    this.name = name;
    this.slotType = slotType;
    this.modifiers = modifiers;
  }

  public String getName() {
    return name;
  }

  public SlotType getSlotType() { return slotType; }

  public ArrayList<Modifier> getModifiers() { return modifiers; }

  public Item(SlotType slotType) {
    slotType = this.slotType;
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
    String output = "\n";
    output += ("Item: " + name + "\n");
    if (isEquippable()) {
      output += ("Equip to: " + slotType.getValue() + "\n");
    }
    output += modifiersToString();
    output += "\n\n" + getLoreText();
    return output;
  }

  public String modifiersToString() {
    String output = "";
    for (Modifier m : modifiers) {
      output += (m.toString() + "  ");
    }
    return output;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
