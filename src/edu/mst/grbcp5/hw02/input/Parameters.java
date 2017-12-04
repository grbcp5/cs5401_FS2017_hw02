package edu.mst.grbcp5.hw02.input;

import java.util.Map;
import java.util.Set;

public class Parameters {

  private Map< String, Object > parameters;

  public Parameters( Map< String, Object > parameters ) {
    this.parameters = parameters;
  }

  public void put( Param param, Object value ) {
    parameters.put( param.identifier(), value );
  }

  public Object get( Param param ) {
    return parameters.get( param.identifier() );
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

  @Override
  public String toString() {

    StringBuilder sb;
    Set< String > keys;

    keys = this.parameters.keySet();
    sb = new StringBuilder( keys.size() * 4 );

    for ( String key : keys ) {
      sb.append( key );
      sb.append( ": " );
      sb.append( this.parameters.get( key ).toString() );
      sb.append( "\n" );
    }

    return sb.toString();
  }

}
