package gui;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class SlideBarPane extends StackPane {

    public SlideBarPane() {
        this.setPrefSize(1360, 250);
        this.setAlignment(Pos.CENTER);
        this.setLayoutX(0);
        this.setLayoutY(530);

        // Create vertical gradient for each zone
        LinearGradient redGradient = new LinearGradient(
            0, 0, 0, 1, true, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.DARKRED), new Stop(1, Color.RED)
        );

        LinearGradient yellowGradient = new LinearGradient(
            0, 0, 0, 1, true, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.GOLD), new Stop(1, Color.YELLOW)
        );

        LinearGradient greenGradient = new LinearGradient(
            0, 0, 0, 1, true, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.DARKGREEN), new Stop(1, Color.LIMEGREEN)
        );

        Rectangle redZone = new Rectangle(1039, 50);
        redZone.setFill(redGradient);

        Rectangle yellowZone = new Rectangle(445, 80);
        yellowZone.setFill(yellowGradient);

        Rectangle greenZone = new Rectangle(89, 128);
        greenZone.setFill(greenGradient);

        this.getChildren().addAll(redZone, yellowZone, greenZone);
    }
}
