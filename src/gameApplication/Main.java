package gameApplication;

import gui.StagePane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create the StagePane which will allow the user to choose a level
            StagePane stagePane = new StagePane(primaryStage);

            // Set up the initial scene with StagePane
            Scene scene = new Scene(stagePane, 1360, 768);
            primaryStage.setTitle("Select Level");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading the game.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
