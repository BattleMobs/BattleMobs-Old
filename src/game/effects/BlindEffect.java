package game.effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlindEffect extends Effect {
	
	@Override
	public void effect(Player p, int ticks, int amplifier) {
		remove(p);
		if (!super.isResistent(p)) { p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, ticks, amplifier)); super.getEffected().add(p); }
	}

	@Override
	public void remove(Player p) {
		if (p.hasPotionEffect(PotionEffectType.BLINDNESS)) { p.removePotionEffect(PotionEffectType.BLINDNESS); super.getEffected().remove(p); }
	}
	
	@Override
	public int getID() {
		return Effect.BLIND;
	}

	@Override
	protected boolean isEffected(Player p) {
		return p.hasPotionEffect(PotionEffectType.BLINDNESS);
	}
	
}
