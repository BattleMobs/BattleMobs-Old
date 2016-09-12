package game.mobs;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.inventivegames.util.title.TitleManager;
import game.database.Database;
import game.scoreboard.Scoreboard;
import game.util.Locations;
import game.util.Mobs;
import game.util.Scheduler;

public class Shop implements Listener {
	
	private Player p;
	private Inventory inv;
	private ItemStack item;
	private ItemMeta meta;
	private int mob, upgrades;
	private String name;
	
	@EventHandler
	public void onShop(InventoryClickEvent e) {
		p = (Player) e.getWhoClicked();
		inv = e.getInventory();
		if (p.getWorld().getName().equals(Locations.LOBBY.getWorld().getName())) {
			mob = Mobs.getMob(inv.getName().substring(9).split("]")[0]);
			if (mob != -1) {
				if (e.getCurrentItem() != null) {
					item = e.getCurrentItem();
					if (item.hasItemMeta()) {
						meta = item.getItemMeta();
						if (meta.getDisplayName().equals("§b§lSTATS")) {
							MobMaster.getMob(mob).equip(p);
						}
						if (meta.getDisplayName().equals("§a§lCLICK TO BUY!")) {
							upgrades = Database.getData(p).getMobData(mob).getBoughtUpgrades();
							Database.getData(p).editMoney(-100);
							Database.getData(p).getMobData(mob).upgrade();
							p.closeInventory();
							
							name = upgrades/3 < 1?
									Mobs.getUpgradeItem1(mob, (upgrades+1)%3).getItemMeta().getDisplayName() :
									Mobs.getUpgradeItem2(mob, (upgrades+1)%3).getItemMeta().getDisplayName();
							
							TitleManager.reset(p);
							TitleManager.sendTitle(p, 5, 30, 5, name);
							TitleManager.sendSubTitle(p, 5, 30, 5, "§8- §7Upgrade unlocked! §8-");
							p.playSound(Locations.getMobLocation(mob), Sound.ANVIL_USE, 1, 1);
							
							Scheduler.schedule(40, new Runnable() {
								public void run() { p.openInventory(MobMenu.createInv(MobMaster.getMob(mob), p)); }
							});
							Scoreboard.updateScoreboard(p);
						}
						if (meta.getDisplayName().equals("§c§lCLICK TO BUY!")) {
							p.playSound(Locations.getMobLocation(mob), Sound.ZOMBIE_WOOD, 1, 1);
						}
					}
				}
			}
		}
	}
	
}
