package map.cells;

public abstract class AbstractCell {

  public char mapIcon = '?';

  public AbstractCell() {}

  public boolean goNorth() {
    return true;
  }

  public boolean goEast() {
    return true;
  }

  public boolean goWest() {
    // Life is peaceful there
    // In the open air
    // Where the skies are blue
    // This is what we're gonna do
    return true;
  }

  public boolean goSouth() {
    return true;
  }

  public abstract void event();
}
