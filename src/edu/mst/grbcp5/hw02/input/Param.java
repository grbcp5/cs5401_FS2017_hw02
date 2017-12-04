package edu.mst.grbcp5.hw02.input;

public enum Param {
  ITERATIONS( "iterations" ),
  MEMORY_DEPTH( "memoryDepth" ),
  MAX_TREE_DEPTH( "maxTreeDepth" ),
  SHOULD_SEED_RANDOM( "shouldSeedRandom" ),
  RANDOM_SEED( "randomSeed" ),
  NUM_RUNS( "numRuns" ),
  CURRENT_RUN( "currentRun" ),
  LOG_FILE( "logFilePath" ),
  LOG_WIRTER( "logWriter" ),
  SOL_FILE( "solFilePath" ),
  SOL_WRITER( "solWriter" ),
  SEARCH_TYPE( "searchType" ),
  NUM_INDIVIDUALS( "numIndividuals" ),
  NUM_CHILDREN( "numChildren" ),
  POPULATION_SIZE( "populationSize" ),
  PARSIMONY_COEFFICIENT( "parsimonyCoeficient" ),
  FITNESS_EVALS( "fitnessEvals" ),
  CONVERGENCE_CRITERION( "convergenceCriterion" ),
  MUTATION_FREQUENCY( "mutationFrequency" ),
  PARENT_SELECTION( "parentSelection" ),
  SURVIVAL_SELECTION( "survivalSelection" ),
  OVER_SELECTION_CUT_OFF_PERCENTAGE( "overSelectionCutOffPercentage" ),
  TOURNAMENT_SIZE( "tournamentSize" ),

  /* Non-required parameters */
  DEPTH_PROPORTIONAL_INIT( "depthProportionalInit" );

  private final String identifier;

  Param( String identifier ) {
    this.identifier = identifier;
  }

  public String identifier() {
    return this.identifier;
  }

}
