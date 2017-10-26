package edu.mst.grbcp5.hw02.search;

public class LogicalOperator extends StrategyFunction {

  private LogicalOperators operator;

  public LogicalOperator( LogicalOperators op ) {
    super( StrategyFunctionType.LOGICAL_OPERATOR );

    this.operator = op;
  }

  public LogicalOperators getOperator() {
    return operator;
  }

  public void setOperator( LogicalOperators operator ) {
    this.operator = operator;
  }
}
