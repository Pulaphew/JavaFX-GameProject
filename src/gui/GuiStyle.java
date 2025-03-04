package gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

public class GuiStyle {
    
    public static void styleCroissantButton(Button button , int width) {
    	String path = ClassLoader.getSystemResource("Button.png").toString();
        ImageView buttonImage = new ImageView(new Image(path));
        
        buttonImage.setFitWidth(width);
        buttonImage.setPreserveRatio(true);
        
        button.setGraphic(buttonImage);
        button.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: black;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 24px;" +
            "-fx-alignment: center;" +
            "-fx-content-display: center;"
        );

        button.setMinWidth(350);
        button.setPrefHeight(100);
        button.setGraphicTextGap(-10);
    }
    
    public static void styleStageButton(Button button) {
    	String path = ClassLoader.getSystemResource("ButtonStage.png").toString();
    	ImageView buttonStageImage = new ImageView(new Image(path));
    	
    	buttonStageImage.setFitWidth(100);
    	buttonStageImage.setFitHeight(100);
    	buttonStageImage.setPreserveRatio(true);
    	
    	button.setGraphic(buttonStageImage);
    	button.setStyle(
    			"-fx-background-color: transparent;" +
    		    "-fx-text-fill: black;" +
    		    "-fx-font-weight: bold;" +
    		    "-fx-font-size: 24px;" +
    		    "-fx-alignment: center;" +
    		    "-fx-content-display: center;"
    	);
    }
    
    public static void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setTranslateY(-5));
        button.setOnMouseExited(e -> button.setTranslateY(0));
    }
}
