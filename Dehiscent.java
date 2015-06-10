import map.*;
import map.cells.*;
import core.Player;
import java.util.Scanner;

public class Dehiscent {

  public static void main(String[] args) {
    Map overworld = createMap();   
    Player p = new Player();
    Scanner in = new Scanner(System.in);

    for (;;) {
      Cell currentCell = overworld.fetchCell(p.getPosition());
      p = currentCell.event(p);

      Cell previousCell = currentCell;
      while(currentCell == previousCell) {
        System.out.println("What will you do?");
        String decision = in.nextLine().toLowerCase();
        if (decision.startsWith("go ")) {
          if (decision.contains("north")) {
            if (currentCell.goNorth()) {
              p.goNorth();
            }
          } else if (decision.contains("east")) {
            if (currentCell.goEast()) {
              p.goEast();
            }
          } else if (decision.contains("south")) {
            if (currentCell.goSouth()) {
              p.goSouth();
            }
          } else if (decision.contains("west")) {
            if (currentCell.goWest()) {
              p.goWest();
            }
          }
        } else if (decision.startsWith("view")) {
          if (decision.contains("map")) {
            overworld.printMap();
          }
          if (decision.contains("position")) {
            System.out.println("You're at " + p.getPosition().toString());
          }
        } else if (decision.startsWith("explore")) { 
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

  public static createMap() {
    Map overworld = new Map();

  }
}
