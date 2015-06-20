package items;

public enum Rating {
  S("S", 1.70), A("A", 1.19), B("B", 0.99), C("C", 0.74),
  D("D", 0.49), E("E", 0.13), U("U", 0.00);

  private final String name;
  private final double value;

  Rating(String name, double value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public String toString() { return name; }
  public double getValue() {
    return value;
  }

}
