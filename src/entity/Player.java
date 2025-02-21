package entity;

import java.util.Random;

import ability.HealingPower;
import ability.UltimatePower;
import gamelogic.AttackZone;
import gui.EnemyPane;
import gui.PlayerPane;

public class Player extends Entity implements UltimatePower , HealingPower {
	private int ultimateTurnCount;
	private int poisonTurnCount; // Tracks how long the player has been poisoned
	private boolean isPoisoned; // Indicates if the player is currently poisoned
	private Random rand;

	public Player(int maxHealth, int attackPower) {
		super("Croissant Student ", maxHealth, attackPower);
		this.ultimateTurnCount = 0;
		this.poisonTurnCount = 0;
		this.isPoisoned = false;
		this.rand = new Random();
	}
	
	@Override
	public String heal() {
		//Heal to player 15% of maxHp
		int healAmount = (int)(this.getMaxHealth() * 0.15);
		this.setCurrentHealth(this.getCurrentHealth() + healAmount);
		String[] dialogueHeal = {
				"You realize that life is not just about exams.",
				"Suddenly, the love from your father and mother comes to you.",
				"YOU SHOULDN'T LOST HERE,ARGHHHH!!!!"
		};
		String dialogue = dialogueHeal[rand.nextInt(2)] + "\nYou got heal " + healAmount + " HP!!\n";
		return dialogue ;
	}

	// Attack For Player
	public String attack(Entity target, AttackZone sliderZone , EnemyPane enemyPane) {

		int dealDamage = this.getAttackPower() + plusDamageFromZone(sliderZone);
		String dialogueDealDamage ;
		// define range of value
		int chanceAttackHit = rand.nextInt(5);
		
		//have a chance 4/5 (80%) to attackHit
		if (chanceAttackHit < 4) {
			double successCriticalHit = rand.nextDouble();
			double failCriticalHit = rand.nextDouble();
			if (successCriticalHit > failCriticalHit) {
				dealDamage *= 1;
			}
			
			// case if enemy is Immortal
			// Since takeDamage(int damage) is void , cannot return String , then I put it in this instead
			if(target instanceof Pta) {
				Pta enemyImmortal = (Pta)target;
				if(enemyImmortal.isImmortal()) {
					String dialogueCanNotDealDamage = enemyImmortal.getName() + " is immortal right now! no damage taken.";
					return dialogueCanNotDealDamage;
				}
			}
			
			target.takeDamage(dealDamage);
			dialogueDealDamage = "Deal Damage To " + target.getName() + " total " + dealDamage + " damage." ;
			
			// update UI health bar of enemy
			enemyPane.updateHealthBar();
			
			double failUltimateCharge = rand.nextDouble();
			double successUltimateCharge = rand.nextDouble();
			if (successUltimateCharge > failUltimateCharge) {
				if(this.ultimateTurnCount < 5) this.ultimateTurnCount++;
			}

		} else {
			String[] dialogueFailAttack = { " 5555555413347", " ,you have hit enemy or else you will get U.",
					" Pathetic" };
			int indexDialogue = rand.nextInt(3);
			dialogueDealDamage = target.getName() + " evades! " + dialogueFailAttack[indexDialogue] ;
		}
		
		System.out.println(dialogueDealDamage);
		System.out.println("current enemy hp is " + target.getCurrentHealth() + "\n");
		
		return dialogueDealDamage ;
	}

	@Override
	public String useUltimate(Entity target , PlayerPane playerPane ,EnemyPane enemyPane) {
		String dialogue = "" ;
		if (this.canUseUltimate()) {
			int dealDamage = this.getAttackPower() * (rand.nextInt(3) + 1) * 5;
			target.takeDamage(dealDamage);
			this.ultimateTurnCount = 0 ;
			dialogue = "Ultimate!!! Deal Damage To " + target.getName() + " total " + dealDamage + " damage." ;
			// update UI health bar of enemy
			enemyPane.updateHealthBar();
		}
		return dialogue ;
	}

	public void applyPoison() {
		if (!isPoisoned && poisonTurnCount == 0) {
			isPoisoned = true;
			poisonTurnCount = 5;
		}
	}

	// parameter poisonDamage for Enemy that have poisonDamage deal to Player
	public String updateStatusEffects(int poisonDamage , PlayerPane playerPane) {
		boolean poisonCritical = false;
		if (isPoisoned) {
			String dialogue = "" ;
			if (rand.nextDouble() > 0.5) {
				poisonCritical = true;
				poisonDamage *= 2;
			}
			this.takeDamage(poisonDamage); // Poison damage each turn
			playerPane.updateHealthBar();
			poisonTurnCount--;

			if (poisonCritical) {
				dialogue += "Poison Critical Hit!\n" ;
			}
			
			dialogue += this.getName() + " takes " + poisonDamage + " poison damage! " + poisonTurnCount + " turns left.\n";
			System.out.println(dialogue);
			
			if (poisonTurnCount <= 0) {
				isPoisoned = false;
				dialogue += "you is no longer poisoned!";
				playerPane.getPlayerSprite().setEffect(null);
			}
			return dialogue ;
		}
		return null ;
	}

	private int plusDamageFromZone(AttackZone sliderZone) {
		if (sliderZone == AttackZone.GREEN) {
			return 10;
		} else if (sliderZone == AttackZone.YELLOW) {
			return 5;
		}
		// Red Zone return 0
		return 0;
	}

	// check player can use ultimate or not
	@Override
	public boolean canUseUltimate() {
		return ultimateTurnCount >= 5;
	}

	// Getters & Setters
	public int getUltimateTurnCount() {
		return ultimateTurnCount;
	}

	public void setUltimateTurnCount(int ultimateTurnCount) {
		this.ultimateTurnCount = Math.max(ultimateTurnCount, 0);
	}

	public boolean isPoisoned() {
		return isPoisoned;
	}
}
