package edu.mst.grbcp5.hw02;

import edu.mst.grbcp5.hw02.input.ConfigFileReader;
import edu.mst.grbcp5.hw02.input.Param;
import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.Individual;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.Prisoner;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.PrisonersDilemmaRandomSearch;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.PrisonersDilemmaRandomSearchDelegate;
import edu.mst.grbcp5.hw02.search.evolutionary.EvolutionarySearch;
import edu.mst.grbcp5.hw02.search.evolutionary.EvolutionarySearchDelegate;
import edu.mst.grbcp5.hw02.search.random.RandomSearch;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

public class Main {

  public static final boolean DEBUG = true;
  public static boolean ENABLE_BREAKPOINT = false;

  public static void main( String[] args ) throws Exception {

    /* Local Variables */
    String configFilePath;
    Parameters parameters;
    PrisonersDilemmaRandomSearchDelegate delegate;
    RandomSearch searcher;
    String searchType;
    String logFilePath;
    PrintWriter logWriter;
    String solFilePath;
    PrintWriter solWriter;
    int numRuns;
    Individual currentBest;
    EvolutionarySearchDelegate esDelegate;
    EvolutionarySearch evolutionarySearch;

    /* Check for program arguments */
    if ( args.length != 1 ) {
      System.out.println( "Usage: run.sh <configFilePath>" );
      return;
    }

    /* Get system parameters */
    configFilePath = args[ 0 ];
    parameters = getParameters( configFilePath );

    /* Seed random number generator based on parameters */
    seedRandomNumberGenerator( parameters );


    /* Create log and sol writers */
    logFilePath = parameters.getString( Param.LOG_FILE );
    logWriter = new PrintWriter( new File( logFilePath ) );
    solFilePath = parameters.getString( Param.SOL_FILE );
    solWriter = new PrintWriter( new File( solFilePath ) );

    logWriter.println( "Result Log:\n" );
    logWriter.println( "Parameters:\n" + parameters.toString() );

    parameters.put( Param.LOG_WIRTER, logWriter );
    parameters.put( Param.SOL_WRITER, solWriter );

    /* Check search type */
    searchType = parameters.getString( Param.SEARCH_TYPE );
    if ( searchType.toLowerCase().equals( "random" ) ) {

      /* Initialize current best */
      currentBest = null;

      /* Execute each run */
      numRuns = parameters.getInteger( Param.NUM_RUNS );
      for ( int r = 0; r < numRuns; r++ ) {

        logWriter.println( "\nRun " + ( r + 1 ) + ":" );

        parameters.put( Param.CURRENT_RUN, r + 1 );

        /* Seed random number generator based on parameters */
        seedRandomNumberGenerator( parameters );

        /* Create searcher objects */
        delegate = new PrisonersDilemmaRandomSearchDelegate( parameters );
        searcher = new PrisonersDilemmaRandomSearch( delegate );

        /* Execute search */
        searcher.getRandomIndividuals( 10000 );

        /* Get best individual from run */
        Individual i = delegate.getCurrentBest();

        System.out.println( "\nBest individual: " );
        System.out.println( "\tPreorder: " + i );
        System.out.println( "\tFitness: " + i.getFitness() );

        if ( currentBest == null ||
          i.getFitness() > currentBest.getFitness() ) {
          currentBest = i;
          System.out.println( "New best" );
        }

      } /* For each run */

      solWriter.println( currentBest.toString() );

    } else if ( searchType.equalsIgnoreCase( "Evolutionary" ) ) {

      /* Initialize current best */
      currentBest = null;

      /* Execute each run */
      numRuns = parameters.getInteger( Param.NUM_RUNS );
      for ( int r = 0; r < numRuns; r++ ) {

        logWriter.println( "\nRun " + ( r + 1 ) + ":" );

        parameters.put( Param.CURRENT_RUN, r + 1 );

        /* Seed random number generator based on parameters */
        seedRandomNumberGenerator( parameters );

        /* Create searcher objects */
        esDelegate = new EvolutionarySearchDelegate( parameters );
        evolutionarySearch = new EvolutionarySearch( esDelegate );

        /* Execute search */
        evolutionarySearch.search();

        /* Get best individual from run */
        Prisoner p = esDelegate.getCurrentBest();

        System.out.println( "\nBest individual: " );
        System.out.println( "\tPreorder: " + p );
        System.out.println( "\tFitness: " + p.getFitness() );

        if ( currentBest == null ||
          p.getFitness() > currentBest.getFitness() ) {
          currentBest = p;
          System.out.println( "New best" );
        }

      } /* For each run */

      solWriter.println( currentBest.toString() );

    }



    logWriter.flush();
    logWriter.close();
    solWriter.flush();
    solWriter.close();

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
      parameters.put( Param.RANDOM_SEED, rndSeed );
    }

    GRandom.setInstance( new Random( rndSeed ) );
  }

}
