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
    return String.format("(%d, %d)", x, y);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    WorldPoint that = (WorldPoint) o;

    if (x != that.x) return false;
    if (y != that.y) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = x;
    result = 31 * result + y;
    return result;
  }
}
