package entity;

import gui.EnemyPane;
import gui.PlayerPane;

public abstract class Enemy extends Entity {
	
	public String battle_img = "";
	public String attack_img= "";
	public String takeDamage_img = "";
	public String backgroundStage = "";
	
	
	
	public Enemy(String name, int maxHealth, int attackPower) {
		super(name, maxHealth, attackPower);
	}
	
	public abstract String[] attack(Entity target , PlayerPane playerPane , EnemyPane enemyPane) ;
	
	@Override
	public String toString() {
		return "Enemy [isAlive()=" + isAlive() + ", getName()=" + getName() + ", getMaxHealth()=" + getMaxHealth()
				+ ", getCurrentHealth()=" + getCurrentHealth() + ", getAttackPower()=" + getAttackPower()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}
//	get image sprite
	public String getBattle_img() {
		return battle_img;
	}

	public String getAttack_img() {
		return attack_img;
	}

	public String getTakeDamage_img() {
		return takeDamage_img;
	}

	public String getBackgroundStage() {
		return backgroundStage;
	}
	
}
