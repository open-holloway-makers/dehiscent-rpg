package items;

public class EquipSlot {
  private SlotType slotType;
  private Item item;

  public EquipSlot(SlotType slotType, Item item) {
    this.setSlotType(slotType);
    this.setItem(item);
  }

  public boolean isFree() {
    return getItem() == null;
  }

  public SlotType getSlotType() {
    return slotType;
  }

  public void setSlotType(SlotType slotType) {
    this.slotType = slotType;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }
}
