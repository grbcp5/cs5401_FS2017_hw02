package edu.mst.grbcp5.hw02.tree;

import java.util.ArrayList;
import java.util.List;

public class Tree< T > {

  private Node< T > root;

  public Tree() {
    root = new Node< T >();
  }

  public Tree( T rootData ) {
    root = new Node< T >();
    root.setData( rootData );
  }

  public Tree( Tree< T > copy ) {
    this.root = new Node< T >( copy.getRoot() );
  }

  public Node< T > getRoot() {
    return root;
  }

  public void preorderTraversal( TreeNodeProcessor<T> tnp ) {

    if( this.getRoot() == null ) {
      return;
    }

    this.getRoot().preorderTraversal( tnp );
  }

  public int getHeight() {

    if( this.getRoot() == null ) {
      return -1;
    }

    return this.getRoot().getHeight();
  }

}

