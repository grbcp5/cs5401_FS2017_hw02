package edu.mst.grbcp5.hw02.search.random;

import edu.mst.grbcp5.hw02.GRandom;
import edu.mst.grbcp5.hw02.Main;
import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.*;
import edu.mst.grbcp5.hw02.tree.Node;
import edu.mst.grbcp5.hw02.tree.Tree;

import java.util.Random;

public class PrisonersDilemmaRandomSearch extends RandomSearch {

  public PrisonersDilemmaRandomSearch( Parameters p ) {
    super( p );

    this.parameters.require( new String[]{
      "maxTreeDepth",
      "memoryDepth"
    } );

  }

  @Override
  public Individual getRandomIndividual() {
    Prisoner p;

    p = new Prisoner( createRandomTree() );

    return p;
  }

  private Tree< StrategyFunction > createRandomTree() {
    Tree< StrategyFunction > result;
    Node< StrategyFunction > node;
    int d = this.parameters.getInteger( "maxTreeDepth" );
    double step;

    result = new Tree<>();

    step = 1 / ( ( double ) d );

    node = getRandomNode( 0, step );
    result.getRoot().setData( node.getData() );
    for ( Node< StrategyFunction > child : node.getChildren() ) {
      result.getRoot().getChildren().add( child );
    }

    return result;
  }

  private Node< StrategyFunction > getRandomNode( int thisNodesLevel,
                                                  double step ) {

    /* Local Variables */
    StrategyFunctionType randType;
    Node< StrategyFunction > result;
    Random rnd;
    LogicalOperator op;
    GRandom<StrategyFunctionType> rndAssist;

    /* Initialize */
    result = new Node<>();
    rndAssist = new GRandom<>();

    if ( thisNodesLevel >= this.parameters.getInteger( "maxTreeDepth" ) ) {
      result = result;
    }

    /* Generate random type based on current level ( See footnote )*/
    randType = rndAssist.getRndXorY(
      StrategyFunctionType.TERMINAL,
      StrategyFunctionType.LOGICAL_OPERATOR,
      thisNodesLevel * step + ( 0.5 ) * step
    );

    /* If terminal */
    if( randType == StrategyFunctionType.TERMINAL ) {

      result.getChildren().clear();
      result.setData( generateRandomTerminal() );

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
            getRandomNode( thisNodesLevel + 1, step )
          );
          result.getChildren().add(
            getRandomNode( thisNodesLevel + 1, step )
          );
          break;
        case NOT:
        /* Add one child for unary operator */
          result.getChildren().add(
            getRandomNode( thisNodesLevel + 1, step )
          );
          break;
      }
    }

    return result;
  }

  private Terminal generateRandomTerminal() {

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
    k = this.parameters.getInteger( "memoryDepth" );

    /* Create a new random terminal */
    rndAgent = rndAssist.getRndXorY(
      Agent.OPPONENT,
      Agent.PRISONER,
      0.5
    );
    rndDepth = rnd.nextInt( k - 1 );
    result = new Terminal( rndAgent, rndDepth );

    return result;
  }

  private LogicalOperator getRandomOperator() {
    Random rnd = GRandom.getInstance();
    return new LogicalOperator(
      Operator.values()[ rnd.nextInt( Operator.values().length ) ]
    );
  }

}


/*
 * Footnote: TODO
 *
 */