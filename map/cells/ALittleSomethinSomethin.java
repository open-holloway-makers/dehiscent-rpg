package map.cells;

import core.IO;
import core.Player;
import core.Stat;
import items.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ALittleSomethinSomethin implements Cell {

  @Override
  public char getMapIcon() {
    return '.';
  }

  @Override
  public void explore(Player player) {
    IO.printAsBox(
            "You look behind a bush to see the corner of an old iron box poking " +
                    "out of the dirt."
    );

    if (IO.getAffirmative("Try and dig it up?")) {

      IO.printAsBox(
              "You dig it up "
      );

    } else {
      return;
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
