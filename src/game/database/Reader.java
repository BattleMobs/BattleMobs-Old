package game.database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bukkit.Bukkit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Reader {
	
	public static List<PlayerData> read() {
		List<PlayerData> data = new ArrayList<>();
		try {
			
			// INIT
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("Players.xml"));
			NodeList players = document.getElementsByTagName("Player");
			
			// PLAYER TAGS
			String name;
			String uuid;
			float onlinetimeinh;
			int level;
			int exp;
			int money;
			
			// MOB TAGS
			String mob_name;
			int mob_level;
			int bought;
			int kills;
			int deaths;
			
			// TEMP
			int n,k;
			Element player;
			Element mob;
			MobData[] mobs;
			
			// ITERATE PLAYERS
			for (n=0;n<players.getLength();n++) {
				
				mobs = new MobData[20];
				player = (Element) players.item(n);
				
				// PLAYER DATA
				name = player.getAttribute("Name");
				uuid = player.getElementsByTagName("UUID").item(0).getTextContent().trim();
				onlinetimeinh = Float.parseFloat(player.getElementsByTagName("OnlineTimeInS").item(0).getTextContent().trim());
				level = Integer.parseInt(player.getElementsByTagName("Level").item(0).getTextContent().trim());
				exp = Integer.parseInt(player.getElementsByTagName("EXP").item(0).getTextContent().trim());
				money = Integer.parseInt(player.getElementsByTagName("Money").item(0).getTextContent().trim());
				
				for (k=0;k<20;k++) {
					
					mob = (Element) player.getElementsByTagName("Mob").item(k);
					
					// MOB DATA
					mob_name = mob.getAttribute("Name");
					mob_level = Integer.parseInt(mob.getElementsByTagName("Level").item(0).getTextContent().trim());
					bought = Integer.parseInt(mob.getElementsByTagName("Bought").item(0).getTextContent().trim());
					kills = Integer.parseInt(mob.getElementsByTagName("Kills").item(0).getTextContent().trim());
					deaths = Integer.parseInt(mob.getElementsByTagName("Deaths").item(0).getTextContent().trim());
					
					if (k==0) System.out.println(name + ">" + mob_name + "|" + mob_level + "|" + bought + "|" + kills);
					
					mobs[k] = new MobData(mob_name, mob_level, bought, kills, deaths);
				}
				
				data.add(new PlayerData(name, uuid, onlinetimeinh, level, exp, money, mobs));
			}
			Bukkit.getConsoleSender().sendMessage("§8[§aBattleMobs§8]§7 Datenbank eingelesen!");
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("§8[§aBattleMobs§8]§7 Keine Datenbank vorhanden! Erstelle neue...");
		}
		System.out.println(data.size());
		return data;
	}
	
}
