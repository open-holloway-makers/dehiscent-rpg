package map;

import core.Player;
import map.cells.Cell;
import static java.lang.Math.abs;

public class Map {

  public static final int CURRENT_MAX_SIZE = 16;

  private Quadrant[] quadrants = new Quadrant[4];

  public Map() {
    quadrants[0] = new Quadrant(CURRENT_MAX_SIZE);
    quadrants[1] = new Quadrant(CURRENT_MAX_SIZE);
    quadrants[2] = new Quadrant(CURRENT_MAX_SIZE);
    quadrants[3] = new Quadrant(CURRENT_MAX_SIZE);
  }

  public Cell fetchCell(WorldPoint p) {
    LogicalPoint l = worldToLogical(p);
    return quadrants[l.q()].getCell(l.x(), l.y());
  }

  public void setCell(int x, int y, Cell cell) {
    LogicalPoint l = worldToLogical(new WorldPoint(x, y));
    quadrants[l.q()].setCell(l.x(), l.y(), cell);
  }

  // This is the most inefficient thing every conceived by humans
  public void printKnownMap(Player player) {
    int x, y;
    LogicalPoint p = worldToLogical(player.getPosition());
    for (y = CURRENT_MAX_SIZE - 1; y >= 0; y--) {
      for (x = CURRENT_MAX_SIZE - 1; x >= 0; x--) {
        printKnownCell(2, x, y, player);
      }
      for (x = 0; x < CURRENT_MAX_SIZE; x++) {
        printKnownCell(3, x, y, player);
      }
      System.out.println();
    }
    for (y = 0; y < CURRENT_MAX_SIZE; y++) {
      for (x = CURRENT_MAX_SIZE - 1; x >= 0; x--) {
        printKnownCell(1, x, y, player);
      }
      for (x = 0; x < CURRENT_MAX_SIZE; x++) {
        printKnownCell(0, x, y, player);
      }
      System.out.println();
    }
  }

  // It's so inefficient, I've written it twice! :D
  public void printKnownMapAlongsideStats(Player player) {
    int x, y;
    int i = 0;
    LogicalPoint p = worldToLogical(player.getPosition());
    String[] playerStats = (player.vitalsToString() + player.statsToString() + player.equippedToString()).split("\\n");
    for (y = CURRENT_MAX_SIZE - 1; y >= 0; y--) {
      for (x = CURRENT_MAX_SIZE - 1; x >= 0; x--) {
        printKnownCell(2, x, y, player);
      }
      for (x = 0; x < CURRENT_MAX_SIZE; x++) {
        printKnownCell(3, x, y, player);
      }
      if (i < playerStats.length) {
        System.out.print(" " + playerStats[i]);
        i++;
      }
      System.out.println();
    }
    for (y = 0; y < CURRENT_MAX_SIZE; y++) {
      for (x = CURRENT_MAX_SIZE - 1; x >= 0; x--) {
        printKnownCell(1, x, y, player);
      }
      for (x = 0; x < CURRENT_MAX_SIZE; x++) {
        printKnownCell(0, x, y, player);
      }
      if (i < playerStats.length) {
        System.out.print(" " + playerStats[i]);
        i++;
      }
      System.out.println();
    }
  }

  private void printKnownCell(int q, int x, int y, Player player) {
    LogicalPoint p = worldToLogical(player.getPosition());
    if (p.q() == q && p.x() == x && p.y() == y) {
      System.out.print('x');
    } else if (player.getVisited().contains(logicalToWorld(q, x, y))) {
      quadrants[q].printCell(x, y);
    } else {
      System.out.print(' ');
    }
  }

  // 23
  // 10
  public void printMap() {
    int x, y;
    for (y = CURRENT_MAX_SIZE - 1; y >= 0; y--) {
      for (x = CURRENT_MAX_SIZE - 1; x >= 0; x--) {
        quadrants[2].printCell(x, y);
      }
      for (x = 0; x < CURRENT_MAX_SIZE; x++) {
        quadrants[3].printCell(x, y);
      }
      System.out.println();
    }
    for (y = 0; y < CURRENT_MAX_SIZE; y++) {
      for (x = CURRENT_MAX_SIZE - 1; x >= 0; x--) {
        quadrants[1].printCell(x, y);
      }
      for (x = 0; x < CURRENT_MAX_SIZE; x++) {
        quadrants[0].printCell(x, y);
      }
      System.out.println();
    }
  }

  public static LogicalPoint worldToLogical(WorldPoint point) {
    return worldToLogical(point.x(), point.y());
  }
  
  public static LogicalPoint worldToLogical(int x, int y) {
    LogicalPoint l;
    if (x >= 0 && y >= 0) { 
      l = new LogicalPoint(0, x, y);
    } else if (x < 0 && y >= 0) {
      l = new LogicalPoint(1, abs(x) - 1, y);
    } else if (x < 0 && y < 0) {
      l = new LogicalPoint(2, abs(x) - 1, abs(y) - 1);
    } else if (x >= 0 && y < 0) { 
      l = new LogicalPoint(3, x, abs(y) - 1);
    } else { 
      System.out.println("Sorry, I've made a terrible maths error!"); 
      l = new LogicalPoint(0, 0, 0);
    }
    return l;
  }

  public static WorldPoint logicalToWorld(LogicalPoint point) {
    return logicalToWorld(point.q(), point.x(), point.y());
  }

  public static WorldPoint logicalToWorld(int q, int x, int y) {
    WorldPoint w;
    if (q == 0) {
      w = new WorldPoint(x, y);
    } else if (q == 1) {
      w = new WorldPoint((-x) - 1, y);
    } else if (q == 2) {
      w = new WorldPoint((-x) - 1, (-y) - 1);
    } else if (q == 3) {
      w = new WorldPoint(x, (-y) - 1);
    } else {
      System.out.println("Error, not a valid logical point!");
      w = new WorldPoint(0, 0);
    }
    return w;
  }

}
