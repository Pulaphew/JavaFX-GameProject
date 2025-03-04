package gamelogic;

import java.util.ArrayList;

import ability.UltimatePower;
import entity.Enemy;
import entity.Narang;
import entity.Natchan;
import entity.Player;
import entity.Pta;
import gui.EnemyPane;
import gui.GameBattlePane;
import gui.PlayerPane;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameLogic {

    private Player player;
    private Enemy enemy;
    private PlayerPane playerPane;
    private EnemyPane enemyPane;
    private GameBattlePane gameBattlePane;
    private Stage primaryStage;
    
    // Store defeated enemies
    private static ArrayList<Enemy> defeatedEnemies = new ArrayList<Enemy>() ;

    private final String image_path_player_battle = ClassLoader.getSystemResource("PlayerBattle.png").toString();
    private final String image_path_player_takedamage = ClassLoader.getSystemResource("PlayerTakeDamage.png")
            .toString();

    public GameLogic(Stage primaryStage, Player player, Enemy enemy, PlayerPane playerPane, EnemyPane enemyPane,
            GameBattlePane gameBattlePane) {
        this.primaryStage = primaryStage;
        this.player = player;
        this.enemy = enemy;
        this.playerPane = playerPane;
        this.enemyPane = enemyPane;
        this.gameBattlePane = gameBattlePane;
    }

    // when player completes an attack
    public void onPlayerAttackCompletes() {
        playerPane.setPlayerSprite(image_path_player_battle);
        if (!enemy.isAlive()) {
            endGame(true); // Player wins!
            return;
        }

        Scene scene = gameBattlePane.getGameMenuBattlePane().getScene();

        if (player.isPoisoned()) {
            Narang enemyPoison = (Narang) enemy;
            gameBattlePane.getGameMenuBattlePane()
                    .switchToDialogue(player.updateStatusEffects(enemyPoison.getPoisonDamage(), playerPane));

            if (scene != null) {
                scene.setOnMouseClicked(ev -> {
                    scene.setOnMouseClicked(null); // Clear previous event
                    gameBattlePane.getGameMenuBattlePane().switchToDialogue("Enemy's Turn!!");

                    scene.setOnMouseClicked(e -> {
                        scene.setOnMouseClicked(null); // Clear event again
                        enemyAttack();
                    });
                });
            }
        } else {
            gameBattlePane.getGameMenuBattlePane().switchToDialogue("Enemy's Turn!!");

            if (scene != null) {
                scene.setOnMouseClicked(e -> enemyAttack());
            }
        }
    }

    private void enemyAttack() {
        Scene scene = gameBattlePane.getGameMenuBattlePane().getScene();

        if (scene != null) {
            scene.setOnMouseClicked(null);
            scene.setOnKeyPressed(null);
        }
        
        enemyPane.setEnemySprite(enemy.getAttack_img());

        // Enemy attack animation (move forward and back)
        TranslateTransition moveForward = new TranslateTransition(Duration.millis(200), enemyPane);
        moveForward.setByX(-30);

        TranslateTransition moveBackward = new TranslateTransition(Duration.millis(200), enemyPane);
        moveBackward.setByX(30);

        SequentialTransition attackAnimation = new SequentialTransition(moveForward, moveBackward);
        attackAnimation.setOnFinished(e -> handleEnemyAttack(scene));

        attackAnimation.play();
    }

    private void handleEnemyAttack(Scene scene) {
        String[] enemyDialogue;
        enemyPane.setEnemySprite(enemy.getBattle_img());
        if (enemy instanceof UltimatePower || enemy instanceof Pta) {
            Pta enemyUltimate = (Pta) enemy;
            if (enemyUltimate.canUseUltimate()) {
                enemyDialogue = new String[] { enemyUltimate.useUltimate(player, playerPane, enemyPane) };
            } else {
                enemyDialogue = enemy.attack(player, playerPane, enemyPane);
            }
        } else {
            enemyDialogue = enemy.attack(player, playerPane, enemyPane);
        }

        playerPane.setPlayerSprite(image_path_player_takedamage);
        playerPane.animationPlayerTakeDamage();
        gameBattlePane.getGameMenuBattlePane().switchToDialogue(enemyDialogue);

        if (!player.isAlive()) {
            endGame(false);
            return;
        }

        if (scene != null) {
            scene.setOnMouseClicked(ev -> {
                playerPane.setPlayerSprite(image_path_player_battle);
                gameBattlePane.getGameMenuBattlePane().advanceDialogue();
            });
        }
    }

    private void endGame(boolean playerWins) {
        // Mark the current enemy as defeated
    	if(playerWins && !defeatedEnemies.contains(enemy)) {
    		defeatedEnemies.add(enemy);
    	}
        SceneController.showEndGameScreen(gameBattlePane, primaryStage, playerWins);
    }

    public GameBattlePane getGameBattlePane() {
        return gameBattlePane;
    }

    // Static method to check if a specific stage is unlocked
    public static boolean isStageUnlocked(Enemy enemy) {
        return defeatedEnemies.contains(enemy);
    }
    
    public static boolean isNatchanDefeated() {
        for (Enemy e : defeatedEnemies) {
            if (e instanceof Natchan) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNarangDefeated() {
        for (Enemy e : defeatedEnemies) {
            if (e instanceof Narang) {
                return true;
            }
        }
        return false;
    }

}
