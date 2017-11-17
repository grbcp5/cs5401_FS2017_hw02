package edu.mst.grbcp5.test.input;

import edu.mst.grbcp5.hw02.input.ConfigFileReader;
import edu.mst.grbcp5.hw02.input.RequiredTagNotFoundException;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class ConfigFileReaderTest {

  @Test
  public void getParameters() throws Exception {

    /* Local variables */
    GetParametersTestCase[] testCases;
    String[] requiredTags;
    ConfigFileReader cfr;
    Map< String, Object > fut_result;

    /* Initialize local variables */
    requiredTags = new String[]{
      "iterations",
      "memoryDepth",
      "maxTreeDepth",
      "shouldSeedRandom",
      "numRuns",
      "logFilePath",
      "solFilePath"
    };

    /* Initialize test casses */
    testCases = new GetParametersTestCase[]{
      new GetParametersTestCase(
        "valid1 no check",
        "./config/test/valid1.xml",
        null,
        false
      ), new GetParametersTestCase(
      "tooFew no check",
      "./config/test/tooFew.xml",
      null,
      false
    ),
      new GetParametersTestCase(
        "valid1",
        "./config/test/valid1.xml",
        requiredTags,
        false
      ),
      new GetParametersTestCase(
        "extra",
        "./config/test/extra.xml",
        requiredTags,
        false
      ),
      new GetParametersTestCase(
        "tooFew",
        "./config/test/tooFew.xml",
        requiredTags,
        true
      )
    };

    /* Execute each test case */
    for ( GetParametersTestCase testCase : testCases ) {

      /* Create new config file reader */
      try {
        cfr = new ConfigFileReader( testCase.configFilePath );
      } catch ( Exception e ) {
        e.printStackTrace();

        System.out.print( testCase.testName + ".construction: " );
        assertEquals( true, false );

        return;
      }

      try {

        fut_result = cfr.getParameters( testCase.requiredPrameters );

        System.out.print( testCase.testName + ".expectingError: " );
        assertEquals( testCase.expectingError, false );
        System.out.println( "passed" );

        System.out.println( "\n" + fut_result );

      } catch ( RequiredTagNotFoundException rtnfe ) {

        System.out.println( testCase.testName + ".expectingError: " );
        assertEquals( testCase.expectingError, true );
        System.out.println( "passed" );

      }

    }


  }


}

class GetParametersTestCase {

  /* Instance variables */
  final String configFilePath;
  final String testName;
  final String[] requiredPrameters;
  final boolean expectingError;

  GetParametersTestCase(
    String testName,
    String configFilePath,
    String[] requiredPrameters,
    boolean expectingError
  ) {
    this.testName = testName;
    this.configFilePath = configFilePath;
    this.requiredPrameters = requiredPrameters;
    this.expectingError = expectingError;
  }

}