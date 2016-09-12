package game.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import game.database.Database;
import game.mobs.MobMaster;
import game.util.Locations;

public class PlayerInteractEntityListener implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld() == Locations.LOBBY_WORLD) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getClickedBlock().getType() == Material.WORKBENCH) e.setCancelled(true);
				if (e.getClickedBlock().getType() == Material.CHEST) e.setCancelled(true);
				if (e.getClickedBlock().getType() == Material.ANVIL) e.setCancelled(true);
				if (e.getClickedBlock().getType() == Material.BREWING_STAND) e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onInteract(final PlayerInteractEntityEvent e) {
		final Player p = e.getPlayer();
		if (p.getWorld() == Locations.LOBBY_WORLD) {
			if (e.getRightClicked() instanceof LivingEntity) {
				if (Database.getData(p).getLevel()>=(((int)((((LivingEntity)e.getRightClicked()).getHealth()))-1)/4)*25) {
					p.openInventory(MobMaster.getMob((int)((LivingEntity)e.getRightClicked()).getHealth()-1).createInventory(p));
				}
				if (e.getRightClicked().getType()==EntityType.VILLAGER) e.setCancelled(true);
			}
		}
	}
	
}
