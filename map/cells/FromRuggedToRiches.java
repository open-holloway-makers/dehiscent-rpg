package map.cells;

import core.IO;
import core.Player;
import core.Stat;
import items.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FromRuggedToRiches implements Cell {

  private int encounterValue = 0;

  @Override
  public char getMapIcon() {
    return '.';
  }

  @Override
  public void explore(Player player) {
    if (encounterValue == -1) {
      IO.println("\nIt's pretty empty around here now that the vagrant has gone.");
    } else {
      IO.println("\nThere's nobody else around except the vagrant.");
    }
  }

  @Override
  public void event(Player player) {
    if (encounterValue == 0) {

      IO.print(IO.formatAsBox(
              "A sorry looking vagrant approaches you. His rags are filthy and his " +
                      "feet are scabbed and bloody."
              , IO.PARA_WIDTH, false));

      IO.print(IO.formatAsBox(
              "\"You couldn't spare some shoes so that I can protect my tired feet " +
                      "from the cold, rough earth, could you?\""
              , IO.PARA_WIDTH, false));

      Item[] possibleShoes = player.getAllKnownItems()
              .parallelStream()
              .filter(i -> i.getSlotType() == SlotType.FEET)
              .sorted()
              .toArray(Item[]::new);

      IntStream.range(0, possibleShoes.length)
              .forEachOrdered(i -> IO.println(i + ": " + possibleShoes[i].getName()));

      double d = IO.getNumberWithinRange(
              "\nWould you like to choose some boots to gift to the vagrant?\n",
              0, possibleShoes.length - 1, true);
      if (d > Double.NEGATIVE_INFINITY) {
        player.lose(possibleShoes[(int) d]);

        IO.print(IO.formatAsBox(
                "\"Thank you so much, you're very kind! With these I can begin making " +
                        "my way to a new home, and start a new life! Please, take my " +
                        "days earnings in return!\""
                , IO.PARA_WIDTH, false));

        player.addGold(2);
        encounterValue++;
      } else {

        IO.print(IO.formatAsBox(
                "You apologise to the poor man and continue on your way."
                , IO.PARA_WIDTH, false));

      }
    } else if (encounterValue >= 1) {
      List<Item> possibleGifts = player.getAllKnownItems()
              .parallelStream()
              .filter(i -> i.getName().toLowerCase().contains("rugged"))
              .sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName()))
              .collect(Collectors.toList());

      if (possibleGifts.size() > 0) {

        if (encounterValue == 1) {

          IO.print(IO.formatAsBox(
                  "By the path is a vagrant. He's looking comfortable in his new shoes. " +
                          "\"These shows are a perfect fit! But I'm worried they won't be " +
                          "enough for me to travel too far, these rags hardly keep me warm!\""
                  , IO.PARA_WIDTH, false));

        } else {

          IO.print(IO.formatAsBox(
                  "You see the vagrant again, wearing some of your clothes. \"Thank you so " +
                          "much! I'm almost ready to leave, but the nights are still hard.\""
                  , IO.PARA_WIDTH, false));

        }

        IO.print(IO.formatAsBox(
                "\"I have nothing left to offer you in return, but please, you wouldn't happen " +
                        "to have something more rugged that I could wear, do you?\""
                , IO.PARA_WIDTH, false));

        IntStream.range(0, possibleGifts.size())
                .forEachOrdered(i -> IO.println(i + ": " + possibleGifts.get(i).getName()));

        Double d = IO.getNumberWithinRange(
                "\nWould you like to choose a gift to give to the vagrant?\n",
                0, possibleGifts.size() - 1, true);

        if (d > Double.NEGATIVE_INFINITY) {
          player.lose(possibleGifts.get(d.intValue()));
          possibleGifts.remove(d.intValue());

          IO.println();
          IO.print(IO.formatAsBox(
                  "\"Thank you so much, these are just what I need to survive my long journey to a new life.\""
                  , IO.PARA_WIDTH, false));

          encounterValue++;
        }
      }
      if (possibleGifts.size() == 0) {

        IO.print(IO.formatAsBox(
                "\"This is perfect! I think I'm ready to make a new life elsewhere now! " +
                        "Thank you for all your help! Please, another traveller gave me " +
                        "this but it's of no use to a simple man like me. You'll make " +
                        "better use of it I think!\""
                , IO.PARA_WIDTH, false));

        Item batteredIronHelm = new Item("Battered Iron Helm", 20, SlotType.HEAD, new Modifier(Stat.PHYS_DEF, 15));
        // You can't have lore text without the phrase "It's definitely seen better days.."
        batteredIronHelm.setLoreText(
                "It's definitely seen better days, but it must have served someone well. The helmet " +
                        "bears no obvious markings which might identify its maker; perhaps they've been " +
                        "worn away or maybe the creator wasn't proud enough to put their name to it."
        );
        player.obtain(batteredIronHelm);
        encounterValue = -1;
      }
    }
  }

  @Override
  public boolean goNorth() {
    return true;
  }

  @Override
  public boolean goSouth() {
    return true;
  }

  @Override
  public boolean goEast() {
    return true;
  }

  @Override
  public boolean goWest() {
    return true;
  }
}
