package gui;

import audio.SoundManager;
import gamelogic.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class TutorialPane extends AnchorPane {
	private final String[] descriptions = {
			"Croissant Revenger is a turn-based game where you need to use a slide bar for combat. Each turn, you can choose one action: Attack, Evade, or Ultimate (inactivated at the start).",
			"When you select Attack, a slide bar will appear. You must press the A button to stop the slider.",
			"If you stop on the green bar, your attack will deal the highest damage. If you stop on the yellow or red bars, the damage will decrease accordingly.",
			"Every time you successfully hit an enemy, you have a chance to gain Ultimate Charge. Once fully charged, you will be able to use the Ultimate Action, which deals massive damage.",
			"When you choose Evade, you have a chance to dodge the enemy’s next attack. If your Evade is successful, there is a small chance you will heal yourself.",
			"Once your turn ends, the enemy's turn begins. Each enemy has different abilities depending on the stage difficulty. Therefore, you should carefully plan when to use Attack and Evade to avoid dying before defeating your enemies." };
//  file:src/imagesrc/testTutorialImage/image1.png
	private final String[] imagePaths = { "image1.png", "image2.png", "image3.png", "image4.png", "image5.png",
			"image6.png" };

	private final Circle[] indicators;
	private int currentIndex = 0;
	private final Text descriptionText;
	private final ImageView imageView;
	private final String image_background = ClassLoader.getSystemResource("croissantBackground.png").toString();

	public TutorialPane(Stage primaryStage) {
		setPadding(new Insets(20, 20, 20, 20));

		// Title
		Text title = new Text("Tutorial");
		title.setStyle("-fx-font-size: 40px; " + "-fx-font-weight: bold; " + "-fx-fill: white; " + "-fx-stroke: black; "
				+ "-fx-stroke-width: 2px;");
		AnchorPane.setTopAnchor(title, 100.0);
		AnchorPane.setLeftAnchor(title, 20.0);

		// Description (Left Side)
		descriptionText = new Text(descriptions[currentIndex]);
		descriptionText.setWrappingWidth(300);
		descriptionText.setTextAlignment(TextAlignment.JUSTIFY);
		descriptionText.setStyle("-fx-font-size: 20px;" + "-fx-font-weight: bold; " + "-fx-fill: black; ");
		descriptionText.setLayoutX(50);

		// Image (Right Side, Larger)
		imageView = new ImageView();
		imageView.setFitWidth(600);
		imageView.setFitHeight(450);
		imageView.setPreserveRatio(true);
		updateImage();

		// Indicators (Right Side)
		HBox indicatorBox = new HBox(10);
		indicatorBox.setAlignment(Pos.CENTER);
		indicators = new Circle[descriptions.length];
		for (int i = 0; i < indicators.length; i++) {
			indicators[i] = new Circle(8, i == currentIndex ? Color.ORANGE : Color.BLACK);
			indicatorBox.getChildren().add(indicators[i]);
		}
		AnchorPane.setBottomAnchor(indicatorBox, 100.0);
		AnchorPane.setRightAnchor(indicatorBox, 150.0);

		// Navigation buttons (Right Side)
		Button prevButton = new Button("PREVIOUS");
		Button nextButton = new Button("NEXT");

		// Button Styling
		GuiStyle.styleCroissantButton(prevButton, 200);
		GuiStyle.styleCroissantButton(nextButton, 200);
		GuiStyle.addHoverEffect(prevButton);
		GuiStyle.addHoverEffect(nextButton);

		// HBox for navigation buttons (Right Side)
		HBox navButtons = new HBox(20, prevButton, nextButton);
		navButtons.setAlignment(Pos.CENTER);

		// Back button (Bottom Left)
		Button backButton = new Button("BACK");
		GuiStyle.styleCroissantButton(backButton, 200);
		GuiStyle.addHoverEffect(backButton);
		backButton.setOnAction(e -> {
            SoundManager.playClickSound();
            SceneController.switchToGameStartMenu(primaryStage);});
		AnchorPane.setBottomAnchor(backButton, 20.0);
		AnchorPane.setLeftAnchor(backButton, 20.0);

		VBox descriptionContainer = new VBox(descriptionText);
		descriptionContainer.setPrefHeight(100); // Adjust as needed
		descriptionContainer.setMinHeight(100);

		VBox imageContainer = new VBox(imageView);
		imageContainer.setMinHeight(400);
		imageContainer.setPrefHeight(400);

		Background transparentWhiteBackground = new Background(
				new BackgroundFill(Color.rgb(255, 255, 255, 0.5), new CornerRadii(10), Insets.EMPTY));

		VBox TopLeftBox = new VBox(title, descriptionContainer);
		TopLeftBox.setAlignment(Pos.CENTER_LEFT);
		AnchorPane.setBottomAnchor(TopLeftBox, 20.0);
		AnchorPane.setTopAnchor(TopLeftBox, 20.0);
		AnchorPane.setLeftAnchor(TopLeftBox, 40.0);

		// (right box)
		VBox LeftBox = new VBox(TopLeftBox, backButton);
		LeftBox.setAlignment(Pos.CENTER_LEFT);
		LeftBox.setBackground(transparentWhiteBackground);
		LeftBox.setSpacing(100);
		AnchorPane.setBottomAnchor(LeftBox, 20.0);
		AnchorPane.setTopAnchor(LeftBox, 20.0);
		AnchorPane.setLeftAnchor(LeftBox, 40.0);
		VBox.setMargin(backButton, new Insets(50, 0, 0, 0));

		VBox BotRightBox = new VBox(40, indicatorBox, navButtons);
		BotRightBox.setAlignment(Pos.CENTER);

		VBox RightBox = new VBox(imageContainer, BotRightBox);
		RightBox.setAlignment(Pos.CENTER);
		AnchorPane.setBottomAnchor(RightBox, 20.0);
		AnchorPane.setRightAnchor(RightBox, 10.0);
		VBox.setMargin(imageContainer, new Insets(0, 0, 0, 50));

		// Button actions
		prevButton.setOnAction(e -> {
            SoundManager.playClickSound();
            navigate(-1);});
		nextButton.setOnAction(e -> {
            SoundManager.playClickSound();
            navigate(1);});

		// set image background
		Image background = new Image(image_background);
		BackgroundImage bg = new BackgroundImage(background, null, null, null, null);
		this.setBackground(new Background(bg));

		// Add elements to AnchorPane
		getChildren().addAll(LeftBox, RightBox);
	}

	private void navigate(int direction) {
		int newIndex = currentIndex + direction;
		if (newIndex >= 0 && newIndex < descriptions.length) {
			currentIndex = newIndex;
			descriptionText.setText(descriptions[currentIndex]);
			updateImage();
			updateIndicators();
		}
	}

	private void updateImage() {
		String path = imagePaths[currentIndex];
		java.net.URL imgURL = ClassLoader.getSystemResource(path);

		if (imgURL != null) {
			imageView.setImage(new Image(imgURL.toString()));
		} else {
			System.out.println("❌ Image not found: " + path);
		}
	}

	private void updateIndicators() {
		for (int i = 0; i < indicators.length; i++) {
			indicators[i].setFill(i == currentIndex ? Color.ORANGE : Color.BLACK);
		}
	}
}
