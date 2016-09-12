package game.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerItemDamageListener implements Listener {
	
	private ItemStack item, temp;
	private ItemMeta meta;
	private short data;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onItemDamage(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand()!=null) {
			item = p.getItemInHand();
			meta = item.getItemMeta();
			data =  p.getItemInHand().getData().getData();
			p.getItemInHand().setDurability((short) 0);
			temp = new ItemStack(item.getType(), item.getAmount(), data);
			temp.setItemMeta(meta);
			p.setItemInHand(temp);
			p.updateInventory();
		}
	}
	
}
