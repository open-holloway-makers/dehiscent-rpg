package map;

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
}
