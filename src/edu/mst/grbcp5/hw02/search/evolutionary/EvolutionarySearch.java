package edu.mst.grbcp5.hw02.search.evolutionary;

import edu.mst.grbcp5.hw02.input.Param;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.Prisoner;

import java.util.LinkedList;

public class EvolutionarySearch {

  private EvolutionarySearchDelegate delegate;

  public EvolutionarySearch(
    EvolutionarySearchDelegate delegate
  ) {
    this.delegate = delegate;
  }

  public void search() {

    Prisoner[] population;
    Prisoner[] children;

    /* Initialize population */
    population = delegate.getInitialPopulation();
    for ( Prisoner p : population ) {
      delegate.handleNewPrisoner( p );
    }

    while ( delegate.shouldContinue() ) {


    }

  }

  private Prisoner[] createChildren( Prisoner[] population ) {
    LinkedList<Prisoner> children;
    int numChildren;

    numChildren = delegate.getParameters().getInteger( Param.NUM_CHILDREN );
    children = new LinkedList<>();

    for ( int i = 0; i < numChildren; i++ ) {
      try {
        children.add( createChild() );
      } catch ( StopRequestException e ) {
        break;
      }
    }

    return children.toArray( new Prisoner[ children.size() ] );
  }

  private Prisoner createChild() throws StopRequestException {

    if ( !delegate.shouldContinue() ) {
      throw new StopRequestException();
    }

    return new Prisoner();
  }

  private Prisoner[] combine( Prisoner[] pop1, Prisoner[] pop2 ) {
    Prisoner[] result = new Prisoner[ pop1.length + pop2.length ];

    System.arraycopy( pop1, 0, result, 0, pop1.length );
    System.arraycopy( pop2, 0, result, pop1.length, pop2.length );

    return result;
  }


  private Prisoner[] selectSurvivors( Prisoner[] surplus ) {

    return new Prisoner[ 0 ];
  }

}
