package game.database;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.inventivegames.util.title.TitleManager;
import game.util.Mobs;

public class PlayerData {
	
	private String name;
	private String uuid;
	private float onlinetimeins;
	private int level;
	private int exp;
	private int money;
	private int exptonextlevel;
	private MobData[] mobs = new MobData[20];
	private int mob = Mobs.NONE;
	
	private long onlinesince = 0;
		
	public PlayerData(String name, String uuid, float onlinetimeinh, int level, int exp, int money, MobData[] mobs) {
		this.name = name;
		this.uuid = uuid;
		this.onlinetimeins = onlinetimeinh;
		this.level = level;
		this.exp = exp;
		this.money = money;
		this.mobs = mobs;
		addEXP(Bukkit.getPlayer(name),0);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUUID() {
		return uuid;
	}
	
	public float getOnlineTimeInS() {
		return onlinetimeins;
	}
	
	public void updateTime() {
		if (System.currentTimeMillis()-onlinesince<10000)this.onlinetimeins+=(System.currentTimeMillis()-onlinesince)/1000.0f;
		resetTime();
	}
	
	public void resetTime() {
		onlinesince = System.currentTimeMillis();
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getEXP() {
		return exp;
	}
	
	public int getEXPToNextLevel() {
		return exptonextlevel;
	}
	
	public void addEXP(Player p, int exp) {
		
		this.exp += exp;
		int n, exptonextlevel;
		exptonextlevel = 0;
		for (n=0;n<=level;n++) exptonextlevel+=getValue(n+1);
		exptonextlevel-=this.exp;
		this.exptonextlevel = exptonextlevel;
		if (exptonextlevel<=0) {
			level++;
			addEXP(p, 0);
			p.sendMessage("§8> §a§lLEVEL UP§8§l! §8[ §2" + level + " §8]");
			p.playSound(p.getLocation(), Sound.ZOMBIE_WOOD, 1, 1);
			TitleManager.reset(p);
			TitleManager.sendTitle(p,"§aLEVEL UP!");
			TitleManager.sendSubTitle(p,"§8- §2§l"+level+" §8-");
		}
		
	}
	
	public int getMoney() {
		return money;
	}
	
	public void editMoney(int money) {
		this.money+=money;
	}
	
	public MobData getMobData(int mob) {
		return mobs[mob];
	}
	
	public int getMobID() {
		return mob;
	}
	
	public MobData getMob() {
		if (mob != -1) return mobs[mob];
		return null;
	}
	
	public void setMobID(int mob) {
		this.mob = mob;
	}
	
	public void addKill() {
		if (mob >= 0 && mob < 20) mobs[mob].addKill();
	}
	
	public void addDeath() {
		if (mob >= 0 && mob < 20) mobs[mob].addDeath();
	}
	
	private int getValue(int x) {
		return (int) (Math.pow(1.55f, (x*0.1))+19);
	}
	
}
