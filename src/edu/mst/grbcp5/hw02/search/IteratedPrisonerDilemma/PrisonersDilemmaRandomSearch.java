package edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma;

import edu.mst.grbcp5.hw02.GRandom;
import edu.mst.grbcp5.hw02.input.Param;
import edu.mst.grbcp5.hw02.search.Individual;
import edu.mst.grbcp5.hw02.search.random.RandomSearch;
import edu.mst.grbcp5.hw02.tree.Node;
import edu.mst.grbcp5.hw02.tree.Tree;

import java.util.Random;

public class PrisonersDilemmaRandomSearch extends RandomSearch {

  public PrisonersDilemmaRandomSearch(
    PrisonersDilemmaRandomSearchDelegate delegate ) {
    super( delegate.getParameters(), delegate );

    this.parameters.require( new Param[]{
      Param.MAX_TREE_DEPTH,
      Param.MEMORY_DEPTH
    } );

  }


  @Override
  public Individual getRandomIndividual() {
    Prisoner p;

    /* Create a random prisoner */
    p = new Prisoner( createRandomTree() );

    return p;
  }

  private Tree< StrategyFunction > createRandomTree() {

    /* Local variables */
    Tree< StrategyFunction > result;
    Node< StrategyFunction > node;
    int d = this.parameters.getInteger( Param.MAX_TREE_DEPTH );
    double step;

    /* Initialize */
    result = new Tree<>();
    step = 1 / ( ( double ) d );

    /* Create a random tree */
    node = getRandomNode( 0, step ); // get a random node
    result.getRoot().setData( node.getData() ); // set rand node's data to root
    for ( Node< StrategyFunction > child : node.getChildren() ) {
      result.getRoot().getChildren().add( child ); // add rand node's children
    }                                              // to root

    return result;
  }

  private Node< StrategyFunction > getRandomNode( int thisNodesLevel,
                                                  double step ) {
    /* Local Variables */
    StrategyFunctionType randType;
    Node< StrategyFunction > result;
    LogicalOperator op;
    GRandom< StrategyFunctionType > rndAssist;
    double chanceOfTerminal;

    /* Initialize */
    result = new Node<>();
    rndAssist = new GRandom<>();

    if (
      parameters.getBoolean( Param.DEPTH_PROPORTIONAL_INIT, true )
      ) {
      /* Generate random type based on current level ( See footnote ) */
      chanceOfTerminal = ( thisNodesLevel == 0 ) ?
        ( 0.5 ) * step :
        thisNodesLevel * step;

    } else {
      /* Generate random type with equal chance of terminal or non-terminal */
      if (
        thisNodesLevel == this.parameters.getInteger( Param.MAX_TREE_DEPTH )
        ) {
        chanceOfTerminal = 1;
      } else {
        chanceOfTerminal = 0.5;
      }
    }

    randType = rndAssist.getRndXorY(
      StrategyFunctionType.TERMINAL,
      StrategyFunctionType.LOGICAL_OPERATOR,
      chanceOfTerminal
    );

    /* If terminal */
    if ( randType == StrategyFunctionType.TERMINAL ) {

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
    k = this.parameters.getInteger( Param.MEMORY_DEPTH );

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
 * Footnote: To increase the likelihood of creating larger trees, the
 * probability of creating a terminal node is proportional to the current
 * level of the tree. This is done such that there is a linear increase in
 * probability of producing a terminal node up to the largest allowed depth
 * at which there is a 100% chance of producing a terminal.
 */