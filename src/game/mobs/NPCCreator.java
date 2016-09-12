package game.mobs;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import game.BattleMobs;
import game.util.Locations;

public class NPCCreator {
	
	private static Entity entity = null;
	private static EntityModifier em;
	
	public static Entity spawnNPC(String name, Mob mob, EntityType type, Location loc) {
		if (Locations.LOBBY_WORLD!=null) {
			for (Entity e : Locations.LOBBY_WORLD.getEntities()) if (e.getType()==type) e.remove();
			entity = null;
			while (entity == null) entity = Locations.LOBBY_WORLD.spawnEntity(loc, type);
			mob.editMob(entity);
			entity.setCustomName("§a§l" + name);
			entity.setPassenger(null);
			em = new EntityModifier(entity, BattleMobs.getInstance());
			em.modify().setNoAI(true);
			em.modify().setCanDespawn(false);
			em.modify().setInvulnerable(true);
			entity.teleport(Locations.getMobLocation(mob.getMob()));
			System.out.println("LOC: "+entity.getLocation().getX() + "|" +entity.getLocation().getY() + "|" + entity.getWorld().getName());
			return entity;
		} //else Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "rl");
		return null;
	}
	
}
