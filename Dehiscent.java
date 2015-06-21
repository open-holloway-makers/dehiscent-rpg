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
    p.addGold(50); // JUST FOR TESTING!!

    // Resume console output
    System.setOut(new PrintStream(realSystemOut));

    for (; ; ) {
      overworld.printKnownMap(p);

      Cell currentCell = overworld.fetchCell(p.getPosition());
      currentCell.event(p);

      Cell previousCell = currentCell;
      while (currentCell == previousCell) {
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
          case "view":
            overworld.printKnownMapAlongsideStats(p);
            break;
        }
        if (decision.startsWith("view") || decision.startsWith("v ")) {
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
          if (decision.contains(" current")) {
            IO.println(p.verboseEquippedToString());
          }
          if (decision.contains(" equip")) {
            IO.println(p.equippedToString());
          }
          if (decision.contains(" inv")) {
            IO.println(p.inventoryToString());
          }
          if (decision.contains(" controls")) {
            IO.println(controlsToString());
          }
        } else if (decision.startsWith("use") || decision.startsWith("u ")) {
          String itemName = decision.substring(decision.indexOf(" ")).trim();
          p.attemptToUse(itemName);
        } else if (decision.startsWith("inspect") || decision.startsWith("i ")) {
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
    overworld.setCell(1, -1, new FromRuggedToRiches());
    overworld.setCell(1, 1, new LittleGrocerShop());

    return overworld;
  }

  public static String controlsToString() {
    return IO.formatBanner(IO.BOX_WIDTH) +
            IO.formatColumns(IO.BOX_WIDTH, "COMMAND", "HOTKEY", "EFFECT") +
            IO.formatBanner(IO.BOX_WIDTH) +
            IO.formatColumns(IO.BOX_WIDTH, "go north", "w", "Go north") +
            IO.formatColumns(IO.BOX_WIDTH, "go south", "a", "Go south") +
            IO.formatColumns(IO.BOX_WIDTH, "go west", "s", "Go west") +
            IO.formatColumns(IO.BOX_WIDTH, "go east", "d", "Go east") +
            IO.formatColumns(IO.BOX_WIDTH, "explore", "x", "Explore the area") +
            IO.formatBanner(IO.BOX_WIDTH) +
            IO.formatColumns(IO.BOX_WIDTH, "use <item>", "u", "Use an item") +
            IO.formatColumns(IO.BOX_WIDTH, "equip <item>", "e", "Equip an item") +
            IO.formatColumns(IO.BOX_WIDTH, "unequip <item>", "ue", "Unequip an item") +
            IO.formatColumns(IO.BOX_WIDTH, "inspect <item>", "i", "Unequip an item") +
            IO.formatBanner(IO.BOX_WIDTH) +
            IO.formatColumns(IO.BOX_WIDTH, "view <options>", "v", "View summary of all") +
            IO.formatColumns(IO.BOX_WIDTH, "  map", "", "Check the map") +
            IO.formatColumns(IO.BOX_WIDTH, "  inv", "", "View inventory") +
            IO.formatColumns(IO.BOX_WIDTH, "  equip", "", "See all equipped") +
            IO.formatColumns(IO.BOX_WIDTH, "  current", "", "Inspect all equipped") +
            IO.formatColumns(IO.BOX_WIDTH, "  position", "", "Check position") +
            IO.formatColumns(IO.BOX_WIDTH, "  stats", "", "View stats") +
            IO.formatColumns(IO.BOX_WIDTH, "  base", "", "View base stats") +
            IO.formatColumns(IO.BOX_WIDTH, "  controls", "", "See this menu") +
            IO.formatBanner(IO.BOX_WIDTH) +
            IO.formatColumns(IO.BOX_WIDTH, "[0-9]", "", "Select menu options") +
            IO.formatBanner(IO.BOX_WIDTH);
  }
}
