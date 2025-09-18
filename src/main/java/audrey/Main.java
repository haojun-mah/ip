package audrey;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import audrey.ui.Audrey;

/** Orchestrator for JavaFX GUI */
public class Main extends Application {

    @FXML private ScrollPane scrollPane;
    @FXML private VBox dialogContainer;
    @FXML private TextField userInput;
    @FXML private Button sendButton;

    private Audrey audrey;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/Main.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            // Make window resizable with title and min size
            stage.setTitle("Audrey - Task Manager");
            stage.setMinWidth(350);
            stage.setMinHeight(400);
            stage.setResizable(true);

            stage.setScene(scene);
            stage.show();
            Main controller = fxmlLoader.getController();
            controller.setAudrey(new Audrey());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set audrey instance
     *
     * @param a audrey instance
     */
    public void setAudrey(Audrey a) {
        audrey = a;
        String welcomeMessage =
                "Hello! I am Audrey, your personal task manager.\n\n"
                        + "Quick Start:\n"
                        + "• Type 'list' to enable task mode\n"
                        + "• Type 'help' anytime to see all commands\n"
                        + "• Type 'bye' when you're done\n\n"
                        + "I'm here to help you stay organized!";
        dialogContainer.getChildren().addAll(DialogBox.getAudreyDialog(welcomeMessage));
    }

    /** Contains logic to process user input */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (audrey == null) {
            dialogContainer
                    .getChildren()
                    .addAll(DialogBox.getAudreyDialog("Audrey is not initialized yet!"));
            userInput.clear();
            return;
        }

        if ("bye".equalsIgnoreCase(input)) {
            audrey.instanceShutdown();
            dialogContainer.getChildren().addAll(DialogBox.getAudreyDialog("Goodnight!"));

            // Close the application after showing the goodbye message
            Platform.runLater(
                    () -> {
                        try {
                            Thread.sleep(1500);
                            Platform.exit();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
        } else {
            String response = audrey.getInstanceResponse(input);

            // Add user message
            dialogContainer.getChildren().add(DialogBox.getUserDialog(input));

            // Determine response type and add appropriate dialog
            if (isErrorMessage(response)) {
                dialogContainer.getChildren().add(DialogBox.getErrorDialog(response));
            } else if (isSuccessMessage(response)) {
                dialogContainer.getChildren().add(DialogBox.getSuccessDialog(response));
            } else {
                dialogContainer.getChildren().add(DialogBox.getAudreyDialog(response));
            }
        }
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
        userInput.clear();
    }

    /** Determines if a response message is an error */
    private boolean isErrorMessage(String response) {
        String lowerResponse = response.toLowerCase();
        return lowerResponse.contains("error")
                || lowerResponse.contains("invalid")
                || lowerResponse.contains("not found")
                || lowerResponse.contains("cannot")
                || lowerResponse.contains("failed")
                || lowerResponse.contains("wrong")
                || lowerResponse.contains("missing")
                || lowerResponse.contains("empty");
    }

    /** Determines if a response message is a success message */
    private boolean isSuccessMessage(String response) {
        String lowerResponse = response.toLowerCase();
        return lowerResponse.contains("added")
                || lowerResponse.contains("marked")
                || lowerResponse.contains("deleted")
                || lowerResponse.contains("snoozed")
                || lowerResponse.contains("unsnoozed")
                || lowerResponse.contains("completed")
                || lowerResponse.contains("success");
    }
}
