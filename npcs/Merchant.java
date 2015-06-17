package npcs;

import core.IO;
import items.Item;
import items.SaleItem;

import java.util.ArrayList;
import java.util.List;

public class Merchant {

  private String name;
  private int gold;
  private double buyingModifier;
  private double sellingModifier;
  private List<SaleItem> inventory;

  public Merchant(String name, int gold, double buyingModifier, double sellingModifier) {
    this.name = name;
    this.gold = gold;
    this.buyingModifier = buyingModifier;
    this.sellingModifier = sellingModifier;
    inventory = new ArrayList<SaleItem>();

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

  public void obtain(Item item) {
    inventory.add(new SaleItem(item, getAdjustedSellingPrice(item)));
  }

  public void lose(SaleItem i) {
    inventory.remove(i);
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

  public SaleItem findSaleItem(String itemName) {
    for (SaleItem i : inventory) {
      if (i.getItemForSale().getName().equalsIgnoreCase(itemName)) {
        return i;
      }
    }
    return null;
  }

  public boolean attemptToInspect(String itemName) {
    Item itemToInspect = findSaleItem(itemName).getItemForSale();
    if (itemToInspect != null) {
      IO.println(itemToInspect.toString());
      return true;
    }
    return false;
  }

  public String inventoryToString() {
    String output = "";
    for (SaleItem i : inventory) {
      output += i.getItemForSale().getName() + "\t" + i.getSalePrice() + "g\n";
    }
    return output;
  }

  /**
   This is the price a merchant will buy goods from you for
   */
  public int getAdjustedBuyingPrice(Item item) {
    return item.getValue() * (int) java.lang.Math.floor(item.getValue() * buyingModifier);
  }

  /**
   * This is the price a merchant will sell his goods to you for,
   * it can be used for setting the price once an item has been
   * sold to the merchant
   */
  public int getAdjustedSellingPrice(Item item) {
    return item.getValue() * (int) java.lang.Math.floor(item.getValue() * sellingModifier);
  }

}
