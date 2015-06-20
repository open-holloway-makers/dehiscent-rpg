package items;

import java.util.List;

public enum SlotType {
  HEAD("Head"), CHEST("Chest"), LEGS("Legs"), ARMS("Arms"), FEET("Feet"), HAND("Hand"), ACCESSORY("Accessory");

  private String value;

  SlotType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return this.getValue();
  }

  public boolean isUnique(List<EquipSlot> slots) {
    return slots.parallelStream().filter(s -> s.getSlotType() == this).count() == 1;
  }

  public boolean isAvailable(List<EquipSlot> slots) {
    return slots.parallelStream().anyMatch(s -> s.getSlotType() == this && s.isFree());
  }
}
