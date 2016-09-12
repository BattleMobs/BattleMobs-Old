package game.database;

import org.bukkit.entity.Player;

public class MobData {
	
	private String name;
	private int level;
	private int bought;
	private int kills;
	private int deaths;
	
	public MobData(String name, int level, int bought, int kills, int deaths) {
		this.name = name;
		this.level = level;
		this.bought = bought;
		this.kills = kills;
		this.deaths = deaths;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getBoughtUpgrades() {
		return bought;
	}
	
	public int getKills() {
		return kills;
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public void addKill() {
		kills++;
	}
	
	public void addDeath() {
		deaths++;
	}
	
	public void update(Player p) {
		int level = (4+kills+deaths)/4;
		if (this.level < level) {
			this.level = level;
			p.sendMessage("§8§l> §aNeue Stufe! §8§l[§7"+name+"§8>>§7"+level+"§8§l]");
		}
	}
	
	public void upgrade() {
		bought++;
	}
	
}
