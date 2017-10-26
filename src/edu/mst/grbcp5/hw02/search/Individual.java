package edu.mst.grbcp5.hw02.search;

import edu.mst.grbcp5.hw02.tree.Tree;

public class Individual< T > {

  protected Tree< T > strategy;

  public Individual() {
    this.strategy = new Tree< T >();
  }

  public Individual( Tree< T > strategy ) {
    this.strategy = new Tree< T >( strategy );
  }

  public Tree< T > getStrategy() {
    return this.strategy;
  }

}
