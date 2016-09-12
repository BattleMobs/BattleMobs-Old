package game.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import game.database.Database;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void onMove(PlayerQuitEvent e) {
		Database.getData(e.getPlayer()).updateTime();
		e.setQuitMessage("§8[§c-§8] "+(e.getPlayer().isOp()?"§c":"§7")+""+e.getPlayer().getName());
		
	}
	
}
