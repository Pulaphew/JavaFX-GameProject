package entity;

import java.util.Random;

import enemyAbility.UltimatePower;

public class Player extends Entity implements UltimatePower {
	private int ultimateTurnCount;
	private int poisonTurnCount; // Tracks how long the player has been poisoned
	private boolean isPoisoned; // Indicates if the player is currently poisoned
	private Random rand;

	public Player(int maxHealth, int attackPower) {
		super("CEDT Student ", maxHealth, attackPower);
		this.ultimateTurnCount = 0;
		this.poisonTurnCount = 0;
		this.isPoisoned = false;
		this.rand = new Random();
	}

	@Override
	public void attack(Entity target) {
		int dealDamage = this.getAttackPower();
		double failToAttack = rand.nextDouble();
		double successToAttack = rand.nextDouble();

		if (successToAttack > failToAttack) {
			double successCriticalHit = rand.nextDouble();
			double failCriticalHit = rand.nextDouble();
			if (successCriticalHit > failCriticalHit) {
				dealDamage *= 2;
			}
			target.takeDamage(dealDamage);
			System.out.println("Deal Damage To " + target.getName() + " total " + dealDamage + " damage.");

			double failUltimateCharge = rand.nextDouble();
			double successUltimateCharge = rand.nextDouble();
			if (successUltimateCharge > failUltimateCharge) {
				this.ultimateTurnCount++;
			}

		} else {
			String[] dialogueFailAttack = { " 5555555413347", " ,you have hit enemy or else you will get U.",
					" Pathetic" };
			int indexDialogue = rand.nextInt(3);
			System.out.println(target.getName() + " evades! " + dialogueFailAttack[indexDialogue]);
		}
	}

	@Override
	public void useUltimate(Entity target) {
		if (this.canUseUltimate()) {
			int dealDamage = this.getAttackPower() * (rand.nextInt(3) + 1) * 5;
			target.takeDamage(dealDamage);
			System.out.println("Deal Damage To " + target.getName() + " total " + dealDamage + " damage.");
		}
	}
	
	public void evade(Enemy enemy) {
		double evadeChance = rand.nextDouble();
		if(evadeChance > enemy.getAttackAccuracy()) {
			System.out.println("Evade Success!!");
		}
		else {
			System.out.println("Evade Fail.");
			this.takeDamage(enemy.getAttackPower());
		}
	}

	public void applyPoison() {
		if (!isPoisoned) {
			isPoisoned = true;
			poisonTurnCount = 5;
			System.out.println(this.getName() + " is poisoned!");
		}
	}

	// parameter poisonDamage for Enemy that have poisonDamage deal to Player
	public void updateStatusEffects(int poisonDamage) {
		boolean poisonCritical = false;
		if (isPoisoned) {
			if (rand.nextDouble() > 0.5) {
				poisonCritical = true;
				poisonDamage *= 2;
			}
			this.takeDamage(poisonDamage); // Poison damage each turn
			poisonTurnCount--;

			if (poisonCritical)
				System.out.println("Poison Critical Hit!");
			System.out.println(
					this.getName() + " takes " + poisonDamage + " poison damage! " + poisonTurnCount + " turns left.");

			if (poisonTurnCount <= 0) {
				isPoisoned = false;
				System.out.println(this.getName() + " is no longer poisoned!");
			}
		}
	}

	//check player can use ultimate or not
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
