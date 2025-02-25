package gui;

import entity.Enemy;
import entity.Narang;
import entity.Pta;
import gamelogic.GameLogic;
import gamelogic.SceneController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StagePane extends AnchorPane {

	private ImageView backgroundImage;
	private HBox buttonContainer;
	private Button stageOne;
	private Button stageTwo;
	private Button stageThree;
	private Button backButton;
	private Pane imageStageTextContainer;
	private ImageView imageStage;

	private final String backgroundImagePath = ClassLoader.getSystemResource("croissantBackground.png").toString();
	private final String imageStagePath = ClassLoader.getSystemResource("StagesText.png").toString();
	
	public StagePane(Stage primaryStage) {
		this.setPrefSize(1360, 768);

		backgroundImage = new ImageView();
		backgroundImage.setImage(new Image(backgroundImagePath));
		backgroundImage.setFitWidth(1360);
		backgroundImage.setFitHeight(768);
//		backgroundImage.setPreserveRatio(true);

		imageStage = new ImageView();
		imageStage.setImage(new Image(imageStagePath));
		imageStage.setFitWidth(500);
		imageStage.setFitHeight(400);
		imageStage.setPreserveRatio(true);

		buttonContainer = new HBox(100);
		buttonContainer.setLayoutX(255);
		buttonContainer.setLayoutY(334);
		buttonContainer.setPrefWidth(850);
		buttonContainer.setPrefHeight(100);
		buttonContainer.setAlignment(Pos.CENTER);

		stageOne = new Button("1");
		stageOne.setPrefSize(100, 100);
		GuiStyle.styleStageButton(stageOne);
		GuiStyle.addHoverEffect(stageOne);
		stageOne.setOnAction(e -> SceneController.switchToGameScene(primaryStage, "Natchan"));

		stageTwo = new Button("2");
		stageTwo.setPrefSize(100, 100);
		GuiStyle.styleStageButton(stageTwo);
		GuiStyle.addHoverEffect(stageTwo);
		stageTwo.setOnAction(e -> SceneController.switchToGameScene(primaryStage, "Narang"));

		stageThree = new Button("3");
		stageThree.setPrefSize(100, 100);
		GuiStyle.styleStageButton(stageThree);
		GuiStyle.addHoverEffect(stageThree);
		stageThree.setOnAction(e -> SceneController.switchToGameScene(primaryStage, "Pta"));

		backButton = new Button("Back");
		backButton.setPrefSize(175, 70);
		backButton.setLayoutX(20);
		backButton.setLayoutY(15);
		backButton.setFont(new Font(30));
		GuiStyle.styleCroissantButton(backButton,200);
		GuiStyle.addHoverEffect(backButton);
		//action
		backButton.setOnAction(e -> SceneController.switchToGameStartMenu(primaryStage));
		
		buttonContainer.getChildren().addAll(stageOne, stageTwo, stageThree);

		imageStageTextContainer = new Pane();
		imageStageTextContainer.setPrefWidth(685);
		imageStageTextContainer.setPrefHeight(169);
		imageStageTextContainer.setLayoutX(430);
		imageStageTextContainer.setLayoutY(200);
		imageStageTextContainer.getChildren().add(imageStage);
		
		updateStageButton(stageOne,stageTwo,stageThree);
		
		// Add all components to the AnchorPane
		this.getChildren().addAll(backgroundImage, imageStageTextContainer, buttonContainer , backButton);
	}
	
	private static void updateStageButton(Button stage1Button, Button stage2Button, Button stage3Button) {
	    // Stage 1 (Always enabled for Natchan)
	    stage1Button.setDisable(false); // Always enabled

	    // Stage 2 (Enabled only if Natchan is defeated)
	    stage2Button.setDisable(!GameLogic.isNatchanDefeated());

	    // Stage 3 (Enabled only if Narang is defeated)
	    stage3Button.setDisable(!GameLogic.isNarangDefeated());
	}
}
