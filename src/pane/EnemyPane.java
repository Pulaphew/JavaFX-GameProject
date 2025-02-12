package pane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EnemyPane extends AnchorPane {
	
	public EnemyPane() {
		this.setPrefSize(250, 250);
		this.setLayoutX(1010);
		this.setLayoutY(80); 
		ImageView enemySpright = new ImageView() ;
		enemySpright.setFitWidth(250);
		enemySpright.setFitHeight(250);
		
		//make hearth bar for Enemy
		//red to show hp decrease by damage
		Rectangle enemyHeathBarRed = new Rectangle(250,20) ;
		enemyHeathBarRed.setFill(Color.RED);
		enemyHeathBarRed.setLayoutX(0);
		enemyHeathBarRed.setLayoutY(210);
		
		//green show current hp
		Rectangle enemyHeathBarGreen = new Rectangle(250,20) ;
		enemyHeathBarGreen.setFill(Color.GREEN);
		enemyHeathBarGreen.setLayoutX(0);
		enemyHeathBarGreen.setLayoutY(210);
		
		//edit image here
		String image_path = ClassLoader.getSystemResource("playerTest.jpg").toString() ;
		enemySpright.setImage(new Image(image_path)) ;
		this.getChildren().addAll(enemySpright,enemyHeathBarRed,enemyHeathBarGreen) ;
	}
}
