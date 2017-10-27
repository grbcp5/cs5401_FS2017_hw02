package edu.mst.grbcp5.hw02.search.random;

import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.Individual;

public abstract class RandomSearch {

  protected Parameters parameters;

  public RandomSearch( Parameters p ) {
    this.parameters = p;
  }

  public abstract Individual getRandomIndividual();

}
