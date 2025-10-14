package audrey;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Test suite to run all tests in the Audrey application. This suite will automatically discover and
 * run all tests in the audrey package and its subpackages.
 */
@Suite
@SuiteDisplayName("Audrey Application Test Suite")
@SelectPackages({
    "audrey.task",
    "audrey.command",
    "audrey.storage",
    "audrey.exception",
    "audrey.ui"
})
public class AllTests {
    // This class remains empty; it is used only as a holder for the above annotations
}
