package entity;

import java.util.Random;

import enemyAbility.HealingPower;

public class Nattee extends Enemy implements HealingPower {

	private int HealingCounter;
	private int HealingUltimateCharge;
	Random rand = new Random();

	public Nattee(int maxHealth, int attackPower) {
		super(" Nattee The DataAlgo ", maxHealth, attackPower);
		this.setHealingCounter(0);
		this.setAttackAccuracy(0.2);
	}

	@Override
	public void attack(Entity target) {
		target.takeDamage(this.getAttackPower());

		double healingChance = rand.nextDouble();

		// 35 % chance to increase healing counter
		if (healingChance < 0.35) {
			HealingCounter++;
		}
		if (HealingCounter == 3) {
			heal();
			HealingUltimateCharge++;
			HealingCounter = 0;
		}
	}

	@Override
	public void heal() {
		// healing from maxHealth 1 in 5 (20%)
		int healAmount = this.getMaxHealth() / 5;
		// healing ultimate heal 50%
		if (HealingUltimateCharge == 5) {
			healAmount = this.getMaxHealth() / 2;
		}

		this.setCurrentHealth(this.getCurrentHealth() + healAmount);
		String healDialogue = this.getName() + " heals " + healAmount + " HP!";
		System.out.println(healDialogue);
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
