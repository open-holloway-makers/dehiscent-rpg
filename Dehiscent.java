import map.*;
import map.cells.*;
import core.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class Dehiscent {

  public static void main(String[] args) {
    Map overworld = createMap();   
    Player p = new Player();
    Scanner in = new Scanner(System.in);

    for (;;) {

      ArrayList<WorldPoint> testPoints = new ArrayList<WorldPoint>();
      WorldPoint test1 = new WorldPoint(10, 10);
      WorldPoint test2 = new WorldPoint(5, 10);
      testPoints.add(test1);

      System.out.println("Expected false: " + testPoints.contains(test2));
      System.out.println("Expected false: " + test1.equals(test2));

      overworld.printKnownMap(p);
      Cell currentCell = overworld.fetchCell(p.getPosition());
      p = currentCell.event(p);

      Cell previousCell = currentCell;
      while(currentCell == previousCell) {
        System.out.println("What will you do?");
        String decision = in.nextLine().toLowerCase();
        switch (decision) {
          case "w":
          case "go north": p.goNorth(); break;
          case "a":
          case "go west": p.goWest(); break;
          case "s":
          case "go south": p.goSouth(); break;
          case "d":
          case "go east": p.goEast(); break;
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
          if (decision.contains("vitals")) {
            System.out.println(p.vitalsToString());
          }
        } else if (decision.startsWith("explore") || decision.equals("e")) {
          p = currentCell.explore(p);
        } else if (decision.startsWith("quit")) {
          System.out.println("Saving isn't implemented yet! Are you sure you want to quit? ('yes' to quit)");
          if (in.nextLine().toLowerCase().startsWith("yes")) {
            System.exit(0);
          }
        }
        currentCell = overworld.fetchCell(p.getPosition());
      }
    }
  }

  public static Map createMap() {
    Map overworld = new Map();
    overworld.setCell(0, 0, new HomeCell());
    overworld.setCell(-1, 0, new TwoTest());
    overworld.setCell(-1, -1, new ThreeTest());
    overworld.setCell(0, -1, new FourTest());
    return overworld;
  }
}
