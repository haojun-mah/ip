# Audrey Test Suite

This directory contains comprehensive unit tests for the Audrey task management application.

## Directory Structure

```
test/java/audrey/
├── AllTests.java              # Main test suite runner
├── task/                      # Task-related tests
│   ├── TaskTest.java         # Base Task class tests
│   ├── TodoTest.java         # Todo task tests
│   ├── DeadlineTest.java     # Deadline task tests
│   ├── EventTest.java        # Event task tests
│   ├── ListTest.java         # Task list management tests
│   └── TaskTestSuite.java    # Task package test suite
├── command/                   # Command processing tests
│   ├── ParserTest.java       # Command parser tests
│   └── CommandTestSuite.java # Command package test suite
├── storage/                   # Data storage tests
│   ├── StorageTest.java      # File I/O and data persistence tests
│   └── StorageTestSuite.java # Storage package test suite
├── exception/                 # Exception handling tests
│   ├── ExceptionTest.java    # Custom exception tests
│   └── ExceptionTestSuite.java # Exception package test suite
└── ui/                       # User interface tests (placeholder)
```

## Test Coverage

### Task Package (`audrey.task`)
- **TaskTest.java**: Tests for base Task class functionality including marking, snoozing, and string representation
- **TodoTest.java**: Tests for Todo task creation and behavior
- **DeadlineTest.java**: Tests for Deadline task creation, date validation, and formatting
- **EventTest.java**: Tests for Event task creation, date range validation, and formatting
- **ListTest.java**: Tests for task list operations including CRUD operations, finding, and snoozing

### Command Package (`audrey.command`)
- **ParserTest.java**: Tests for command parsing, validation, error handling, and all supported commands

### Storage Package (`audrey.storage`)
- **StorageTest.java**: Tests for file loading, saving, parsing, backup/restore, and error handling

### Exception Package (`audrey.exception`)
- **ExceptionTest.java**: Tests for custom exception classes and their behavior

## Running Tests

### Run All Tests
```bash
./gradlew test
```

### Run Specific Test Packages
```bash
# Task-related tests only
./gradlew test --tests="audrey.task.*"

# Command-related tests only  
./gradlew test --tests="audrey.command.*"

# Storage-related tests only
./gradlew test --tests="audrey.storage.*"

# Exception-related tests only
./gradlew test --tests="audrey.exception.*"
```

### Run Individual Test Classes
```bash
# Run a specific test class
./gradlew test --tests="audrey.task.TaskTest"

# Run a specific test method
./gradlew test --tests="audrey.task.TaskTest.task_markTask_isCompleted"
```

## Test Statistics

- **Total Test Files**: 8
- **Total Test Methods**: 106+
- **Coverage Areas**: Task management, Command parsing, Data storage, Exception handling
- **Test Types**: Unit tests, Integration tests, Edge case tests

## Test Philosophy

The test suite follows these principles:

1. **Comprehensive Coverage**: Tests cover all major functionality and edge cases
2. **Realistic Scenarios**: Tests use real-world examples and data
3. **Error Handling**: Tests verify proper error handling and validation
4. **Maintainability**: Tests are well-organized and documented
5. **Reliability**: Tests provide consistent and reproducible results

## Contributing

When adding new tests:

1. Place tests in the appropriate package directory
2. Follow existing naming conventions (`ClassName + "Test.java"`)
3. Use descriptive test method names with the pattern `methodName_scenario_expectedResult`
4. Include proper documentation and assertions
5. Update the relevant test suite class if needed

## Notes

- All tests use JUnit 5 framework
- Tests are configured to run with Gradle
- Test data files are cleaned up automatically
- Temporary files use system temp directories to avoid conflicts