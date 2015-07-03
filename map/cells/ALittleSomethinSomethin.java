package map.cells;

import core.IO;
import core.Player;
import items.Rating;
import items.SlotType;
import items.Weapon;

public class ALittleSomethinSomethin implements Cell {

  private int encounterValue = 0;

  @Override
  public char getMapIcon() {
    return '.';
  }

  @Override
  public void explore(Player player) {

    if (encounterValue == 0) {
      IO.printAsPara(
              "You look behind a bush to see the corner of an old iron box poking " +
                      "out of the dirt."
      );

      if (IO.getAffirmative("Try and dig it up? ")) {
        IO.printAsPara(
                "You dig it up and wipe some of the mud from the front. " +
                        "The box is plain but very heavy, perhaps something valuable " +
                        " is inside. It's locked but the latch is old and rusty."
        );
        encounterValue = 1;
      }
    }
    if (encounterValue == 1) {
      if (IO.getAffirmative("Try and pry open the box? ")) {
        IO.printAsPara(
                "The latch snaps open suddenly and a sharp edge cuts your wrist deeply. " +
                        "As you drop it to the floor you see a note laying on top of the box. " +
                        "\"Here lies Bowboha, a loyal friend and companion. R.I.P.\""
        );
        player.subHp(10);
        encounterValue = 2;
      }
    }

    if (encounterValue == 2) {
      IO.printAsPara(
              "Still lodged in the box is a large rock wrapped neatly in a tattered " +
                      "muslin cloth."
      );

      if (IO.getAffirmative("Take the rock with you? ")) {
        Weapon largeRock = new Weapon("Large Rock", 3, SlotType.HAND, null, 12, 0, Rating.C, Rating.U, Rating.U);
        largeRock.setLoreText("An abnormally large rock, with quite some weight behind it. " +
                "A weak man might struggle to carry it in one hand, but a strong one could certainly " +
                "use it to crush a foe's skull.");
        player.obtain(largeRock);
        encounterValue++;
      }
    } else {
      IO.printAsPara(
              "You step over the hole where the rock was once buried."
      );
    }
  }

  @Override
  public void event(Player player) {
    IO.println("There's some shrubbery and a lone tree, nothing else around here.");
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
