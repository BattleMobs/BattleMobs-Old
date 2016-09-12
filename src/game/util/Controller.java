package game.util;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import game.listeners.PlayerDeathListener;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;

public class Controller {
	
	public static void reset(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		p.getInventory().setHeldItemSlot(4);
		p.setExp(0);
		p.setGameMode(GameMode.SURVIVAL);
		p.setExhaustion(0);
		p.setLevel(0);
		p.setFireTicks(0);
		p.setFlying(false);
		p.setWalkSpeed(0.4f);
		p.closeInventory();
		changeHealth(p, 20.0);
		DisguiseAPI.disguiseToAll(p,new PlayerDisguise(p.getName()));
	}
	
	public static void changeHealth(Player p, double health) {
		p.setMaxHealth(health);
		p.setHealth(p.getMaxHealth());
	}
	
	public static void heal(Player p, double heal) {
		if (heal+p.getHealth()<p.getMaxHealth()) {
			p.setHealth(p.getHealth()+heal);
		} else p.setHealth(p.getMaxHealth());
	}
	
	@SuppressWarnings("deprecation")
	public static void damage(Player p, EntityDamageByEntityEvent e, double damage) {
		e.setDamage(0);
		boolean crit = false;
		if (e.getDamager()instanceof Player) {
			Player k = (Player) e.getDamager();
			if(k.getVelocity().getY() < 0 &&
					!k.isOnGround() &&
					!k.getLocation().getBlock().getType().equals(Material.WATER) &&
					!k.getLocation().getBlock().getType().equals(Material.LADDER) &&
					!k.getLocation().getBlock().getType().equals(Material.VINE) &&
					!k.getLocation().getBlock().getRelative(0, 1, 0).getType().equals(Material.VINE) &&
					!k.hasPotionEffect(PotionEffectType.BLINDNESS) &&
					k.getVehicle() == null ) {
				crit = true;
			}
		}
		if (p.getHealth()-(crit?damage*1.6:damage)>=0.5) {
			p.setHealth(p.getHealth()-damage);
		} else PlayerDeathListener.kill(p, e);
	}
	
	public static void damage(Player p, double damage) {
		if (p.getHealth()-damage>=0.5) {
			p.setHealth(p.getHealth()-damage);
			p.damage(0);
		} else PlayerDeathListener.kill(p, null);
	}
	
}
