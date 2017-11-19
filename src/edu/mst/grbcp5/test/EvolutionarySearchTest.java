package edu.mst.grbcp5.test;

import edu.mst.grbcp5.hw02.GRandom;
import edu.mst.grbcp5.hw02.input.ConfigFileReader;
import edu.mst.grbcp5.hw02.input.Param;
import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.Prisoner;
import edu.mst.grbcp5.hw02.search.evolutionary.EvolutionarySearchDelegate;
import org.junit.Before;
import org.junit.Test;

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

}