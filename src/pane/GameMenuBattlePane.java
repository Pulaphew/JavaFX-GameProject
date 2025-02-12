package pane;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class GameMenuBattlePane extends Pane {

	private HBox menuButton ;
	private SliderPane sliderPane ;
	private SlideBarPane slideBarPane ;
	private boolean isAttackingProgress = false ;
	private TranslateTransition slideAnimation ;
	
	public GameMenuBattlePane() {
		this.setPrefSize(1360, 250);
		this.setLayoutX(0);
		this.setLayoutY(530);

		Rectangle backgroundMenu = new Rectangle(1360, 250);
		backgroundMenu.setFill(Color.BLACK);
		backgroundMenu.setOpacity(0.5);

		// Create HBox contain Button
		menuButton = new HBox();
		menuButton.setPrefSize(920, 180);
		menuButton.setLayoutX(220);
		menuButton.setLayoutY(35);
		menuButton.setSpacing(100);
		menuButton.setAlignment(Pos.CENTER);

		// Create Button
		Button attackButton = new Button("Attack") ;
		attackButton.setPrefSize(250, 90);
		attackButton.setFont(new Font(30));
		//action
		attackButton.setOnAction(e -> switchAttackToSlideBar());
		
		Button ultimateButton = new Button("Ultimate") ;
		ultimateButton.setPrefSize(250, 90);
		ultimateButton.setFont(new Font(30));
		
		Button evadeButton = new Button("Evade") ;
		evadeButton.setPrefSize(250, 90);
		evadeButton.setFont(new Font(30));
		
		
		menuButton.getChildren().addAll(attackButton,ultimateButton,evadeButton);

		this.getChildren().addAll(backgroundMenu,menuButton);
	}
	
	// method to switch to slide bar after action with attack button
	//after switch , Slider will start Transition animation
	public void switchAttackToSlideBar() {
		
		// handle multiple trigger
		if(isAttackingProgress) return ;
		
		isAttackingProgress = true ;
		menuButton.setVisible(false);
		
		slideBarPane = new SlideBarPane() ;
		sliderPane = new SliderPane() ;
		
		//set layout same as GameMenu
		slideBarPane.setLayoutX(0);
		slideBarPane.setLayoutY(0);
		
		sliderPane.setLayoutX(150);
		sliderPane.setLayoutY(50);
		
		this.getChildren().addAll(slideBarPane,sliderPane) ;
		
		slideAnimation = new TranslateTransition(Duration.seconds(1),sliderPane);
		slideAnimation.setToX(1039);
		slideAnimation.setCycleCount(1);
		slideAnimation.play();
		
		Scene scene = this.getScene() ;
		if(scene != null) {
			scene.setOnKeyPressed(event -> {
				if(event.getCode() == KeyCode.A) {
					stopSlider() ;
				}
			});
		}
 	}
	
	private void stopSlider() {
		slideAnimation.stop();
		
		//delay before return to GameMenu
		PauseTransition delay = new PauseTransition(Duration.seconds(1)) ;
		delay.setOnFinished(e -> returnToGameMenu());
		delay.play();
	}
	
	private void returnToGameMenu() {
		this.getChildren().removeAll(slideBarPane,sliderPane) ;
		menuButton.setVisible(true);
		isAttackingProgress = false ;
	}
}

