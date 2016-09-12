package game.listeners;

import org.bukkit.event.Listener;

import game.BattleMobs;

public class ListenerMaster {
	
	/*
	 * INIT LISTENERS
	 */
	public static void init() {
		registerListener(new AsyncPlayerChatListener());
		registerListener(new BlockBreakListener());
		registerListener(new BlockPlaceListener());
		registerListener(new EntityDamageListener());
		registerListener(new FoodLevelChangeListener());
		registerListener(new InventoryClickListener());
		registerListener(new LeavesDecayListener());
		registerListener(new PlayerDeathListener());
		registerListener(new PlayerDropItemListener());
		registerListener(new PlayerInteractEntityListener());
		registerListener(new PlayerInteractListener());
		registerListener(new PlayerItemDamageListener());
		registerListener(new PlayerJoinListener());
		registerListener(new PlayerMoveListener());
		registerListener(new PlayerQuitListener());
		registerListener(new WeatherChangeListener());
	}
	
	/*
	 * REGISTER METHOD
	 */
	private static void registerListener(Listener l) {
		BattleMobs.getInstance().getServer().getPluginManager().registerEvents(l, BattleMobs.getInstance());
	}
	
}
