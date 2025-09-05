package audrey;

import java.io.IOException;

import audrey.ui.Audrey;
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

/**
 * Orchestrator for JavaFX GUI
 */
public class Main extends Application {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Audrey audrey;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/Main.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
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
     * @param a
     *            audrey instance
     */
    public void setAudrey(Audrey a) {
        audrey = a;
        dialogContainer.getChildren().addAll(DialogBox.getAudreyDialog(
                                        "Hello! I am Audrey, your personal bot. Tell what to do! \nEnter list to enable list mode!"));
    }

    /**
     * Contains logic to process user input
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (audrey == null) {
            dialogContainer.getChildren().addAll(DialogBox.getAudreyDialog("Audrey is not initialized yet!"));
            userInput.clear();
            return;
        }

        if ("bye".equalsIgnoreCase(input)) {
            audrey.instanceShutdown();
            dialogContainer.getChildren().addAll(DialogBox.getAudreyDialog("Goodnight!"));

            // Close the application after showing the goodbye message
            Platform.runLater(() -> {
                try {
                    Thread.sleep(1500);
                    Platform.exit();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        } else {
            String response = audrey.getInstanceResponse(input);
            dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input), DialogBox.getAudreyDialog(response));
        }
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
        userInput.clear();
    }
}
