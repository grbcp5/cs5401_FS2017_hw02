package edu.mst.grbcp5.hw02.input;

import java.util.Map;

public class Parameters {

  private Map< String, Object > parameters;

  public Parameters( Map< String, Object > parameters ) {
    this.parameters = parameters;
  }

  public void require( Param[] requiredParameters ) throws
    NullPointerException {

    String dummy;

    for ( Param param : requiredParameters ) {
      /* Throws a null pointer exception if param is not found */
      dummy = this.parameters.get( param.identifier() ).toString();
    }

  }

  public String getString( Param p ) {
    return ( String ) ( this.parameters.get( p.identifier() ) );
  }

  public String getString( Param p, String defaultValue ) {
    String param = ( String ) ( this.parameters.get( p.identifier() ) );
    return ( param == null ) ? defaultValue : param;
  }

  public Double getDouble( Param p ) {
    return ( Double ) ( this.parameters.get( p.identifier() ) );
  }

  public double getDouble( Param p, double defaultValue ) {
    Double param = ( Double ) ( this.parameters.get( p.identifier() ) );
    return ( param == null ) ? defaultValue : param;
  }

  public Integer getInteger( Param p ) {
    return ( Integer ) ( this.parameters.get( p.identifier() ) );
  }

  public Integer getInteger( Param p, int defaultValue ) {
    Integer param = ( Integer ) ( this.parameters.get( p.identifier() ) );
    return ( param == null ) ? defaultValue : param;
  }

  public Boolean getBoolean( Param p ) {
    return ( Boolean ) ( this.parameters.get( p.identifier() ) );
  }

  public Boolean getBoolean( Param p,
                             boolean defaultValue ) {
    Boolean param = ( Boolean ) ( this.parameters.get( p.identifier() ) );
    return ( param == null ) ? defaultValue : param;
  }

  public Long getLong( Param p ) {
    return ( Long ) ( this.parameters.get( p.identifier() ) );
  }

  public Long getLong( Param p, long defaultValue ) {
    Long param = ( Long ) ( this.parameters.get( p.identifier() ) );
    return ( param == null ) ? defaultValue : param;
  }

  public String[] getList( Param p ) {
    return ( String[] ) ( this.parameters.get( p.identifier() ) );
  }

}
