package edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma;

import edu.mst.grbcp5.hw02.tree.Node;

public class IteratedPrisonerDilemmaSimulator {

  public static int simulate(
    Prisoner p1,
    Prisoner p2,
    int iterations,
    IterationRecord[] environment
  ) {

    /* Local variables */
    int totalYears;
    IterationRecord thisIteration;

    /* Initialize */
    totalYears = 0;

    /* Iterate */
    for ( int i = 0; i < iterations; i++ ) {
      thisIteration = simulate(
        p1,
        p2,
        environment
      );

      /* Shift everything to the left one */
      System.arraycopy(
        environment,
        1,
        environment,
        0,
        environment.length - 1
      );
      /* Put this iteration at most recent */
      environment[ environment.length - 1 ] = thisIteration;

      /* Increment time spent */
      totalYears += thisIteration.prisonerTimeSpent;
    }

    return totalYears;
  }

  public static IterationRecord simulate(
    Prisoner p1,
    Prisoner p2,
    IterationRecord[] environment
  ) {
    return new IterationRecord(
      simulate( p1, environment ),
      simulate( p2, environment )
    );
  }

  public static boolean simulate(
    Prisoner p,
    IterationRecord[] environment
  ) {
    return getValue( p.getStrategy().getRoot(), environment );
  }

  public static boolean getValue(
    Node< StrategyFunction > n,
    IterationRecord[] environment
  ) {
    LogicalOperator op;
    boolean child1;
    boolean child2;

    if ( n.getData().getType() == StrategyFunctionType.TERMINAL ) {
      return getValue( ( Terminal ) n.getData(), environment );
    }

    op = ( LogicalOperator ) n.getData();

    switch ( op.getOperator() ) {

      case AND:
        child1 = getValue( n.getChildren().get( 0 ), environment );
        child2 = getValue( n.getChildren().get( 1 ), environment );
        return child1 && child2;
      case OR:
        child1 = getValue( n.getChildren().get( 0 ), environment );
        child2 = getValue( n.getChildren().get( 1 ), environment );
        return child1 || child2;
      case NOT:
        return !getValue( n.getChildren().get( 0 ), environment );
      case XOR:
        child1 = getValue( n.getChildren().get( 0 ), environment );
        child2 = getValue( n.getChildren().get( 1 ), environment );
        return child1 ^ child2;
    }

    return false;
  }

  public static boolean getValue(
    Terminal terminal,
    IterationRecord[] environment
  ) {
    IterationRecord referencedIteration;

    referencedIteration = environment[ terminal.getTime() ];

    if ( terminal.getAgent() == Agent.OPPONENT ) {
      return referencedIteration.opponentDefected;
    }

    return referencedIteration.prisonerDefected;
  }

}
