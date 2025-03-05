package gui;


import audio.SoundManager;
import entity.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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
		String image_path = ClassLoader.getSystemResource("PlayerBattle.png").toString();
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
	
	public void animationPlayerAction() {
		TranslateTransition moveForward = new TranslateTransition(Duration.seconds(0.3), this);
		moveForward.setByX(50); // move to right
		moveForward.setAutoReverse(true);
		moveForward.setCycleCount(2);// move forward then back

		// reset position after play animation
		moveForward.setOnFinished(e -> this.setTranslateX(0));

		moveForward.play();
	}
	
	public void animationPlayerTakeDamage() {
		TranslateTransition moveBackward = new TranslateTransition(Duration.seconds(0.3), this);
		moveBackward.setByX(-50); // move to left
		moveBackward.setAutoReverse(true);
		moveBackward.setCycleCount(2); // move left then back
		SoundManager.playDamageSound();
		// reset position after play animation
		moveBackward.setOnFinished(e -> this.setTranslateX(0));

		moveBackward.play();
	}
	
	public void setPlayerSprite(String imagePath) {
		this.playerSprite.setImage(new Image(imagePath));
	}
	
	public ImageView getPlayerSprite() {
		return playerSprite ;
	}
	
}
