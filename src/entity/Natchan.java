package entity;

import java.util.Random;

import ability.HealingPower;
import gui.EnemyPane;
import gui.PlayerPane;

public class Natchan extends Enemy implements HealingPower {

	private int HealingCounter;
	private int HealingUltimateCharge;
	Random rand = new Random();

	public Natchan(int maxHealth, int attackPower) {
		super("Natchan The DataAlgo", maxHealth, attackPower);
		this.setHealingCounter(0);
		this.battle_img = ClassLoader.getSystemResource("NatchanBattle.png").toString();
		this.attack_img = ClassLoader.getSystemResource("NatchanAttack.png").toString();
		this.takeDamage_img = ClassLoader.getSystemResource("NatchanTakeDamage.png").toString();
		this.backgroundStage = ClassLoader.getSystemResource("bgStage1.png").toString();
	}

	@Override
	public String[] attack(Entity target, PlayerPane playerPane, EnemyPane enemyPane) {

		String[] allDialogue = new String[1];
		target.takeDamage(this.getAttackPower());
		playerPane.updateHealthBar();
		String[] message = { "\"Maybe you should study harder, students.\"\n", "\"Who wants to drop this course?\"\n",
				"\"Why do I feel like you're slow, like Big O squared?\"\n" };
		String dialogue = message[rand.nextInt(2)] + this.getName() + " deal damage to you ,equal "
				+ this.getAttackPower();

		int healingChance = rand.nextInt(20);

		// 35 % chance to increase healing counter
		if (healingChance < 7) {
			HealingCounter++;
		}
		if (HealingCounter == 2) {
			String healDialogue = heal();
			enemyPane.updateHealthBar();
			dialogue += healDialogue;
			HealingUltimateCharge++;
			HealingCounter = 0;
		}

		// sysout to terminal for check
		System.out.println(this.getName() + "\nhave HealingCounter = " + this.getHealingCounter()
				+ "\nhave HealingUltimateCharge = " + this.getHealingUltimateCharge());
		allDialogue[0] = dialogue;
		return allDialogue;
	}

	@Override
	public String heal() {
		boolean isUltimate = false;
		// healing from maxHealth 1 in 5 (20%)
		int healAmount = this.getMaxHealth() / 10;
		// healing ultimate heal 50%
		if (HealingUltimateCharge == 5) {
			isUltimate = true;
			healAmount = this.getMaxHealth() / 5;
		}

		this.setCurrentHealth(this.getCurrentHealth() + healAmount);
		String healDialogue = "\n" + this.getName() + " heals " + healAmount + " HP!";
		if (isUltimate) {
			healDialogue = "\nNattee using ULTIMATE HEALING!!! " + healDialogue;
		}

		return healDialogue;
	}

	// Getter & Setter
	public int getHealingCounter() {
		return HealingCounter;
	}

	public void setHealingCounter(int healingCounter) {
		HealingCounter = healingCounter;
	}

	public int getHealingUltimateCharge() {
		return HealingUltimateCharge;
	}

	public void setHealingUltimateCharge(int healingUltimateCharge) {
		HealingUltimateCharge = healingUltimateCharge;
	}

}
