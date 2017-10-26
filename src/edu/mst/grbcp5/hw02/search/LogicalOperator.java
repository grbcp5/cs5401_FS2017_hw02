package edu.mst.grbcp5.hw02.search;

import edu.mst.grbcp5.hw02.Main;

public class LogicalOperator extends StrategyFunction {

  private Operator operator;

  public LogicalOperator( Operator op ) {
    super( StrategyFunctionType.LOGICAL_OPERATOR );
    String dummy;

    /* throw null pointer reference */
    if( Main.DEBUG  )
      dummy = op.toString();

    this.operator = op;
  }

  public Operator getOperator() {
    return operator;
  }

  public void setOperator( Operator operator ) {
    this.operator = operator;
  }

  @Override
  public String toString() {

    switch ( this.operator ) {
      case AND:
        return "AND";
      case OR:
        return "OR";
      case NOT:
        return "NOT";
      case XOR:
        return "XOR";
    }

    return "";
  }

  @Override
  public boolean equals( Object o ) {
    if ( this == o ) return true;
    if ( !( o instanceof LogicalOperator ) ) return false;

    LogicalOperator that = ( LogicalOperator ) o;

    return getOperator() == that.getOperator();
  }

  @Override
  public int hashCode() {
    return getOperator().hashCode();
  }
}
