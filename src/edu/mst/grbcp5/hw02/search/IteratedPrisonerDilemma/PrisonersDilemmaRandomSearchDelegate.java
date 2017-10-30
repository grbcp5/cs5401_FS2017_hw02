package edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma;

import edu.mst.grbcp5.hw02.GRandom;
import edu.mst.grbcp5.hw02.input.Param;
import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.Individual;
import edu.mst.grbcp5.hw02.search.random.RandomSearchDelegate;
import edu.mst.grbcp5.hw02.tree.Tree;

import java.util.Random;

public class PrisonersDilemmaRandomSearchDelegate extends RandomSearchDelegate {

  private Prisoner titForTat;
  private IterationRecord[] environment;
  private int individualsRecieved;

  public PrisonersDilemmaRandomSearchDelegate( Parameters parameters ) {
    super( parameters );

    int memDepth;
    Random rnd;

    rnd = GRandom.getInstance();

    /* Create tit for tat stategy individual */
    this.titForTat = new Prisoner(
      new Tree< StrategyFunction >(
        new Terminal(
          Agent.OPPONENT,
          0
        )
      )
    );

    /* Create random initial enviroment */
    memDepth = parameters.getInteger( Param.MEMORY_DEPTH );
    environment = new IterationRecord[ memDepth ];
    for ( int i = 0; i < memDepth; i++ ) {
      environment[ i ] = new IterationRecord(
        ( rnd.nextInt( 2 ) == 1 ),
        ( rnd.nextInt( 2 ) == 1 )
      );
    }

    individualsRecieved = 0;

  }

  @Override
  public void handleNewIndividual( Individual i ) {

    individualsRecieved++;

    i.setFitness( this.fitness( i, this.titForTat, this.environment ) );

    System.out.println( "Individual " + individualsRecieved +
                          ": " + i.getFitness() );

    if ( currentBest == null || i.getFitness() > currentBest.getFitness() ) {
      System.out.println( "Found new best ^" );
      currentBest = i;
    }

  }

  @Override
  public double fitness(
    Individual i,
    Individual o,
    IterationRecord[] environment
  ) {
    Prisoner p = ( Prisoner ) i;
    Prisoner opponent = ( Prisoner ) o;
    int numYearsSepnt;
    int iterations;
    double pctFree;

    iterations = this.parameters.getInteger( Param.ITERATIONS );
    numYearsSepnt = IteratedPrisonerDilemmaSimulator.simulate(
      ( Prisoner ) i,
      ( Prisoner ) o,
      iterations,
      environment
    );

    pctFree = 1 - ( numYearsSepnt /
      ( double ) ( IterationRecord.OPP_DEFECTS * iterations ) );

    pctFree = ( double ) Math.round( pctFree * 100000d ) / 100000d;

    return pctFree;
  }

}
