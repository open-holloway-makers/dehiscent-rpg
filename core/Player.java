package core;

import map.WorldPoint;

import java.util.List;
import java.util.ArrayList;

public class Player {

  private int vitality, strength, dexterity, intelligence;
  private int hp, xp, gold;
  private int physicalDefence;

  private WorldPoint position;
  private List<WorldPoint> visitedPoints;

  public Player() {
    initStatsBase();
    initVitals();
    visitedPoints = new ArrayList<WorldPoint>();
    position = new WorldPoint(0, 0);
    visitedPoints.add(new WorldPoint(0, 0));
  }

  public void addHp(int x) {
    hp = java.lang.Math.min(hp + x, getMaxHp());
  }

  public void subHp(int x) {
    this.hp -= x;
  }

  public void setVit(int x) {
    vitality = x;
  }

  public void setStr(int x) {
    strength = x;
  }

  public void setDex(int x) {
    dexterity = x;
  }

  public void setInt(int x) {
    intelligence = x;
  }

  public void setPosition(int x, int y) {
    position = new WorldPoint(x, y);
    visitedPoints.add(position);
  }

  // This should probably be changed in the future!
  public int getMaxHp() {
    return 100 * (this.vitality / 10);
  }

  public int getVit() {
    return vitality;
  }

  public int getStr() {
    return strength;
  }

  public int getDex() {
    return dexterity;
  }

  public int getInt() {
    return intelligence;
  }

  public WorldPoint getPosition() {
    return position;
  }

  public List<WorldPoint> getVisited() {
    return visitedPoints;
  }

  public void initStatsBase() {
    this.vitality = 6;
    this.dexterity = 6;
    this.strength = 6;
    this.intelligence = 6;
  }

  public void initVitals() {
    this.hp = this.getMaxHp();
    this.xp = 0;
    this.gold = 0;
  }

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

  public String vitalsToString() {
    return "HP: " + hp + " | XP: " + xp + " | Gold: " + gold;
  }

  public String statsToString() {
    return "VIT: " + vitality + " | DEX:" + dexterity + " | STR: " + strength + " | INT: " + intelligence;
  }
}
