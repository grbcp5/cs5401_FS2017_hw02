package edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma;

import edu.mst.grbcp5.hw02.search.Individual;
import edu.mst.grbcp5.hw02.tree.Tree;

public class Prisoner extends Individual {

  public Prisoner() {
    super();
  }

  public Prisoner( Tree< StrategyFunction > t ) {
    super( t );
  }

  @Override
  public Tree<StrategyFunction> getStrategy() {
    return super.getStrategy();
  }

  @Override
  public String toString() {
    return this.strategy.toString();
  }
}
