package edu.mst.grbcp5.hw02;

import edu.mst.grbcp5.hw02.input.ConfigFileReader;
import edu.mst.grbcp5.hw02.input.Param;
import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.Individual;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.PrisonersDilemmaRandomSearch;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.PrisonersDilemmaRandomSearchDelegate;
import edu.mst.grbcp5.hw02.search.random.RandomSearch;

import java.util.Random;

public class Main {

  public static final boolean DEBUG = true;

  public static void main( String[] args ) throws Exception {

    /* Local Variables */
    String configFilePath;
    Parameters parameters;
    PrisonersDilemmaRandomSearchDelegate delegate;
    RandomSearch searcher;
    String searchType;
    int numRuns;

    /* Check for program arguments */
    if ( args.length != 1 ) {
      System.out.println( "Usage: run.sh <configFilePath>" );
      return;
    }

    /* Get system parameters */
    configFilePath = args[ 0 ];
    parameters = getParameters( configFilePath );

    numRuns = parameters.getInteger( Param.NUM_RUNS );
    for ( int r = 0; r < numRuns; r++ ) {

      parameters.put( Param.CURRENT_RUN, r + 1 );

      /* Seed random number generator based on parameters */
      seedRandomNumberGenerator( parameters );

      /* Check search type */
      searchType = parameters.getString( Param.SEARCH_TYPE );
      if ( !searchType.toLowerCase().equals( "random" ) ) {

        /* Only support random search */
        throw new Exception( "Only random search supported" );

      }

      delegate = new PrisonersDilemmaRandomSearchDelegate( parameters );
      searcher = new PrisonersDilemmaRandomSearch( delegate );

      searcher.getRandomIndividuals( 10000 );
      Individual i = delegate.getCurrentBest();

      System.out.println( "\nBest individual: " );
      System.out.println( "\tPreorder: " + i );
      System.out.println( "\tFitness: " + i.getFitness() );

    }

  }

  private static Parameters getParameters( String configFilePath ) throws
    Exception {

    /* Local Variables */
    ConfigFileReader fileReader;
    Parameters parameters;

    fileReader = new ConfigFileReader( configFilePath );


    parameters = new Parameters( fileReader.getParameters() );


    return parameters;
  }

  private static void seedRandomNumberGenerator( Parameters parameters ) {

    Long rndSeed;

    if ( parameters.getBoolean( Param.SHOULD_SEED_RANDOM ) ) {
      rndSeed = parameters.getLong( Param.RANDOM_SEED );
    } else {
      rndSeed = System.currentTimeMillis();
    }

    GRandom.setInstance( new Random( rndSeed ) );
  }

}
