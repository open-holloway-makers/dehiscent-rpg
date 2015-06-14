package map;

import java.util.Objects;

public class LogicalPoint {

  private int q, x, y;

  public LogicalPoint(int q, int x, int y) {
    q(q); x(x); y(y);
  }

  public int q() { return q; }
  public int x() { return x; }
  public int y() { return y; }

  public void q(int q) { this.q = q; }
  public void x(int x) { this.x = x; }
  public void y(int y) { this.y = y; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LogicalPoint that = (LogicalPoint) o;

    if (q != that.q) return false;
    if (x != that.x) return false;
    if (y != that.y) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = q;
    result = 31 * result + x;
    result = 31 * result + y;
    return result;
  }
}
