package game.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import game.database.Database;
import game.effects.Effect;
import game.effects.EffectMaster;
import game.util.Controller;
import game.util.Item;
import game.util.Locations;
import game.util.Mobs;
import game.util.PlayerTask;
import game.util.Scheduler;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

public class Zombie extends Mob {
	
	private static final ItemStack ITEM1 = Item.createItem("§a§lSeelenbrecher", "", Material.IRON_SWORD, 1, 0);
	private static final ItemStack ITEM2 = Item.createItem("§a§lBlut der Untoten", "", Material.SKULL_ITEM, 1, 2);
	private static final ItemStack ITEM3 = Item.createItem("§a§lArmee der Toten", "", Material.MONSTER_EGG, 1, 54);
	private Events events;
	
	@Override
	protected int getMob() {
		return Mobs.ZOMBIE;
	}
	
	@Override
	protected EntityType getEntityType() {
		return EntityType.ZOMBIE;
	}
	
	@Override
	protected void editMob(Entity entity) {
		org.bukkit.entity.Zombie zombie = (org.bukkit.entity.Zombie) entity;
		zombie.setCanPickupItems(false);
		zombie.setMaxHealth(getMob()+1);
		zombie.setBaby(false);
		zombie.setVillager(false);
		zombie.setHealth(zombie.getMaxHealth());
	}

	@Override
	protected void equip(final Player p) {
		
		Controller.reset(p);
		Controller.changeHealth(p, 40.0);
		
		p.getInventory().setHeldItemSlot(0);
		
		p.setWalkSpeed(0.15f);
		EffectMaster.getEffect(Effect.POISON).resistent(p);
		
		p.getInventory().setItem(0, ITEM1);
		p.getInventory().setItem(1, ITEM2);
		p.getInventory().setItem(2, ITEM3);
		
		DisguiseAPI.disguiseToAll(p, new MobDisguise(DisguiseType.ZOMBIE, true));
		Database.getData(p).setMobID(getMob());
		
		p.teleport(Locations.getRandomMapLocation());
	}

	@Override
	protected Listener getListener() {
		events = new Events();
		return events;
	}

	@Override
	public void clear(Player p) {
		events.clear(p);
	}
	
	/*
	 * 
	 * GAMEPLAY LISTENER
	 * 
	 */
	
	private class Events implements Listener {
		
		private List<PlayerTask> ability2 = new ArrayList<>();
		private List<PlayerTask> ability3 = new ArrayList<>();
		private final PotionEffect FLASH = new PotionEffect(PotionEffectType.BLINDNESS, 20, 0);
		
		@EventHandler (priority = EventPriority.HIGHEST)
		public void onDamage(EntityDamageByEntityEvent e) {
			/*
			 * - ABILITY 1
			 * - UPGRADE 1.1
			 * - UPGRADE 1.2
			 * - ABILITY 2
			 */
			if (e.getEntity().getWorld()==Locations.MAP_WORLD) {
				if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
					Player p = (Player) e.getEntity(), k = (Player) e.getDamager();
					int level = getBoughtUpgrades(k);
					if (isMob(k)) {
						if (k.getItemInHand()!=null&&k.getItemInHand().getType()==Material.IRON_SWORD) {
							Controller.damage(p, e, level < 4 ? 3.0 : 6.0);
							if (level>=1) EffectMaster.effect(Effect.SLOW, p, 20, 0);
							if (k.getWalkSpeed() > 0.3f) Controller.heal(k, level < 5 ? (level < 4 ? 1.5 : 3.0) : (level < 4 ? 3.0 : 6.0));
						} else { ((EntityDamageEvent)e).setCancelled(true); e.setDamage(0.0); }
					}
				}
			}
		}
		
		@EventHandler
		public void onInteract(PlayerInteractEvent e) {
			/*
			 * - ABILITY 2
			 * - ABILITY 3
			 */
			if (e.getPlayer().getWorld()==Locations.MAP_WORLD) {
				final Player p = e.getPlayer();
				if (isMob(p)) {
					if (e.getAction()==Action.RIGHT_CLICK_AIR||e.getAction()==Action.RIGHT_CLICK_BLOCK) {
						if (p.getItemInHand()!=null) {
							int level = getBoughtUpgrades(p);
							if (p.getItemInHand().getType()==Material.SKULL_ITEM) {
								if (getPlayerTask(ability2, p)==null) {
									final PlayerTask task = new PlayerTask(p);
									final ItemStack cooldown = Item.createItem("§7§lBlut der Untoten", "", Material.FIREWORK_CHARGE, level<2?21:16, 0);
									final ItemStack blocked = Item.createItem("§c§lBlut der Untoten", "", Material.BARRIER, 1, 0);
									
									p.setWalkSpeed(0.5f);
									p.addPotionEffect(FLASH);
									p.getInventory().setItem(1, blocked);
									p.updateInventory();
									p.playSound(p.getLocation(), Sound.ZOMBIE_WOOD, 1, 1);
									EffectMaster.effect(Effect.REGENERATION, p, 100, 1);
									
									task.addTask(0,Scheduler.schedule(level<2?500:400, new Runnable() {
										public void run() {
											task.cancelTasks(1);
											ability2.remove(task);
											p.getInventory().setItem(1, ITEM2);
											p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
										}
									}));
									task.addTask(1,Scheduler.schedule(0,20, new Runnable() {
										public void run() {
											if (p.getInventory().getItem(1).getType()==Material.FIREWORK_CHARGE) {
												if (cooldown.getAmount()>1) cooldown.setAmount(cooldown.getAmount()-1);
												p.getInventory().setItem(1, cooldown);
												p.updateInventory();
											}
										}
									}));
									task.addTask(2,Scheduler.schedule(100, new Runnable() {
										public void run() {
											p.getInventory().setItem(1, cooldown);
											p.updateInventory();
											p.setWalkSpeed(0.15f);
											p.addPotionEffect(FLASH);
											p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 1, 1);
										}
									}));
									ability2.add(task);
								}
							}
							if (p.getItemInHand().getType()==Material.MONSTER_EGG) {
								if (getPlayerTask(ability3, p) == null) {
									final PlayerTask task = new PlayerTask(p);
									final ItemStack cooldown = Item.createItem("§7§lArmee der Toten", "", Material.FIREWORK_CHARGE, 31, 0);
									final ItemStack blocked = Item.createItem("§c§lArmee der Toten", "", Material.BARRIER, 1, 0);
									
									p.addPotionEffect(FLASH);
									p.getInventory().setItem(2, blocked);
									p.updateInventory();
									p.playSound(p.getLocation(), Sound.ZOMBIE_WOOD, 1, 1);
									createArmyOfDeath(task, level);
									
									task.addTask(0,Scheduler.schedule(800, new Runnable() {
										public void run() {
											task.cancelTasks(1);
											ability3.remove(task);
											p.getInventory().setItem(2, ITEM3);
											p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
										}
									}));
									task.addTask(1,Scheduler.schedule(0,20, new Runnable() {
										public void run() {
											if (p.getInventory().getItem(2).getType()==Material.FIREWORK_CHARGE) {
												if (cooldown.getAmount()>1) cooldown.setAmount(cooldown.getAmount()-1);
												p.getInventory().setItem(2, cooldown);
												p.updateInventory();
											}
										}
									}));
									task.addTask(2,Scheduler.schedule(200, new Runnable() {
										public void run() {
											p.getInventory().setItem(2, cooldown);
											p.updateInventory();
											p.addPotionEffect(FLASH);
											p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 1, 1);
										}
									}));
									ability3.add(task);
								}
							}
						}
					}
				}
			}
		}
		
		private void createArmyOfDeath(final PlayerTask task, int level) {
			
			final List<org.bukkit.entity.Zombie> zombies = new ArrayList<>();
			task.getPlayer().sendMessage("spawn them!");
			
			for (int n = 0; n < (level < 3 ? 3 : 6); n++) {
				org.bukkit.entity.Zombie zombie = (org.bukkit.entity.Zombie) Locations.MAP_WORLD.spawnEntity(task.getPlayer().getLocation(), EntityType.ZOMBIE);
				zombie.setMaxHealth(2.0);
				zombie.setHealth(zombie.getMaxHealth());
				zombie.setBaby(true);
				zombie.setVillager(false);
				zombie.setPassenger(null);
				zombie.setTarget(null);
				zombie.getEquipment().setItemInHand(null);
				zombie.setCustomName("");
				zombie.setCustomNameVisible(false);
				zombie.setCanPickupItems(false);
				if (level >= 6) zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1));
				zombies.add(zombie);
			}
			
			task.addTask(3,Scheduler.schedule(0, 10, new Runnable() {
				public void run() {
					Player nearest = null;
					for (Entity e : task.getPlayer().getNearbyEntities(30.0, 30.0, 30.0)) {
						if (e instanceof Player && (Player) e != task.getPlayer()) {
							nearest = (Player) e;
							break;
						}
					}
					if (nearest == null) killArmyOfDeath(zombies);
					else for (org.bukkit.entity.Zombie z : zombies) z.setTarget(nearest);
				}
			}));
			task.addTask(4,Scheduler.schedule(200, new Runnable() {
				public void run() {
					task.cancelTasks(3);
					killArmyOfDeath(zombies);
				}
			}));
		}
		
		private void killArmyOfDeath(List<org.bukkit.entity.Zombie> zombies) {
			for (org.bukkit.entity.Zombie z : zombies) z.remove();
			zombies.clear();
		}
		
		private void clear(Player p) {
			PlayerTask ability2 = getPlayerTask(this.ability2, p);
			if (ability2!=null) {
				ability2.cancelTasks(0);
				ability2.cancelTasks(1);
				ability2.cancelTasks(2);
				this.ability2.remove(ability2);
			}
			PlayerTask ability3 = getPlayerTask(this.ability3, p);
			if (ability3!=null) {
				ability3.cancelTasks(0);
				ability3.cancelTasks(1);
				ability3.cancelTasks(2);
				ability3.cancelTasks(3);
				this.ability3.remove(ability3);
			}
		}
		
		private PlayerTask getPlayerTask(List<PlayerTask> p_tasks, Player p) {
			for (PlayerTask p_task : p_tasks) if (p_task.getPlayer().getName().equalsIgnoreCase(p.getName())) return p_task;
			return null;
		}
		
	}
	
}
