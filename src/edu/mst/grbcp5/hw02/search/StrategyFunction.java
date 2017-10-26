package edu.mst.grbcp5.hw02.search;

public abstract class StrategyFunction {

  private StrategyFunctionType type;

  public StrategyFunction( StrategyFunctionType type ) {
    this.type = type;
  }

  public StrategyFunctionType getType() {
    return this.type;
  }

  public abstract String toString();

  public abstract boolean equals( Object o );

}
