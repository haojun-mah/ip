package audrey;

import java.io.IOException;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class DialogBox extends HBox {
    private static final Image USER_IMAGE =
            new Image(DialogBox.class.getResourceAsStream("/images/userimage.jpg"));
    private static final Image AUDREY_IMAGE =
            new Image(DialogBox.class.getResourceAsStream("/images/audreyimage.jpeg"));

    @FXML private Label dialog;
    @FXML private ImageView displayPicture;

    public enum MessageType {
        USER,
        AUDREY,
        ERROR,
        SUCCESS
    }

    private DialogBox(String text, Image img, MessageType type) {
        // Assert: Text parameter should not be null
        assert text != null : "Dialog text cannot be null";
        // Assert: Image parameter should not be null
        assert img != null : "Dialog image cannot be null";

        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assert: FXML components should be loaded
        assert dialog != null : "Dialog label should be loaded from FXML";
        assert displayPicture != null : "Display picture should be loaded from FXML";

        dialog.setText(text);
        displayPicture.setImage(img);
        styleDialogBox(type);
    }

    /** Applies styling based on message type for improved UX */
    private void styleDialogBox(MessageType type) {
        // Make profile pictures smaller and circular
        displayPicture.setFitHeight(45);
        displayPicture.setFitWidth(45);

        // Create circular clipping for profile pictures
        Circle clip = new Circle();
        clip.setCenterX(22.5);
        clip.setCenterY(22.5);
        clip.setRadius(22.5);
        displayPicture.setClip(clip);

        switch (type) {
            case USER:
                // User messages: Light blue background, right aligned
                dialog.setStyle(
                        "-fx-background-color: #E3F2FD; -fx-background-radius: 18; "
                                + "-fx-padding: 12 16 12 16; -fx-font-size: 13px; "
                                + "-fx-text-fill: #1976D2; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
                this.setAlignment(Pos.TOP_RIGHT);
                break;
            case AUDREY:
                // Audrey messages: Soft purple/pink background, left aligned
                dialog.setStyle(
                        "-fx-background-color: #F3E5F5; -fx-background-radius: 18; "
                                + "-fx-padding: 12 16 12 16; -fx-font-size: 13px; "
                                + "-fx-text-fill: #7B1FA2; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
                this.setAlignment(Pos.TOP_LEFT);
                break;
            case ERROR:
                // Error messages: Light red background with red text
                dialog.setStyle(
                        "-fx-background-color: #FFEBEE; -fx-background-radius: 18; "
                                + "-fx-padding: 12 16 12 16; -fx-font-size: 13px; "
                                + "-fx-text-fill: #D32F2F; -fx-font-family: 'Segoe UI', Arial, sans-serif; "
                                + "-fx-border-color: #F44336; -fx-border-width: 1; -fx-border-radius: 18;");
                this.setAlignment(Pos.TOP_LEFT);
                break;
            case SUCCESS:
                // Success messages: Light green background
                dialog.setStyle(
                        "-fx-background-color: #E8F5E8; -fx-background-radius: 18; "
                                + "-fx-padding: 12 16 12 16; -fx-font-size: 13px; "
                                + "-fx-text-fill: #388E3C; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
                this.setAlignment(Pos.TOP_LEFT);
                break;
        }

        // Add some spacing between messages
        this.setSpacing(8);
        this.setMaxWidth(350); // Limit width for better readability
    }

    /** Flips the dialog box such that the ImageView is on the left and text on the right. */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates user dialog
     *
     * @param text user text
     * @return dialogbox instance
     */
    public static DialogBox getUserDialog(String text) {
        // Assert: User image should be loaded
        assert USER_IMAGE != null : "User image should be loaded";
        // Assert: Text should not be null
        assert text != null : "User dialog text cannot be null";

        DialogBox userDialog = new DialogBox(text, USER_IMAGE, MessageType.USER);

        // Assert: Created dialog should not be null
        assert userDialog != null : "Created user dialog should not be null";

        return userDialog;
    }

    /**
     * Creates audrey dialogue
     *
     * @param text audrey response text
     * @return dialogbox instance
     */
    public static DialogBox getAudreyDialog(String text) {
        var db = new DialogBox(text, AUDREY_IMAGE, MessageType.AUDREY);
        db.flip();
        return db;
    }

    /**
     * Creates error dialogue for highlighting errors
     *
     * @param text error message text
     * @return dialogbox instance
     */
    public static DialogBox getErrorDialog(String text) {
        var db = new DialogBox(text, AUDREY_IMAGE, MessageType.ERROR);
        db.flip();
        return db;
    }

    /**
     * Creates success dialogue for highlighting successful operations
     *
     * @param text success message text
     * @return dialogbox instance
     */
    public static DialogBox getSuccessDialog(String text) {
        var db = new DialogBox(text, AUDREY_IMAGE, MessageType.SUCCESS);
        db.flip();
        return db;
    }
}
