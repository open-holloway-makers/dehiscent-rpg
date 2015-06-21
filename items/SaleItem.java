package items;

public class SaleItem {

  private Item itemForSale;
  private int salePrice;
  private int quantity;

  public SaleItem(Item itemForSale, int salePrice, int quantity) {
    this.itemForSale = itemForSale;
    this.salePrice = salePrice;
    this.quantity = quantity;
  }

  public Item getItemForSale() {
    return itemForSale;
  }

  public void setItemForSale(Item itemForSale) {
    this.itemForSale = itemForSale;
  }

  public int getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(int salePrice) {
    this.salePrice = salePrice;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return itemForSale.toString();
  }
}
