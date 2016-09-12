package game.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import game.database.Database;

public class AsyncPlayerChatListener implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		if (p.isOp()) e.setMessage(e.getMessage().replace('&', '§'));
		if (e.getMessage().equalsIgnoreCase("loc")) Bukkit.getConsoleSender().sendMessage("  new Location(MAP_WORLD, "+(float)loc.getX()+"f, "+(int)loc.getY()+"f, "+(float)loc.getZ()+"f, "+(int)loc.getYaw()+", "+(int)loc.getPitch()+");");
		if (e.getMessage().startsWith("money")) {
			String[] msg = e.getMessage().split(" ");
			Database.getData(p).editMoney(Integer.parseInt(msg[1]));
		}
		else Bukkit.broadcastMessage("§8[" + (p.isOp() ? "§c" : "§a") + "" + p.getName() + "§8] §7" + e.getMessage());
	}
	
}
