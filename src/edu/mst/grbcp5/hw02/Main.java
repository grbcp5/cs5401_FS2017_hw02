package edu.mst.grbcp5.hw02;

import edu.mst.grbcp5.hw02.search.Prisoner;

import java.util.Random;

public class Main {

  public static final boolean DEBUG = true;

  private static Random rnd;

  public static Random getRandomInstance() {
    return rnd;
  }
  public static void setRandomInstance( Random rnd ) {
    Main.rnd = rnd;
  }

  public static void main( String[] args ) {
    System.out.println( "Hello, world" );
  }

}
