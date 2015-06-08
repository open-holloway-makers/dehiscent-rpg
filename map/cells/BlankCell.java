package map.cells;

public class BlankCell extends AbstractCell {
  
  public char mapIcon = ' ';

  public BlankCell() { super(); };

  public boolean goNorth() {
    System.out.println("You continue wandering.");
    return true;
  }

  public boolean goEast() {
    System.out.println("You continue wandering.");
    return true;
  }

  public boolean goSouth() {
    System.out.println("You continue wandering.");
    return true;
  }

  public boolean goWest() {
    System.out.println("You continue wandering.");
    return true;
  }

  public void event() {
    System.out.println("There's nothing here...");
  }
}
