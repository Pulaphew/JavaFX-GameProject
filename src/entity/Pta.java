package entity;

import java.util.ArrayList;
import java.util.Random;

import ability.HealingPower;
import ability.UltimatePower;
import gui.EnemyPane;
import gui.PlayerPane;

public class Pta extends Enemy implements UltimatePower, HealingPower {

	private int ultimateTurnCount;
	private boolean isImmortal;
	private int immorTalTurnCount;
	private Random rand = new Random();

	public Pta(int maxHealth, int attackPower) {
		super(" PTa The ProgMeth ", maxHealth, attackPower);
		this.setUltimateTurnCount(0);
		this.setImmortal(false);
		this.setImmorTalTurnCount(0);
		this.setAttackAccuracy(0.6);
	}
	
	@Override
	public String heal() {
		int healAmount = this.getMaxHealth() / 4;
		this.setCurrentHealth(this.getCurrentHealth() + healAmount);
		String dialogueHeal = "\n" + this.getName() + " heals " + healAmount + " HP!\n";
		return dialogueHeal;
	}

	@Override
	public String useUltimate(Entity player , PlayerPane playerPane , EnemyPane enemyPane) {
		String dialogue ;
		dialogue = this.getName() + " uses ULTIMATE ATTACK!!!\n";
		player.takeDamage(this.getAttackPower() * 3);
		playerPane.updateHealthBar();
		
		dialogue += this.getName() + " deal damage equal " + this.getAttackPower() * 3 + " damage"; 

		// Heal and become immortal for 2 turns
		dialogue += heal();
		enemyPane.updateHealthBar();
		this.setImmortal(true);
		this.setImmorTalTurnCount(2);
		dialogue += this.getName() + " is now Immortal for 2 turns!";
		
		//update color after immortal
		enemyPane.updateEnemyImmortal();
		
		// reset ultimate turn count
		this.setUltimateTurnCount(0);
		return dialogue ;
	}

	@Override
	public String[] attack(Entity target , PlayerPane playerPane , EnemyPane enemyPane) {
		
		ArrayList<String> dialogues = new ArrayList<String>();
		
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
		playerPane.updateHealthBar();
		dialogue += this.getName() + " deals " + damage + " damage to " + target.getName();
		
		dialogues.add(dialogue);
		
		 // 40% chance to increase ultimate charge
	    double ultimateChargeChance = rand.nextInt(5); // 0 1 2 3 4
	    if (ultimateChargeChance < 2 && !this.isImmortal()) { // 40% probability
	        this.setUltimateTurnCount(this.getUltimateTurnCount() + 1);
	        System.out.println(this.getUltimateTurnCount());
	    }

	    // If enemy is immortal, reduce immortal turn count
	    if (this.isImmortal()) {
	        this.setImmorTalTurnCount(this.getImmorTalTurnCount() - 1);
	        System.out.println(this.getName() + " have immortal " + this.getImmorTalTurnCount() + " turn");
	        if (this.getImmorTalTurnCount() <= 0) {
	            this.setImmortal(false); 
	            enemyPane.updateEnemyImmortal();
	            String noImmortal =  this.getName() + "\n is no longer immortal!";
	            dialogues.add(noImmortal);
	        }
	    }
	   
	    String[] allDialogue = dialogues.toArray(new String[0]);
	    
	    return allDialogue ;
	}
	
	@Override
	public boolean canUseUltimate() {
		return ultimateTurnCount >= 2 ;
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
