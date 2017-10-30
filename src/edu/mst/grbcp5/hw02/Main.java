package edu.mst.grbcp5.hw02;

import edu.mst.grbcp5.hw02.input.ConfigFileReader;
import edu.mst.grbcp5.hw02.input.Param;
import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.Individual;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.PrisonersDilemmaRandomSearch;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.PrisonersDilemmaRandomSearchDelegate;
import edu.mst.grbcp5.hw02.search.random.RandomSearch;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Random;

public class Main {

  public static final boolean DEBUG = true;

  public static void main( String[] args ) {

    String configFilePath;
    ConfigFileReader fileReader;
    Parameters parameters;
    PrisonersDilemmaRandomSearchDelegate delegate;
    RandomSearch searcher;
    Long rndSeed;

    if ( args.length != 1 ) {
      System.out.println( "Usage: run.sh <configFilePath>" );
      return;
    }

    configFilePath = args[ 0 ];
    try {
      fileReader = new ConfigFileReader( configFilePath );
    } catch ( ParserConfigurationException | IOException | SAXException e ) {
      e.printStackTrace();
      return;
    }

    parameters = new Parameters( fileReader.getParameters() );

    if ( parameters.getBoolean( Param.SHOULD_SEED_RANDOM ) ) {
      rndSeed = parameters.getLong( Param.RANDOM_SEED );
    } else {
      rndSeed = System.currentTimeMillis();
    }
    GRandom.setInstance( new Random( rndSeed ) );

    delegate = new PrisonersDilemmaRandomSearchDelegate( parameters );
    searcher = new PrisonersDilemmaRandomSearch( parameters, delegate );


    searcher.getRandomIndividuals( 10000 );
    Individual i = delegate.getCurrentBest();

    System.out.println( "Best individual: " );
    System.out.println( "\tPreorder: " + i );
    System.out.println( "\tFitness: " + i.getFitness() );
  }

}
