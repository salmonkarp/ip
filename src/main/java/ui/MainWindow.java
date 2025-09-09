package ui;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import taskmanager.TaskManager;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private TaskManager taskManager;

    private final Image userImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream("/images/userIcon.png")));
    private final Image botImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream("/images/robotIcon.png")));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the TaskManager instance */
    public void setTaskManager(TaskManager d) {
        taskManager = d;
        String greetingMessage = "Hi, I'm TaskManager!\n";
        greetingMessage += taskManager.getResponse("list");

        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(greetingMessage, botImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = taskManager.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, botImage)
        );
        userInput.clear();

        if (input.equals("bye")) {
            triggerExit();
        }
    }

    private void triggerExit() {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                Platform.exit();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
