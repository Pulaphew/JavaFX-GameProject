package gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SliderPane extends Pane {

	public SliderPane() {
		this.setPrefSize(10, 150);
		Rectangle slider = new Rectangle(10,150) ;
		slider.setFill(Color.AQUA);
		this.getChildren().add(slider);
	}
	
}
