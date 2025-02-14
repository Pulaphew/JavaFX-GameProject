package gui;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SlideBarPane extends StackPane {

	public SlideBarPane() {
		this.setPrefSize(1360, 250);
		this.setAlignment(Pos.CENTER);
		this.setLayoutX(0);
		this.setLayoutY(530);

		// Create different bar zone
		Rectangle redZone = new Rectangle(1039, 50);
		redZone.setFill(Color.RED);
		Rectangle yellowZone = new Rectangle(445, 80);
		yellowZone.setFill(Color.YELLOW);
		Rectangle greenZone = new Rectangle(89, 128);
		greenZone.setFill(Color.GREEN);
		
		this.getChildren().addAll(redZone,yellowZone,greenZone) ;
	}

}
