package edu.mst.grbcp5.hw02.input;


public class RequiredTagNotFoundException extends Exception {

  public RequiredTagNotFoundException() {
    this( "A required tag was not found." );
  }

  public RequiredTagNotFoundException( String message ) {
    super( message );
  }


}
