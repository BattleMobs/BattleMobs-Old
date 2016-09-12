package game.effects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import game.util.Locations;
import game.util.Scheduler;

public class EffectMaster {
	
	private static List<Effect> effects = new ArrayList<>();
	
	public static void init() {
		effects.add(new PoisonEffect());
		effects.add(new SlowEffect());
		effects.add(new RegenerationEffect());
		effects.add(new StunEffect());
		effects.add(new BlindEffect());
		Scheduler.schedule(20, 5, new Runnable() {
			public void run() { update(); }
		});
	}
	
	private static void update() {
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (Locations.LOBBY_WORLD != null) if (all.getWorld().getName().equalsIgnoreCase(Locations.LOBBY_WORLD.getName())) {
				for (Effect eff : effects) eff.unresistent(all);
			}
		}
		for (Effect eff : effects) eff.update();
	}
	
	public static void effect(int effect, Player p, int ticks, int amplifier) {
		getEffect(effect).effect(p, ticks, amplifier);
	}
	
	public static Effect getEffect(int id) {
		for (Effect eff : effects) if (eff.getID()==id) return eff;
		return null;
	}
	
}
