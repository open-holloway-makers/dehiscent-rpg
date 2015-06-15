package items;

public enum Rating {
  S(1.70), A(1.19), B(0.99), C(0.74), D(0.49), E(0.13), U(0.00);

  private final double value;

  Rating(double value) {
    this.value = value;
  }

  public double getValue() {
    return value;
  }
}
