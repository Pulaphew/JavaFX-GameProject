package gui;

import entity.Enemy;
import entity.Player;
import gamelogic.AttackZone;
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

	private Player player ;
	private Enemy enemy ;
	
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
		
		int speed = 1 ;
		slideAnimation = new TranslateTransition(Duration.seconds(speed),sliderPane);
		slideAnimation.setToX(1039);
		slideAnimation.setCycleCount(1);
		slideAnimation.play();
		
		// if animation finish , return to game menu
		slideAnimation.setOnFinished(e -> {
			PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
			delay.setOnFinished(ev -> returnToGameMenu()) ;
			delay.play();
		});
			
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
		
		//check position x of Slider that player was press key A 
		int SliderPos = (int) sliderPane.getTranslateX() ;
		System.out.println("Attack at X: " + SliderPos);
		
		AttackZone SliderZone = checkZoneSlider(SliderPos) ;
		System.out.println("Zone that Slider stop is : " + SliderZone);
		
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
	
	private AttackZone checkZoneSlider(double SliderPos) {
		AttackZone res = AttackZone.MISS ;
		if((SliderPos >= 0 && SliderPos <= 297) || (SliderPos >= 746 && SliderPos <= 1039)) {
			res = AttackZone.RED ;
		}
		else if((SliderPos >= 298 && SliderPos <= 476) || (SliderPos >= 567 && SliderPos <= 745)) {
			res = AttackZone.YELLOW ;
		}
		else if(SliderPos >= 477 && SliderPos <= 566) {
			res = AttackZone.GREEN ;
		}
		return res ;
	}
}

