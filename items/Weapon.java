package items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Player;
import core.Stat;

public class Weapon extends Item {

  private double baseDamage;
  private double baseMagicDamage;

  private ScalingPair strengthScaling;
  private ScalingPair dexterityScaling;
  private ScalingPair intelligenceScaling;

  public Weapon(String name, int value, SlotType slotType, Modifier modifier, double baseDamage, double baseMagicDamage, Rating strength, Rating dexterity, Rating intelligence) {
    super(name, value, slotType, modifier);
    this.baseDamage = baseDamage;
    this.baseMagicDamage = baseMagicDamage;
    this.strengthScaling = new ScalingPair(Stat.STR, strength);
    this.dexterityScaling = new ScalingPair(Stat.DEX, strength);
    this.intelligenceScaling = new ScalingPair(Stat.INT, strength);
  }

  public int getAttackRating(Player p) {
    double attackRating = baseDamage;
    double magicRating = baseMagicDamage;
    attackRating *= (1 + strengthScaling.getRating().getValue() * (p.getStr() * 0.02));
    attackRating *= (1 + dexterityScaling.getRating().getValue() * (p.getDex() * 0.02));
    magicRating *= (1 + intelligenceScaling.getRating().getValue() * (p.getInt() * 0.02));
    Random rng = new Random();
    return (int)java.lang.Math.ceil((attackRating + magicRating) * ((rng.nextInt(10) + 95) / 100.0));
  }

  public double getBaseDamage() {
    return baseDamage;
  }

  public void setBaseDamage(double baseDamage) {
    this.baseDamage = baseDamage;
  }

  @Override
  public String toString() {
    String output = "\n";
    output += ("Item: " + getName() + "\n");
    if (isEquippable()) {
      output += ("Equip to: " + getSlotType().getValue() + "\n");
    }
    output += modifiersToString();
    output += "\n\n" + getLoreText();
    return output;
  }

  public double getBaseMagicDamage() {
    return baseMagicDamage;
  }

  public void setBaseMagicDamage(double baseMagicDamage) {
    this.baseMagicDamage = baseMagicDamage;
  }
}
