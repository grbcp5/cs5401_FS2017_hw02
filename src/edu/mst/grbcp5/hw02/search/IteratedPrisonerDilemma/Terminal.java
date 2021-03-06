package edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma;

public class Terminal extends StrategyFunction {

  private final Agent agent;
  private final Integer time;

  public Terminal( Agent agent, Integer time ) {
    super( StrategyFunctionType.TERMINAL );

    if ( time <= 0 ) {
      throw new IllegalArgumentException( "Time must be positive" );
    }

    this.agent = agent;
    this.time = time;
  }

  public Agent getAgent() {
    return agent;
  }

  public Integer getTime() {
    return time;
  }

  @Override
  public String toString() {
    switch ( this.agent ) {
      case OPPONENT:
        return "O" + this.time;
      case PRISONER:
        return "P" + this.time;
    }

    return "";
  }

  @Override
  public boolean equals( Object o ) {
    if ( this == o ) return true;
    if ( !( o instanceof Terminal ) ) return false;

    Terminal terminal = ( Terminal ) o;

    if ( getAgent() != terminal.getAgent() ) return false;
    return getTime() != null ? getTime().equals( terminal.getTime() ) : terminal.getTime() == null;
  }

  @Override
  public int hashCode() {
    int result = getAgent() != null ? getAgent().hashCode() : 0;
    result = 31 * result + ( getTime() != null ? getTime().hashCode() : 0 );
    return result;
  }

}
