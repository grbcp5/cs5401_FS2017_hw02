package edu.mst.grbcp5.hw02.search.random;

import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.Individual;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.Prisoner;

public abstract class RandomSearch {

  protected Parameters parameters;
  protected RandomSearchDelegate delegate;

  public RandomSearch(
    Parameters p,
    RandomSearchDelegate delegate
  ) {
    this.parameters = p;
    this.delegate = delegate;
  }

  public abstract Prisoner getRandomIndividual();

  public void getRandomIndividuals( int n ) {

    for ( int i = 0; i < n; i++ ) {
      delegate.handleNewIndividual( this.getRandomIndividual() );
    }

  }

}
