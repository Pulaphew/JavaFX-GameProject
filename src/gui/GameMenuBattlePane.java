package gui;

import entity.Enemy;
import entity.Player;
import gamelogic.AttackZone;
import gamelogic.GameLogic;
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
import javafx.scene.text.TextAlignment;
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

	private Button attackButton;
	private Button ultimateButton;
	private Button evadeButton;

	private boolean isPressed = false;
	private int dialogueIndex = 0;
	private String[] dialogueArray;

	private GameLogic gameLogic;

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
		attackButton = new Button("Attack");
		attackButton.setPrefSize(250, 90);
		attackButton.setFont(new Font(30));
		// action
		attackButton.setOnAction(e -> switchAttackToSlideBar());

		ultimateButton = new Button("Ultimate\n" + this.player.getUltimateTurnCount());
		ultimateButton.setPrefSize(250, 90);
		ultimateButton.setFont(new Font(25));
		ultimateButton.setWrapText(true);
		ultimateButton.setTextAlignment(TextAlignment.CENTER);
		ultimateButton.setDisable(true); // can use when ultimatecount >= 5
		// action
		ultimateButton.setOnAction(e -> playerUseUltimate());

		evadeButton = new Button("Evade");
		evadeButton.setPrefSize(250, 90);
		evadeButton.setFont(new Font(30));

		menuButton.getChildren().addAll(attackButton, ultimateButton, evadeButton);

		this.getChildren().addAll(backgroundMenu, menuButton, dialoguePane);
	}

	// method to switch after stop slider then show dialogue battle
	// display situation in Damage
	public void switchToDialogue(String... dialogue) {

		if (dialogue == null || dialogue.length == 0)
			return;

		dialogueIndex = 0;
		dialogueArray = dialogue;

		if (menuButton != null) {
			menuButton.setVisible(false);
		}
		if (slideBarPane != null && sliderPane != null) {
			this.getChildren().removeAll(slideBarPane, sliderPane);
		}

		this.dialoguePane.toFront();
		this.dialoguePane.setVisible(true);
		this.dialoguePane.setDialogueText(dialogueArray[dialogueIndex]);

		Scene scene = this.getScene();
		if (scene != null) {
			scene.setOnMouseClicked(null); // clear
			scene.setOnKeyPressed(null);
			if (this.isAttackingProgress) {
				scene.setOnMouseClicked(e -> advanceDialogue());
			}
		}
		else {
			System.out.println("scene is null from switchToDialogue");
		}
	}

	private void advanceDialogue() {
		if (dialogueArray == null || dialogueIndex >= dialogueArray.length - 1) {
			returnToGameMenu();
		} else {
			dialogueIndex++;
			this.dialoguePane.setDialogueText(dialogueArray[dialogueIndex]);
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
			delay.setOnFinished(ev -> switchToDialogue("You did not do Attack!!!"));
			delay.play();
		});

		Scene scene = this.getScene();
		if (scene != null) {
			scene.setOnKeyPressed(event -> {
				if (event.getCode() == KeyCode.A) {
					isPressed = true;
					stopSlider();
					scene.setOnKeyPressed(null);
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
		updateUltimateButton();

		PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
		delay.setOnFinished(e -> {
			switchToDialogue(dialogueDamage);

			Scene scene = this.getScene();
			if (scene != null) {
				scene.setOnMouseClicked(ev -> {
					scene.setOnMouseClicked(null); // clear
					gameLogic.onPlayerAttackCompletes();
				});
			}
		});
		delay.play();

	}

	public void returnToGameMenu() {
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

	private void updateUltimateButton() {
		this.ultimateButton.setText("Ultimate\n" + this.player.getUltimateTurnCount());
		if (this.player.canUseUltimate()) {
			this.ultimateButton.setText("Ultimate\nREADY!!");
			this.ultimateButton.setDisable(false);
		}
	}

	private void playerUseUltimate() {
		String dialogueDamage = this.player.useUltimate(enemy, enemyPane);
		this.ultimateButton.setDisable(true);
		this.ultimateButton.setText("Ultimate\n" + this.player.getUltimateTurnCount());
		switchToDialogue(dialogueDamage);

		Scene scene = this.getScene() ;
		if (scene != null) {
			scene.setOnMouseClicked(e -> gameLogic.onPlayerAttackCompletes());
		}

	}

	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}
}
