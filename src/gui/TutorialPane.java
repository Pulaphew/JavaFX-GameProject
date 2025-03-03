package gui;

import gamelogic.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
        "Once your turn ends, the enemy's turn begins. Each enemy has different abilities depending on the stage difficulty. Therefore, you should carefully plan when to use Attack and Evade to avoid dying before defeating your enemies."
    };
    private final String[] imagePaths = {
        "image1.png",
        "image2.png",
        "image3.png",
        "image4.png",
        "image5.png",
        "iamge6.png"
    };

    private final Circle[] indicators;
    private int currentIndex = 0;
    private final Text descriptionText;
    private final ImageView imageView;

    public TutorialPane(Stage primaryStage) {
        setPadding(new Insets(20, 20, 20, 20));

        // Title
        Text title = new Text("Tutorial");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor(title, 20.0);
        AnchorPane.setLeftAnchor(title, 20.0);

        // Description (Left Side)
        descriptionText = new Text(descriptions[currentIndex]);
        descriptionText.setWrappingWidth(300);
        descriptionText.setTextAlignment(TextAlignment.JUSTIFY);
        descriptionText.setStyle("-fx-font-size: 14px;");
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

        // HBox for content (Text on Left, Image on Right)
     // HBox for content (Image on Left, Text on Right)
//        HBox contentBox = new HBox(50, descriptionText, imageView);
//        contentBox.setAlignment(Pos.CENTER_RIGHT);
//        AnchorPane.setTopAnchor(contentBox, 70.0);
//        AnchorPane.setLeftAnchor(contentBox, 50.0); // Move image closer to middle-left
//        AnchorPane.setRightAnchor(contentBox, 50.0);


        // Navigation buttons (Right Side)
        Button prevButton = new Button("PREVIOUS");
        Button nextButton = new Button("NEXT");

        // Button Styling
        String buttonStyle = "-fx-background-color: orange; -fx-padding: 10px 20px; -fx-font-size: 14px; -fx-font-weight: bold;";
        prevButton.setStyle(buttonStyle);
        nextButton.setStyle(buttonStyle);

        // HBox for navigation buttons (Right Side)
        HBox navButtons = new HBox(20, prevButton, nextButton);
        navButtons.setAlignment(Pos.CENTER);
        AnchorPane.setBottomAnchor(navButtons, 50.0);
        AnchorPane.setRightAnchor(navButtons, 120.0);

        // Back button (Bottom Left)
        Button backButton = new Button("BACK");
        backButton.setStyle(buttonStyle);
        backButton.setOnAction(e -> SceneController.switchToGameStartMenu(primaryStage));
        AnchorPane.setBottomAnchor(backButton, 20.0);
        AnchorPane.setLeftAnchor(backButton, 20.0);
        
        VBox descriptionContainer = new VBox(descriptionText);
        descriptionContainer.setPrefHeight(100); // Adjust as needed
        descriptionContainer.setMinHeight(100);
        
        VBox imageContainer = new VBox(imageView);
        imageContainer.setMinHeight(400);
        imageContainer.setPrefHeight(400);
        
        VBox TopLeftBox = new  VBox(40,title,descriptionContainer);
        TopLeftBox.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setBottomAnchor(TopLeftBox, 20.0);
        AnchorPane.setTopAnchor(TopLeftBox, 20.0);
        AnchorPane.setLeftAnchor(TopLeftBox, 40.0);
        
     // (right box)
        VBox LeftBox = new VBox(500,TopLeftBox,backButton);
        LeftBox.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setBottomAnchor(LeftBox, 20.0);
        AnchorPane.setTopAnchor(LeftBox, 20.0);
        AnchorPane.setLeftAnchor(LeftBox, 40.0);
        
        VBox BotRightBox = new VBox(40,indicatorBox,navButtons);
        BotRightBox.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(BotRightBox, 20.0);
        AnchorPane.setTopAnchor(BotRightBox, 20.0);
        AnchorPane.setRightAnchor(BotRightBox, 60.0);
        
        VBox RightBox = new VBox(50,imageContainer,BotRightBox);
        RightBox.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setBottomAnchor(RightBox, 20.0);
        AnchorPane.setTopAnchor(RightBox, 20.0);
        AnchorPane.setRightAnchor(RightBox, 60.0); 
        
       

        // Button actions
        prevButton.setOnAction(e -> navigate(-1));
        nextButton.setOnAction(e -> navigate(1));

        // Add elements to AnchorPane
        getChildren().addAll( RightBox, LeftBox);
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
