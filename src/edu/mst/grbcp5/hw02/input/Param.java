package edu.mst.grbcp5.hw02.input;

public enum Param {
  ITERATIONS( "iterations" ),
  MEMORY_DEPTH( "memoryDepth" ),
  MAX_TREE_DEPTH( "maxTreeDepth" ),
  SHOULD_SEED_RANDOM( "shouldSeedRandom" ),
  RANDOM_SEED( "randomSeed" ),
  NUM_RUNS( "numRuns" ),
  LOG_FILE( "logFilePath" ),
  SOL_FILE( "solFilePath" ),

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
