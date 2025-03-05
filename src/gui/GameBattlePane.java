package gui;

import audio.SoundManager;
import gamelogic.SceneController;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameBattlePane extends AnchorPane {

    PlayerPane playerPane;
    EnemyPane enemyPane;
    GameMenuBattlePane gameMenuBattlePane;
    private static boolean isConfirmationOpen = false;

    public GameBattlePane(PlayerPane playerPane, EnemyPane enemyPane, GameMenuBattlePane gameMenuBattlePane) {
        this.playerPane = playerPane;
        this.enemyPane = enemyPane;
        this.gameMenuBattlePane = gameMenuBattlePane;

        this.setPrefSize(1360, 768);
        SoundManager.playBackgroundMusic("/msbg.mp3", 0.2);

        // Pane for background
        Pane backgroundPane = new Pane();
        backgroundPane.setPrefSize(1360, 768);
        ImageView backgroundImage = new ImageView();
        backgroundImage.setFitWidth(1360);
        backgroundImage.setFitHeight(768);
        // Edit background image from here
        String image_path = enemyPane.getEnemyBackgroundStage();
        backgroundImage.setImage(new Image(image_path));
        backgroundPane.getChildren().add(backgroundImage);

        // Make surrender button
        Button surrenderButton = new Button("Surrender");
        surrenderButton.setPrefSize(175, 70);
        surrenderButton.setLayoutX(20);
        surrenderButton.setLayoutY(15);
        surrenderButton.setFont(new Font(30));
        GuiStyle.styleCroissantButton(surrenderButton, 175);
        GuiStyle.addHoverEffect(surrenderButton);

        // Action for surrender button
        surrenderButton.setOnAction(e -> {
        	SoundManager.playClickSound();
            if (!isConfirmationOpen) { 
                isConfirmationOpen = true; 
                SceneController.showSurrenderConfirmation((Stage)this.getScene().getWindow(), this);
            }
        });

        // Add all nodes into game battle pane
        this.getChildren().addAll(
                backgroundPane,
                surrenderButton,
                gameMenuBattlePane,
                playerPane,
                enemyPane);
    }

    // Method to reset the confirmation flag when confirmation is closed
    public void resetConfirmationFlag() {
        isConfirmationOpen = false;
    }

    public GameMenuBattlePane getGameMenuBattlePane() {
        return gameMenuBattlePane;
    }

	public static void setConfirmationOpen(boolean value) {
		isConfirmationOpen = value;
	}
    
}
