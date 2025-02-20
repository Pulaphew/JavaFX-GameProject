package enemyAbility;

import entity.Entity;
import gui.EntityPane;

public interface UltimatePower {
	public String useUltimate(Entity entity, EntityPane entityPane) ;
	public boolean canUseUltimate() ;
}
