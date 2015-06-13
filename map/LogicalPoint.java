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
  public boolean equals(Object object) {
    return this.q() == ((LogicalPoint)object).q() &&
           this.x() == ((LogicalPoint)object).x() &&
           this.y() == ((LogicalPoint)object).y();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.q, this.x, this.y);
  }
}
