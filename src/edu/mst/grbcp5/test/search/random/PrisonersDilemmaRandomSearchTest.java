package edu.mst.grbcp5.test.search.random;

import edu.mst.grbcp5.hw02.GRandom;
import edu.mst.grbcp5.hw02.Main;
import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.*;
import edu.mst.grbcp5.hw02.search.random.PrisonersDilemmaRandomSearch;
import edu.mst.grbcp5.hw02.tree.Node;
import edu.mst.grbcp5.hw02.tree.Tree;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class PrisonersDilemmaRandomSearchTest {

  public static final int NUM_TEST_CASES = 10000;

  private PrisonersDilemmaRandomSearch fut; // file under test
  private Parameters parameters;

  @Before
  public void setUp() throws Exception {

    GRandom.setInstance( new Random( 10021996 ) );

    Map< String, Object > p;

    p = new HashMap<>();
    p.put( "maxTreeDepth", 10 );
    p.put( "memoryDepth", 5 );
    this.parameters = new Parameters( p );
    this.fut = new PrisonersDilemmaRandomSearch( parameters );

  }

  @Test
  public void getRandomIndividual() throws Exception {

    Prisoner p;
    Tree< StrategyFunction > t;
    Node< StrategyFunction > n;
    int maxTreeDepth;
    int height;
    int heights[];

    maxTreeDepth = this.parameters.getInteger( "maxTreeDepth" );
    heights = new int[ maxTreeDepth + 1 ];

    for ( int i = 0; i < NUM_TEST_CASES; i++ ) {
      p = ( Prisoner ) fut.getRandomIndividual();
      t = p.getStrategy();
      n = t.getRoot();

      height = t.getHeight();
      assertTrue( height <= maxTreeDepth );
      this.testNode( n );
      System.out.println( "Tree passed: " + t );

      heights[ height ]++;

    }

    System.out.println( "\nHeights: " );
    for ( int i = 0; i < maxTreeDepth + 1; i++ ) {
      System.out.println( "Height " + i + ": " + heights[ i ] +
                            " (" +
                            ( ( double ) heights[ i ] ) / NUM_TEST_CASES +
                            ")" );
    }

  }

  private void testNode( Node<StrategyFunction> n ) throws Exception {

    /* Local variables */
    Terminal terminal;
    LogicalOperator op;

    /* Test node depending on type */
    switch ( n.getData().getType() ) {
      case LOGICAL_OPERATOR:
        op = ( LogicalOperator ) n.getData();

        /* Assert node has correct number of children */
        if( op.getOperator() == Operator.NOT ) {
          assertEquals( 1, n.getChildren().size() );
        } else {
          assertEquals( 2, n.getChildren().size() );
        }

        /* Test each child node */
        for ( Node< StrategyFunction > child : n.getChildren() ) {
          testNode( child );
        }

        break;
      case TERMINAL:
        terminal = ( Terminal ) n.getData();

        /* Assert memory reference isn't greater than memory depth */
        assertTrue( terminal.getTime()
          < this.parameters.getInteger( "memoryDepth" ) );
        assertTrue( n.getChildren().isEmpty() );
        break;
    }

  }

}