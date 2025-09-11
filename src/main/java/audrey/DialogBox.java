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

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face and a label containing text from
 * the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private static final Image USER_IMAGE = new Image(DialogBox.class.getResourceAsStream("/images/userimage.jpg"));
    private static final Image AUDREY_IMAGE = new Image(
                                    DialogBox.class.getResourceAsStream("/images/audreyimage.jpeg"));

    private DialogBox(String text, Image img) {
        // Assert: Text parameter should not be null
        assert text != null : "Dialog text cannot be null";
        // Assert: Image parameter should not be null
        assert img != null : "Dialog image cannot be null";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
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
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
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

        DialogBox userDialog = new DialogBox(text, USER_IMAGE);

        // Assert: Created dialog should not be null
        assert userDialog != null : "Created user dialog should not be null";

        return userDialog;
    }

    /**
     * Creates audrey dialogue
     *
     * @param text user text
     * @return dialogbox instance
     */
    public static DialogBox getAudreyDialog(String text) {
        var db = new DialogBox(text, AUDREY_IMAGE);
        db.flip();
        return db;
    }
}
