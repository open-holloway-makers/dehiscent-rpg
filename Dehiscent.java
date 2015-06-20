import classes.Wanderer;
import core.IO;
import core.Player;
import map.Map;
import map.cells.*;

import java.io.OutputStream;
import java.io.PrintStream;

public class Dehiscent {

  public static void main(String[] args) {

    // Suppress console output during setup
    OutputStream realSystemOut = System.out;

    System.setOut(IO.getNullPrintStream());
    Map overworld = createMap();   
    Player p = new Wanderer();

    // Resume console output
    System.setOut(new PrintStream(realSystemOut));

    for (;;) {
      overworld.printKnownMap(p);

      Cell currentCell = overworld.fetchCell(p.getPosition());
      currentCell.event(p);

      Cell previousCell = currentCell;
      while(currentCell == previousCell) {
        // TODO decision making code should be extracted and made reusable
        String decision = IO.getDecision("\nWhat will you do?\n");
        switch (decision) {
          case "w":
          case "go north":
            if (currentCell.goNorth())
              p.goNorth();
            break;
          case "a":
          case "go west":
            if (currentCell.goWest())
              p.goWest();
            break;
          case "s":
          case "go south":
            if (currentCell.goSouth())
              p.goSouth();
            break;
          case "d":
          case "go east":
            if (currentCell.goEast())
              p.goEast();
            break;
          case "v":
            overworld.printKnownMapAlongsideStats(p);
            break;
        }
        if (decision.startsWith("view" )|| decision.startsWith("v ")) {
          if (decision.contains(" map")) {
            overworld.printKnownMap(p);
          }
          if (decision.contains(" position")) {
            IO.println("You're at " + p.getPosition().toString());
          }
          if (decision.contains(" stats")) {
            IO.println(p.statsToString());
          }
          if (decision.contains(" base")) {
            IO.println(p.baseStatsToString());
          }
          if (decision.contains(" vitals")) {
            IO.println(p.vitalsToString());
          }
          if (decision.contains(" equip")) {
            IO.println(p.equippedToString());
          }
          if (decision.contains(" inv")) {
            IO.println("===============\n");
            IO.println(p.inventoryToString());
            IO.println("===============");
          }
        } else if (decision.startsWith("use") || decision.startsWith("u ")) {
          String itemName = decision.substring(decision.indexOf(" ")).trim();
          p.attemptToUse(itemName);
        }else if (decision.startsWith("inspect") || decision.startsWith("i ")) {
          String itemName = decision.substring(decision.indexOf(" ")).trim();
          p.attemptToInspect(itemName);
        } else if (decision.startsWith("explore") || decision.equals("x")) {
          currentCell.explore(p);
        } else {
          if (decision.startsWith("equip") || decision.startsWith("e ")) {
            String itemName = decision.substring(decision.indexOf(" ")).trim();
            p.attemptToEquip(itemName);
          } else if (decision.startsWith("unequip") || decision.startsWith("ue ")) {
            String itemName = decision.substring(decision.indexOf(" ")).trim();
            p.attemptToUnequip(itemName);
          } else if (decision.startsWith("quit")) {
            if (IO.getAffirmative("Saving isn't implemented yet! Are you sure you want to quit? ('yes' to quit)\n")) {
              System.exit(0);
            }
          }
        }
        currentCell = overworld.fetchCell(p.getPosition());
      }
    }
  }

  public static Map createMap() {
    Map overworld = new Map();
    overworld.setCell(0, 0, new HomeCell());
    overworld.setCell(-1, 0, new HelmCell());
    overworld.setCell(-1, -1, new ThreeTest());
    overworld.setCell(0, -1, new FourTest());
    overworld.setCell(1, 1, new Beggar());
    overworld.setCell(1, 1, new OneTest());
    overworld.setCell(1, 1, new TwoTest());
    return overworld;
  }
}
