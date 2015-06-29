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

  public int getAttackRating(Player player) {
    return (int) java.lang.Math.round((
            getCompositeAttackRating(player)) *
            ((new Random().nextInt(10) + 95) / 100.0));
  }

  public double getCompositeAttackRating(Player player) {
    return getPhysicalAttackRating(player) + getMagicAttackRating(player);
  }

  private double getPhysicalAttackRating(Player player) {
    return baseDamage *
            (1 + strengthScaling.getRating().getValue() * (player.getStr() * 0.02)) *
            (1 + dexterityScaling.getRating().getValue() * (player.getDex() * 0.02));
  }

  private double getMagicAttackRating(Player player) {
    return baseMagicDamage *= (1 + intelligenceScaling.getRating().getValue() * (player.getInt() * 0.02));
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
    final int len = IO.BOX_WIDTH;
    return "\n" +
            IO.formatBanner(len) +
            IO.formatColumns(len, getName(), getValue() + " gold") +
            IO.formatBanner(len) +
            IO.formatColumns(len, "Equip to:", (isEquippable()) ? getSlotType().getValue().toString() : "n/a") +
            IO.formatColumns(len, "Modifier:", (getModifiers().size() > 0) ? modifiersToString() : "n/a") +
            IO.formatColumns(len, "Physical Damage:", String.format("(%d)+%d", (int) getBaseDamage(),
                    (int) (getPhysicalAttackRating(player) - getBaseDamage()))) +
            IO.formatColumns(len, "Magic Damage:", String.format("(%d)+%d", (int) getBaseMagicDamage(),
                    (int) (getMagicAttackRating(player) - getBaseMagicDamage()))) +
            IO.formatBanner(len) +
            IO.formatColumns(len, "STR Scaling:", strengthScaling.getRating().toString()) +
            IO.formatColumns(len, "DEX Scaling:", dexterityScaling.getRating().toString()) +
            IO.formatColumns(len, "INT Scaling:", intelligenceScaling.getRating().toString()) +
            IO.formatAsBox(getLoreText(), len, true);
  }
}
