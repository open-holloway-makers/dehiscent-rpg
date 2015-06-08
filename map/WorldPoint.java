package map;

public class WorldPoint {
  private int x, y;

  public WorldPoint(int x, int y) {
    x(x); y(y);
  }

  public int x() { return x; }
  public int y() { return y; }

  public void x(int x) { this.x = x; }
  public void y(int y) { this.y = y; }

  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
