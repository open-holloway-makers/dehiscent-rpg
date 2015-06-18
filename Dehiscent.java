import classes.Wanderer;
import core.IO;
import core.Player;
import map.Map;
import map.cells.*;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Dehiscent {

  public static void main(String[] args) {
    // Suppress console output during setup
    OutputStream realSystemOut = System.out;
    System.setOut(IO.getNullPrintStream());
    Map overworld = createMap();   
    Player p = new Wanderer();

    // Resume console output
    System.setOut(new PrintStream(realSystemOut));

    Scanner in = new Scanner(System.in);

    for (;;) {
      overworld.printKnownMap(p);

      Cell currentCell = overworld.fetchCell(p.getPosition());
      currentCell.event(p);

      Cell previousCell = currentCell;
      while(currentCell == previousCell) {
        System.out.println("\nWhat will you do?");
        String decision = in.nextLine().toLowerCase();
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
        if (decision.startsWith("view")) {
          if (decision.contains("map")) {
            overworld.printKnownMap(p);
          }
          if (decision.contains("position")) {
            System.out.println("You're at " + p.getPosition().toString());
          }
          if (decision.contains("stats")) {
            System.out.println(p.statsToString());
          }
          if (decision.contains("base")) {
            System.out.println(p.baseStatsToString());
          }
          if (decision.contains("vitals")) {
            System.out.println(p.vitalsToString());
          }
          if (decision.contains("equip")) {
            System.out.println(p.equippedToString());
          }
          if (decision.contains("inventory")) {
            System.out.println("===============\n");
            System.out.println(p.inventoryToString());
            System.out.println("===============");
          }
        } else if (decision.startsWith("use") || decision.startsWith("u ")) {
          String itemName = decision.substring(decision.indexOf(" ")).trim();
          p.attemptToUse(itemName);
        }else if (decision.startsWith("inspect") || decision.startsWith("i ")) {
          String itemName = decision.substring(decision.indexOf(" ")).trim();
          p.attemptToInspect(itemName);
        } else if (decision.startsWith("explore") || decision.equals("e")) {
          currentCell.explore(p);
        } else {
          if (decision.startsWith("equip") || decision.startsWith("e ")) {
            String itemName = decision.substring(decision.indexOf(" ")).trim();
            p.attemptToEquip(itemName);
          } else if (decision.startsWith("unequip") || decision.startsWith("ue ")) {
            String itemName = decision.substring(decision.indexOf(" ")).trim();
            p.attemptToUnequip(itemName);
          } else if (decision.startsWith("quit")) {
            System.out.println("Saving isn't implemented yet! Are you sure you want to quit? ('yes' to quit)");
            if (in.nextLine().toLowerCase().startsWith("yes")) {
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
    return overworld;
  }
}
