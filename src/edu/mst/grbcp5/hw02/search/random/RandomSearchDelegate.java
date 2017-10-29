package edu.mst.grbcp5.hw02.search.random;

import edu.mst.grbcp5.hw02.search.Individual;

public abstract class RandomSearchDelegate {

  protected Individual currentBest;

  public RandomSearchDelegate() {
    this.currentBest = null;
  }

  public abstract void handleNewIndividual( Individual i );

  public abstract double fitness( Individual i );

  public Individual getCurrentBest() {
    return currentBest;
  }

}
