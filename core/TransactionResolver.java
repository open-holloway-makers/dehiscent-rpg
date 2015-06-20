package core;

import items.Item;
import items.SaleItem;
import npcs.Merchant;

import java.util.Optional;

public class TransactionResolver {

  public static void ResolveTransaction(Player p, Merchant m) {
    String decision = "";
    while (!decision.equals("leave")) {
      decision = IO.getDecision("What would you like to do? View wares, buy, sell or leave?\n");
      if (decision.startsWith("view")) {
        IO.println(m.inventoryToString());
      } else if (decision.startsWith("inspect") || decision.startsWith("i ")) {
        String itemName = decision.substring(decision.indexOf(" ")).trim();
        if (!m.attemptToInspect(itemName)) {
          p.attemptToInspect(itemName);
        }
      } else if (decision.startsWith("buy") || decision.startsWith("b ")) {
        String itemName = decision.substring(decision.indexOf(" ")).trim();
        buy(p, m, itemName);
      } else if (decision.startsWith("sell") || decision.startsWith("s ")) {
        String itemName = decision.substring(decision.indexOf(" ")).trim();
        sell(p, m, itemName);
      }
    }
  }

  public static void buy(Player p, Merchant m, String itemName) {
    Optional<SaleItem> itemToBuy = m.findSaleItem(itemName);
    if (itemToBuy.isPresent())
      buy(p, m, itemName, String.format("Want to buy a %s for %d gold? ",
              itemName, itemToBuy.get().getSalePrice()));
  }

  public static void sell(Player p, Merchant m, String itemName) {
    Optional<Item> itemToSell = p.findInventoryItem(itemName);
    if (itemToSell.isPresent())
      sell(p, m, itemName, String.format("Want to sell a %s for %d gold? ",
              itemName, m.getAdjustedBuyingPrice(itemToSell.get())));
  }

  public static void buy(Player p, Merchant m, String itemName, String confirmationMessage) {
    Optional<SaleItem> itemToBuy = m.findSaleItem(itemName);
    if (itemToBuy.isPresent()) {
      String d = IO.getDecision(confirmationMessage);
      if (d.startsWith("y")) {
        if (itemToBuy != null && p.getGold() >= itemToBuy.get().getSalePrice()) {
          p.obtain(itemToBuy.get().getItemForSale());
          m.lose(itemToBuy.get());
          p.subGold(itemToBuy.get().getSalePrice());
          m.addGold(itemToBuy.get().getSalePrice());
        }
      }
    }
  }

  public static void sell(Player p, Merchant m, String itemName, String confirmationMessage) {
    Optional<Item> itemToSell = p.findInventoryItem(itemName);
    if (itemToSell.isPresent()) {
      int adjustedPrice = m.getAdjustedBuyingPrice(itemToSell.get());
      String d = IO.getDecision(confirmationMessage);
      if (d.startsWith("y")) {
        if (itemToSell != null && m.getGold() >= itemToSell.get().getValue()) {
          p.lose(itemToSell.get());
          m.obtain(itemToSell.get());
          p.addGold(adjustedPrice);
          m.subGold(adjustedPrice);
        }
      }
    }
  }
}
