package game.effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoisonEffect extends Effect {
	
	@Override
	public void effect(Player p, int ticks, int amplifier) {
		remove(p);
		if (!super.isResistent(p)) { p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, ticks, amplifier)); super.getEffected().add(p); }
	}

	@Override
	public void remove(Player p) {
		if (p.hasPotionEffect(PotionEffectType.POISON)) { p.removePotionEffect(PotionEffectType.POISON); super.getEffected().remove(p); }
	}
	
	@Override
	public int getID() {
		return Effect.POISON;
	}

	@Override
	protected boolean isEffected(Player p) {
		return p.hasPotionEffect(PotionEffectType.POISON);
	}
	
}
