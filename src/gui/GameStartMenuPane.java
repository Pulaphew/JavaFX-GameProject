package gui;

import audio.SoundManager;
import gamelogic.SceneController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameStartMenuPane extends StackPane {
	public GameStartMenuPane(Stage primaryStage, Runnable onStartGame) {
		// พื้นหลัง
		ImageView background = new ImageView(new Image(ClassLoader.getSystemResource("startMenu.png").toString()));
		background.setFitWidth(1360);
		background.setFitHeight(768);
		SoundManager.playBackgroundMusic("/hypeboy.mp3", 0.1);
		// ปุ่มต่างๆ
		Button startButton = new Button("StartGame");
		GuiStyle.styleCroissantButton(startButton, 200);

		Button tutorialButton = new Button("Tutorial");
		GuiStyle.styleCroissantButton(tutorialButton, 200);

		Button exitButton = new Button("Exit");
		GuiStyle.styleCroissantButton(exitButton, 200);

		startButton.setOnAction(e -> {
			SoundManager.playClickSound(); // เล่นเสียงเมื่อคลิก
			onStartGame.run(); // เรียกใช้งานเมื่อคลิก
		});

		tutorialButton.setOnAction(e -> {
			SoundManager.playClickSound(); // เล่นเสียงเมื่อคลิก
			SceneController.switchToTutorial(primaryStage); // สลับไปยังหน้า Tutorial
		});

		exitButton.setOnAction(e -> {
			SoundManager.playClickSound(); // เล่นเสียงเมื่อคลิก
			primaryStage.close(); // ปิดโปรแกรม
		});

		// ปุ่มเครดิต
		Button infoButton = new Button();
		ImageView creditImage = new ImageView(new Image(ClassLoader.getSystemResource("Credits.png").toString()));

		creditImage.setFitWidth(100);
		creditImage.setPreserveRatio(true);

		infoButton.setGraphic(creditImage);
		infoButton.setStyle("-fx-background-color: transparent; -fx-padding: 0; -fx-border-width: 0;");

		infoButton.setOnAction(e -> showCredits());

		// ตำแหน่งของปุ่มเครดิต
		infoButton.setTranslateX(600);
		infoButton.setTranslateY(320);

		// เอฟเฟกต์ Hover
		GuiStyle.addHoverEffect(startButton);
		GuiStyle.addHoverEffect(tutorialButton);
		GuiStyle.addHoverEffect(exitButton);

		VBox menuBox = new VBox(1, startButton, tutorialButton, exitButton);
		menuBox.setAlignment(Pos.CENTER_LEFT);
		menuBox.setTranslateX(170);
		menuBox.setTranslateY(120); // Moves it down by 50 pixels
		getChildren().addAll(background, menuBox, infoButton);
	}

	private void showCredits() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Credits");
		alert.setHeaderText("🎮 Game Developer 🎮");

		// GridPane สำหรับข้อมูลผู้พัฒนา
		GridPane grid = new GridPane();
		grid.setHgap(30);
		grid.setVgap(20);
		grid.setAlignment(Pos.CENTER);

		// ข้อมูลผู้พัฒนา
		String credit_image_path = ClassLoader.getSystemResource("exsax.png").toString();
		Developer[] developers = {
				new Developer("Punyapat Phewkham", "6733154321", "Developer/Graphic Design", credit_image_path),
				new Developer("Siwakorn KaewSaad", "6733257321", "Developer/Game Balance", credit_image_path),
				new Developer("Yodsaran Chaipakdee", "6733213721", "Developer/Sound Design", credit_image_path) };

		// เพิ่มข้อมูลผู้พัฒนาเข้า GridPane
		for (int i = 0; i < developers.length; i++) {
			Developer dev = developers[i];

			ImageView imageView = new ImageView(new Image(dev.imagePath));
			imageView.setFitWidth(80);
			imageView.setPreserveRatio(true);

			Label nameLabel = new Label("Name: " + dev.name);
			Label idLabel = new Label("StudentID: " + dev.studentID);
			Label roleLabel = new Label("Role: " + dev.role);

			VBox devBox = new VBox(5, imageView, nameLabel, idLabel, roleLabel);
			devBox.setAlignment(Pos.CENTER);

			grid.add(devBox, i, 0);
		}

		alert.getDialogPane().setContent(grid);
		alert.showAndWait();
	}

	// คลาส Developer สำหรับเก็บข้อมูลผู้พัฒนา
	static class Developer {
		String name, studentID, role, imagePath;

		Developer(String name, String studentID, String role, String imagePath) {
			this.name = name;
			this.studentID = studentID;
			this.role = role;
			this.imagePath = imagePath;
		}
	}
}
