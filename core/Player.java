package core;

import map.WorldPoint;
import java.util.List;
import java.util.ArrayList;

public class Player {

  private int vitality, strength, dexterity, intelligence;
  private int gold, xp;
  private int physicalDefence;

  private WorldPoint position;
  private List<WorldPoint> visitedPoints;

  public Player() {
      visitedPoints = new ArrayList<WorldPoint>();
      position = new WorldPoint(0, 0);
      visitedPoints.add(new WorldPoint(0, 0));
  }
  
  public void setVit(int x) { vitality = x; }
  public void setStr(int x) { strength = x; }
  public void setDex(int x) { dexterity = x; }
  public void setInt(int x) { intelligence = x; }

  public void setPosition(int x, int y) { 
    position = new WorldPoint(x, y);
    visitedPoints.add(position);
  }

  public int getVit() { return vitality; }
  public int getStr() { return strength; }
  public int getDex() { return dexterity; }
  public int getInt() { return intelligence; }

  public WorldPoint getPosition() { return position; }

  public List<WorldPoint> getVisited() { return visitedPoints; }


  public void goNorth() {
    setPosition(position.x(), position.y() - 1);
  }

  public void goEast() {
    setPosition(position.x() + 1, position.y());
  }

  public void goSouth() {
    setPosition(position.x(), position.y() + 1);
  }

  public void goWest() {
    setPosition(position.x() - 1, position.y());
  }
}
