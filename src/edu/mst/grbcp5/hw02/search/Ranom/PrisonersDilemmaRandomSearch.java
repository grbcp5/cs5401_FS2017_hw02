package edu.mst.grbcp5.hw02.search.Ranom;

import com.sun.xml.internal.xsom.impl.Ref;
import edu.mst.grbcp5.hw02.Main;
import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.*;
import edu.mst.grbcp5.hw02.tree.Node;
import edu.mst.grbcp5.hw02.tree.Tree;

import java.util.Random;

public class PrisonersDilemmaRandomSearch extends RandomSearch {

  public PrisonersDilemmaRandomSearch( Parameters p ) {
    super( p );
  }

  @Override
  public Individual getRandomIndividual() {
    Prisoner i;

    i = new Prisoner( createRandomTree() );

    return i;
  }

  private Tree< StrategyFunction > createRandomTree() {
    Tree< StrategyFunction > result;
    Node< StrategyFunction > node;
    int d = this.parameters.getInteger( "maxTreeDepth" );
    int currentLevel;
    double step;

    result = new Tree<>();
    node = result.getRoot();

    step = 1 / ( ( double ) d );
    currentLevel = 0;


    return result;
  }

  private Node< StrategyFunction > getRandomNode( int thisNodesLevel,
                                                  int step ) {

    /* Local Variables */
    StrategyFunctionType randType;
    Node< StrategyFunction > result;
    RndAssist< StrategyFunctionType > rndAssist;
    Random rnd;

    /* Initialize */
    result = new Node<>();
    rnd = Main.getRandomInstance();
    rndAssist = new RndAssist< StrategyFunctionType >();

    /* Generate random type based on current level ( See footnote )*/
    randType = rndAssist.getRndXorY(
      rnd,
      StrategyFunctionType.TERMINAL,
      StrategyFunctionType.LOGICAL_OPERATOR,
      thisNodesLevel * step + ( 0.5 ) * step
    );

    /* If terminal */


    /* If operator */

    return result;
  }

  private Terminal generateRandomTerminal() {

    /* Local variables */
    Random rnd;
    Terminal result;
    RndAssist< Agent > rndAssist;
    Agent rndAgent;
    int k;


    rnd = Main.getRandomInstance();
    rndAssist = new RndAssist<>();
    k = this.parameters.getInteger( "memoryDepth" );


    rndAgent = rndAssist.getRndXorY(
      rnd,
      Agent.OPPONENT,
      Agent.PRISONER,
      0.5
    );

    result = null;

    return result;
  }

}

class RndAssist< T > {

  T getRndXorY(
    Random rnd,
    T x,
    T y,
    double chanceOfX
  ) {
    double result = rnd.nextDouble();

    if ( result <= chanceOfX ) {
      return x;
    }

    return y;
  }

}


/*
 * Footnote:
 *
 */