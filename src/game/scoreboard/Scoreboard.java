package game.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import game.database.Database;
import game.database.PlayerData;

public class Scoreboard {
	
	private static PlayerData data;
	private static int kills, deaths, n;
	
	public static void updateScoreboard(Player p) {
		org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = scoreboard.registerNewObjective("...", "...");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§8[§6§lSTATS§8]");
		data = Database.getData(p);
		
		kills = 0;
		deaths = 0;
		
		for (n = 0; n < 20; n++) kills+=data.getMobData(n).getKills();
		for (n = 0; n < 20; n++) deaths+=data.getMobData(n).getDeaths();
		
		addLine(obj,6,"§7Level§8: §e" + data.getLevel());
		addLine(obj,5,"§7EXP to level§8: §e" + data.getEXPToNextLevel());
		addLine(obj,4,"§7Money§8: §e$" + data.getMoney());
		addLine(obj,3,"§7Kills§8: §e" + kills);
		addLine(obj,2,"§7Deaths§8: §e" + deaths);
		addLine(obj,1,"§7KDR§8: §e" + ((float)kills/(deaths!=0?deaths:1)));
		addLine(obj,0,"§7Ping§8: §e" + ((CraftPlayer) p).getHandle().ping + "ms");
		p.setScoreboard(scoreboard);
	}
	
	private static void addLine(Objective obj, int n, String data) {
		Score s = obj.getScore(data);
		s.setScore(n);
	}
	
}
