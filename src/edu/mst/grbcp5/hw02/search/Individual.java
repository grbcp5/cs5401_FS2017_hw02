package edu.mst.grbcp5.hw02.search;

import edu.mst.grbcp5.hw02.tree.Tree;

import java.util.Comparator;

public abstract class Individual< T > implements Comparable{

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

  public Integer getSize() {
    return this.strategy.getSize();
  }

  @Override
  public int compareTo( Object o ) {
    Individual other = (Individual)o;

    int b1 = this.getFitness().compareTo( other.getFitness() );

    return ( b1 == 0 ) ?
      -this.getSize().compareTo( other.getSize() ):
      b1;
  }
}
