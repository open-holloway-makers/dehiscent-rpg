package items;

import core.Player;

import java.util.ArrayList;

public class Item {

  private String name;
  private Slot slot;
  private ArrayList<Modifier> modifiers;

  public Item(String name, Slot slot, Modifier modifier) {
    this.name = name;
    this.slot = slot;
    modifiers = new ArrayList<Modifier>();
    this.modifiers.add(modifier);
  }

  public Item(String name, Slot slot, ArrayList<Modifier> modifiers) {
    this.name = name;
    this.slot = slot;
    this.modifiers = modifiers;
  }

  public String getName() {
    return name;
  }

  public Slot getSlot() { return slot; }

  public ArrayList<Modifier> getModifiers() { return modifiers; }

  public Item(Slot slot) {
    slot = this.slot;
  }

  public boolean isEquippable() {
    return slot != null;
  }
}
