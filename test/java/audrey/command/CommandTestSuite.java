package audrey.command;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/** Test suite for all command-related classes. Includes tests for Parser and Command classes. */
@Suite
@SuiteDisplayName("Command Package Test Suite")
@SelectClasses({ParserTest.class})
public class CommandTestSuite {
    // This class remains empty; it is used only as a holder for the above annotations
}
