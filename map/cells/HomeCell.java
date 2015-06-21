package map.cells;

import core.IO;
import core.Player;

public class HomeCell implements Cell {

  public char getMapIcon() { return 'O'; }

  public boolean isVisited = false;
  public boolean isExplored = false;

  @Override
  public boolean goNorth() {
    IO.println("You look over at the blank wall and think, what is my life?");
    return false;
  }

  @Override
  public boolean goEast() {
    return goNorth();
  }

  @Override
  public boolean goWest() {
    return goNorth();
  }

  @Override
  public boolean goSouth() {
    IO.println("You open the cabin door and go out into the big wide world.");
    return true;
  }

  @Override 
  public void explore(Player player) {
    if (!isExplored) {
      IO.println("There's some warm soup on a table. It has an aroma that you've never smelt before...");
      String d = IO.getDecision("Have some of the soup? ");
      if (d.startsWith("y")) {
        IO.println("The soup tastes great and you feel your strength return.");
        player.fullHeal();
      } else if (d.startsWith("n")) {
        IO.println("Better leave it alone, no telling who made it or what's in it...");
      }
      isExplored = true;
    } else {
      IO.print("There's nothing left to see here, just a cold, empty room.");
    }
  }

  @Override 
  public void event(Player player) {
    if (!isVisited) {
      player.setHp((int) (player.getHp() * 0.6));
      IO.println("You feel weary, waking on a straw bed.");
      isVisited = true;
    } else {
      IO.println("What use is there returning here to be reminded of your woes..?");
    }
  }
}
