package edu.mst.grbcp5.hw02.tree;

import java.util.ArrayList;
import java.util.List;

public class Node< T > {
  private T data;
  private Node< T > parent;
  private List< Node< T > > children;

  public Node() {
    this.children = new ArrayList<>();
  }

  public Node( Node< T > copy ) {
    this.children = new ArrayList<>( copy.getChildren().size() );

    for ( Node< T > copyChild : copy.getChildren() ) {
      this.children.add( new Node< T >( copyChild ) );
    }

  }

  public T getData() {
    return data;
  }

  public void setData( T data ) {
    this.data = data;
  }

  public Node< T > getParent() {
    return parent;
  }

  public void setParent( Node< T > parent ) {
    this.parent = parent;
  }

  public List< Node< T > > getChildren() {
    return children;
  }

  public void preorderTraversal( TreeNodeProcessor<T> tnp ) {

    tnp.process( this.getData() );
    for( int i = 0; i < this.getChildren().size(); i++ ) {
      this.getChildren().get( i ).preorderTraversal( tnp );
    }
  }

}
