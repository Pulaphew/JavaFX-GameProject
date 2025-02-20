
package gamelogic;

import entity.Enemy;
import entity.Player;
import gui.EnemyPane;
import gui.GameBattlePane;
import gui.PlayerPane;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.util.Duration;

public class GameLogic {

	private Player player;
	private Enemy enemy;
	private PlayerPane playerPane;
	private EnemyPane enemyPane;
	private GameBattlePane gameBattlePane;
	private boolean isPlayerTurn = true ;

	public GameLogic(Player player, Enemy enemy, PlayerPane playerPane, EnemyPane enemyPane,
			GameBattlePane gameBattlePane) {
		this.player = player;
		this.enemy = enemy;
		this.playerPane = playerPane;
		this.enemyPane = enemyPane;
		this.gameBattlePane = gameBattlePane ;
	}
	
	// when player completes an attack
	public void onPlayerAttackCompletes() {
		if(enemy.getCurrentHealth() <= 0) {
			endGame(true); //Player win!!
			return;
		}
		
		isPlayerTurn = false ;
		gameBattlePane.getGameMenuBattlePane().switchToDialogue("Enemy's Turn!!");
		
		Scene scene = gameBattlePane.getGameMenuBattlePane().getScene();
		if (scene != null) {
			scene.setOnMouseClicked(e -> enemyAttack());
		}
		else {
			System.out.println("scene is null from onPlayerAttackCompletes");
		}
	}
	
	private void enemyAttack() {
		Scene scene = gameBattlePane.getGameMenuBattlePane().getScene();
		if(scene != null) {
			scene.setOnMouseClicked(null);
		}
		// enemy attack animation
		TranslateTransition moveForward = new TranslateTransition(Duration.millis(200),enemyPane);
		moveForward.setByX(-30); //move to left (negative value)
		
		TranslateTransition moveBackward = new TranslateTransition(Duration.millis(200),enemyPane);
		moveBackward.setByX(30); //move back to original position
		
		//play both transition
		SequentialTransition attackAnimation = new SequentialTransition(moveForward,moveBackward);
		attackAnimation.setOnFinished(e -> {
			String enemyDialogue = this.enemy.attack(player,playerPane,enemyPane);
			gameBattlePane.getGameMenuBattlePane().switchToDialogue(enemyDialogue);
			if(scene != null) {
				scene.setOnMouseClicked(ev -> onEnemyAttackCompletes());
			}
		});
		attackAnimation.play();
	}
	
	private void onEnemyAttackCompletes() {
		if(player.getCurrentHealth()<=0) {
			endGame(false); //Player loses
			return;
		}
		isPlayerTurn = true ;
		gameBattlePane.getGameMenuBattlePane().switchToDialogue("Your Turn!");
		gameBattlePane.getGameMenuBattlePane().returnToGameMenu();
	}
	
	private void endGame(boolean playerWins) {
		if(playerWins) {
			gameBattlePane.getGameMenuBattlePane().switchToDialogue("You Win");
		}
		else {
			gameBattlePane.getGameMenuBattlePane().switchToDialogue("You Lose");
		}
	}

}
