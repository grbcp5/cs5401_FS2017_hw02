package edu.mst.grbcp5.hw02.search.evolutionary;

import edu.mst.grbcp5.hw02.input.Param;
import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.Prisoner;

public class EvolutionarySearchDelegate {

  private Parameters parameters;

  public EvolutionarySearchDelegate (
    Parameters parameters
  ) {
    this.parameters = parameters;
  }

  public Prisoner[] getInitialPopulation() {
    return Initialization.rampedHalfAndHalf(
      parameters.getInteger( Param.POPULATION_SIZE ),
      this
    );
  }

  public boolean shouldContinue() {
    return false;
  }

  public boolean handleNewPrisoner( Prisoner p ) {
    return shouldContinue();
  }

  public Parameters getParameters() {
    return parameters;
  }

}
