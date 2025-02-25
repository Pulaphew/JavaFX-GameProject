package gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

public class GuiStyle {

    public static void styleGameButton(Button button) {
        button.setPrefSize(250, 90);
        button.setTextAlignment(TextAlignment.CENTER);

        // Base style (white gradient with blue sky)
        String baseStyle = 
            "-fx-background-color: white, linear-gradient(to bottom, #add8e6, #ffffff);" +  // White gradient to blue sky
            "-fx-background-insets: 0, 2;" + 
            "-fx-background-radius: 0;" + 
            "-fx-border-color: black;" + 
            "-fx-border-width: 4px;" + 
            "-fx-border-radius: 0;" + 
            "-fx-text-fill: black;" +  // Default text color (black)
            "-fx-font-weight: bold;" + 
            "-fx-font-size: 20;" + 
            "-fx-text-alignment: center;" + 
            "-fx-effect: dropshadow(gaussian, black, 5, 0, 2, 2);";  // Subtle shadow

        // Hover effect (blue gradient with white)
        String hoverStyle = 
            "-fx-background-color: blue, linear-gradient(to bottom, #0000ff, #ffffff);" + // Blue gradient with white
            "-fx-border-color: white;" + // White border when hovered
            "-fx-border-width: 4px;" + 
            "-fx-border-radius: 0;" + 
            "-fx-text-fill: white;" + // White text when hovered
            "-fx-font-weight: bold;" + 
            "-fx-font-size: 20;" +  // Set font size to 30 for hover as well
            "-fx-text-alignment: center;" + 
            "-fx-effect: dropshadow(gaussian, black, 10, 0, 3, 3);"; // Stronger shadow

        button.setStyle(baseStyle); // Apply base style

        // Add event handlers for hover effects
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle)); // Apply hover style
        button.setOnMouseExited(e -> button.setStyle(baseStyle)); // Revert to base style
    }
    
    public static void styleCroissantButton(Button button , int width) {
        ImageView buttonImage = new ImageView(new Image("file:src/imagesrc/buttonImage/Button.png"));
        
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
    	ImageView buttonStageImage = new ImageView(new Image("file:src/imagesrc/buttonImage/ButtonStage.png"));
    	
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
