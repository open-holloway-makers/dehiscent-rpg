package items;

import core.Stat;

public class ScalingPair {
  private Stat stat;
  private Rating rating;

  public ScalingPair(Stat stat, Rating rating) {
    this.stat = stat;
    this.rating = rating;
  }

  public Stat getStat() {
    return stat;
  }

  public void setStat(Stat stat) {
    this.stat = stat;
  }

  public Rating getRating() {
    return rating;
  }

  public void setRating(Rating rating) {
    this.rating = rating;
  }
}


