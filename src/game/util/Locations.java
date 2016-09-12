package game.util;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public final class Locations {
	
	public static World LOBBY_WORLD;
	public static World MAP_WORLD;
	
	public static Location LOBBY;
	
	private static final Location[] MAP_LOCS = new Location[10];
	private static final Location[] MOB_LOCS = new Location[20];
	private static final Random random = new Random();
	
	public static void init() {
		LOBBY_WORLD = Bukkit.getWorld("Lobby");
		MAP_WORLD = Bukkit.getWorld("Map");
		
		MAP_LOCS[0] = new Location(MAP_WORLD, -1.5434864f, 61f, -33.25935f, 1, -4);
		MAP_LOCS[1] = new Location(MAP_WORLD, 31.848808f, 60f, -21.262424f, 59, -5);
		MAP_LOCS[2] = new Location(MAP_WORLD, 42.754173f, 60f, 13.138672f, 92, -1);
		MAP_LOCS[3] = new Location(MAP_WORLD, 28.318691f, 60f, 40.94689f, 136, -1);
		MAP_LOCS[4] = new Location(MAP_WORLD, -0.5174937f, 60f, 47.50994f, 185, -2);
		MAP_LOCS[5] = new Location(MAP_WORLD, -26.051785f, 61f, 37.75451f, 221, -6);
		MAP_LOCS[6] = new Location(MAP_WORLD, -40.869526f, 60f, 15.680045f, 251, -3);
		MAP_LOCS[7] = new Location(MAP_WORLD, -36.649036f, 60f, -16.436045f, 302, -3);
		MAP_LOCS[8] = new Location(MAP_WORLD, -20.147245f, 60f, -31.109928f, 336, -2);
		MAP_LOCS[9] = new Location(MAP_WORLD, -0.34216967f, 61f, -2.0251234f, 56, 0);
		
		LOBBY = new Location(LOBBY_WORLD, 100.5f, 102.0f, 100.5f, 90, 0);
		MOB_LOCS[Mobs.ZOMBIE] = new Location(LOBBY_WORLD, 87.5, 101, 100.5, -90, 0);
		MOB_LOCS[Mobs.WOLF] = new Location(LOBBY_WORLD, 113.5, 101, 100.5, 90, 0);
		MOB_LOCS[Mobs.VILLAGER] = new Location(LOBBY_WORLD, 100.5f, 101f, 113.5f, 180, 0);
		MOB_LOCS[Mobs.COW] = new Location(LOBBY_WORLD, 100.5f, 101f, 87.5f, 0, 0);
	}
	
	public static Location getRandomMapLocation() {
		return MAP_LOCS[random.nextInt(10)];
	}
	
	public static Location getMobLocation(int mob) {
		return MOB_LOCS[mob];
	}
	
}
