package map;

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

  public LogicalPoint worldToLogical(WorldPoint point) {
    return worldToLogical(point.x(), point.y());
  }
  
  public LogicalPoint worldToLogical(int x, int y) {
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

  public WorldPoint logicalToWorld(LogicalPoint point) {
    return logicalToWorld(point.q(), point.x(), point.y());
  }

  public WorldPoint logicalToWorld(int q, int x, int y) { 
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
