package gameApplication;

import gui.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create game components
            PlayerPane playerPane = new PlayerPane();
            EnemyPane enemyPane = new EnemyPane();
            GameMenuBattlePane gameMenuBattlePane = new GameMenuBattlePane();

            // Create GameBattlePane (which includes the background)
            GameBattlePane gameBattlePane = new GameBattlePane(playerPane, enemyPane, gameMenuBattlePane);

            // Root pane
            Pane root = new Pane();
            root.getChildren().add(gameBattlePane);

            // Set up the scene
            Scene scene = new Scene(root, 1360, 768);
            primaryStage.setTitle("Battle System");
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
