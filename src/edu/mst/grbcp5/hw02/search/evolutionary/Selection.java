package edu.mst.grbcp5.hw02.search.evolutionary;

import edu.mst.grbcp5.hw02.GRandom;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.Prisoner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Selection {

  public static Prisoner[] fitnessProportional(
    Prisoner[] population,
    int selectionSize
  ) {

    /* Local Variables */
    double[] cumulativeFitnesses = new double[ population.length ];
    cumulativeFitnesses[ 0 ] = population[ 0 ].getFitness();
    Random rnd = GRandom.getInstance();

    /* Initialize */
    for ( int i = 1; i < population.length; i++ ) {
      double fitness = population[ i ].getFitness();
      cumulativeFitnesses[ i ] = cumulativeFitnesses[ i - 1 ] + fitness;
    }
    Prisoner selection[] = new Prisoner[ selectionSize ];

    /* Select */
    for ( int i = 0; i < selectionSize; i++ ) {
      double randomFitness = rnd.nextDouble() *
        cumulativeFitnesses[ cumulativeFitnesses.length - 1 ];
      int index = Arrays.binarySearch( cumulativeFitnesses, randomFitness );
      if ( index < 0 ) {
        index = Math.abs( index + 1 );
      }
      selection[ i ] = population[ index ];
    }

    return selection;
  }

  public static Prisoner[] Truncation(
    Prisoner[] population,
    int selectionSize
  ) {

    /* Local variables*/
    Prisoner selection[];
    int length;

    /* Initialization */
    selection = new Prisoner[ selectionSize ];
    Arrays.sort( population );
    length = population.length;

    /* Select */
    for ( int i = 0; i < selectionSize; i++ ) {
      selection[ i ] = population[ length - 1 - i ];
    }

    return selection;
  }

  public static Prisoner[] kTournamentSelection(
    Prisoner[] population,
    int selectionSize,
    int k,
    boolean replace
  ) {

    /* Local variables */
    Prisoner selection[];
    List<Prisoner> pop = new ArrayList<>( Arrays.asList( population ) );
    double best;
    double cur;
    int bestIdx;
    Random rnd;

    /* Initialize */
    selection = new Prisoner[ selectionSize ];
    rnd = GRandom.getInstance();
    bestIdx = 0;

    /* Select */
    for ( int i = 0; i < selectionSize; i++ ) {

      /* Get best from group of size k */
      best = -1.0;
      for ( int j = 0; j < k  ; j++ ) {
        cur = pop.get( rnd.nextInt( pop.size() ) ).getFitness();
        if( cur > best ) {
          best =
          bestIdx = j;
        }
      }

      /* Add best from group to selection */
      if( replace ) {
        selection[ i ] = pop.get( bestIdx );
      } else {
        selection[ i ] = pop.remove( bestIdx );
      }

    }

    return selection;
  }

}
