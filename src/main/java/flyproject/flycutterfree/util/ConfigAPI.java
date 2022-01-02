package flyproject.flycutterfree.util;

import java.io.File;
import java.util.logging.Logger;

import flyproject.flycutterfree.FlyCutter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigAPI  {

	public final static Logger logger = Logger.getLogger("Minecraft");
	
	public static  void CheckForConfig(Plugin plugin){
		try{
			if(!plugin.getDataFolder().exists()){
				log(": Data Folder doesn't exist");
				log(": Creating Data Folder");
				plugin.getDataFolder().mkdirs();
				log(": Data Folder Created at " + plugin.getDataFolder());
			}
			File  file = new File(plugin.getDataFolder(), "config.yml");
			plugin.getLogger().info("" + file);
			if(!file.exists()){
				log("config.yml not found, creating!");
				plugin.saveDefaultConfig();
				FileConfiguration config = plugin.getConfig();
				
				config.options().copyDefaults(true);
				plugin.saveConfig();
			}
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	public static void Reloadconfig(Plugin plugin){
		// Load config.
		FileConfiguration config = plugin.getConfig();
		String daString = config.getString("debug").replace("'", "") + ",";
		
		if(daString.contains("true")){
			FlyCutter.debug = true;
		}else{
			FlyCutter.debug = false;
		}
		String daString2 = config.getString("auto_update_check").replace("'", "") + ",";
		if(daString2.contains("true")){
			FlyCutter.UpdateCheck = true;
		}else{
			FlyCutter.UpdateCheck = false;
		}
		
		if(FlyCutter.debug){log("UpdateCheck = " + FlyCutter.UpdateCheck);} //TODO: Logger
	}
	public static  void log(String dalog){
		Bukkit.getLogger().info(dalog);
	}
}
