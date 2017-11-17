package edu.mst.grbcp5.hw02;

import java.util.Random;

public class GRandom<T> {

  private static Random rnd;

  public static void setInstance( Random rnd ) {
    GRandom.rnd = rnd;
  }

  public static Random getInstance() {
    return rnd;
  }

  public T getRndXorY(
    T x,
    T y,
    double chanceOfX
  ) {
    Random rnd = GRandom.getInstance();
    double result = rnd.nextDouble();

    if ( result <= chanceOfX ) {
      return x;
    }

    return y;
  }
}
