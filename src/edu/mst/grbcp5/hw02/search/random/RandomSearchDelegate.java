package edu.mst.grbcp5.hw02.search.random;

import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.Individual;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.IterationRecord;

public abstract class RandomSearchDelegate {

  protected Individual currentBest;
  protected Parameters parameters;

  public RandomSearchDelegate( Parameters parameters ) {
    this.currentBest = null;
    this.parameters = parameters;
  }

  public abstract void handleNewIndividual( Individual i );

  public abstract double fitness(
    Individual i,
    Individual o,
    IterationRecord[] enviroment
  );

  public Individual getCurrentBest() {
    return currentBest;
  }

}
