package items;

import core.IO;
import core.Player;
import core.Stat;

import java.util.Random;

public class Weapon extends Item {

  private double baseDamage;
  private double baseMagicDamage;

  private ScalingPair strengthScaling;
  private ScalingPair dexterityScaling;
  private ScalingPair intelligenceScaling;

  public Weapon(String name, int value, SlotType slotType, Modifier modifier, double baseDamage,
                double baseMagicDamage, Rating strength, Rating dexterity, Rating intelligence) {
    super(name, value, slotType, modifier);
    this.baseDamage = baseDamage;
    this.baseMagicDamage = baseMagicDamage;
    this.strengthScaling = new ScalingPair(Stat.STR, strength);
    this.dexterityScaling = new ScalingPair(Stat.DEX, dexterity);
    this.intelligenceScaling = new ScalingPair(Stat.INT, intelligence);
  }

  public int getAttackRating(Player p) {
    return (int) java.lang.Math.ceil((
            getPhysicalAttackRating(p) + getMagicAttackRating(p)) *
            ((new Random().nextInt(10) + 95) / 100.0));
  }

  public double getPhysicalAttackRating(Player p) {
    return baseDamage *
            (1 + strengthScaling.getRating().getValue() * (p.getStr() * 0.02)) *
            (1 + dexterityScaling.getRating().getValue() * (p.getDex() * 0.02));
  }

  public double getMagicAttackRating(Player p) {
    return baseMagicDamage *= (1 + intelligenceScaling.getRating().getValue() * (p.getInt() * 0.02));
  }

  public double getBaseDamage() {
    return baseDamage;
  }

  public void setBaseDamage(double baseDamage) {
    this.baseDamage = baseDamage;
  }

  public double getBaseMagicDamage() {
    return baseMagicDamage;
  }

  public void setBaseMagicDamage(double baseMagicDamage) {
    this.baseMagicDamage = baseMagicDamage;
  }

  public String toString(Player player) {
    final int len = 60;
    return "\n" +
            IO.formatBanner(len) +
            IO.formatOpposite(len, getName(), getValue() + " gold") +
            IO.formatBanner(len) +
            IO.formatOpposite(len, "Equip to:", (isEquippable()) ? getSlotType().getValue().toString() : "n/a") +
            IO.formatOpposite(len, "Modifier:", (getModifiers().size() > 0) ? modifiersToString() : "n/a") +
            IO.formatOpposite(len, "Physical Damage:", String.format("(%d)+%d", (int) getBaseDamage(), (int) (getAttackRating(player) - getMagicAttackRating(player)))) +
            IO.formatOpposite(len, "Magic Damage:", String.format("(%d)+%d", (int) getBaseMagicDamage(), (int) (getAttackRating(player) - getPhysicalAttackRating(player)))) +
            IO.formatAsBox(getLoreText(), len);
  }
}
