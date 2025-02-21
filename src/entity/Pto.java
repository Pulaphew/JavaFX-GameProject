package entity;

import java.util.Random;

import ability.HealingPower;
import ability.UltimatePower;
import gui.EnemyPane;
import gui.EntityPane;
import gui.PlayerPane;

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
	public String heal() {
		int healAmount = this.getMaxHealth() / 4;
		this.setCurrentHealth(this.getCurrentHealth() + healAmount);
		String dialogueHeal = "\n" + this.getName() + " heals " + healAmount + " HP!\n";
		return dialogueHeal;
	}

	@Override
	public String useUltimate(Entity player , EntityPane playerPane) {
		String dialogue = null ;
		if(this.canUseUltimate()) {
			dialogue = this.getName() + " uses ULTIMATE ATTACK!!!\n";
			player.takeDamage(this.getAttackPower() * 3);
			dialogue += this.getName() + " deal damage equal" + this.getAttackPower() * 3 + " damage\n"; 

			// Heal and become immortal for 2 turns
			dialogue += heal();
			this.setImmortal(true);
			this.setImmorTalTurnCount(2);
			dialogue += this.getName() + " is now Immortal for 2 turns!";
		}
		return dialogue ;
	}

	@Override
	public String[] attack(Entity target , PlayerPane playerPane , EnemyPane enemyPane) {
		String[] allDialogue = new String[1];
		String dialogue = "" ;
		int damage = this.getAttackPower();
		boolean isCritical = false ;
		double criticalChance = rand.nextDouble();
		if(criticalChance > 0.5) {
			isCritical = true ;
			damage *= 3 ;
		}
		if(isCritical) {
			dialogue += this.getName() + " lands a CRITICAL HIT!\n";
		}
		target.takeDamage(damage);
		dialogue += this.getName() + " deals " + damage + " damage to " + target.getName();
		
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
	            dialogue += this.getName() + "\n is no longer immortal!";
	        }
	    }
	    allDialogue[0] = dialogue ;
	    return allDialogue ;
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
