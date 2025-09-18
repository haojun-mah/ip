package audrey.exception;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Test suite for all exception-related classes. Includes tests for custom exceptions and error
 * handling.
 */
@Suite
@SuiteDisplayName("Exception Package Test Suite")
@SelectClasses({ExceptionTest.class})
public class ExceptionTestSuite {
    // This class remains empty; it is used only as a holder for the above annotations
}
