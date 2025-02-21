package gui;


import entity.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PlayerPane extends EntityPane{
	
	private ImageView playerSprite ;
	private Rectangle playerHeathBarGreen ;
	private Player player ;
	
	public PlayerPane(Player player) {
		super();
		this.player = player ;
	
		this.setLayoutX(62);
		this.setLayoutY(279); 
		playerSprite = new ImageView() ;
		playerSprite.setFitWidth(250);
		playerSprite.setFitHeight(250);
		
		//make hearth bar for Player
		//red to show hp decrease by damage
		Rectangle playerHeathBarRed = new Rectangle(250,20) ;
		playerHeathBarRed.setFill(Color.RED);
		playerHeathBarRed.setLayoutX(0);
		playerHeathBarRed.setLayoutY(210);
		
		//green show current hp
		playerHeathBarGreen = new Rectangle(250,20) ;
		playerHeathBarGreen.setFill(Color.GREEN);
		playerHeathBarGreen.setLayoutX(0);
		playerHeathBarGreen.setLayoutY(210);
		
		//edit image here
		String image_path = ClassLoader.getSystemResource("playerTest.jpg").toString() ;
		playerSprite.setImage(new Image(image_path)) ;
		this.getChildren().addAll(playerSprite,playerHeathBarRed,playerHeathBarGreen) ;
	}
	
	public void updateHealthBar() {
		double healthPercentage = (double) player.getCurrentHealth() / player.getMaxHealth() ;
		double targetWidth = 250 * healthPercentage ;
		double currentWidth = playerHeathBarGreen.getWidth();
		
		// Timeline for smooth transition of health bar width
        Timeline timeline = new Timeline();

        // Gradual change in width, 60fps smooth animation
        double step = (targetWidth - currentWidth) / 30; // 30 frames to reach the target width
        for (int i = 0; i <= 30; i++) {
            double finalStep = step * i;
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.01 * i), e -> {
                playerHeathBarGreen.setWidth(currentWidth + finalStep);
            });
            timeline.getKeyFrames().add(keyFrame);
        }
		
		timeline.setCycleCount(1);
		timeline.play();
	}
	
	public ImageView getPlayerSprite() {
		return playerSprite ;
	}
	
}
