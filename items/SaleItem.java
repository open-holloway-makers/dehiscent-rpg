package items;

public class SaleItem {

  private Item itemForSale;
  private int salePrice;

  public SaleItem(Item itemForSale, int salePrice) {
    this.itemForSale = itemForSale;
    this.salePrice = salePrice;
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
}
