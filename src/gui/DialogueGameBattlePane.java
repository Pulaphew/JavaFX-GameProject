package gui;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;

public class DialogueGameBattlePane extends Pane {
    
    private Text dialogue;

    public DialogueGameBattlePane() {
        this.setPrefSize(1360, 250);
        this.setLayoutX(0);
        this.setLayoutY(0);

        this.dialogue = new Text("test");

        // Use a JavaFX built-in font with a bold, game-like style
        this.dialogue.setFont(Font.font("Lucia Console", FontWeight.EXTRA_BOLD, 30));

        // Set text color
        this.dialogue.setFill(Color.WHITE);
        
        // Add black stroke around the text
        this.dialogue.setStroke(Color.BLACK);
        this.dialogue.setStrokeWidth(1);

        // Add a glowing effect
//        DropShadow glowEffect = new DropShadow();
//        glowEffect.setColor(Color.YELLOW);
//        glowEffect.setRadius(10);
//        glowEffect.setSpread(0.2);
//        this.dialogue.setEffect(glowEffect);

        // Set text position and wrapping
        this.dialogue.setLayoutX(50);
        this.dialogue.setLayoutY(100);
        this.dialogue.setWrappingWidth(1275);

        this.getChildren().add(dialogue);
    }

    public void setDialogueText(String text) {
        this.dialogue.setText(text);
    }
}
