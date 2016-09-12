package game.effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RegenerationEffect extends Effect {

	@Override
	protected void effect(Player p, int ticks, int amplifier) {
		remove(p);
		if (!super.isResistent(p)) { p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, ticks, amplifier)); super.getEffected().add(p); }
	}

	@Override
	protected void remove(Player p) {
		if (p.hasPotionEffect(PotionEffectType.REGENERATION)) { p.removePotionEffect(PotionEffectType.REGENERATION); super.getEffected().remove(p); }
	}

	@Override
	protected int getID() {
		return Effect.REGENERATION;
	}

	@Override
	protected boolean isEffected(Player p) {
		return p.hasPotionEffect(PotionEffectType.REGENERATION);
	}

}
