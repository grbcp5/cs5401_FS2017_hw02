package edu.mst.grbcp5.hw02.search;

import edu.mst.grbcp5.hw02.tree.Tree;

public abstract class Individual< T > {

  protected Tree< T > strategy;
  protected Double fitness;

  public Individual() {
    this.strategy = new Tree< T >();
  }

  public Individual( Tree< T > strategy ) {
    this.strategy = new Tree< T >( strategy );
    this.fitness = null;
  }

  public Tree< T > getStrategy() {
    return this.strategy;
  }

  public Double getFitness() {
    return fitness;
  }

  public void setFitness( Double fitness ) {
    this.fitness = fitness;
  }

  public abstract String toString();

}
