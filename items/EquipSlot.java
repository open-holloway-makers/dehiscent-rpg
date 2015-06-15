package items;

public class EquipSlot {
  public SlotType slotType;
  public Item item;

  public EquipSlot(SlotType slotType, Item item) {
    this.slotType = slotType;
    this.item = item;
  }

  public boolean isFree() {
    return item == null;
  }


}
