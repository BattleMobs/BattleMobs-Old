package game.mobs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import game.database.Database;
import game.database.MobData;
import game.util.Item;
import game.util.Mobs;

public class MobMenu {
	
	private static int upgrades;
	private static Inventory inv;
	private static ItemStack info;
	private static ItemStack blocked;
	private static ItemStack blocked_buyable;
	private static ItemStack ability_1;
	private static ItemStack ability_2;
	private static ItemStack ability_3;
	private static ItemStack item_1;
	private static ItemStack item_2;
	private static ItemStack item_3;
	private static ItemStack item_4;
	private static ItemStack item_5;
	private static ItemStack item_6;
	private static MobData data;
	
	public static Inventory createInv(Mob mob, Player p) {
		data = Database.getData(p).getMobData(mob.getMob());
		upgrades = data.getBoughtUpgrades();
		inv = Bukkit.createInventory(null, 54, "§8§l[§2§l"+mob.getName()+"§8§l]");
		info = Item.createItem("§b§lSTATS", 
				"§7Played§8:§b "+(data.getKills()+data.getDeaths())+
				"#§7Kills§8:§b "+data.getKills()+
				"#§7Deaths§8:§b "+data.getDeaths()+
				"#§7Stufe§8:§b "+data.getLevel()+
				"##§b§lCLICK TO PLAY!"
				, Material.NETHER_STAR, 1, 0);
		inv.setItem(4, info);
		
		ability_1 = Mobs.getAbilityItem(mob.getMob(), 1);
		ability_2 = Mobs.getAbilityItem(mob.getMob(), 2);
		ability_3 = Mobs.getAbilityItem(mob.getMob(), 3);
		item_1 = Mobs.getUpgradeItem1(mob.getMob(), 1);
		item_2 = Mobs.getUpgradeItem1(mob.getMob(), 2);
		item_3 = Mobs.getUpgradeItem1(mob.getMob(), 3);
		item_4 = Mobs.getUpgradeItem2(mob.getMob(), 1);
		item_5 = Mobs.getUpgradeItem2(mob.getMob(), 2);
		item_6 = Mobs.getUpgradeItem2(mob.getMob(), 3);
		
		
		blocked = Item.createItem("§4§lGESPERRT!", "#§7Erreiche zuerst#§7niedrigere Upgrades!", Material.BARRIER, 1, 0);
		blocked_buyable = Item.createItem("§"+(Database.getData(p).getMoney()>=100&&data.getLevel()>=(((data.getBoughtUpgrades()+1)*15)+10)?"a":"c")+"§lCLICK TO BUY!",
				"#§7Moblevel§8: §"+(data.getLevel()>=(((data.getBoughtUpgrades()+1)*15)+10)?"a":"c")+""+(((data.getBoughtUpgrades()+1)*15)+10)+
				"#§7Prize§8: §"+(Database.getData(p).getMoney()>=100?"a":"c")+"100", Material.BARRIER, 1, 0);
		
		
		
		inv.setItem(19, ability_1);
		inv.setItem(22, ability_2);
		inv.setItem(25, ability_3);
		
		if (upgrades < 1) inv.setItem(28, blocked_buyable);
		else inv.setItem(28, item_1);
		if (upgrades < 2) inv.setItem(31, upgrades < 1 ? blocked : blocked_buyable);
		else inv.setItem(31, item_2);
		if (upgrades < 3) inv.setItem(34, upgrades < 2 ? blocked : blocked_buyable);
		else inv.setItem(34, item_3);
		if (upgrades < 4) inv.setItem(37, upgrades < 3 ? blocked : blocked_buyable);
		else inv.setItem(37, item_4);
		if (upgrades < 5) inv.setItem(40, upgrades < 4 ? blocked : blocked_buyable);
		else inv.setItem(40, item_5);
		if (upgrades < 6) inv.setItem(43, upgrades < 5 ? blocked : blocked_buyable);
		else inv.setItem(43, item_6);
		
		
		
		
		
		return inv;
	}
	
}
