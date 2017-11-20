package edu.mst.grbcp5.hw02.search.evolutionary;

import edu.mst.grbcp5.hw02.GRandom;
import edu.mst.grbcp5.hw02.input.Param;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.Prisoner;

import java.util.LinkedList;
import java.util.Random;

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
    delegate.signalEndOfGeneration();

    while ( delegate.shouldContinue() ) {

      children = createChildren( population );
      population = selectSurvivors( combine( population, children ) );
      delegate.signalEndOfGeneration();

    }

  }

  private Prisoner[] createChildren( Prisoner[] population ) {

    LinkedList< Prisoner > children;
    int numChildren;
    Prisoner child;

    numChildren = delegate.getParameters().getInteger( Param.NUM_CHILDREN );
    children = new LinkedList<>();

    for ( int i = 0; i < numChildren; i++ ) {
      try {
        child = createChild( population );
        delegate.handleNewPrisoner( child );
        children.add( child );
      } catch ( StopRequestException e ) {
        break;
      }
    }

    return children.toArray( new Prisoner[ children.size() ] );
  }

  private Prisoner createChild( Prisoner[] pop ) throws StopRequestException {

    if ( !delegate.shouldContinue() ) {
      throw new StopRequestException();
    }

    double mutationFrequency;
    Random rnd;
    Prisoner parents[];
    String parentSelectionMethod;
    double cutOffPercentage;
    Prisoner child;

    rnd = GRandom.getInstance();
    parents = new Prisoner[ 2 ];
    mutationFrequency = delegate.getParameters().getDouble(
      Param.MUTATION_FREQUENCY,
      0.5
    );
    parentSelectionMethod = delegate.getParameters().getString(
      Param.PARENT_SELECTION
    );
    child = null;

    /* If mutate */
    if ( rnd.nextDouble() < mutationFrequency ) {

      /* Determine parent selection method */
      if ( parentSelectionMethod.equalsIgnoreCase( "overSelection" ) ) {

        cutOffPercentage = delegate.getParameters().getDouble(
          Param.OVER_SELECTION_CUT_OFF_PERCENTAGE,
          0.75
        );

        /* Select Parent */
        parents = Selection.overSelection( pop, 1, cutOffPercentage );

      } else if ( parentSelectionMethod.equalsIgnoreCase(
        "fitnessProportionalSelection" ) ) {

        /* Select Parent */
        parents = Selection.fitnessProportional( pop, 1 );

      }

      /* Create child from parent */
      child = Manipulation.subTreeMutation(
        parents[ 0 ],
        delegate.getParameters().getInteger( Param.MAX_TREE_DEPTH ),
        delegate.getParameters().getInteger( Param.MEMORY_DEPTH )
      );

    /* If recombination */
    } else {

      /* Determine parent selection method */
      if ( parentSelectionMethod.equalsIgnoreCase( "overSelection" ) ) {

        cutOffPercentage = delegate.getParameters().getDouble(
          Param.OVER_SELECTION_CUT_OFF_PERCENTAGE,
          0.75
        );

        /* Select Parents */
        parents = Selection.overSelection( pop, 2, cutOffPercentage );

      } else if ( parentSelectionMethod.equalsIgnoreCase(
        "fitnessProportionalSelection" ) ) {

        /* Select Parents */
        parents = Selection.fitnessProportional( pop, 2 );

      }

      /* Create child from parents */
      child = Manipulation.subTreeCrossover(
        parents[ 0 ],
        parents[ 1 ],
        delegate.getParameters().getInteger( Param.MAX_TREE_DEPTH )
      );

    }

    return child;
  }

  private Prisoner[] combine( Prisoner[] pop1, Prisoner[] pop2 ) {
    Prisoner[] result = new Prisoner[ pop1.length + pop2.length ];

    System.arraycopy( pop1, 0, result, 0, pop1.length );
    System.arraycopy( pop2, 0, result, pop1.length, pop2.length );

    return result;
  }


  private Prisoner[] selectSurvivors( Prisoner[] surplus ) {

    String survivalSelectionMethod;

    survivalSelectionMethod = delegate.getParameters().getString(
      Param.SURVIVAL_SELECTION
    );

    if ( survivalSelectionMethod.equalsIgnoreCase( "Truncation" ) ) {

      return Selection.truncation(
        surplus,
        delegate.getParameters().getInteger( Param.POPULATION_SIZE )
      );

    } else if ( survivalSelectionMethod.equalsIgnoreCase(
      "kTournamentSelection" ) ) {

      return Selection.kTournamentSelection(
        surplus,
        delegate.getParameters().getInteger( Param.POPULATION_SIZE ),
        delegate.getParameters().getInteger( Param.TOURNAMENT_SIZE ),
        false
      );

    }

    return null;
  }

}
