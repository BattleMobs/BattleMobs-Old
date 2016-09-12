package game.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.inventivegames.util.title.TitleManager;
import game.database.Database;
import game.database.PlayerData;
import game.mobs.MobMaster;
import game.scoreboard.Scoreboard;
import game.util.Controller;
import game.util.Item;
import game.util.Locations;
import game.util.Scheduler;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		MobMaster.init();
		final Player p = e.getPlayer();
		PlayerData data = Database.getData(p);
		
		e.setJoinMessage("§8[§a+§8]" + (p.isOp() ? "§c" : "§7") + " " + p.getName());
		
		// RESET + LOBBY_ITEMS
		Controller.reset(p);
		p.getInventory().setItem(1, Item.createItem("§b§lINFO", "", Material.NETHER_STAR, 1, 0));
		p.getInventory().setItem(4, Item.createItem("§b§lTELEPORTER", "", Material.COMPASS, 1, 0));
		p.getInventory().setItem(7, Item.createItem("§b§lComing soon!", "", Material.NETHER_STAR, 1, 0));
		
		// INTRO
		TitleManager.sendTitle(p, "§6BattleMobs");
		TitleManager.sendSubTitle(p, "§7Willkommen " + p.getName());
		p.playSound(Locations.LOBBY, Sound.LEVEL_UP, 1, 1);

		// DATABASE
		if (data == null) Database.createInsert(p);
		data = Database.getData(p);
		data.resetTime();
		if (!data.getName().equals(p.getName())) data.setName(p.getName());
		Scoreboard.updateScoreboard(p);
		
		// TELEPORT
		Scheduler.schedule(1, new Runnable() { 
			public void run() { p.teleport(Locations.LOBBY); } 
		});
	}
	
}
