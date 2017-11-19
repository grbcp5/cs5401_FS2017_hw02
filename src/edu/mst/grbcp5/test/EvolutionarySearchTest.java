package edu.mst.grbcp5.test;

import edu.mst.grbcp5.hw02.GRandom;
import edu.mst.grbcp5.hw02.input.ConfigFileReader;
import edu.mst.grbcp5.hw02.input.Param;
import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.Prisoner;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.PrisonersDilemmaRandomSearch;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.PrisonersDilemmaRandomSearchDelegate;
import edu.mst.grbcp5.hw02.search.evolutionary.EvolutionarySearchDelegate;
import edu.mst.grbcp5.hw02.search.evolutionary.Manipulation;
import edu.mst.grbcp5.hw02.search.random.RandomSearch;
import edu.mst.grbcp5.hw02.search.random.RandomSearchDelegate;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class EvolutionarySearchTest {

  private EvolutionarySearchDelegate esDelegate;

  @Before
  public void setup() throws Exception {


    Parameters parameters;
    ConfigFileReader cfr;

    cfr = new ConfigFileReader(
      "config/test/evolutionarySearchTest.xml"
    );
    parameters = new Parameters( cfr.getParameters() );
    esDelegate = new EvolutionarySearchDelegate( parameters );

    GRandom.setInstance( new Random( parameters.getLong( Param.RANDOM_SEED )
    ) );
  }

  @Test
  public void testInitialPopulation() throws Exception {

    Prisoner[] initialPop = esDelegate.getInitialPopulation();
    int height;
    int lessThanFull = 0;
    int full = 0;
    int fullHeight
      = esDelegate.getParameters().getInteger( Param.MAX_TREE_DEPTH );

    for ( int i = 0; i < initialPop.length; i++ ) {
      height = initialPop[ i ].getStrategy().getHeight();
      System.out.println( "Prisoner " + i );
      System.out.println( "\tPreorder: " + initialPop[ i ].toString() );
      System.out.println(
        "\tDepth: " + height
      );

      if( height < fullHeight ) {
        lessThanFull++;
      } else {
        full++;
      }

    }

    System.out.println( "Less than full: " + lessThanFull );
    System.out.println( "Full: " + full );

  }

  @Test
  public void testMutation() throws Exception {

    Map<String, Object> parameters = new HashMap<>();
    parameters.put( Param.MEMORY_DEPTH.identifier(), 5 );
    parameters.put( Param.MAX_TREE_DEPTH.identifier(), 5 );
    parameters.put( Param.DEPTH_PROPORTIONAL_INIT.identifier(), true );
    PrisonersDilemmaRandomSearchDelegate rsDelegate
      = new PrisonersDilemmaRandomSearchDelegate(
      new Parameters( parameters )
    );
    RandomSearch randomSearch = new PrisonersDilemmaRandomSearch( rsDelegate );
    Prisoner p;
    Prisoner c;

    for ( int i = 0; i < 10; i++ ) {
      p = randomSearch.getRandomIndividual();
      System.out.println( "Iteration " + i );
      System.out.println( "\tRandom Individual: " );
      System.out.println( "\t\t" + p.toString() );

      c = Manipulation.subTreeMutation( p, 5, 5 );

      System.out.println( "\tParent Individual: " );
      System.out.println( "\t\t" + p.toString() );
      System.out.println( "\tChild Individual: " );
      System.out.println( "\t\t" + c.toString() );


    }

  }

  @Test
  public void testCrossover() throws Exception {

    Map<String, Object> parameters = new HashMap<>();
    parameters.put( Param.MEMORY_DEPTH.identifier(), 5 );
    parameters.put( Param.MAX_TREE_DEPTH.identifier(), 5 );
    parameters.put( Param.DEPTH_PROPORTIONAL_INIT.identifier(), true );
    PrisonersDilemmaRandomSearchDelegate rsDelegate
      = new PrisonersDilemmaRandomSearchDelegate(
      new Parameters( parameters )
    );
    RandomSearch randomSearch = new PrisonersDilemmaRandomSearch( rsDelegate );
    Prisoner p1;
    Prisoner p2;
    Prisoner c;

    for ( int i = 0; i < 10; i++ ) {
      p1 = randomSearch.getRandomIndividual();
      p2 = randomSearch.getRandomIndividual();
      System.out.println( "Iteration " + i );
      System.out.println( "\tRandom Parent 1: " );
      System.out.println( "\t\t" + p1.toString() );
      System.out.println( "\tRandom Parent 2: " );
      System.out.println( "\t\t" + p2.toString() );

      c = Manipulation.subTreeCrossover( p1, p2, 5 );
      System.out.println( "\tExecute Crossover" );

      System.out.println( "\tRandom Parent 1: " );
      System.out.println( "\t\t" + p1.toString() );
      System.out.println( "\tRandom Parent 2: " );
      System.out.println( "\t\t" + p2.toString() );
      System.out.println( "\tChild: " );
      System.out.println( "\t\t" + c.toString() );
    }

  }

}