package gui;

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
        ImageView background = new ImageView(new Image("file:src/imagesrc/backgroundImage/startMenu.png"));
        background.setFitWidth(1360);
        background.setFitHeight(768);

        // ปุ่มต่างๆ
        Button startButton = new Button("StartGame");
        GuiStyle.styleCroissantButton(startButton,200);

        Button tutorialButton = new Button("Tutorial");
        GuiStyle.styleCroissantButton(tutorialButton,200);

        Button exitButton = new Button("Exit");
        GuiStyle.styleCroissantButton(exitButton,200);

        startButton.setOnAction(e -> onStartGame.run());
        tutorialButton.setOnAction(e -> SceneController.switchToTutorial(primaryStage));
        exitButton.setOnAction(e -> primaryStage.close());

        // ปุ่มเครดิต
        Button infoButton = new Button();
        ImageView creditImage = new ImageView(new Image("file:src/imagesrc/buttonImage/Credits.png"));

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
        alert.setHeaderText("🎮 ผู้พัฒนาเกม 🎮");

        // GridPane สำหรับข้อมูลผู้พัฒนา
        GridPane grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        // ข้อมูลผู้พัฒนา
        String credit_image_path = "file:src/imagesrc/credits/exsax.png";
        Developer[] developers = {
            new Developer("Pula - kun", "6733154321", "Developer", credit_image_path),
            new Developer("Tong - kun", "6733257321", "Developer", credit_image_path),
            new Developer("Yodsaran Chaipakdee", "6733213721", "Developer", credit_image_path)
        };

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
