package game.effects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public abstract class Effect {
	
	private List<Player> effected = new ArrayList<>();
	private List<Player> resistent = new ArrayList<>();
	private List<Player> buffer = new ArrayList<>();
	
	public static final int POISON = 0;
	public static final int SLOW = 1;
	public static final int REGENERATION = 2;
	public static final int STUN = 3;
	public static final int BLIND = 4;
	
	protected void update() {
		buffer.clear();
		for (Player p : effected) if (!isEffected(p) || resistent.contains(p)) buffer.add(p);
		for (Player p : buffer) { remove(p); effected.remove(p); }
	}
	
	public void resistent(Player p) {
		if (!resistent.contains(p)) resistent.add(p);
		remove(p);
	}
	
	public void unresistent(Player p) {
		if (resistent.contains(p)) resistent.remove(p);
	}
	
	protected boolean isResistent(Player p) {
		return resistent.contains(p);
	}
	
	public List<Player> getEffected() {
		return effected;
	}
	
	public List<Player> getResistent() {
		return resistent;
	}
	
	protected abstract void effect(Player p, int ticks, int amplifier);
	protected abstract void remove(Player p);
	protected abstract int getID();
	protected abstract boolean isEffected(Player p);
	
}
