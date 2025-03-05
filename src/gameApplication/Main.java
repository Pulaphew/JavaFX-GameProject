package gameApplication;

import gui.GameStartMenuPane;
import gui.StagePane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create the start menu pane
            GameStartMenuPane startMenu = new GameStartMenuPane(primaryStage, () -> {
                // When "Start Game" is clicked, switch to StagePane
                StagePane stagePane = new StagePane(primaryStage);
                Scene stageScene = new Scene(stagePane, 1360, 768);
                primaryStage.setScene(stageScene);
                primaryStage.setTitle("Select Level");
            });

            // Set up the initial scene with GameStartMenuPane
            Scene scene = new Scene(startMenu, 1360, 768);
            primaryStage.setTitle("Game Start Menu");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image(ClassLoader.getSystemResource("Credits.png").toString()));
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
