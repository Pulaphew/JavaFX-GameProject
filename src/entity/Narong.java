package entity;

import java.util.Random;

import enemyAbility.PoisonousPower;

public class Narong extends Enemy implements PoisonousPower {

	private Random rand = new Random();
	private int poisonDamage;

	public Narong(int maxHealth, int attackPower) {
		super(" Narongdech The DigLo ", maxHealth, attackPower);
		// set default as 10
		this.setPoisonDamage(10);
		this.setAttackAccuracy(0.4);
	}

	@Override
	public void attack(Entity target) {
		target.takeDamage(this.getAttackPower());

		if (target instanceof Player && rand.nextDouble() > 0.5) {
			applyPoison((Player) target);
		}
	}

	@Override
	public void applyPoison(Player player) {
		System.out.println(player.getName() + " is poisoned!");
		// make player poisoned
		player.applyPoison();
	}

	// Getter & Setter
	public int getPoisonDamage() {
		return poisonDamage;
	}

	public void setPoisonDamage(int poisonDamage) {
		this.poisonDamage = poisonDamage;
	}

}
