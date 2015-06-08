package map;

import map.cells.AbstractCell;
import map.cells.BlankCell;

public class Quadrant {

  private int size;
  private AbstractCell[][] cells;

  public Quadrant(int size) {
    this.size = size;
    cells = new BlankCell[16][16];
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        cells[x][y] = new BlankCell();
      }
    }
  }

  public void printCell(int x, int y) {
    System.out.print(cells[x][y].mapIcon);
  }
}
