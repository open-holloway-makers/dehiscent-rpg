package core;

import items.Item;
import items.SaleItem;
import npcs.Merchant;

import java.util.Optional;

public class TransactionResolver {

  public static void resolveTransaction(Player player, Merchant merchant) {
    String decision = "";
    while (!decision.startsWith("l")) {
      if (!decision.startsWith("v") && !decision.startsWith("i")) {
        IO.println(merchant.inventoryToString());
        IO.println(affairsToString(player, merchant));
      }
      decision = IO.getDecision("What would you like to do? Buy, sell or leave?\n");
      if (decision.startsWith("v")) {
        if (decision.contains(" inv")) {
          IO.println(player.inventoryToString());
        } else {
          IO.println(merchant.inventoryToString());
        }
      } else if (decision.startsWith("inspect") || decision.startsWith("i ")) {
        String itemName = decision.substring(decision.indexOf(" ")).trim();
        if (!merchant.attemptToInspect(itemName)) {
          player.attemptToInspect(itemName);
        }
      } else if (decision.startsWith("buy") || decision.startsWith("b ")) {
        String itemName = decision.substring(decision.indexOf(" ")).trim();
        buy(player, merchant, itemName);
      } else if (decision.startsWith("sell") || decision.startsWith("s ")) {
        String itemName = decision.substring(decision.indexOf(" ")).trim();
        sell(player, merchant, itemName);
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
        if (p.getGold() < itemToBuy.get().getSalePrice()) {
          IO.println("Sorry, you don't have enough gold.");
        } else if (itemToBuy.get() != null) {
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
        if (itemToSell.get() != null && m.getGold() >= itemToSell.get().getValue()) {
          p.lose(itemToSell.get());
          m.obtain(itemToSell.get(), 1);
          p.addGold(adjustedPrice);
          m.subGold(adjustedPrice);
        }
      }
    }
  }

  private static String affairsToString(Player player, Merchant merchant) {
    return "\b" + IO.formatBanner(IO.BOX_WIDTH) +
            IO.formatColumns(IO.BOX_WIDTH, "You : " + player.getGold(),
                    merchant.getGold() + " : " + merchant.getName()) +
            IO.formatBanner(IO.BOX_WIDTH);
  }
}
