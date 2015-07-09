package core;

import items.Item;
import items.SaleItem;
import npcs.Merchant;

import java.util.Optional;

/**
 * A helper class to resolve shop events.
 */
public class TransactionResolver {

  /**
   * A static helper method which takes a single player and
   * a single merchant and allows them to make commercial
   * exchanges with one another.
   *
   * The core of the method is a loop which asks whether the
   * player would like to buy sell or leave, they also have
   * the option to
   *
   * @param player the player involved in the transaction.
   * @param merchant the merchant involved in the transaction.
   */
  /*
   * TODO Player should be able to buy/sell multiple in one go.
   * TODO Player should be able to buy/sell without needing confirmation.
   */
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

  /**
   * Wrapper method for buy which generates a confirmation message
   * before initiating the exchange.
   *
   * @param player the player acquiring the item.
   * @param merchant the merchant selling the item.
   * @param itemName the name of the item being bought.
   */
  public static void buy(Player player, Merchant merchant, String itemName) {
    Optional<SaleItem> itemToBuy = merchant.findSaleItem(itemName);
    if (itemToBuy.isPresent())
      buy(player, merchant, itemName, String.format("Want to buy a %s for %d gold? ",
              itemName, itemToBuy.get().getSalePrice()));
  }

  /**
   * Wrapper method for sell which generates a confirmation message
   * before initiating the exchange.
   *
   * @param player the player selling the item.
   * @param merchant the merchant buying the item.
   * @param itemName the name of the item being sold.
   */
  public static void sell(Player player, Merchant merchant, String itemName) {
    Optional<Item> itemToSell = player.findInventoryItem(itemName);
    if (itemToSell.isPresent())
      sell(player, merchant, itemName, String.format("Want to sell a %s for %d gold? ",
              itemName, merchant.getAdjustedBuyingPrice(itemToSell.get())));
  }

  /**
   * Attempts to complete a buying transaction which will
   * complete if each participant has the appropriate resources
   * and will then allocate items and gold as required.
   *
   * @param player the player acquiring the item.
   * @param merchant the merchant selling the item.
   * @param itemName the name of the item being bought.
   */
  public static void buy(Player player, Merchant merchant, String itemName, String confirmationMessage) {
    Optional<SaleItem> itemToBuy = merchant.findSaleItem(itemName);
    if (itemToBuy.isPresent()) {
      String d = IO.getDecision(confirmationMessage);
      if (d.startsWith("y")) {
        if (player.getGold() < itemToBuy.get().getSalePrice()) {
          IO.println("Sorry, you don't have enough gold.");
        } else if (itemToBuy.get() != null) {
          player.obtain(itemToBuy.get().getItemForSale());
          merchant.lose(itemToBuy.get());
          player.subGold(itemToBuy.get().getSalePrice());
          merchant.addGold(itemToBuy.get().getSalePrice());
        }
      }
    }
  }

  /**
   * Attempts to complete a selling transaction which will
   * complete if each participant has the appropriate resources
   * and will then allocate items and gold as required.
   *
   * @param player the player selling the item.
   * @param merchant the merchant buying the item.
   * @param itemName the name of the item being sold.
   */
  public static void sell(Player player, Merchant merchant, String itemName, String confirmationMessage) {
    Optional<Item> itemToSell = player.findInventoryItem(itemName);
    if (itemToSell.isPresent()) {
      int adjustedPrice = merchant.getAdjustedBuyingPrice(itemToSell.get());
      String d = IO.getDecision(confirmationMessage);
      if (d.startsWith("y")) {
        if (itemToSell.get() != null && merchant.getGold() >= itemToSell.get().getValue()) {
          player.lose(itemToSell.get());
          merchant.obtain(itemToSell.get(), 1);
          player.addGold(adjustedPrice);
          merchant.subGold(adjustedPrice);
        }
      }
    }
  }

  /**
   * Returns a bannered box displaying how much money is
   * available to each participant in the current transaction.
   *
   * @param player the player in the transaction.
   * @param merchant the merchant in the transaction.
   * @return a string displaying the finances of each participant.
   */
  private static String affairsToString(Player player, Merchant merchant) {
    return "\b" + IO.formatBanner(IO.BOX_WIDTH) +
            IO.formatColumns(IO.BOX_WIDTH, true, true, "You : " + player.getGold(),
                    merchant.getGold() + " : " + merchant.getName()) +
            IO.formatBanner(IO.BOX_WIDTH);
  }
}
