package ability;

import entity.Entity;
import gui.EnemyPane;
import gui.EntityPane;
import gui.PlayerPane;

public interface UltimatePower {
	public String useUltimate(Entity entity, PlayerPane entityPane , EnemyPane enemyPane) ;
	public boolean canUseUltimate() ;
}
