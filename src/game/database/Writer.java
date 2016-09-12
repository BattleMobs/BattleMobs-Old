package game.database;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.bukkit.Bukkit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Writer {
	
	public static void write(List<PlayerData> database) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			
			// ROOT TAG
			Element root = doc.createElement("Players");
			doc.appendChild(root);
			
			//PLAYER TAGS
			Element name;
			Element uuid;
			Element onlinetimeins;
			Element level;
			Element exp;
			Element money;
			
			// TEMP
			int n = 0;
			MobData mobdata;
			
			
			for (PlayerData data : database) {
				// UPDATE ONLINETIME
				if (Bukkit.getServer().getPlayerExact(data.getName()) != null) data.updateTime();
				
				name = doc.createElement("Player");
				name.setAttribute("Name", data.getName());
				
				uuid = doc.createElement("UUID");
				onlinetimeins = doc.createElement("OnlineTimeInS");
				level = doc.createElement("Level");
				exp = doc.createElement("EXP");
				money = doc.createElement("Money");
				
				root.appendChild(name);
				uuid.appendChild(doc.createTextNode(data.getUUID()));
				name.appendChild(uuid);
				onlinetimeins.appendChild(doc.createTextNode(""+data.getOnlineTimeInS()));
				name.appendChild(onlinetimeins);
				level.appendChild(doc.createTextNode(""+data.getLevel()));
				name.appendChild(level);
				exp.appendChild(doc.createTextNode(""+data.getEXP()));
				name.appendChild(exp);
				money.appendChild(doc.createTextNode(""+data.getMoney()));
				name.appendChild(money);
				
				for (n=0;n<20;n++) {
					
					// MOB TAGS
					Element mob_name;
					Element mob_level;
					Element bought;
					Element kills;
					Element deaths;
					
					mobdata = data.getMobData(n);
					mob_name = doc.createElement("Mob");
					mob_name.setAttribute("Name", mobdata.getName());
					mob_level = doc.createElement("Level");
					bought = doc.createElement("Bought");
					kills = doc.createElement("Kills");
					deaths = doc.createElement("Deaths");
					
					name.appendChild(mob_name);
					
					mob_level.appendChild(doc.createTextNode(""+mobdata.getLevel()));
					mob_name.appendChild(mob_level);
					bought.appendChild(doc.createTextNode(""+mobdata.getBoughtUpgrades()));
					mob_name.appendChild(bought);
					kills.appendChild(doc.createTextNode(""+mobdata.getKills()));
					mob_name.appendChild(kills);
					deaths.appendChild(doc.createTextNode(""+mobdata.getDeaths()));
					mob_name.appendChild(deaths);
				}
				
			}
			
			TransformerFactory t_factory = TransformerFactory.newInstance();
			Transformer t = t_factory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("Players.xml"));
			t.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
}
