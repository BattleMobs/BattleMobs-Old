package game.mobs;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import game.BattleMobs;
import game.database.Database;
import game.util.Locations;
import game.util.Mobs;

public abstract class Mob {
	
	public Mob() {
		NPCCreator.spawnNPC(getName(), this, getEntityType(), getLocation());
		BattleMobs.getInstance().getServer().getPluginManager().registerEvents(getListener(), BattleMobs.getInstance());
	}
	
	public String getName() {
		return Mobs.getGERName(getMob());
	}
	
	protected abstract int getMob();
	protected abstract EntityType getEntityType();
	protected abstract void editMob(Entity entity);
	protected abstract void equip(Player p);
	protected abstract Listener getListener();
	public abstract void clear(Player p);
	
	public Inventory createInventory(Player p) {
		return MobMenu.createInv(this, p);
	}
	
	protected int getBoughtUpgrades(Player p) {
		return Database.getData(p).getMobData(getMob()).getBoughtUpgrades();
	}
	
	protected boolean isMob(Player p) {
		return Database.getData(p).getMobID() == getMob();
	}
	
	protected Location getLocation() {
		return Locations.getMobLocation(getMob());
	}
	
}
