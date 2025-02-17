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

	private Player player;
	private Enemy enemy;

	private PlayerPane playerPane;
	private EnemyPane enemyPane;

	private DialogueGameBattlePane dialoguePane;

	private HBox menuButton;
	private SliderPane sliderPane;
	private SlideBarPane slideBarPane;
	private boolean isAttackingProgress = false;
	private TranslateTransition slideAnimation;

	private boolean isPressed = false;

	public GameMenuBattlePane(Player player, Enemy enemy, PlayerPane playerPane, EnemyPane enemyPane) {

		this.player = player;
		this.enemy = enemy;
		this.playerPane = playerPane;
		this.enemyPane = enemyPane;

		this.dialoguePane = new DialogueGameBattlePane();
		this.dialoguePane.setVisible(false);

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
		Button attackButton = new Button("Attack");
		attackButton.setPrefSize(250, 90);
		attackButton.setFont(new Font(30));
		// action
		attackButton.setOnAction(e -> switchAttackToSlideBar());

		Button ultimateButton = new Button("Ultimate");
		ultimateButton.setPrefSize(250, 90);
		ultimateButton.setFont(new Font(30));

		Button evadeButton = new Button("Evade");
		evadeButton.setPrefSize(250, 90);
		evadeButton.setFont(new Font(30));

		menuButton.getChildren().addAll(attackButton, ultimateButton, evadeButton);

		this.getChildren().addAll(backgroundMenu, menuButton, dialoguePane);
	}

	// method to switch after stop slider then show dialogue battle
	// display situation in Damage
	public void switchSliderBarToDialogue(String dialogue) {

		this.getChildren().removeAll(slideBarPane, sliderPane);
		this.dialoguePane.setDialogueText(dialogue);
		this.dialoguePane.toFront();
		this.dialoguePane.setVisible(true);

		Scene scene = this.getScene();
		if (scene != null) {
			scene.setOnMouseClicked(null); // clear
			scene.setOnMouseClicked(e -> returnToGameMenu());
		}
	}

	// method to switch to slide bar after action with attack button
	// after switch , Slider will start Transition animation
	public void switchAttackToSlideBar() {

		// handle multiple trigger
		if (isAttackingProgress)
			return;

		isAttackingProgress = true;
		menuButton.setVisible(false);

		slideBarPane = new SlideBarPane();
		sliderPane = new SliderPane();

		// set layout same as GameMenu
		slideBarPane.setLayoutX(0);
		slideBarPane.setLayoutY(0);

		sliderPane.setLayoutX(150);
		sliderPane.setLayoutY(50);

		this.getChildren().addAll(slideBarPane, sliderPane);

		int speed = 1;
		slideAnimation = new TranslateTransition(Duration.seconds(speed), sliderPane);
		slideAnimation.setToX(1039);
		slideAnimation.setCycleCount(1);
		slideAnimation.play();

		// if animation finish , return to game menu
		slideAnimation.setOnFinished(e -> {
			PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
			delay.setOnFinished(ev -> switchSliderBarToDialogue("You did not do Attack!!!"));
			delay.play();
		});

		Scene scene = this.getScene();
		if (scene != null) {
			scene.setOnKeyPressed(event -> {
				if (event.getCode() == KeyCode.A) {
					isPressed = true;
					stopSlider();
				}
			});
		}

	}

	private void stopSlider() {
		slideAnimation.stop();

		// check position x of Slider that player was press key A
		int SliderPos = (int) sliderPane.getTranslateX();
		System.out.println("Attack at X: " + SliderPos);

		AttackZone SliderZone = checkZoneSlider(SliderPos);
		System.out.println("Zone that Slider stop is : " + SliderZone);

		// call method from Player to attack Enemy
		String dialogueDamage = player.attack(enemy, SliderZone, enemyPane);

		// delay before return to GameMenu
		PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
		delay.setOnFinished(e -> switchSliderBarToDialogue(dialogueDamage));
		delay.play();
	}

	private void returnToGameMenu() {
		this.dialoguePane.setVisible(false);
		isPressed = false;
		menuButton.setVisible(true);
		isAttackingProgress = false;

		Scene scene = this.getScene();
		if (scene != null) {
			scene.setOnMouseClicked(null); // clear
		}
	}

	private AttackZone checkZoneSlider(double SliderPos) {
		AttackZone res = AttackZone.MISS;
		if ((SliderPos >= 0 && SliderPos <= 297) || (SliderPos >= 746 && SliderPos <= 1039)) {
			res = AttackZone.RED;
		} else if ((SliderPos >= 298 && SliderPos <= 476) || (SliderPos >= 567 && SliderPos <= 745)) {
			res = AttackZone.YELLOW;
		} else if (SliderPos >= 477 && SliderPos <= 566) {
			res = AttackZone.GREEN;
		}
		return res;
	}
}
