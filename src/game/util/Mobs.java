package game.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class Mobs {
	
	public static final int NONE = -1;
	
	public static final int ZOMBIE = 0;
	public static final int COW = 1;
	public static final int WOLF = 2;
	public static final int VILLAGER = 3;
	
	public static final int SKELETON = 4;
	public static final int CHICKEN = 5;
	public static final int RABBIT = 6;
	public static final int WITCH = 7;
	
	public static final int SPIDER = 8;
	public static final int PIG = 9;
	public static final int OCELOT = 10;
	public static final int SLIME = 11;
	
	public static final int CREEPER = 12;
	public static final int SHEEP = 13;
	public static final int SQUID = 14;
	public static final int SNOW_MAN = 15;
	
	public static final int IRON_GOLEM = 16;
	public static final int MAGMA_SLIME = 17;
	public static final int BLAZE = 18;
	public static final int GUARDIAN = 19;
	
	public static final List<String> LORE_EMPTY = new ArrayList<>();
	
	public static String getENName(int mob) {
		switch (mob) {
		case NONE: return "None";
		
		case ZOMBIE: return "Zombie";
		case COW: return "Cow";
		case WOLF: return "Wolf";
		case VILLAGER: return "Villager";
		
		case SKELETON: return "Skeleton";
		case CHICKEN: return "Chicken";
		case RABBIT: return "Rabbit";
		case WITCH: return "Witch";
		
		case SPIDER: return "Spider";
		case PIG: return "Pig";
		case OCELOT: return "Ocelot";
		case SLIME: return "Slime";
		
		case CREEPER: return "Creeper";
		case SHEEP: return "Sheep";
		case SQUID: return "Squid";
		case SNOW_MAN: return "SnowMan";
		
		case IRON_GOLEM: return "IronGolem";
		case MAGMA_SLIME: return "MagmaSlime";
		case BLAZE: return "Blaze";
		case GUARDIAN: return "Guardian";
		}
		return "null";
	}
	
	public static String getGERName(int mob) {
		switch (mob) {
		case NONE: return "None";
		
		case ZOMBIE: return "Zombie";
		case COW: return "Kuh";
		case WOLF: return "Wolf";
		case VILLAGER: return "Dorfbewohner";
		
		case SKELETON: return "Skelett";
		case CHICKEN: return "Huhn";
		case RABBIT: return "Hase";
		case WITCH: return "Hexe";
		
		case SPIDER: return "Spinne";
		case PIG: return "Schwein";
		case OCELOT: return "Katze";
		case SLIME: return "Schleim";
		
		case CREEPER: return "Creeper";
		case SHEEP: return "Schaf";
		case SQUID: return "Tintenfisch";
		case SNOW_MAN: return "Schneemann";
		
		case IRON_GOLEM: return "Eisengolem";
		case MAGMA_SLIME: return "Magmaschleim";
		case BLAZE: return "Lohe";
		case GUARDIAN: return "Wächter";
		}
		return "null";
	}
	
	public static int getMob(String name) {
		for (int n = 0; n < 20; n++) if (name.startsWith(getGERName(n))) return n;
		return -1;
	}
	
	public static ItemStack getAbilityItem(int mob, int ability) {
		switch (ability) {
		case 1:
			switch (mob) {
			case ZOMBIE: return Item.createItem("§a§lSeelenbrecher", "§7Kraftvolle Nahkampfwaffe.", Material.IRON_SWORD, 1, 0);
			case WOLF: return Item.createItem("§a§lKnochenbrecher", "§7Macht eine DASH-Attacke,#§7welche AREA-Schaden verursacht und#§7getroffene flasht.", Material.BONE, 1, 0);
			}
			break;
		case 2:
			switch (mob) {
			case ZOMBIE: return Item.createItem("§a§lBlut der Untoten", "§7Während der Wirkung dieser Fähigkeit#§7erhälst du starke Regeneration und#§7einen §a50%igen§7 Lifestealeffekt.", Material.SKULL_ITEM, 1, 2);
			case WOLF: return Item.createItem("§a§lLetzter Ausweg", "§7Gibt dir einen extremen Schub,#§7der dir die Flucht ermöglichen#§7kann. Zudem bekommst du einen#§a3s§7 Schadensresistenzeffekt.#§7Nahe Gegner werden weg geschleudert.", Material.INK_SACK, 1, 15);
			}
			break;
		case 3:
			switch (mob) {
			case ZOMBIE: return Item.createItem("§a§lArmee der Toten", "§7Spawnt eine Armee von Babyzombies#§7die dich im Kampf unterstützen.", Material.MONSTER_EGG, 1, 54);
			case WOLF: return Item.createItem("§a§lKraft der Finsternis", "§7Während der Wirkung dieser Fähigkeit#§7teilst du §a1.5-fachen§7 Schaden aus.", Material.WATCH, 1, 0);
			}
			break;
		}
		return null;
	}
	
	public static ItemStack getUpgradeItem1(int mob, int ability) {
		switch (ability) {
		case 1:
			switch (mob) {
			case ZOMBIE: return Item.createItem("§a§lKein Entkommen!", "§7Verursacht einen §a1s§7 SLOW-Effekt bei Treffer.", Material.MAP, 1, 0);
			case WOLF: return Item.createItem("§a§lStärkere Reißzähne", "§a1.5-facher§7 Schaden wird ausgeteilt", Material.MAP, 1, 0);
			}
			break;
		case 2:
			switch (mob) {
			case ZOMBIE: return Item.createItem("§a§lZeit ist Energie!", "§7Cooldown wird um §a25%§7 reduziert.", Material.MAP, 1, 0);
			case WOLF: return Item.createItem("§a§lEntkommen leicht gemacht", "§7Nahe Gegner werden für §a2s§7 geblendet.", Material.MAP, 1, 0);
			}
			break;
		case 3:
			switch (mob) {
			case ZOMBIE: return Item.createItem("§a§lAufrüstung", "§7Deine Armee ist nun doppelt so groß.", Material.MAP, 1, 0);
			}
			break;
		}
		return null;
	}
	
	public static ItemStack getUpgradeItem2(int mob, int ability) {
		switch (ability) {
		case 1:
			switch (mob) {
			case ZOMBIE: return Item.createItem("§a§lSchaden kann nie Schaden", "§7Du teilst nun doppelten Schaden aus.", Material.MAP, 1, 0);
			case WOLF: return Item.createItem("§a§l", "§a1.5-facher§7 Schaden wird ausgeteilt", Material.MAP, 1, 0);
			}
			break;
		case 2:
			switch (mob) {
			case ZOMBIE: return Item.createItem("§a§lBlutrausch", "§7Lifestealeffekt ist nun doppelt so effektiv.", Material.MAP, 1, 0);
			case WOLF: return Item.createItem("§a§lVolle Wucht!", "§7Verdreht Gegner den Kopf um §a180°.", Material.MAP, 1, 0);
			}
			break;
		case 3:
			switch (mob) {
			case ZOMBIE: return Item.createItem("§a§lAuf sie mit Gebrüll!", "§7Deine Armee ist nun doppelt so schnell.", Material.MAP, 1, 0);
			}
			break;
		}
		return null;
	}
	
}
