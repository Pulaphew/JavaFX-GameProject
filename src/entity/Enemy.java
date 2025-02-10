package entity;

public abstract class Enemy extends Entity {

	public Enemy(String name, int maxHealth, int attackPower) {
		super(name, maxHealth, attackPower);
	}

	@Override
	public String toString() {
		return "Enemy [isAlive()=" + isAlive() + ", getName()=" + getName() + ", getMaxHealth()=" + getMaxHealth()
				+ ", getCurrentHealth()=" + getCurrentHealth() + ", getAttackPower()=" + getAttackPower()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
