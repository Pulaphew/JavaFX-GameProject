package entity;

import gui.EnemyPane;
import gui.PlayerPane;

public abstract class Enemy extends Entity {
	
	private double attackAccuracy ;
	
	
	public Enemy(String name, int maxHealth, int attackPower) {
		super(name, maxHealth, attackPower);
		this.setAttackAccuracy(0.2);
	}
	
	public abstract String[] attack(Entity target , PlayerPane playerPane , EnemyPane enemyPane) ;
	
	@Override
	public String toString() {
		return "Enemy [isAlive()=" + isAlive() + ", getName()=" + getName() + ", getMaxHealth()=" + getMaxHealth()
				+ ", getCurrentHealth()=" + getCurrentHealth() + ", getAttackPower()=" + getAttackPower()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

	public double getAttackAccuracy() {
		return attackAccuracy;
	}

	public void setAttackAccuracy(double attackAccuracy) {
		this.attackAccuracy = attackAccuracy;
	}
	
	

}
