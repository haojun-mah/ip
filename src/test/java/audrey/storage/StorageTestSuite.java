package audrey.storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Test suite for all storage-related classes. Includes tests for Storage and file handling
 * operations.
 */
@Suite
@SuiteDisplayName("Storage Package Test Suite")
@SelectClasses({StorageTest.class})
public class StorageTestSuite {
    // This class remains empty; it is used only as a holder for the above annotations
}
