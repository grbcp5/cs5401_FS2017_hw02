package edu.mst.grbcp5.hw02.input;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Parameters {

  private Map< String, Object > parameters;

  public Parameters( Map< String, Object > parameters ) {
    this.parameters = parameters;
  }

  public String getString( String parameterIdentifier ) {
    return ( String ) ( this.parameters.get( parameterIdentifier ) );
  }

  public Double getDouble( String parameterIdentifier ) {
    return ( Double ) ( this.parameters.get( parameterIdentifier ) );
  }

  public Integer getInteger( String parameterIdentifier ) {
    return ( Integer ) ( this.parameters.get( parameterIdentifier ) );
  }

  public Boolean getBoolean( String parameterIdentifier ) {
    return ( Boolean ) ( this.parameters.get( parameterIdentifier ) );
  }

  public Long getLong( String parameterIdentifier ) {
    return ( Long ) ( this.parameters.get( parameterIdentifier ) );
  }

  public String[] getList( String parameterIdentifier ) {
    return ( String[] ) ( this.parameters.get( parameterIdentifier ) );
  }

}
