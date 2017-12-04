package edu.mst.grbcp5.hw02.search.evolutionary;

import edu.mst.grbcp5.hw02.GRandom;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.*;
import edu.mst.grbcp5.hw02.tree.Node;
import edu.mst.grbcp5.hw02.tree.Tree;

import java.util.Random;

public class Manipulation {

  public static Prisoner subTreeMutation(
    Prisoner parent,
    int maxLevel,
    int memoryDepth
  ) {

    Prisoner child;

    child = new Prisoner(
      new Tree<>( parent.getStrategy() )
    );


    return new Prisoner(
      new Tree<>(
        mutateRandomNode(
          child.getStrategy().getRoot(),
          0,
          maxLevel,
          memoryDepth
        )
      )
    );
  }

  private static Node< StrategyFunction > mutateRandomNode(
    Node< StrategyFunction > n,
    int level,
    int maxLevel,
    int memoryDepth
  ) {

    Node< StrategyFunction > child;

    if ( n.getData().getType() == StrategyFunctionType.TERMINAL ) {

      return getRandomNode( level, maxLevel, memoryDepth );

    } else {

      /* 50/50 Shot */
      if ( GRandom.getInstance().nextDouble() < 0.5 ) {

        /* Mutate this node */
        return getRandomNode( level, maxLevel, memoryDepth );

      } else {

        /* Mutate child node */
        if ( ( ( LogicalOperator ) n.getData() ).getOperator() ==
          Operator.NOT || GRandom.getInstance().nextDouble() < 0.5 ) {

          child = n.getChildren().remove( 0 );

        } else {

          child = n.getChildren().remove( 1 );

        }

        n.getChildren().add( mutateRandomNode(
          child,
          level + 1,
          maxLevel,
          memoryDepth )
        );

        return n;
      }

    }

  }

  private static Node< StrategyFunction > getRandomNode(
    int thisNodesLevel,
    int maxLevel,
    int memoryDepth
  ) {

    /* Local Variables */
    StrategyFunctionType randType;
    Node< StrategyFunction > result;
    LogicalOperator op;
    GRandom< StrategyFunctionType > rndAssist;
    double chanceOfTerminal;

    /* Initialize */
    result = new Node<>();
    rndAssist = new GRandom<>();

    chanceOfTerminal = ( thisNodesLevel == maxLevel ) ? 1.0 : 0.5;

    randType = rndAssist.getRndXorY(
      StrategyFunctionType.TERMINAL,
      StrategyFunctionType.LOGICAL_OPERATOR,
      chanceOfTerminal
    );

    /* If terminal */
    if ( randType == StrategyFunctionType.TERMINAL ) {

      result.getChildren().clear();
      result.setData( generateRandomTerminal( memoryDepth ) );

    /* If operator */
    } else {
      /* Set data */
      op = getRandomOperator();
      result.setData( op );

      switch ( op.getOperator() ) {
        case AND:
        case OR:
        case XOR:
          /* Add two children for binary operators */
          result.getChildren().add(
            getRandomNode(
              thisNodesLevel + 1,
              maxLevel,
              memoryDepth
            )
          );
          result.getChildren().add(
            getRandomNode(
              thisNodesLevel + 1,
              maxLevel,
              memoryDepth
            )
          );
          break;
        case NOT:
          /* Add one child for unary operator */
          result.getChildren().add(
            getRandomNode(
              thisNodesLevel + 1,
              maxLevel,
              memoryDepth
            )
          );
          break;
      }
    }

    return result;
  }

  private static Terminal generateRandomTerminal( int memoryDepth ) {

    /* Local variables */
    Random rnd;
    Terminal result;
    GRandom< Agent > rndAssist;
    Agent rndAgent;
    int k;

    /* Initialize */
    rnd = GRandom.getInstance();
    rndAssist = new GRandom<>();
    int rndDepth;
    k = memoryDepth;

    /* Create a new random terminal */
    rndAgent = rndAssist.getRndXorY(
      Agent.OPPONENT,
      Agent.PRISONER,
      0.5
    );
    rndDepth = rnd.nextInt( k ) + 1;
    result = new Terminal( rndAgent, rndDepth );

    return result;
  }

  private static LogicalOperator getRandomOperator() {
    Random rnd = GRandom.getInstance();
    return new LogicalOperator(
      Operator.values()[ rnd.nextInt( Operator.values().length ) ]
    );
  }

  public static Prisoner subTreeCrossover(
    Prisoner p1,
    Prisoner p2,
    int maxLevel
  ) {

    Prisoner child;

    child = new Prisoner(
      new Tree<>(
        addSubtree(
          new Node<>( p1.getStrategy().getRoot() ),
          new Node<>( getRandomNode( p2.getStrategy().getRoot() ) ),
          0,
          maxLevel
        )
      )
    );

    return child;
  }

  private static Node< StrategyFunction > addSubtree(
    Node< StrategyFunction > root,
    Node< StrategyFunction > subTree,
    int level,
    int maxHeight
  ) {

    Node< StrategyFunction > child;

    if ( root.getData().getType() == StrategyFunctionType.TERMINAL
      || level + subTree.getHeight() == maxHeight
      || GRandom.getInstance().nextDouble() < 0.5 ) {
      return subTree;
    }

    if ( ( ( LogicalOperator ) root.getData() ).getOperator() == Operator.NOT
      || GRandom.getInstance().nextDouble() < 0.5 ) {
      child = root.getChildren().remove( 0 );
    } else {
      child = root.getChildren().remove( 1 );
    }

    root.getChildren().add(
      addSubtree(
        child,
        subTree,
        level + 1,
        maxHeight
      )
    );

    return root;

  }

  private static Node< StrategyFunction > getRandomNode(
    Node< StrategyFunction > n
  ) {

    if ( n.getData().getType() == StrategyFunctionType.TERMINAL
      || GRandom.getInstance().nextDouble() < 0.5 ) {
      return n;
    } else {

      if ( ( ( LogicalOperator ) n.getData() ).getOperator() == Operator.NOT
        || GRandom.getInstance().nextDouble() < 0.5 ) {

        return getRandomNode( n.getChildren().get( 0 ) );

      } else {

        return getRandomNode( n.getChildren().get( 1 ) );


      }

    }

  }

}
