package enemies;

import java.util.Random;

public class Enemy {
  private String name;
  private int hp;
  private int maxHp;
  private int attackRating;
  private int xpReward;
  private int goldReward;

  public Enemy(String name, int hp, int attackRating, int xpReward, int goldReward) {
    this.name = name;
    this.hp = hp;
    this.maxHp = hp;
    this.attackRating = attackRating;
    this.xpReward = xpReward;
    this.goldReward = goldReward;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getHp() {
    return hp;
  }

  public void subHp(int x) {
    hp -= x;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public int getAttackRating() {
    Random rng = new Random();
    return (int)java.lang.Math.round(attackRating * ((rng.nextInt(20) + 90) / 100.0));
  }

  public void setAttackRating(int attackRating) {
    this.attackRating = attackRating;
  }

  public int getXpReward() {
    return xpReward;
  }

  public void setXpReward(int xpReward) {
    this.xpReward = xpReward;
  }

  public int getGoldReward() {
    return goldReward;
  }

  public void setGoldReward(int goldReward) {
    this.goldReward = goldReward;
  }

  public int getMaxHp() {
    return maxHp;
  }

  public void setMaxHp(int maxHp) {
    this.maxHp = maxHp;
  }
}
