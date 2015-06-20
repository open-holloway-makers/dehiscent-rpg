package map.cells;

import core.IO;
import core.Player;
import core.Stat;
import items.*;

import java.util.stream.IntStream;

public class FromRuggedToRiches implements Cell {

  private int encounterCount = 0;

  @Override
  public char getMapIcon() {
    return '.';
  }

  @Override
  public void explore(Player p) {
    IO.println("There's nobody else around except the beggar.");
  }

  @Override
  public void event(Player p) {

    {
      if (encounterCount == 0) {
        IO.println("A vagrant draped only in rags approaches you.");
        IO.println("\"Spare me some shoes would you?\"\n");
        Item[] possibleGifts = p.getAllKnownItems()
                .parallelStream()
                .filter(i -> i.getSlotType() == SlotType.FEET)
                .sorted()
                .toArray(Item[]::new);

        IntStream.range(0, possibleGifts.length)
                .forEachOrdered(i -> IO.println(i + ": " + possibleGifts[i].getName()));

        double d = IO.getNumberWithinRange(
                "\nWould you like to choose some shoes to gift to the vagrant?\n",
                0, possibleGifts.length - 1, true);
        if (d > Double.NEGATIVE_INFINITY) {
          p.lose(possibleGifts[(int) d]);
          IO.println("\"Thank you so much, with these I can travel to better lands!\n" +
                  "Take my days earnings for your trouble\"\n");
          p.addGold(2);
          encounterCount++;
        }
      }
      {
        if (encounterCount >= 1) {

          Item[] possibleGifts = p.getAllKnownItems()
                  .parallelStream()
                  .filter(i -> i.getName().toLowerCase().contains("rugged"))
                  .sorted()
                  .toArray(Item[]::new);

          if (possibleGifts.length > 0) {

            if (encounterCount == 1) {
              IO.println("By the path is a vagrant wearing some nice new shoes.");
              IO.println("\"These shoes are perfect! But I'm worried I won't be able to leave" +
                      "for a better place with only these rags.\"\n");
            } else {
              IO.println("You see the vagrant again, wearing some of your clothes");
              IO.println("\"Thank you so much for these clothes! But I'm worried it's not" +
                      "enough for me to begin my journey.\n");
            }
            IO.println("I have nothing left to offer you in return, but please, " +
                    "you wouldn't happen to have something more rugged that I could wear, do you?\n");

            IntStream.range(0, possibleGifts.length)
                    .forEachOrdered(i -> IO.println(i + ": " + possibleGifts[i].getName()));

            double d = IO.getNumberWithinRange(
                    "\nWould you like to choose a gift to give to the vagrant?\n",
                    0, possibleGifts.length - 1, true);
            if (d > Double.NEGATIVE_INFINITY) {
              p.lose(possibleGifts[(int) d]);
              IO.println("\"Thank you so much, with these I can travel to better lands!\n" +
                      "Take my days earnings for your trouble\"\n");
              encounterCount++;
            }
          }
          if (possibleGifts.length == 0) {
            IO.println("\"I think I'm ready to make a new life elsewhere now! Thank you for all" +
                    "your help! Please, another traveller gave me this but it's of no use to a simple" +
                    "man like me. You'll make better use of it I think!\"");

            Item batteredHelm = new Item("Battered iron helm", 20, SlotType.HEAD, new Modifier(Stat.PHYS_DEF, 15));
            p.obtain(batteredHelm);
          }
        }
      }
    }

    Consumable kedgeree = new Consumable("kedgeree", 5, 2,
            (player) -> player.subHp(10));

    Consumable onions = new Consumable("onions", 10, 2, "You feel revitalised!\n",
            (player) -> player.addVit(1)
    );

    Consumable potion = new Consumable("potion", 200, 1, "You're almost fully recovered!\n", new Useable() {
      @Override
      public void use(Player player) {
        player.addDex(1);
        player.fullHeal();
      }
    });

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
