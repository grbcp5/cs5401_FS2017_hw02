package edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma;

import edu.mst.grbcp5.hw02.GRandom;
import edu.mst.grbcp5.hw02.input.Param;
import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.Individual;
import edu.mst.grbcp5.hw02.search.random.RandomSearchDelegate;
import edu.mst.grbcp5.hw02.tree.Tree;

import java.io.PrintWriter;
import java.util.Random;

public class PrisonersDilemmaRandomSearchDelegate extends RandomSearchDelegate {

  private Prisoner titForTat;
  private Parameters parameters;
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
          1
        )
      )
    );

    /* Create random initial environment */
    memDepth = parameters.getInteger( Param.MEMORY_DEPTH );
    environment = new IterationRecord[ memDepth ];
    for ( int i = 0; i < memDepth; i++ ) {
      environment[ i ] = new IterationRecord(
        ( rnd.nextInt( 2 ) == 1 ),
        ( rnd.nextInt( 2 ) == 1 )
      );
    }

    individualsRecieved = 0;


    this.parameters = parameters;
  }

  @Override
  public void handleNewIndividual( Individual ind ) {

    int numIndividuals;
    int numProgressBarSteps;
    int progressBarStepSize;
    int stepsCompleted;
    int curRun;
    PrintWriter logWriter;

    individualsRecieved++;
    ind.setFitness( this.fitness( ind, this.titForTat, this.environment ) );

    numIndividuals = parameters.getInteger( Param.NUM_INDIVIDUALS );
    numProgressBarSteps = 20;
    progressBarStepSize = numIndividuals / numProgressBarSteps;

    if ( individualsRecieved % progressBarStepSize == 0 ) {

      stepsCompleted = ( individualsRecieved / progressBarStepSize );
      curRun = parameters.getInteger( Param.CURRENT_RUN );

      System.out.print( "\rRun " + curRun + ": [" );
      for ( int i = 0; i < stepsCompleted; i++ ) {
        System.out.print( "=" );
      }
      for ( int i = 0; i < numProgressBarSteps - stepsCompleted; i++ ) {
        System.out.print( " " );
      }
      System.out.print( "]" );

    }

    if ( currentBest == null || ind.getFitness() > currentBest.getFitness() ) {
      currentBest = ind;

      logWriter = ( PrintWriter ) parameters.get( Param.LOG_WIRTER );
      logWriter.println(
        individualsRecieved + "\t" +
          ind.getFitness()
      );
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
    int numYearsSpent;
    int iterations;
    double pctFree;

    iterations = this.parameters.getInteger( Param.ITERATIONS );
    numYearsSpent = IteratedPrisonerDilemmaSimulator.simulate(
      ( Prisoner ) i,
      ( Prisoner ) o,
      iterations,
      environment
    );

    pctFree = 1 - ( numYearsSpent /
      ( double ) ( IterationRecord.OPP_DEFECTS * iterations ) );

    //pctFree = ( double ) Math.round( pctFree * 100000d ) / 100000d;

    return pctFree;
  }

  public Parameters getParameters() {
    return this.parameters;
  }

}
