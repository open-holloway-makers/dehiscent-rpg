package map;

import java.util.Objects;

public class WorldPoint {
  private int x, y;

  public WorldPoint(int x, int y) {
    x(x); y(y);
  }

  public int x() { return x; }
  public int y() { return y; }

  public void x(int x) { this.x = x; }
  public void y(int y) { this.y = y; }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  @Override
  public boolean equals(Object object) {
    return this.x() == ((WorldPoint)object).x() && this.y() == ((WorldPoint)object).y();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}
