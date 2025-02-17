package gui;


import entity.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerPane extends AnchorPane{
	
	private Player player ;
	
	public PlayerPane(Player player) {
		
		this.player = player ;
		
		this.setPrefSize(250, 250);
		this.setLayoutX(62);
		this.setLayoutY(279); 
		ImageView playerSpright = new ImageView() ;
		playerSpright.setFitWidth(250);
		playerSpright.setFitHeight(250);
		
		//make hearth bar for Player
		//red to show hp decrease by damage
		Rectangle playerHeathBarRed = new Rectangle(250,20) ;
		playerHeathBarRed.setFill(Color.RED);
		playerHeathBarRed.setLayoutX(0);
		playerHeathBarRed.setLayoutY(210);
		
		//green show current hp
		Rectangle playerHeathBarGreen = new Rectangle(250,20) ;
		playerHeathBarGreen.setFill(Color.GREEN);
		playerHeathBarGreen.setLayoutX(0);
		playerHeathBarGreen.setLayoutY(210);
		
		//edit image here
		String image_path = ClassLoader.getSystemResource("playerTest.jpg").toString() ;
		playerSpright.setImage(new Image(image_path)) ;
		this.getChildren().addAll(playerSpright,playerHeathBarRed,playerHeathBarGreen) ;
	
		
	}
	
}
