package edu.mst.grbcp5.hw02.search;

public class LogicalOperator extends StrategyFunction {

  private Operator operator;

  public LogicalOperator( Operator op ) {
    super( StrategyFunctionType.LOGICAL_OPERATOR );

    this.operator = op;
  }

  public Operator getOperator() {
    return operator;
  }

  public void setOperator( Operator operator ) {
    this.operator = operator;
  }
}
