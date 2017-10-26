package edu.mst.grbcp5.hw02.search;

import edu.mst.grbcp5.hw02.tree.Node;
import edu.mst.grbcp5.hw02.tree.Tree;

public class Individual< T > {

  protected Tree< T > tree;

  public Individual() {
    tree = new Tree< T >();
  }

  public Individual( Tree< T > tree ) {
    this.tree = new Tree< T >( tree );
  }

  public Tree< T > getTree() {
    return tree;
  }

}
