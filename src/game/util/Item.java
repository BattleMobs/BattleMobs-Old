package game.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
	
	private static ItemStack item;
	private static ItemMeta meta;
	private static String[] description;
	private static List<String> lore = new ArrayList<>();
	private static int n;
	
	public static ItemStack createItem(String name, String dscp, Material mat, int amount, int data) {
		item = new ItemStack(mat, amount, (short) data);
		meta = item.getItemMeta();
		meta.setDisplayName(name);
		lore.clear();
		description = dscp.split("#");
		for (n=0;n<description.length;n++) lore.add(description[n]);
		if (!dscp.isEmpty()) meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
}
