package gameApplication;

import entity.Enemy;
import entity.Narong;
import entity.Player;
import gamelogic.GameLogic;
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
            Player player = new Player(100, 2);
            Enemy enemy = new Narong(100, 5);

            PlayerPane playerPane = new PlayerPane(player);
            EnemyPane enemyPane = new EnemyPane(enemy);
            GameMenuBattlePane gameMenuBattlePane = new GameMenuBattlePane(player, enemy, playerPane, enemyPane);

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
            primaryStage.show();  // Scene is now fully initialized

            // Initialize GameLogic AFTER primaryStage.show()
            GameLogic gameLogic = new GameLogic(player, enemy, playerPane, enemyPane, gameBattlePane);
            gameMenuBattlePane.setGameLogic(gameLogic);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading the game.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}