package edu.mst.grbcp5.hw02.search.evolutionary;

import edu.mst.grbcp5.hw02.input.Param;
import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.Prisoner;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.PrisonersDilemmaRandomSearch;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.PrisonersDilemmaRandomSearchDelegate;

import java.util.HashMap;
import java.util.Map;

public class Initialization {

  public static Prisoner[] rampedHalfAndHalf(
    int count,
    EvolutionarySearchDelegate delegate
  ) {

    /* Local variables */
    Prisoner[] result;
    PrisonersDilemmaRandomSearch randomSearch;
    Map<String, Object> parameters;

    /* Initialize local varaibles */
    result = new Prisoner[ count ];
    parameters = new HashMap<>();
    parameters.put(
      Param.MAX_TREE_DEPTH.identifier(),
      delegate.getParameters().getInteger( Param.MAX_TREE_DEPTH )
    );
    parameters.put(
      Param.MEMORY_DEPTH.identifier(),
      delegate.getParameters().getInteger( Param.MEMORY_DEPTH )
    );
    randomSearch = new PrisonersDilemmaRandomSearch(
      new PrisonersDilemmaRandomSearchDelegate(
        new Parameters(
          parameters
        )
      )
    );

    /* Half grow */
    for ( int i = 0; i < count / 2; i++ ) {
      result[ i ] = (Prisoner) randomSearch.getRandomIndividual();
    }

    /* Half full */
    for ( int i = count / 2; i < count; i++ ) {
      result[ i ] = randomSearch.getFullIndividual();
    }

    return result;
  }

}
