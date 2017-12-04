package edu.mst.grbcp5.hw02.search.evolutionary;

import edu.mst.grbcp5.hw02.GRandom;
import edu.mst.grbcp5.hw02.input.Param;
import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.*;
import edu.mst.grbcp5.hw02.tree.Tree;

import java.io.PrintWriter;
import java.util.Random;

public class EvolutionarySearchDelegate {

  private static final char loadingChars[] =
    { '/', '-', '\\', '|' };
  private int currentLoadingChar;

  private Parameters parameters;
  private boolean converged;
  private int numIndividuals;
  private int totalFitnessEvals;
  private Prisoner titForTat;
  private IterationRecord[] environment;
  private Prisoner currentBest;
  private int bestGeneration;
  private int currentGeneration;
  private int convergenceCriterion;
  private double fitnessSum;

  public EvolutionarySearchDelegate(
    Parameters parameters
  ) {

    int memDepth;
    Random rnd;

    rnd = GRandom.getInstance();

    this.parameters = parameters;

    this.converged = false;
    this.numIndividuals = 0;
    this.totalFitnessEvals = parameters.getInteger( Param.FITNESS_EVALS );
    currentBest = null;
    bestGeneration = 0;
    currentGeneration = 0;
    convergenceCriterion = parameters.getInteger( Param.CONVERGENCE_CRITERION );
    currentLoadingChar = 0;

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
    this.environment = new IterationRecord[ memDepth ];
    for ( int i = 0; i < memDepth; i++ ) {
      environment[ i ] = new IterationRecord(
        ( rnd.nextInt( 2 ) == 1 ),
        ( rnd.nextInt( 2 ) == 1 )
      );
    }
  }

  public Prisoner[] getInitialPopulation() {
    return Initialization.rampedHalfAndHalf(
      parameters.getInteger( Param.POPULATION_SIZE ),
      this
    );
  }

  public double fitness(
    Prisoner p,
    Prisoner o,
    IterationRecord[] environment
  ) {
    int numYearsSpent;
    int iterations;
    double pctFree;
    double parsimonyCoeffient;

    parsimonyCoeffient = parameters.getDouble(
      Param.PARSIMONY_COEFFICIENT,
      0.0
    );
    iterations = this.parameters.getInteger( Param.ITERATIONS );
    numYearsSpent = IteratedPrisonerDilemmaSimulator.simulate(
      p,
      o,
      iterations,
      environment
    );

    pctFree = 1 - ( numYearsSpent /
      ( double ) ( IterationRecord.OPP_DEFECTS * iterations ) );

    return pctFree - parsimonyCoeffient;
  }

  public boolean shouldContinue() {

    if ( converged ) {
      System.out.println( "\nconverged: " + this.numIndividuals );
    }

    return !converged && ( this.numIndividuals < this.totalFitnessEvals );
  }

  public void signalEndOfGeneration() {
    currentGeneration++;

    PrintWriter logWriter;

    if ( this.currentGeneration - this.bestGeneration >=
      this.convergenceCriterion ) {
      converged = true;
    }

    double averageFitness = fitnessSum / numIndividuals;
    logWriter = ( PrintWriter ) parameters.get( Param.LOG_WIRTER );
    logWriter.println(
      this.numIndividuals + "\t" +
        averageFitness + "\t" +
        currentBest.getFitness()
    );

  }

  public boolean handleNewPrisoner( Prisoner p ) {

    int curRun;
    int printDistance;

    this.numIndividuals++;

    p.setFitness( this.fitness( p, titForTat, environment ) );
    this.fitnessSum += p.getFitness();

    if ( currentBest == null || p.compareTo( currentBest ) > 0 ) {
      currentBest = p;
      bestGeneration = currentGeneration;
    }

    printDistance = parameters.getInteger( Param.NUM_CHILDREN );
    if ( numIndividuals % printDistance == 0 ) {
      curRun = parameters.getInteger( Param.CURRENT_RUN );
      currentLoadingChar = ( currentLoadingChar + 1 ) % loadingChars.length;
      System.out.print(
        "\rRun " + curRun + ": " + loadingChars[ currentLoadingChar ]
      );
      System.out.flush();
    }


    return shouldContinue();
  }

  public Parameters getParameters() {
    return parameters;
  }

  public Prisoner getCurrentBest() {
    return this.currentBest;
  }

}
