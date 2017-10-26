package edu.mst.grbcp5.test.input;

import edu.mst.grbcp5.hw02.input.ConfigFileReader;
import edu.mst.grbcp5.hw02.input.Parameters;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParametersTest {

  Parameters parametersUnderTest;

  @Before
  public void setUp() throws Exception {

    String configFilePath;
    ConfigFileReader cfr;

    configFilePath = "./config/test/parametersTest.xml";
    cfr = new ConfigFileReader( configFilePath );
    parametersUnderTest = new Parameters( cfr.getParameters() );

  }

  @Test
  public void getString() throws Exception {

    String fut_result;

    fut_result = parametersUnderTest.getString( "theString" );
    assertEquals( "theExpectedStringsValue", fut_result );

    fut_result = parametersUnderTest.getString( "null" );
    assertEquals( null, fut_result );

  }

  @Test
  public void getDouble() throws Exception {

    Double fut_result;

    fut_result = parametersUnderTest.getDouble( "theDouble" );
    assertEquals( new Double( 1.0 ), fut_result );

    fut_result = parametersUnderTest.getDouble( "null" );
    assertEquals( null, fut_result );

  }

  @Test
  public void getInteger() throws Exception {

    Integer fut_result;

    fut_result = parametersUnderTest.getInteger( "theInteger" );
    assertEquals( new Integer( 1 ), fut_result );

    fut_result = parametersUnderTest.getInteger( "null" );
    assertEquals( null, fut_result );

  }

  @Test
  public void getBoolean() throws Exception {

    Boolean fut_result;

    fut_result = parametersUnderTest.getBoolean( "theBoolean" );
    assertEquals( Boolean.TRUE, fut_result );

    fut_result = parametersUnderTest.getBoolean( "null" );
    assertEquals( null, fut_result );

  }

  @Test
  public void getLong() throws Exception {

    Long fut_result;

    fut_result = parametersUnderTest.getLong( "theLong" );
    assertEquals( new Long( 10021996 ), fut_result );

    fut_result = parametersUnderTest.getLong( "null" );
    assertEquals( null, fut_result );

  }

  @Test
  public void getList() throws Exception {

    String[] fut_result;
    String[] expectedResult = new String[]{
      "theFirstListItem",
      "theSecondListItem"
    };

    fut_result = parametersUnderTest.getList( "theList" );
    assertEquals( expectedResult, fut_result );

    fut_result = parametersUnderTest.getList( "null" );
    assertEquals( null, fut_result );

  }


}