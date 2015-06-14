package items;

import core.Player;

import java.util.ArrayList;
import java.util.List;

public class EquipSlot {
  public Slot slot;
  public Item item;

  public EquipSlot(Slot slot, Item item) {
    this.slot = slot;
    this.item = item;
  }

  public boolean isFree() {
    return item == null;
  }


}
