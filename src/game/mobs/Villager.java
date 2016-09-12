package game.mobs;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import game.util.Mobs;

public class Villager extends Mob {
	
	private Events events;
	
	@Override
	protected int getMob() {
		return Mobs.VILLAGER;
	}

	@Override
	protected EntityType getEntityType() {
		return EntityType.VILLAGER;
	}

	@Override
	protected void editMob(Entity entity) {
		org.bukkit.entity.Villager villager = (org.bukkit.entity.Villager) entity;
		villager.setCanPickupItems(false);
		villager.setMaxHealth(getMob() + 1);
		villager.setAdult();
		villager.setHealth(villager.getMaxHealth());
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
		
		@EventHandler
		public void onSpawn(EntitySpawnEvent e) {
			if (e.getEntityType()==EntityType.EXPERIENCE_ORB) e.getEntity().remove();
		}
		
	}

}
