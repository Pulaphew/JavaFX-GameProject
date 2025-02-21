package gui;

import java.util.ArrayList;
import java.util.Random;

import entity.Enemy;
import entity.Narang;
import entity.Player;
import gamelogic.AttackZone;
import gamelogic.GameLogic;
import gamelogic.SlideAnimationSelector;
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

	private Random rand = new Random();

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

	private boolean isInDialogue = false;
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
		// action
		evadeButton.setOnAction(e -> playerUseEvade());

		menuButton.getChildren().addAll(attackButton, ultimateButton, evadeButton);

		this.getChildren().addAll(backgroundMenu, menuButton, dialoguePane);
	}

	// method to switch after stop slider then show dialogue battle
	// display situation in Damage
	public void switchToDialogue(String... dialogue) {

		if (dialogue == null || dialogue.length == 0)
			return;

		isInDialogue = true;
		dialogueIndex = 0;
		dialogueArray = dialogue;

		System.out.println("\nHERE IS CHECK ITEM IN ARRAY FROM SWITCHTODIALOGUE");
		for (int i = 0; i < dialogueArray.length; i++) {
			System.out.println(dialogueArray[i]);
		}

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
			scene.setOnMouseClicked(e -> advanceDialogue());
		} else {
			System.out.println("scene is null from switchToDialogue");
		}
	}

	public void advanceDialogue() {
		if (!isInDialogue) {
			return;
		}

		if (dialogueArray == null || dialogueIndex >= dialogueArray.length) {
			returnToGameMenu();
			return;
		}

		// Only increment if we're not at the last element
		if (dialogueIndex < dialogueArray.length - 1) {
			dialogueIndex++;
			this.dialoguePane.setDialogueText(dialogueArray[dialogueIndex]);
			System.out.println("Displaying Dialogue: " + dialogueArray[dialogueIndex]);
		} else {
			// End of dialogue, maybe perform some action like transitioning back to the
			// game or finishing the battle
			returnToGameMenu();
		}

		// Set the mouse click event again to advance the dialogue
		Scene scene = this.getScene();
		if (scene != null) {
			scene.setOnMouseClicked(e -> advanceDialogue());
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

		// Get setting based on enemy
		SlideAnimationSelector.SlideAnimationSettings settings = SlideAnimationSelector.getSetting(enemy);
		double speed = settings.speed;
		double initialPositionX = settings.initialPositionX;
		double setToPositionX = settings.endToPositionX;

		// set layout same as GameMenu
		slideBarPane.setLayoutX(0);
		slideBarPane.setLayoutY(0);

		sliderPane.setLayoutX(initialPositionX);
		sliderPane.setLayoutY(50);

		System.out.println(String.format("\nSliderPane, speed = %.2f, initialPosX = %.1f, endPosX = %.1f", speed,
				initialPositionX, setToPositionX));

		this.getChildren().addAll(slideBarPane, sliderPane);

		slideAnimation = new TranslateTransition(Duration.seconds(speed), sliderPane);
		double distance = setToPositionX - initialPositionX;
		slideAnimation.setByX(distance);
//		slideAnimation.setToX(setToPositionX);
		slideAnimation.setCycleCount(1);
		slideAnimation.play();

		// if animation finish , return to game menu
		slideAnimation.setOnFinished(e -> {
			PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
			delay.setOnFinished(ev -> {
				switchToDialogue("You did not do Attack!!!");
				Scene scene = this.getScene();
				if (scene != null) {
					scene.setOnMouseClicked(ev2 -> {
						scene.setOnMouseClicked(null);
						gameLogic.onPlayerAttackCompletes();
					});
				}
			});
			delay.play();
		});

		Scene scene = this.getScene();
		if (scene != null) {
			scene.setOnKeyPressed(event -> {
				if (event.getCode() == KeyCode.A) {
					stopSlider();
					scene.setOnKeyPressed(null);
				}
			});
		}
	}

	private void stopSlider() {
		slideAnimation.stop();

		ArrayList<String> dialogues = new ArrayList<String>();

		// check position x of Slider that player was press key A
		int SliderPos = (int) sliderPane.getTranslateX();
		System.out.println("Attack at X: " + SliderPos);

		AttackZone SliderZone = checkZoneSlider(SliderPos);
		System.out.println("Zone that Slider stop is : " + SliderZone);

		// call method from Player to attack Enemy
		String dialogueDamage = player.attack(enemy, SliderZone, enemyPane);
		dialogues.add(dialogueDamage);
		updateUltimateButton();

		String[] allDialogue = dialogues.toArray(new String[0]);

		// play animation player action
		animationPlayerAction();

		PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
		delay.setOnFinished(e -> {
			switchToDialogue(allDialogue);

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
		ArrayList<String> dialogues = new ArrayList<String>();

		String dialogueDamage = this.player.useUltimate(enemy, playerPane, enemyPane);
		dialogues.add(dialogueDamage);

		this.ultimateButton.setDisable(true);
		this.ultimateButton.setText("Ultimate\n" + this.player.getUltimateTurnCount());

		animationPlayerAction();

		String[] allDialogue = dialogues.toArray(new String[0]);

		switchToDialogue(allDialogue);

		Scene scene = this.getScene();
		if (scene != null) {
			scene.setOnMouseClicked(e -> gameLogic.onPlayerAttackCompletes());
		}

	}

	private void playerUseEvade() {
		ArrayList<String> dialogues = new ArrayList<String>();
		String dialogueEvade;
		// evade chance = 60 % (3 in 5)
		int evadeChance = rand.nextInt(5);
		boolean isEvadeSuccessful = evadeChance < 3;

		if (isEvadeSuccessful) {
			dialogueEvade = "Evade Success!! You dodged " + enemy.getName() + " attack 555+";
			dialogues.add(dialogueEvade);

			// 30% chance to heal after evading
			if (rand.nextInt(10) < 7) {
				dialogues.add(player.heal());
				playerPane.updateHealthBar();
			}

			// skip enemy attack and return to game menu
			switchToDialogue(dialogues.toArray(new String[0]));
			// if player is poison , then switch dialogue to show poison damge first
			Scene scene = this.getScene();
			if (scene != null) {
				if (player.isPoisoned()) {
					scene.setOnMouseClicked(e -> switchToDialogue(
							player.updateStatusEffects(((Narang) enemy).getPoisonDamage(), playerPane)));
				}
			}
		} else {
			dialogueEvade = "Evade Failed... Noooo Please don't hurt me~";
			dialogues.add(dialogueEvade);
			switchToDialogue(dialogues.toArray(new String[0]));
			Scene scene = this.getScene();
			if (scene != null) {
				scene.setOnMouseClicked(e -> {
					scene.setOnMouseClicked(null); // clear
					gameLogic.onPlayerAttackCompletes();
				});
			}
		}
	}

	private void animationPlayerAction() {
		TranslateTransition moveForward = new TranslateTransition(Duration.seconds(0.3), playerPane);
		moveForward.setByX(50); // move to right
		moveForward.setAutoReverse(true);
		moveForward.setCycleCount(2);// move forward then back

		// reset position after play animation
		moveForward.setOnFinished(e -> playerPane.setTranslateX(0));

		moveForward.play();
	}

	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}
}
