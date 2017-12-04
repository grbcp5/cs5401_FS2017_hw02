package edu.mst.grbcp5.hw02.search.evolutionary;

import edu.mst.grbcp5.hw02.GRandom;
import edu.mst.grbcp5.hw02.input.Param;
import edu.mst.grbcp5.hw02.input.Parameters;
import edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma.*;
import edu.mst.grbcp5.hw02.tree.Tree;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EvolutionarySearchDelegate {

  private static final char loadingChars[] =
    { '/', '-', '\\', '|' };
  private int currentLoadingChar;

  private Parameters parameters;
  private boolean converged;
  private int numFitnessEvaluations;
  private int totalFitnessEvals;
  private Prisoner titForTat;
  private IterationRecord[] environment;
  private Prisoner currentBest;
  private int bestGeneration;
  private int currentGeneration;
  private int convergenceCriterion;
  private double fitnessSum;

  private boolean coevolution;

  public EvolutionarySearchDelegate(
    Parameters parameters
  ) {

    int memDepth;
    Random rnd;

    rnd = GRandom.getInstance();

    this.parameters = parameters;

    this.converged = false;
    this.numFitnessEvaluations = 0;
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

    this.coevolution = parameters.getBoolean(
      Param.COEVOLUTION,
      false
    );
    double fitnessSamplingPct = parameters.getDouble(
      Param.FITNESS_SAMPLING_PCT,
      ( 1.0 / parameters.getInteger( Param.POPULATION_SIZE ) )
    );

  }

  public Prisoner[] getInitialPopulation() {

    Prisoner[] result = Initialization.rampedHalfAndHalf(
      parameters.getInteger( Param.POPULATION_SIZE ),
      this
    );

    if ( this.coevolution ) {

      this.coevolution = false;

      for ( Prisoner p : result ) {
        this.handleNewPrisoner( p );
      }

      this.coevolution = true;

    }

    return result;
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

  public double coevolutionaryFiteness(
    Prisoner p,
    Prisoner[] population,
    Prisoner[] children
  ) {

    /* Local variables */
    int numOpponents;
    Prisoner[] opponents;
    double cumulativeFitness;
    int k;
    int iterations;
    IterationRecord[] env;

    /* Initialize */
    numOpponents =
      ( int ) ( ( parameters.getInteger( Param.POPULATION_SIZE )
        + parameters.getInteger( Param.NUM_CHILDREN ) - 1 )
        * parameters.getDouble( Param.FITNESS_SAMPLING_PCT ) );
    opponents = getRandomSampling(
      population,
      children,
      numOpponents,
      p
    );
    cumulativeFitness = 0.0;
    env = Arrays.copyOf( this.environment, this.environment.length );
    k = parameters.getInteger( Param.MEMORY_DEPTH );
    iterations = parameters.getInteger( Param.ITERATIONS );

    /* Calculate fitness */
    for ( Prisoner opponent : opponents ) {

      /* Don't count first 2k iterations*/
      IteratedPrisonerDilemmaSimulator.simulate(
        p,
        opponent,
        2 * k,
        env
      );

      /* Count last l-2k iterations */
      cumulativeFitness += IteratedPrisonerDilemmaSimulator.simulate(
        p,
        opponent,
        iterations - ( 2 * k ),
        env
      );

      /* Increment fitness counter */
      numFitnessEvaluations++;

    }

    /* Return average of random sampling */
    return cumulativeFitness / numOpponents;
  }

  private Prisoner[] getRandomSampling(
    Prisoner[] population,
    Prisoner[] children,
    int samplingSize,
    Prisoner exclude
  ) {

    /* Local variables */
    Prisoner result[];
    List< Prisoner > surplus;
    Random rnd;
    int rndIdx;
    Prisoner prisonerToAdd;

    /* Initialize */
    rnd = GRandom.getInstance();
    result = new Prisoner[ samplingSize ];
    surplus = new LinkedList<>(
      Arrays.asList(
        EvolutionarySearch.combine( population, children )
      )
    );

    for ( int i = 0; i < samplingSize; i++ ) {

      /* Select random prisoner from surplus */
      rndIdx = rnd.nextInt( surplus.size() );
      prisonerToAdd = surplus.remove( rndIdx );

      if ( prisonerToAdd == exclude ) {
        i--; // Add another prisoner if this prisoner was randomly chosen
      } else {
        result[ i ] = prisonerToAdd;
      }

    }

    return result;
  }

  public boolean shouldContinue() {

    if ( converged ) {
      System.out.println( "\nconverged: " + this.numFitnessEvaluations );
    }

    return !converged &&
      ( this.numFitnessEvaluations < this.totalFitnessEvals );
  }

  public void signalEndOfGeneration() {
    currentGeneration++;

    PrintWriter logWriter;

    if ( this.currentGeneration - this.bestGeneration >=
      this.convergenceCriterion ) {
      converged = true;
    }

    double averageFitness = fitnessSum / numFitnessEvaluations;
    logWriter = ( PrintWriter ) parameters.get( Param.LOG_WIRTER );
    logWriter.println(
      this.numFitnessEvaluations + "\t" +
        averageFitness + "\t" +
        currentBest.getFitness()
    );

  }

  public void signalAllChildrenCreated(
    Prisoner[] population,
    Prisoner[] children
  ) {

    /* Don't do anything if not doing a coevolutionary search */
    if ( !this.coevolution ) {
      return;
    }

    /* Local variables */
    int printDistance;
    int curRun;

    for ( int i = 0; i < children.length; i++ ) {

      /* Set fitness (Fitness eval counter updated in function)*/
      children[ i ].setFitness(
        this.coevolutionaryFiteness(
          children[ i ],
          population,
          children
        )
      );

      /* Update best */
      if ( currentBest == null || children[ i ].compareTo( currentBest ) > 0 ) {
        currentBest = children[ i ];
        bestGeneration = currentGeneration;
      }

      /* Keep console responsive */
      curRun = parameters.getInteger( Param.CURRENT_RUN );
      System.out.print(
        "\rRun: " + curRun + ": " + numFitnessEvaluations
          + "/" + totalFitnessEvals
      );
      System.out.flush();
    }

  }

  public boolean handleNewPrisoner( Prisoner p ) {

    int curRun;
    int printDistance;

    /* Not doing coevolutionary search */
    if ( !this.coevolution ) {

      /* Set fitness */
      p.setFitness( this.fitness( p, titForTat, environment ) );
      this.numFitnessEvaluations++;
      this.fitnessSum += p.getFitness();

      /* Update best */
      if ( currentBest == null || p.compareTo( currentBest ) > 0 ) {
        currentBest = p;
        bestGeneration = currentGeneration;
      }

      /* Keep console responsive */
      printDistance = parameters.getInteger( Param.NUM_CHILDREN );
      if ( numFitnessEvaluations % printDistance == 0 ) {
        curRun = parameters.getInteger( Param.CURRENT_RUN );
        currentLoadingChar = ( currentLoadingChar + 1 ) % loadingChars.length;
        System.out.print(
          "\rRun " + curRun + ": " + loadingChars[ currentLoadingChar ]
        );
        System.out.flush();
      }

    } // if not coevolutionary search

    return shouldContinue();
  }

  public Parameters getParameters() {
    return parameters;
  }

  public Prisoner getCurrentBest() {
    return this.currentBest;
  }

  public void exitingWithPopulation( Prisoner[] population ) {

    /* Local variables */
    PrintWriter logWriter;
    double curFitness;
    Prisoner bestSoFar;

    /* Initialize */
    logWriter = ( PrintWriter ) parameters.get( Param.LOG_WIRTER );
    bestSoFar = null;

    logWriter.println( "\nAbsolute Fitness:" );

    for ( int i = 0; i < population.length; i++ ) {
      curFitness = this.fitness(
        population[ i ],
        titForTat,
        this.environment
      );

      if( bestSoFar == null || bestSoFar.getFitness() < curFitness ) {
        population[ i ].setFitness( curFitness );
        bestSoFar = population[ i ];
      }

    }

    logWriter.println( bestSoFar.getFitness() );
    this.currentBest = bestSoFar;

  }

}
