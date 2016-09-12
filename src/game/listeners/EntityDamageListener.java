package game.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import game.util.Locations;

public class EntityDamageListener implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity().getWorld().getName().equalsIgnoreCase(Locations.LOBBY.getWorld().getName())) e.setCancelled(true);
	}
	
}
