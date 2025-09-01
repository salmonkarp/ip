package taskmanager;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Main class used for JavaFX Application, acting
 * as the root for the Stage
 */
public class Main extends Application {

    private TaskManager taskManager = new TaskManager();

    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/robotIcon.png")));
            stage.setTitle("TaskManager");
            Font.loadFont(Main.class.getResource("/fonts/JetBrainsMono.ttf").toExternalForm(), 14);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setTaskManager(taskManager);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
