package pane;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SliderPane extends Pane {

	public SliderPane() {
		this.setPrefSize(10, 200);
		Rectangle slider = new Rectangle(10,200) ;
		slider.setFill(Color.AQUA);
		this.getChildren().add(slider);
	}
	
}
