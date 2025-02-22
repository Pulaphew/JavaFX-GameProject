package gui;

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

	private final String backgroundImagePath = ClassLoader.getSystemResource("croissantTestImage.jpg").toString();
	private final String imageStagePath = ClassLoader.getSystemResource("croissantTestImage.jpg").toString();

	public StagePane(Stage primaryStage) {
		this.setPrefSize(1360, 768);

		backgroundImage = new ImageView();
		backgroundImage.setImage(new Image(backgroundImagePath));
		backgroundImage.setFitWidth(1360);
		backgroundImage.setFitHeight(768);
		backgroundImage.setPreserveRatio(true);

		imageStage = new ImageView();
		imageStage.setImage(new Image(imageStagePath));
		imageStage.setFitWidth(770);
		imageStage.setFitHeight(115);
		imageStage.setPreserveRatio(true);
		imageStage.setLayoutX(295);
		imageStage.setLayoutY(115);

		buttonContainer = new HBox(100);
		buttonContainer.setLayoutX(255);
		buttonContainer.setLayoutY(334);
		buttonContainer.setPrefWidth(850);
		buttonContainer.setPrefHeight(100);
		buttonContainer.setAlignment(Pos.CENTER);

		stageOne = new Button("1");
		stageOne.setPrefSize(100, 100);
		stageOne.setOnAction(e -> SceneController.switchToGameScene(primaryStage, "Natchan"));

		stageTwo = new Button("2");
		stageTwo.setPrefSize(100, 100);
		stageTwo.setOnAction(e -> SceneController.switchToGameScene(primaryStage, "Narang"));

		stageThree = new Button("3");
		stageThree.setPrefSize(100, 100);
		stageThree.setOnAction(e -> SceneController.switchToGameScene(primaryStage, "Pta"));

		backButton = new Button("Back");
		backButton.setPrefSize(175, 70);
		backButton.setLayoutX(20);
		backButton.setLayoutY(15);
		backButton.setFont(new Font(30));
		
		buttonContainer.getChildren().addAll(stageOne, stageTwo, stageThree);

		imageStageTextContainer = new Pane();
		imageStageTextContainer.setLayoutX(295);
		imageStageTextContainer.setLayoutY(115);
		imageStageTextContainer.getChildren().add(imageStage);

		// Add all components to the AnchorPane
		this.getChildren().addAll(backgroundImage, imageStageTextContainer, buttonContainer , backButton);
	}
}
