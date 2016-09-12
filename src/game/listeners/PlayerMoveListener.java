package game.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import game.util.Locations;

public class PlayerMoveListener implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld() == Locations.LOBBY_WORLD) {
			if (p.getLocation().getBlockY() <= 40) {
				p.teleport(Locations.LOBBY);
			}
		}
	}
	
}
