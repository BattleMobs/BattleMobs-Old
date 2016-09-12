package game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.slikey.effectlib.EffectManager;
import game.database.Database;
import game.effects.EffectMaster;
import game.listeners.ListenerMaster;
import game.util.Locations;
import game.util.Scheduler;

public class BattleMobs extends JavaPlugin {
	
	private static BattleMobs instance;
	private static EffectManager effect;
	
	@Override
	public void onEnable() {
		instance = this;
		effect = new EffectManager(instance);
		Locations.init();
		Scheduler.schedule(20, new Runnable() {
			@Override
			public void run() {
				ListenerMaster.init();
				Database.init();
				EffectMaster.init();
			}
		});
	}
	
	@Override
	public void onDisable() {
		for (Player all : Bukkit.getOnlinePlayers()) all.kickPlayer("§8[§cSERVER IS RESTARTING!§8]");
		Database.save();
	}
	
	public static BattleMobs getInstance() {
		return instance;
	}
	
	public static EffectManager getEffectManager() {
		return effect;
	}
	
}
