package gamelogic;

import entity.Enemy;
import entity.Natchan;
import entity.Narang;
import entity.Pta;
import entity.Player;
import gui.EnemyPane;
import gui.GameBattlePane;
import gui.GameMenuBattlePane;
import gui.PlayerPane;
import gui.StagePane;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SceneController {
	
	private static Player player ;
	private static Enemy enemy ;

    public static void switchToGameScene(Stage primaryStage, String enemyType) {
        try {
            // Create the player
            player = new Player(5, 1000);

            // Determine which enemy to use based on the button click
            switch (enemyType) {
                case "Natchan":  
                    enemy = new Natchan(100, 2);
                    break;
                case "Narang":
                    enemy = new Narang(100, 2);
                    break;
                case "Pta":
                    enemy = new Pta(100, 2);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown enemy type");
            }

            // Set up the game components
            PlayerPane playerPane = new PlayerPane(player);
            EnemyPane enemyPane = new EnemyPane(enemy);
            GameMenuBattlePane gameMenuBattlePane = new GameMenuBattlePane(player, enemy, playerPane, enemyPane);

            // Create the GameBattlePane (includes the background)
            GameBattlePane gameBattlePane = new GameBattlePane(playerPane, enemyPane, gameMenuBattlePane);
            
            // Root pane for the game scene
            Scene gameScene = new Scene(gameBattlePane, 1360, 768);
            primaryStage.setScene(gameScene);
            primaryStage.setTitle("Battle System");

            // Initialize the game logic
            GameLogic gameLogic = new GameLogic(primaryStage,player, enemy, playerPane, enemyPane, gameBattlePane);
            gameMenuBattlePane.setGameLogic(gameLogic);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading the game.");
        }
    }
    
    public static void showEndGameScreen(GameBattlePane gameBattlePane, Stage primaryStage, boolean playerWins) {
        
    	Scene scene = gameBattlePane.getScene() ;
    	if(scene != null) scene.setOnMouseClicked(null);
    	
    	// Background overlay (dim screen)
        Rectangle background = new Rectangle(1360, 400);
        background.setFill(Color.BLACK);
        background.setOpacity(0.5);

        // End game container
        VBox endGamePane = new VBox(20); // 20px spacing
        endGamePane.setPrefSize(600, 300);
        endGamePane.setAlignment(Pos.CENTER); // Center contents

        // Victory or Defeat message
        String message = playerWins ? "You Win!" : "You Lost!";
        Text endGameText = new Text(message);
        endGameText.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-fill: white;");

        // Return Button
        Button returnButton = new Button("Return To Stage");
        returnButton.setPrefSize(230, 75);
        returnButton.setStyle("-fx-font-size: 20px;");
        returnButton.setOnAction(e -> primaryStage.setScene(new Scene(new StagePane(primaryStage), 1360, 768)));

        // Add components to the VBox
        endGamePane.getChildren().addAll(endGameText, returnButton);

        // StackPane to center VBox in the scene
        StackPane overlayPane = new StackPane();
        overlayPane.setPrefSize(1360, 768);
        overlayPane.getChildren().addAll(background, endGamePane);
        overlayPane.setAlignment(Pos.CENTER); // Centering

        // Add overlay to GameBattlePane
        gameBattlePane.getChildren().add(overlayPane);
    }
    
    public static void showSurrenderConfirmation(Stage primaryStage, GameBattlePane gameBattlePane) {
    	Scene scene = gameBattlePane.getScene() ;
    	if(scene != null) scene.setOnMouseClicked(null);
    	
    	AnchorPane surrenderPane = new AnchorPane();
        surrenderPane.setPrefSize(600, 300);
        surrenderPane.setLayoutX((1360 - 600) / 2); // Center horizontally
        surrenderPane.setLayoutY((768 - 300) / 2 - 10);  // Center vertically

        // Background rectangle with opacity
        Rectangle background = new Rectangle(600, 300);
        background.setFill(Color.BLACK);
        background.setOpacity(0.7);

        // Confirmation text
        Text confirmText = new Text("Are you sure you want to surrender?");
        confirmText.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: white;");
        confirmText.setLayoutX(50);
        confirmText.setLayoutY(120);

        // Continue Button (closes the pane)
        Button continueButton = new Button("Continue");
        continueButton.setPrefSize(150, 60);
        continueButton.setLayoutX(100);
        continueButton.setLayoutY(180);
        continueButton.setStyle("-fx-font-size: 20px;");
        continueButton.setOnAction(e -> gameBattlePane.getChildren().remove(surrenderPane));

        // Surrender Button (go back to StagePane)
        Button surrenderButton = new Button("Surrender");
        surrenderButton.setPrefSize(150, 60);
        surrenderButton.setLayoutX(350);
        surrenderButton.setLayoutY(180);
        surrenderButton.setStyle("-fx-font-size: 20px;");
        surrenderButton.setOnAction(e -> primaryStage.setScene(new Scene(new StagePane(primaryStage), 1360, 768)));

        // Add components to the pane
        surrenderPane.getChildren().addAll(background, confirmText, continueButton, surrenderButton);

        // Add to GameBattlePane
        gameBattlePane.getChildren().add(surrenderPane);
    }

	public static Enemy getEnemy() {
		return enemy;
	}
}
