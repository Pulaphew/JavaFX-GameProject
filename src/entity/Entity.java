package entity;


public abstract class Entity {

	// field
	private String name;
	private int maxHealth;
	private int currentHealth;
	private int attackPower;

	// constructor
	public Entity(String name, int maxHealth, int attackPower) {
		this.setName(name);
		this.setMaxHealth(maxHealth);
		this.setCurrentHealth(maxHealth);
		this.setAttackPower(attackPower);
	}

	// Player will lose if take damage until hp = 0
	// while Enemy take damage until hp = 0 Player will win
	
	public void takeDamage(int damage) {
		this.setCurrentHealth(this.getCurrentHealth() - damage);
	}
	

	// check that Entity is alive or not
	public boolean isAlive() {
		return currentHealth > 0;
	}

	// getter /setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	// handle case set is less than 0
	public void setMaxHealth(int maxHealth) {
		if (maxHealth < 0)
			this.maxHealth = 0;
		else
			this.maxHealth = maxHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		if (currentHealth < 0)
			this.currentHealth = 0;
		else
			this.currentHealth = currentHealth;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		if (attackPower < 0)
			this.attackPower = 0;
		else
			this.attackPower = attackPower;
	}

	@Override
	public String toString() {
		return "Entity [name=" + name + ", maxHealth=" + maxHealth + ", currentHealth=" + currentHealth
				+ ", attackPower=" + attackPower + "]";
	}
}
