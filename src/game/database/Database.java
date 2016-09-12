package game.database;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import game.util.Mobs;
import game.util.Scheduler;

public class Database {
	
	private static List<PlayerData> database;
	private static long ago;
	
	public static void init() {
		database = Reader.read();
		Scheduler.schedule(20, 2400, new Runnable() {
			@Override
			public void run() {
				ago = System.currentTimeMillis();
				save();
				Bukkit.getConsoleSender().sendMessage("§8[§aBattleMobs§8]§7 Database saved! §8(§2"+(System.currentTimeMillis()-ago)+"ms§8)");
			}
		});
	}
	
	public static void createInsert(Player p) {
		System.out.println("[BattleMobs] "+p.getName()+" has been added to database!");
		MobData[] mobs = new MobData[20];
		for (int n = 0 ; n < 20; n++) {
			mobs[n] = new MobData(Mobs.getENName(n), 1, 0, 0, 0);
		}
		PlayerData data = new PlayerData(p.getName(), p.getUniqueId().toString(), 0, 1,25,100, mobs);
		database.add(data);
		save();
	}
	
	public static PlayerData getData(Player p) {
		for (PlayerData data : database) if (data.getUUID().equalsIgnoreCase(p.getUniqueId().toString())) return data;
		return null;
	}
	
	public static void save() {
		Writer.write(database);
	}
	
}
