package edu.mst.grbcp5.hw02.search.IteratedPrisonerDilemma;

public class IterationRecord {

  public static final int BOTH_DEFECT = 4;
  public static final int BOTH_COOPERATE = 2;
  public static final int OPP_DEFECTS = 5;
  public static final int OPP_COOPERATES = 0;

  public boolean prisonerDefected;
  public int prisonerTimeSpent;
  public boolean opponentDefected;
  public int opponentTimeSpent;

  public IterationRecord() {
    this.prisonerDefected = false;
    this.opponentDefected = false;
    this.prisonerTimeSpent = BOTH_COOPERATE;
    this.opponentTimeSpent = BOTH_COOPERATE;
  }

  public IterationRecord( boolean p, boolean o ) {
    this.prisonerDefected = p;
    this.opponentDefected = o;
    this.prisonerTimeSpent = getTimeSpent( p, o );
    this.opponentTimeSpent = getTimeSpent( o, p );
  }

  public void setValues( boolean p, boolean o ) {
    this.prisonerDefected = p;
    this.opponentDefected = o;
    this.prisonerTimeSpent = getTimeSpent( p, o );
    this.opponentTimeSpent = getTimeSpent( o, p );
  }

  public static int getTimeSpent( boolean p, boolean o ) {

    if ( p && o ) {
      return BOTH_DEFECT;
    }

    if ( !( p || o ) ) {
      return BOTH_COOPERATE;
    }

    if ( !p && o ) {
      return OPP_DEFECTS;
    }

    return OPP_COOPERATES;

  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder( 9 );

    sb.append( "P: " );
    sb.append( prisonerDefected ? "Defect" : "Cooperate" );
    sb.append( " (" );
    sb.append( prisonerTimeSpent );
    sb.append( ") O: " );
    sb.append( opponentDefected ? "Defect" : "Cooperate" );
    sb.append( " (" );
    sb.append( opponentTimeSpent );
    sb.append( ")" );

    return sb.toString().trim();
  }
}
