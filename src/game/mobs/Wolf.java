package game.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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

public class Wolf extends Mob {

	private static final ItemStack ITEM1 = Item.createItem("§a§lKnochenbrecher", "", Material.BONE, 1, 0);
	private static final ItemStack ITEM2 = Item.createItem("§a§lLetzter Ausweg!", "", Material.INK_SACK, 1, 15);
	private static final ItemStack ITEM3 = Item.createItem("§a§lKraft der Finsternis", "", Material.WATCH, 1, 0);
	private Events events;
	
	@Override
	protected int getMob() {
		return Mobs.WOLF;
	}

	@Override
	protected EntityType getEntityType() {
		return EntityType.WOLF;
	}

	@Override
	protected void editMob(Entity entity) {
		org.bukkit.entity.Wolf wolf = (org.bukkit.entity.Wolf) entity;
		wolf.setCanPickupItems(false);
		wolf.setMaxHealth(getMob()+1);
		wolf.setAdult();
		wolf.setAngry(true);
		wolf.setHealth(wolf.getMaxHealth());
	}

	@Override
	protected void equip(Player p) {
		Controller.reset(p);
		Controller.changeHealth(p, 16.0);
		
		p.getInventory().setHeldItemSlot(0);
		
		p.setWalkSpeed(0.35f);
		EffectMaster.getEffect(Effect.POISON).resistent(p);
		
		p.getInventory().setItem(0, ITEM1);
		p.getInventory().setItem(1, ITEM2);
		p.getInventory().setItem(2, ITEM3);
		
		DisguiseAPI.disguiseToAll(p, new MobDisguise(DisguiseType.WOLF, true));
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
	
	private class Events implements Listener {
		
		private List<PlayerTask> ability1 = new ArrayList<>();
		private List<PlayerTask> ability2 = new ArrayList<>();
		private final PotionEffect FLASH = new PotionEffect(PotionEffectType.BLINDNESS, 20, 0);
		
		@EventHandler
		public void onHit(PlayerInteractEvent e) {
			/*
			 * 
			 * - ABILITY 1
			 * 
			 */
			if (e.getPlayer().getWorld()==Locations.MAP_WORLD)
			if (isMob(e.getPlayer())) {
				final Player p = e.getPlayer();
				if (e.getAction()==Action.LEFT_CLICK_AIR||e.getAction()==Action.LEFT_CLICK_BLOCK) {
					if (p.getItemInHand()!=null) {
						if (p.getItemInHand().getType()==Material.BONE) {
							p.setVelocity(p.getLocation().getDirection());
							if (p.getVelocity().getY()==0) p.setVelocity(p.getVelocity().setY(0.8));
							else p.setVelocity(p.getVelocity().setY(0));
							
							if (getPlayerTask(ability1, p)==null) {
								final PlayerTask task = new PlayerTask(p);
								final ItemStack blocked = Item.createItem("§c§lKnochenbrecher", "", Material.BARRIER, 1, 0);
								
								p.setItemInHand(blocked);
								p.updateInventory();
								
								task.addTask(0, Scheduler.schedule(5, new Runnable() {
									public void run() {
										p.playSound(p.getLocation(), Sound.WOLF_BARK, 1, 1);
										for (Entity en : p.getNearbyEntities(1.5, 1.5, 1.5)) if (en instanceof Player) { Controller.damage((Player) en, getBoughtUpgrades((Player) en) >= 3 ? 4.5: 3); ((Player) en).addPotionEffect(FLASH); }
									}
								}));
								task.addTask(1, Scheduler.schedule(20, new Runnable() {
									public void run() {
										p.getInventory().setItem(0, ITEM1);
										p.updateInventory();
										ability1.remove(task);
									}
								}));
								
								ability1.add(task);
							}
						}
					}
					return;
				}
				if (e.getAction()==Action.RIGHT_CLICK_AIR||e.getAction()==Action.RIGHT_CLICK_BLOCK) {
					if (p.getItemInHand()!=null)
					if (p.getItemInHand().getType()==Material.INK_SACK) {
						if (getPlayerTask(ability2, p)==null) {
							final PlayerTask task = new PlayerTask(p);
							final ItemStack cooldown = Item.createItem("§7§lLetzter Ausweg!", "", Material.FIREWORK_CHARGE, 26, 0);
							
							p.teleport(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY()+0.3, p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
							p.setVelocity(p.getLocation().getDirection().setY(0).multiply(3));
							for (Entity en : p.getNearbyEntities(8, 8, 8)) if (en instanceof Player) {
								if (getBoughtUpgrades(p)>=2) EffectMaster.effect(Effect.BLIND, (Player) en, 40, 0);
								if (getBoughtUpgrades(p)>=4) 
								en.setVelocity(p.getVelocity().subtract(en.getVelocity()).multiply(-2).setY(1.2));
							}
							p.setItemInHand(cooldown);
							p.updateInventory();
							
							task.addTask(0, Scheduler.schedule(0,20, new Runnable() {
								public void run() {
									if (p.getInventory().getItem(1).getType()==Material.FIREWORK_CHARGE) {
										if (cooldown.getAmount()>1) cooldown.setAmount(cooldown.getAmount()-1);
										p.getInventory().setItem(1, cooldown);
										p.updateInventory();
									}
								}
							}));
							task.addTask(1, Scheduler.schedule(500, new Runnable() {
								public void run() {
									p.getInventory().setItem(1, ITEM2);
									p.updateInventory();
									task.cancelTasks(0);
									ability2.remove(task);
								}
							}));
							
							ability2.add(task);
						}
					}
				}
			}	
		}
		
		@EventHandler
		public void onDamage(EntityDamageByEntityEvent e) {
			if (e.getEntity().getWorld()==Locations.MAP_WORLD) {
				if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
					final Player k = (Player) e.getDamager();
					if (isMob(k)) {
						((EntityDamageEvent)e).setCancelled(true); e.setDamage(0.0);
					}
				}
			}
		}
		
		public void clear(Player p) {
			PlayerTask ability1 = getPlayerTask(this.ability1, p);
			if (ability1 != null) {
				ability1.cancelTasks(0);
				ability1.cancelTasks(1);
				this.ability1.remove(ability1);
			}
			
			PlayerTask ability2 = getPlayerTask(this.ability2, p);
			if (ability2 != null) {
				ability2.cancelTasks(0);
				ability2.cancelTasks(1);
				this.ability2.remove(ability2);
			}
		}
		
		private PlayerTask getPlayerTask(List<PlayerTask> p_tasks, Player p) {
			for (PlayerTask p_task : p_tasks) if (p_task.getPlayer().getName().equalsIgnoreCase(p.getName())) return p_task;
			return null;
		}
		
	}

}
