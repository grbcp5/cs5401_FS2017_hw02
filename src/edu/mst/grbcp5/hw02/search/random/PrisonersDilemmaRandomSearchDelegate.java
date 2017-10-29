package edu.mst.grbcp5.hw02.search.random;

import edu.mst.grbcp5.hw02.search.Individual;
import edu.mst.grbcp5.hw02.search.Prisoner;

public class PrisonersDilemmaRandomSearchDelegate extends RandomSearchDelegate {

  @Override
  public void handleNewIndividual( Individual i ) {

    i.setFitness( this.fitness( i ) );

    if ( currentBest == null || i.getFitness() > currentBest.getFitness() ) {
      this.currentBest = i;
    }

  }

  @Override
  public double fitness( Individual i ) {
    Prisoner p = ( Prisoner ) i;

    return 0;
  }

}
