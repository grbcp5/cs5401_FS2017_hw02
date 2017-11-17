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

      /* Get results of this iteration */
      thisIteration = simulateOneRound(
        p1,
        p2,
        environment
      );

      /* Shift enviroment "back" one */
      System.arraycopy(
        environment,
        0,
        environment,
        1,
        environment.length - 1
      );
      /* Put this iteration at most recent position */
      environment[ 0 ] = thisIteration;

      /* Increment time spent */
      totalYears += thisIteration.prisonerTimeSpent;

    } /* For each iteration */

    return totalYears;
  }

  public static IterationRecord simulateOneRound(
    Prisoner p1,
    Prisoner p2,
    IterationRecord[] environment
  ) {
    return new IterationRecord(
      getPrisonersDecision( p1, environment ),
      getPrisonersDecision( p2, IterationRecord.swapSides( environment ) )
    );
  }

  public static boolean getPrisonersDecision(
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

    referencedIteration = environment[ terminal.getTime() - 1 ];

    if ( terminal.getAgent() == Agent.OPPONENT ) {
      return referencedIteration.opponentDefected;
    }

    return referencedIteration.prisonerDefected;
  }

}
