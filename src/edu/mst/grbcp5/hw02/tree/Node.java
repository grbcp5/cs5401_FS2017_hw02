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
    this.data = copy.getData();

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

  public int getHeight() {
    int height = -1;

    for ( Node< T > child : this.getChildren() ) {
      height = Math.max( height, child.getHeight() );
    }

    return height + 1;
  }

  public int getSize() {
    int size = 1;

    for ( Node< T > child : this.getChildren() ) {
      size += child.getSize();
    }

    return size;
  }

  public String toString() {
    return stringBuilderToString( new StringBuilder() );
  }

  private String stringBuilderToString( StringBuilder sb ) {
    sb.append( this.getData().toString() );
    sb.append( " " );

    for ( int i = 0; i < this.getChildren().size(); i++ ) {
      this.getChildren().get( i ).stringBuilderToString( sb );
    }

    return sb.toString().trim();
  }

}
