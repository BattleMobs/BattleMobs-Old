package game.mobs;

import game.BattleMobs;
import game.util.Mobs;

public class MobMaster {
	
	private static Mob[] mobs = new Mob[20];
	
	public static void init() {
		
		BattleMobs.getInstance().getServer().getPluginManager().registerEvents(new Shop(), BattleMobs.getInstance());
		mobs[Mobs.ZOMBIE] = new Zombie();
		mobs[Mobs.WOLF] = new Wolf();
		mobs[Mobs.VILLAGER] = new Villager();
		mobs[Mobs.COW] = new Cow();
	}
	
	public static Mob getMob(int mob) {
		return mobs[mob];
	}
	
}
