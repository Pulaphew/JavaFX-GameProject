package gui;


import entity.Enemy;
import entity.Pto;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class EnemyPane extends EntityPane {
	
	private ImageView enemySprite;
	private Rectangle enemyHeathBarGreen ;
	private Enemy enemy ;
	
	public EnemyPane(Enemy enemy) {
		//super
		super();
		this.enemy = enemy ;
		
		this.setLayoutX(1010);
		this.setLayoutY(80); 
		enemySprite = new ImageView() ;
		enemySprite.setFitWidth(250);
		enemySprite.setFitHeight(250);
		
		//make hearth bar for Enemy
		//red to show hp decrease by damage
		Rectangle enemyHeathBarRed = new Rectangle(250,20) ;
		enemyHeathBarRed.setFill(Color.RED);
		enemyHeathBarRed.setLayoutX(0);
		enemyHeathBarRed.setLayoutY(210);
		
		//green show current hp
		enemyHeathBarGreen = new Rectangle(250,20) ;
		enemyHeathBarGreen.setFill(Color.GREEN);
		enemyHeathBarGreen.setLayoutX(0);
		enemyHeathBarGreen.setLayoutY(210);
		
		//edit image here
		String image_path = ClassLoader.getSystemResource("playerTest.jpg").toString() ;
		enemySprite.setImage(new Image(image_path)) ;
		this.getChildren().addAll(enemySprite,enemyHeathBarRed,enemyHeathBarGreen) ;
	}
	
	// set width of health bar after take damage
	public void updateHealthBar() {
		double healthPercentage = (double) enemy.getCurrentHealth() / enemy.getMaxHealth() ;
		double targetWidth = 250 * healthPercentage ;
		double currentWidth = enemyHeathBarGreen.getWidth();
		
		// Timeline for smooth transition of health bar width
        Timeline timeline = new Timeline();

        // Gradual change in width, 60fps smooth animation
        double step = (targetWidth - currentWidth) / 30; // 30 frames to reach the target width
        for (int i = 0; i <= 30; i++) {
            double finalStep = step * i;
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.01 * i), e -> {
                enemyHeathBarGreen.setWidth(currentWidth + finalStep);
            });
            timeline.getKeyFrames().add(keyFrame);
        }
		
		timeline.setCycleCount(1);
		timeline.play();
	}
	
	public void updateEnemyImmortal() {
		if(enemy instanceof Pto) {
			Pto enemyImmortal = (Pto)enemy;
			if(enemyImmortal.isImmortal()) {
				ColorAdjust colorAdjust = new ColorAdjust();
				colorAdjust.setHue(40.0 / 360);  // Hue value (40 degrees converted to a fraction)
				colorAdjust.setSaturation(0.3); // Saturation value (30% increase in saturation)
				colorAdjust.setBrightness(0);  // No change in brightness
				enemySprite.setEffect(colorAdjust);
				enemyHeathBarGreen.setFill(Color.YELLOW);
			}
			else {
				enemySprite.setEffect(null);
				enemyHeathBarGreen.setFill(Color.GREEN);
			}
		}
	}
}
