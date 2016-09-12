package game.effects;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.slikey.effectlib.effect.SphereEffect;
import de.slikey.effectlib.util.DynamicLocation;
import de.slikey.effectlib.util.ParticleEffect;
import game.BattleMobs;
import game.util.Scheduler;

public class StunEffect extends Effect implements Listener {
	
	private SphereEffect sphere;
	private Location loc;
	
	public StunEffect() {
		BattleMobs.getInstance().getServer().getPluginManager().registerEvents(this, BattleMobs.getInstance());
	}
	
	@Override
	protected void effect(final Player p, int ticks, int amplifier) {
		remove(p);
		if (!super.isResistent(p)) {
			super.getEffected().add(p);
			sphere = new SphereEffect(BattleMobs.getEffectManager());
			loc = p.getLocation();
			loc.setY(loc.getY()+0.5);
			sphere.setDynamicOrigin(new DynamicLocation(loc));
			sphere.setDynamicTarget(new DynamicLocation(loc));
			sphere.particle = ParticleEffect.ENCHANTMENT_TABLE;
			sphere.duration = ticks*50;
			sphere.start();
			Scheduler.schedule(ticks, new Runnable() {
				public void run() {
					remove(p);
				}
			});
		}
	}

	@Override
	protected void remove(Player p) {
		if (isEffected(p)) { super.getEffected().remove(p); }
	}

	@Override
	protected int getID() {
		return Effect.STUN;
	}

	@Override
	protected boolean isEffected(Player p) {
		return super.getEffected().contains(p);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (isEffected(e.getPlayer())) {
			Location to = e.getFrom();
			to.setPitch(e.getTo().getPitch());
			to.setYaw(e.getTo().getYaw());
			e.setTo(to);
		}
	}
	
}
