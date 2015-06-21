package npcs;

import core.IO;
import items.Item;
import items.SaleItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Merchant {

  private String name;
  private String storeName;
  private int gold;
  private double buyingModifier;
  private double sellingModifier;
  private List<SaleItem> inventory;

  public Merchant(String name, int gold, double buyingModifier, double sellingModifier) {
    this.name = name;
    this.gold = gold;
    this.buyingModifier = buyingModifier;
    this.sellingModifier = sellingModifier;
    inventory = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<SaleItem> getInventory() {
    return inventory;
  }

  public void setInventory(List<SaleItem> inventory) {
    this.inventory = inventory;
  }

  public void obtain(Item item, int quantity) {
    int index = -1;
    for (SaleItem s : getInventory()) {
      if (s.getItemForSale().getName().equals(item.getName()) &&
              s.getItemForSale().getValue() == item.getValue()) {
        index = getInventory().indexOf(s);
      }
    }
    if (index >= 0) {
      if (inventory.get(index).getQuantity() < Integer.MAX_VALUE) {
        inventory.get(index).setQuantity(inventory.get(index).getQuantity() + 1);
      }
    } else {
      inventory.add(new SaleItem(item, getAdjustedSellingPrice(item), quantity));
    }
  }

  public void lose(SaleItem item) {
    int index = inventory.indexOf(item);
    if (inventory.get(index).getQuantity() < Integer.MAX_VALUE) {
      inventory.get(index).setQuantity(inventory.get(index).getQuantity() - 1);
    }
    if (inventory.get(index).getQuantity() <= 0) {
      inventory.remove(index);
    }
  }

  public int getGold() {
    return gold;
  }

  public void setGold(int gold) {
    this.gold = gold;
  }

  public void addGold(int gold) {
    this.gold += gold;
  }

  public void subGold(int gold) {
    this.gold -= gold;
  }

  public Optional<SaleItem> findSaleItem(String itemName) {
    return inventory.parallelStream()
            .filter(i -> i.getItemForSale().getName().equalsIgnoreCase(itemName))
            .findAny();
  }

  public boolean attemptToInspect(String itemName) {
    Optional<SaleItem> itemToInspectOption = findSaleItem(itemName);
    if (itemToInspectOption.isPresent()) {
      IO.println(itemToInspectOption.get().toString());
      return true;
    }
    return false;
  }

  public String inventoryToString() {
    StringBuilder output = new StringBuilder();
    output.append("\n" + IO.formatBanner(IO.BOX_WIDTH));
    output.append(IO.formatColumns(IO.BOX_WIDTH, false, true,
            (getName() != null) ? getStoreName().toUpperCase() : "INVENTORY"));
    output.append(IO.formatBanner(IO.BOX_WIDTH));
    for (SaleItem s : getInventory()) {
      output.append(
              IO.formatColumns(IO.BOX_WIDTH, false, true,
                      s.getItemForSale().getName(),
                      s.getSalePrice() + " gold",
                      ((s.getQuantity()) < Integer.MAX_VALUE ? "x" + s.getQuantity() : "\u221e" )));
    }
    output.append(IO.formatBanner(IO.BOX_WIDTH));
    return output.toString();
  }

  /**
   * This is the price a merchant will buy goods from you for
   */
  public int getAdjustedBuyingPrice(Item item) {
    return (int) java.lang.Math.floor(item.getValue() * buyingModifier);
  }

  /**
   * This is the price a merchant will sell his goods to you for,
   * it can be used for setting the price once an item has been
   * sold to the merchant
   */
  public int getAdjustedSellingPrice(Item item) {
    return (int) java.lang.Math.floor(item.getValue() * sellingModifier);
  }

  public String getStoreName() {
    return storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }
}
