package game.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.inventivegames.util.title.TitleManager;
import game.database.Database;
import game.database.PlayerData;
import game.mobs.MobMaster;
import game.scoreboard.Scoreboard;
import game.util.Controller;
import game.util.Item;
import game.util.Locations;
import game.util.Mobs;

public class PlayerDeathListener implements Listener {
	
	private static final PotionEffect FLASH = new PotionEffect(PotionEffectType.BLINDNESS, 20, 0);
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity().getType() == EntityType.PLAYER) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == DamageCause.FALL) e.setCancelled(true);
			if (p.getWorld() == Locations.LOBBY_WORLD) e.setCancelled(true);
			if (e.getDamage() >= p.getHealth()) kill(p, e);
		}
	}
	
	public static void kill(Player p, EntityDamageEvent e) {
		if (e!=null) { e.setCancelled(true); e.setDamage(0); }
		p.setHealth(p.getMaxHealth());
		Player k = null;
		if (e!=null) if (e instanceof EntityDamageByEntityEvent) if (((EntityDamageByEntityEvent) e).getDamager() instanceof Player) k = (Player) ((EntityDamageByEntityEvent) e).getDamager();
		if (k == null) for (Entity nearby : p.getNearbyEntities(15.0, 15.0, 15.0)) if (nearby instanceof Player) k = (Player) nearby;
		if (p.getWorld() != Locations.LOBBY_WORLD) onPlayerDeath(p, k);
		p.addPotionEffect(FLASH);
	}
	
	/*
	 * FAKE DEATH EVENT
	 */
	private static void onPlayerDeath(Player p, Player k) {
		
		PlayerData p_data=Database.getData(p),k_data;
		
		TitleManager.sendTitle(p,5,15,5, "§a+1 EXP");
		TitleManager.sendSubTitle(p,5,15,5, "§a+2$");
		MobMaster.getMob(p_data.getMobID()).clear(p);
		p_data.addDeath();
		p_data.getMob().update(p);
		p_data.setMobID(Mobs.NONE);
		p_data.addEXP(p,1);
		p_data.editMoney(2);
		
		Scoreboard.updateScoreboard(p);
		if (k != null) {
			k.playSound(p.getLocation(), Sound.ZOMBIE_DEATH, 1, 1);
			TitleManager.sendTitle(k,5,5,5, "§a+2 EXP");
			TitleManager.sendSubTitle(k,5,5,5, "§a+5$");
			k_data = Database.getData(k);
			k_data.addKill();
			k_data.getMob().update(k);
			k_data.addEXP(k,2);
			k_data.editMoney(5);
			Scoreboard.updateScoreboard(k);
			Bukkit.broadcastMessage("§a" + k.getName() + "§7 killed §c" + p.getName());
		} else {
			Bukkit.broadcastMessage("§c" + p.getName() + " §7died");
		}
		p.teleport(Locations.LOBBY);
		Controller.reset(p);
		p.getInventory().setItem(1, Item.createItem("§b§lINFO", "", Material.NETHER_STAR, 1, 0));
		p.getInventory().setItem(4, Item.createItem("§b§lTELEPORTER", "", Material.COMPASS, 1, 0));
		p.getInventory().setItem(7, Item.createItem("§b§lComing soon!", "", Material.NETHER_STAR, 1, 0));
	}
	
}
