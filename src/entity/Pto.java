package entity;

import java.util.Random;

import enemyAbility.HealingPower;
import enemyAbility.UltimatePower;

public class Pto extends Enemy implements UltimatePower, HealingPower {

	private int ultimateTurnCount;
	private boolean isImmortal;
	private int immorTalTurnCount;
	private Random rand = new Random();

	public Pto(int maxHealth, int attackPower) {
		super(" To The ProgMeth ", maxHealth, attackPower);
		this.setUltimateTurnCount(0);
		this.setImmortal(false);
		this.setImmorTalTurnCount(0);
		this.setAttackAccuracy(0.6);
	}
	
	@Override
	public void takeDamage(int damage) {
		if(this.isImmortal()) {
			System.out.println(this.getName() + " is immortal right now! no damage taken.");
		}
		else {
			this.setCurrentHealth(this.getCurrentHealth() - damage);
		}
	}

	@Override
	public void heal() {
		int healAmount = this.getMaxHealth() / 4;
		this.setCurrentHealth(this.getCurrentHealth() + healAmount);
		System.out.println(this.getName() + " heals " + healAmount + " HP!");
	}

	@Override
	public void useUltimate(Entity player) {
		if(this.canUseUltimate()) {
			System.out.println(this.getName() + " uses ULTIMATE ATTACK!!!");
			player.takeDamage(this.getAttackPower() * 3);

			// Heal and become immortal for 2 turns
			heal();
			this.setImmortal(true);
			this.setImmorTalTurnCount(2);
			System.out.println(this.getName() + " is now Immortal for 2 turns!");
		}
	}

	@Override
	public void attack(Entity target) {
		int damage = this.getAttackPower();
		boolean isCritical = false ;
		double criticalChance = rand.nextDouble();
		if(criticalChance > 0.5) {
			isCritical = true ;
			damage *= 3 ;
		}
		if(isCritical) {
			System.out.println(this.getName() + " lands a CRITICAL HIT!");
		}
		target.takeDamage(damage);
		System.out.println(this.getName() + " deals " + damage + " damage to " + target.getName());
		
		 // 40% chance to increase ultimate charge
	    double ultimateChargeChance = rand.nextDouble();
	    if (ultimateChargeChance < 0.4) { // 40% probability
	        this.setUltimateTurnCount(this.getUltimateTurnCount() + 1);
	    }

	    // If enemy is immortal, reduce immortal turn count
	    if (this.isImmortal()) {
	        this.setImmorTalTurnCount(this.getImmorTalTurnCount() - 1);
	        if (this.getImmorTalTurnCount() <= 0) {
	            this.setImmortal(false);
	            System.out.println(this.getName() + " is no longer immortal!");
	        }
	    }
	}
	
	@Override
	public boolean canUseUltimate() {
		return ultimateTurnCount >= 10 ;
	}

	// Getter & Setter
	public int getUltimateTurnCount() {
		return ultimateTurnCount;
	}

	public void setUltimateTurnCount(int ultimateTurnCount) {
		this.ultimateTurnCount = ultimateTurnCount;
	}

	public boolean isImmortal() {
		return isImmortal;
	}

	public void setImmortal(boolean isImmortal) {
		this.isImmortal = isImmortal;
	}

	public int getImmorTalTurnCount() {
		return immorTalTurnCount;
	}

	public void setImmorTalTurnCount(int immorTalTurnCount) {
		this.immorTalTurnCount = immorTalTurnCount;
	}
}
