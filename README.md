# Audrey Task Manager

<p align="center">
  <img src="src/main/resources/images/audreyimage.jpeg" alt="Audrey" width="200"/>
</p>

This is a desktop task management application optimized for use via a Command Line Interface (CLI) while also providing the benefits of a Graphical User Interface (GUI). It's named _Audrey_ and helps you manage your tasks efficiently. Given below are instructions on how to set it up and use it.

## Setting up in Intellij

Prerequisites: JDK 17, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 17** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/audrey/Launcher.java` file, right-click it, and choose `Run Launcher.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see the Audrey GUI application window appear.

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Running the Application

You can run Audrey in several ways:

### Using Gradle
```bash
./gradlew run
```

### Using the JAR file
```bash
java -jar audrey.jar
```

### Using IntelliJ
Right-click on `src/main/java/audrey/Launcher.java` and select "Run Launcher.main()".

## Features

Audrey supports the following task management features:

- **Todo tasks**: Simple tasks without dates
- **Deadline tasks**: Tasks with specific due dates
- **Event tasks**: Tasks with start and end dates
- **Task marking**: Mark tasks as completed or incomplete
- **Task deletion**: Remove tasks permanently
- **Task finding**: Search for tasks by keywords
- **Task snoozing**: Temporarily hide tasks until a specific date or forever
- **Data persistence**: Automatic saving and loading of tasks

## User Guide

For detailed usage instructions, see the [User Guide](docs/UserGuide.md).

## Testing

The project includes comprehensive unit tests located in the `test/` directory:

```bash
# Run all tests
./gradlew test

# Run specific test packages
./gradlew test --tests="audrey.task.*"
./gradlew test --tests="audrey.command.*"
./gradlew test --tests="audrey.storage.*"
./gradlew test --tests="audrey.exception.*"
```

For more information about the test structure, see [test/README.md](test/README.md).
