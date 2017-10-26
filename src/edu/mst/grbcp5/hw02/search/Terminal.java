package edu.mst.grbcp5.hw02.search;

public class Terminal extends StrategyFunction {

  private Agent agent;
  private Integer time;

  public Terminal( Agent agent, Integer time ) {
    super( StrategyFunctionType.TERMINAL );

    this.agent = agent;
    this.time = time;
  }

  public Agent getAgent() {
    return agent;
  }

  public void setAgent( Agent agent ) {
    this.agent = agent;
  }

  public Integer getTime() {
    return time;
  }

  public void setTime( Integer time ) {
    this.time = time;
  }

}
