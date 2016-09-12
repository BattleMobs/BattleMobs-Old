package game.mobs;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import game.util.Mobs;

public class Cow extends Mob {
	
	private Events events;
	
	@Override
	protected int getMob() {
		return Mobs.COW;
	}

	@Override
	protected EntityType getEntityType() {
		return EntityType.COW;
	}

	@Override
	protected void editMob(Entity entity) {
		org.bukkit.entity.Cow cow = (org.bukkit.entity.Cow) entity;
		cow.setCanPickupItems(false);
		cow.setMaxHealth(getMob() + 1);
		cow.setAdult();
		cow.setHealth(cow.getMaxHealth());
	}

	@Override
	protected void equip(Player p) {

	}

	@Override
	protected Listener getListener() {
		events = new Events();
		return events;
	}

	@Override
	public void clear(Player p) {
		
	}
	
	private class Events implements Listener {
		
	}

}
