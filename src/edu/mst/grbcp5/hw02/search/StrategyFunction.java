package edu.mst.grbcp5.hw02.search;

import edu.mst.grbcp5.hw02.search.random.StrategyFunctionType;

public abstract class StrategyFunction {

  private StrategyFunctionType type;

  public StrategyFunction( StrategyFunctionType type ) {
    this.type = type;
  }

  public StrategyFunctionType getType() {
    return this.type;
  }

}
