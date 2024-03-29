package flyproject.flycutterfree;
//1.14

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import flyproject.flycutterfree.util.RabbitHeads;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Goat;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.TraderLlama;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import flyproject.flycutterfree.handlers.EventHandler_1_16_R2;
import flyproject.flycutterfree.handlers.EventHandler_1_17_R1;
import flyproject.flycutterfree.util.Ansi;
import flyproject.flycutterfree.util.CatHeads;
import flyproject.flycutterfree.util.HorseHeads;
import flyproject.flycutterfree.util.LlamaHeads;
import flyproject.flycutterfree.util.Metrics;
import flyproject.flycutterfree.util.MobHeads;
import flyproject.flycutterfree.util.MobHeads117;
import flyproject.flycutterfree.util.SheepHeads;
import flyproject.flycutterfree.util.StrUtils;
import flyproject.flycutterfree.util.UpdateChecker;
import flyproject.flycutterfree.util.VillagerHeads;
import flyproject.flycutterfree.util.YmlConfiguration;
import flyproject.flycutterfree.util.ZombieVillagerHeads;
import flyproject.flycutterfree.util.datatypes.JsonDataType;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTList;
import de.tr7zw.changeme.nbtapi.NBTListCompound;

@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class FlyCutter extends JavaPlugin implements Listener{
	
	public final static Logger logger = Logger.getLogger("Minecraft");
	/** update checker variables */
	public String updateurl = "";
	public String newVerMsg;
	public int updateVersion = 73997; // https://spigotmc.org/resources/73997
	boolean UpdateAvailable =  false;
	public String UColdVers;
	public String UCnewVers;
	public static boolean UpdateCheck;
	public String thisName = this.getName();
	public String thisVersion = this.getDescription().getVersion();
	/** end update checker variables */
	public static boolean debug = false;
	public static String daLang;
	//String updateURL = "https://github.com/JoelGodOfwar/MoreMobHeads/raw/master/versioncheck/1.14/version.txt";
	File langFile;
	public FileConfiguration lang;
	File langNameFile;
	public FileConfiguration langName;
	public File playerFile;
	public FileConfiguration playerHeads;
	File blockFile;
	File blockFile116;
	File blockFile1162;
	File blockFile117;
	public FileConfiguration blockHeads  = new YamlConfiguration();
	public FileConfiguration blockHeads2  = new YamlConfiguration();
	public FileConfiguration blockHeads3  = new YamlConfiguration();
	public File customFile;
	public FileConfiguration traderCustom;
	File chanceFile;
	public YmlConfiguration chanceConfig;
	public YmlConfiguration oldchanceConfig;
	File mobnameFile;
	FileConfiguration mobname;
	double defpercent = 0.013;
	//static boolean showkiller;
	//static boolean showpluginname;
	public YmlConfiguration config = new YmlConfiguration();
	YamlConfiguration oldconfig = new YamlConfiguration();
	static PluginDescriptionFile pdfFile;
	static String datafolder;
	public String world_whitelist;
	public String world_blacklist;
	public String mob_whitelist;
	public String mob_blacklist;
	boolean colorful_console;
	public final NamespacedKey NAMETAG_KEY = new NamespacedKey(this, "name_tag");
	File debugFile;
	boolean status;
	
	@Override // TODO: onEnable
	public void onEnable() {
		//Bukkit.getPluginManager().registerEvents(new ClickReflush(),this);
		System.out.println("\n" +
				"    ________      ______      __  __           \n" +
				"   / ____/ /_  __/ ____/_  __/ /_/ /____  _____\n" +
				"  / /_  / / / / / /   / / / / __/ __/ _ \\/ ___/\n" +
				" / __/ / / /_/ / /___/ /_/ / /_/ /_/  __/ /    \n" +
				"/_/   /_/\\__, /\\____/\\__,_/\\__/\\__/\\___/_/     \n" +
				"        /____/                                 \n\n" +
				"(c) Copyright 2021-2022 FlyProject\n" +
				"FlyCutter-Free Version\n" +
				"此版本为免费版 众多功能已被删除");
		UpdateCheck = false;
		//showkiller = getConfig().getBoolean("lore.show_killer", true);
		//showpluginname = getConfig().getBoolean("lore.show_plugin_name", true);
		debug = getConfig().getBoolean("debug", false);
		daLang = getConfig().getString("lang", "zh_CN");
		oldconfig = new YamlConfiguration();
		pdfFile = this.getDescription();
		datafolder = this.getDataFolder().toString();
		colorful_console = getConfig().getBoolean("colorful_console", true);

		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info("\n");
		logger.info(Ansi.YELLOW + pdfFile.getName() + Ansi.RESET + " 加载中...");

		debugFile = new File(this.getDataFolder() + File.separator + "logs" + File.separator + "mmh_debug.log");
		if (!debugFile.exists()) {
			saveResource("logs" + File.separatorChar + "mmh_debug.log", true);
		}

		/** DEV check **/
		File jarfile = this.getFile().getAbsoluteFile();
		if (jarfile.toString().contains("-Beta")) {
			debug = true;
			logDebug("此版本为测试 带有不稳定性");
			//log("jarfile contains dev, debug set to true.");
		}
		if (jarfile.toString().contains("Free")) {
			status = true;
			logDebug("此版本为免费版 付费功能已被移除");
			//log("jarfile contains dev, debug set to true.");
		}
		if (debug) {
			logDebug("datafolder=" + getDataFolder());
		}
		langFile = new File(getDataFolder() + "" + File.separatorChar + "lang" + File.separatorChar, daLang + ".yml");//\
		if (debug) {
			logDebug("langFilePath=" + langFile.getPath());
		}
		if (!langFile.exists()) {                                                                    // checks if the yaml does not exist
			langFile.getParentFile().mkdirs();                                    // creates the /plugins/<pluginName>/ directory if not found
			saveResource("lang" + File.separatorChar + "cs_CZ.yml", true);
			saveResource("lang" + File.separatorChar + "de_DE.yml", true);
			saveResource("lang" + File.separatorChar + "en_US.yml", true);
			saveResource("lang" + File.separatorChar + "es_MX.yml", true);
			saveResource("lang" + File.separatorChar + "fr_FR.yml", true);
			saveResource("lang" + File.separatorChar + "nl_NL.yml", true);
			saveResource("lang" + File.separatorChar + "pt_BR.yml", true);
			saveResource("lang" + File.separatorChar + "zh_CN.yml", true);
			log(Level.INFO, "语言文件不存在 已自动生成至 " + getDataFolder() + "" + File.separatorChar + "lang");
			//ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
		}
		log(Level.INFO, "加载语言文件");
		lang = new YamlConfiguration();
		try {
			lang.load(langFile);
		} catch (IOException | InvalidConfigurationException e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		String checklangversion = lang.getString("langversion", "1.0.0");
		if (checklangversion != null) {
			if (!checklangversion.equalsIgnoreCase("1.0.15")) {
				saveResource("lang" + File.separatorChar + "cs_CZ.yml", true);
				saveResource("lang" + File.separatorChar + "de_DE.yml", true);
				saveResource("lang" + File.separatorChar + "en_US.yml", true);
				saveResource("lang" + File.separatorChar + "es_MX.yml", true);
				saveResource("lang" + File.separatorChar + "fr_FR.yml", true);
				saveResource("lang" + File.separatorChar + "nl_NL.yml", true);
				saveResource("lang" + File.separatorChar + "pt_BR.yml", true);
				saveResource("lang" + File.separatorChar + "zh_CN.yml", true);
				saveResource("lang" + File.separatorChar + "cs_CZ_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "de_DE_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "en_US_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "es_MX_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "fr_FR_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "nl_NL_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "pt_BR_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "zh_CN_mobnames.yml", true);
				log(Level.INFO, "更新语言文件中! 已将 cs_CZ.yml, de_DE.yml, en_US.yml, es_MX.yml, fr_FR.yml, nl_NL.yml, pt_BR.yml, and zh_CN.yml 备份至 "
						+ getDataFolder() + "" + File.separatorChar + "lang");
			}
		} else {
			saveResource("lang" + File.separatorChar + "cs_CZ.yml", true);
			saveResource("lang" + File.separatorChar + "de_DE.yml", true);
			saveResource("lang" + File.separatorChar + "en_US.yml", true);
			saveResource("lang" + File.separatorChar + "es_MX.yml", true);
			saveResource("lang" + File.separatorChar + "fr_FR.yml", true);
			saveResource("lang" + File.separatorChar + "nl_NL.yml", true);
			saveResource("lang" + File.separatorChar + "pt_BR.yml", true);
			saveResource("lang" + File.separatorChar + "zh_CN.yml", true);
			log(Level.INFO, "语言文件已更新 自动覆盖至 "
					+ getDataFolder() + "" + File.separatorChar + "lang");
		}

		/** update checker variable */
		newVerMsg = Ansi.YELLOW + this.getName() + Ansi.MAGENTA + " v{oVer}" + Ansi.RESET + " " + lang.get("newvers") + Ansi.GREEN + " v{nVer}" + Ansi.RESET;
		/** end update checker variable */

		/** Version Check */
		if (!getMCVersion().startsWith("1.14") && !getMCVersion().startsWith("1.15") && !getMCVersion().startsWith("1.16")
				&& !getMCVersion().startsWith("1.17")) {
			logger.info(Ansi.RED + "警告! *!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!" + Ansi.RESET);
			logger.info(Ansi.RED + "警告! " + lang.get("server_not_version") + Ansi.RESET);
			logger.info(Ansi.RED + "警告! " + this.getName() + " v" + this.getDescription().getVersion() + " 插件自动卸载." + Ansi.RESET);
			logger.info(Ansi.RED + "警告! *!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!" + Ansi.RESET);
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		/**    Check for config */
		try {
			if (!getDataFolder().exists()) {
				log(Level.INFO, "文件目录不存在");
				log(Level.INFO, "自动创建...");
				getDataFolder().mkdirs();
				log(Level.INFO, "目录位置 " + getDataFolder());
			}
			File file = new File(getDataFolder(), "config.yml");
			if (debug) {
				logDebug("" + file);
			}
			if (!file.exists()) {
				log(Level.INFO, "config.yml 不存在 已自动创建!");
				saveResource("config.yml", true);
				saveResource("chance_config.yml", true);
			}
		} catch (Exception e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		log(Level.INFO, "加载配置文件...");
		try {
			oldconfig.load(new File(getDataFolder() + "" + File.separatorChar + "config.yml"));
		} catch (Exception e2) {
			logWarn("无法加载 config.yml");
			stacktraceInfo();
			e2.printStackTrace();
		}
		String checkconfigversion = oldconfig.getString("version", "1.0.0");
		if (checkconfigversion != null) {
			if (!checkconfigversion.equalsIgnoreCase("1.0.18")) {
				boolean isOldFormat;
				try {
					copyFile_Java7(getDataFolder() + "" + File.separatorChar + "config.yml", getDataFolder() + "" + File.separatorChar + "old_config.yml");
				} catch (IOException e) {
					stacktraceInfo();
					e.printStackTrace();
				}
				isOldFormat = false;
				saveResource("config.yml", true);

				try {
					config.load(new File(getDataFolder(), "config.yml"));
				} catch (IOException | InvalidConfigurationException e1) {
					logWarn("无法加载 config.yml");
					stacktraceInfo();
					e1.printStackTrace();
				}
				try {
					oldconfig.load(new File(getDataFolder(), "old_config.yml"));
				} catch (IOException | InvalidConfigurationException e1) {
					stacktraceInfo();
					e1.printStackTrace();
				}
				if (isOldFormat) {
					config.set("auto_update_check", oldconfig.get("auto_update_check", true));
					config.set("debug", oldconfig.get("debug", false));
					config.set("lang", oldconfig.get("lang", "en_US"));
					config.set("colorful_console", oldconfig.get("colorful_console", true));//
					config.set("vanilla_heads.creepers", oldconfig.get("creeper_vanilla_heads", false));
					config.set("vanilla_heads.ender_dragon", oldconfig.get("ender_dragon_vanilla_heads", false));
					config.set("vanilla_heads.skeleton", oldconfig.get("skeleton_vanilla_heads", false));
					config.set("vanilla_heads.wither_skeleton", oldconfig.get("wither_skeleton_vanilla_heads", false));
					config.set("vanilla_heads.zombie", oldconfig.get("zombie_vanilla_heads", false));
					config.set("world.whitelist", oldconfig.get("world.whitelist", ""));
					config.set("world.blacklist", oldconfig.get("world.blacklist", ""));
					config.set("mob.whitelist", oldconfig.get("mob.whitelist", ""));
					config.set("mob.blacklist", oldconfig.get("mob.blacklist", ""));
					config.set("mob.nametag", oldconfig.get("mob.nametag", false));
					config.set("lore.show_killer", oldconfig.get("show_killer", true));
					config.set("lore.show_plugin_name", oldconfig.get("show_plugin_name", true));
					config.set("apply_looting", oldconfig.get("apply_looting", true));
					config.set("whitelist.enforce", oldconfig.get("enforce_whitelist", true));
					config.set("whitelist.player_head_whitelist", oldconfig.get("player_head_whitelist", "names_go_here"));
					config.set("blacklist.enforce", oldconfig.get("enforce_blacklist", true));
					config.set("blacklist.player_head_blacklist", oldconfig.get("player_head_blacklist", "names_go_here"));
				} else {
					config.set("auto_update_check", oldconfig.get("auto_update_check", true));
					config.set("debug", oldconfig.get("debug", false));
					config.set("lang", oldconfig.get("lang", "en_US"));
					config.set("colorful_console", oldconfig.get("colorful_console", true));
					config.set("vanilla_heads.creepers", oldconfig.get("vanilla_heads.creepers", false));
					config.set("vanilla_heads.ender_dragon", oldconfig.get("vanilla_heads.ender_dragon", false));
					config.set("vanilla_heads.skeleton", oldconfig.get("vanilla_heads.skeleton", false));
					config.set("vanilla_heads.wither_skeleton", oldconfig.get("vanilla_heads.wither_skeleton", false));
					config.set("vanilla_heads.zombie", oldconfig.get("vanilla_heads.zombie", false));
					config.set("world.whitelist", oldconfig.get("world.whitelist", ""));
					config.set("world.blacklist", oldconfig.get("world.blacklist", ""));
					config.set("mob.whitelist", oldconfig.get("mob.whitelist", ""));
					config.set("mob.blacklist", oldconfig.get("mob.blacklist", ""));
					config.set("mob.nametag", oldconfig.get("mob.nametag", false));
					config.set("lore.show_killer", oldconfig.get("lore.show_killer", true));
					config.set("lore.show_plugin_name", oldconfig.get("lore.show_plugin_name", true));
					config.set("apply_looting", oldconfig.get("apply_looting", true));
					config.set("whitelist.enforce", oldconfig.get("whitelist.enforce", true));
					config.set("whitelist.player_head_whitelist", oldconfig.get("whitelist.player_head_whitelist", "names_go_here"));
					config.set("blacklist.enforce", oldconfig.get("enforce_blacklist", true));
					config.set("blacklist.player_head_blacklist", oldconfig.get("blacklist.player_head_blacklist", "names_go_here"));
				}
				//config.set("", oldconfig.get("", true));

				try {
					config.save(new File(getDataFolder(), "config.yml"));
				} catch (IOException e) {
					logWarn("无法保存原始设置到 config.yml");
					stacktraceInfo();
					e.printStackTrace();
				}
				saveResource("chance_config.yml", true);
				log(Level.INFO, "config.yml 已更新 旧版配置文件已储存为 old_config.yml");
				log(Level.INFO, "chance_config.yml 已保存.");
			} else {
				try {
					config.load(new File(getDataFolder(), "config.yml"));
				} catch (IOException | InvalidConfigurationException e1) {
					logWarn("无法加载 config.yml");
					stacktraceInfo();
					e1.printStackTrace();
				}
			}
			oldconfig = null;
		}
		/** end config check */



		/** chanceConfig load */
		chanceFile = new File(getDataFolder() + "" + File.separatorChar + "chance_config.yml");//\
		if(debug){logDebug("chanceFile=" + chanceFile.getPath());}
		if(!chanceFile.exists()){																	// checks if the yaml does not exist
			saveResource("chance_config.yml", true);
			log(Level.INFO, "chance_config.yml 不存在! 复制 chance_config.yml 到 " + getDataFolder() + "");
			//ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
		}
	log(Level.INFO, "加载 chance_config 文件...");
		chanceConfig = new YmlConfiguration();
		oldchanceConfig = new YmlConfiguration();
		try {
			chanceConfig.load(chanceFile);
		} catch (IOException | InvalidConfigurationException e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		/** chanceConfig update check */
		String checkchanceConfigversion = chanceConfig.getString("version", "1.0.0");
		if(checkchanceConfigversion != null){
			if(!checkchanceConfigversion.equalsIgnoreCase("1.0.19")){
				try {
					copyFile_Java7(getDataFolder() + "" + File.separatorChar + "chance_config.yml",getDataFolder() + "" + File.separatorChar + "old_chance_config.yml");
				} catch (IOException e) {
					stacktraceInfo();
					e.printStackTrace();
				}
				
				saveResource("chance_config.yml", true);
				copyChance(getDataFolder() + "" + File.separatorChar + "old_chance_config.yml", chanceFile.getPath());
				log(Level.INFO, "chance_config.yml updated.");
			}
		}
		
		
		/** Mob names translation */
		langNameFile = new File(getDataFolder() + "" + File.separatorChar + "lang" + File.separatorChar, daLang + "_mobnames.yml");//\
		if(debug){logDebug("langFilePath=" + langNameFile.getPath());}
		if(!langNameFile.exists()){																	// checks if the yaml does not exist
			saveResource("lang" + File.separatorChar + "cs_CZ_mobnames.yml", true);
			saveResource("lang" + File.separatorChar + "de_DE_mobnames.yml", true);
			saveResource("lang" + File.separatorChar + "en_US_mobnames.yml", true);
			saveResource("lang" + File.separatorChar + "es_MX_mobnames.yml", true);
			saveResource("lang" + File.separatorChar + "fr_FR_mobnames.yml", true);
			saveResource("lang" + File.separatorChar + "nl_NL_mobnames.yml", true);
			saveResource("lang" + File.separatorChar + "pt_BR_mobnames.yml", true);
			saveResource("lang" + File.separatorChar + "zh_CN_mobnames.yml", true);
			log(Level.INFO, "lang_mobnames file 不存在! 复制 cs_CZ_mobnames.yml, de_DE_mobnames.yml, en_US_mobnames.yml, es_MX_mobnames.yml, fr_FR_mobnames.yml, nl_NL_mobnames.yml, pt_BR_mobnames.yml, and zh_CN_mobnames.yml 到 " + getDataFolder() + "" + File.separatorChar + "lang");
			//ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
		}
		log(Level.INFO, "加载 language based mobnames 文件...");
		langName = new YamlConfiguration();
		try {
			langName.load(langNameFile);
		} catch (IOException | InvalidConfigurationException e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		/** Mob Names update check */
		String checklangnameConfigversion = langName.getString("axolotl.blue", "outdated");
		if(checklangnameConfigversion != null){
			if(checklangnameConfigversion.equalsIgnoreCase("outdated")){
				log(Level.INFO, "lang_mobnames file outdated! Updating.");
				saveResource("lang" + File.separatorChar + "cs_CZ_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "de_DE_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "en_US_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "es_MX_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "fr_FR_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "nl_NL_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "pt_BR_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "zh_CN_mobnames.yml", true);
				log(Level.INFO, "cs_CZ_mobnames.yml, de_DE_mobnames.yml, en_US_mobnames.yml, es_MX_mobnames.yml, fr_FR_mobnames.yml, nl_NL_mobnames.yml, pt_BR_mobnames.yml, and zh_CN_mobnames.yml updated.");
				try {
					langName.load(langNameFile);
				} catch (IOException | InvalidConfigurationException e) {
					stacktraceInfo();
					e.printStackTrace();
				}
			}
		}
		/** end Mob names translation */
		
		world_whitelist = config.getString("world.whitelist", "");
		world_blacklist = config.getString("world.blacklist", "");
		mob_whitelist = config.getString("mob.whitelist", "");
		mob_blacklist = config.getString("mob.blacklist", "");
		
		/** Update Checker */
		if(UpdateCheck){
			try {
				Bukkit.getConsoleSender().sendMessage("检查更新...");
				UpdateChecker updater = new UpdateChecker(this, updateVersion, updateurl);
				if(updater.checkForUpdates()) {
					UpdateAvailable = true; // TODO: Update Checker
					UColdVers = updater.oldVersion();
					UCnewVers = updater.newVersion();
					Bukkit.getConsoleSender().sendMessage(newVerMsg.replace("{oVer}", UColdVers).replace("{nVer}", UCnewVers));
					Bukkit.getConsoleSender().sendMessage(Ansi.GREEN + "请联系作者获得更新" + Ansi.RESET);
				}else{
					UpdateAvailable = false;
				}
			}catch(Exception e) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "无法使用更新检测");
				e.printStackTrace();
			}
		}
		/** end update checker */
		
		getServer().getPluginManager().registerEvents(this, this);
		
		String jarfilename = this.getFile().getAbsoluteFile().toString();
		
		//Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "version");

		
		/** Register EventHandler */ // TODO: Register Events
		String packageName = this.getServer().getClass().getPackage().getName();
    	String version = packageName.substring(packageName.lastIndexOf('.') + 2);
    	if(debug)logDebug("version=" + version);
    	if( version.contains("1_16_R") || version.contains("1_15_R1") || version.contains("1_14_R1") ){
    		getServer().getPluginManager().registerEvents( new EventHandler_1_16_R2(this), this);
    		getCommand("flycutter").setExecutor(new EventHandler_1_16_R2(this));
		}else if( version.contains("1_17_R1") ){
			getServer().getPluginManager().registerEvents( new EventHandler_1_17_R1(this), this);
			//getCommand("cmh").setExecutor(new EventHandler_1_17_R1(this));
		}else{
			logWarn("您使用的版本不受支持:" + version);
			getServer().getPluginManager().disablePlugin(this);
		}
		
/** Experimental code 	
		for (World world : Bukkit.getServer().getWorlds()){
			world.setDifficulty(Difficulty.HARD);
		}
		//player.setPlayerListName(ChatColor.GREEN + player.getDisplayName());
 Experimental code */

		Metrics metrics	= new Metrics(this, 13797);
		// New chart here
		// myPlugins()
	}

	@Override // TODO: onDisable
	public void onDisable(){
	}

	
	public	void log(Level level, String dalog){// TODO: log
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.log(level, ChatColor.YELLOW + pdfFile.getName() + ChatColor.RESET + " " + dalog );
	}
	
	public	void log(Level level, String dalog, Throwable thrown){// TODO: log
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.log(level, ChatColor.YELLOW + pdfFile.getName() + ChatColor.RESET + " " + dalog );
	}
	
	public	void logDebug(String dalog){
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); //"dd/MM HH:mm:ss"
        String date = dateFormat.format(new Date());
        String message = "[" + date + "] [v" + this.getDescription().getVersion() + "] [DEBUG]: " + ChatColor.stripColor(dalog);
        try {
        	FileWriter writer = new FileWriter(debugFile.toString(), true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.append(message + "\n");
            bw.close(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        log(Level.INFO, Ansi.RED + "[DEBUG] " + Ansi.RESET + dalog);
		//log(Ansi.RED + "[DEBUG] " + Ansi.RESET + dalog);
	}
	public void logWarn(String dalog){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm:ss");
        String date = dateFormat.format(new Date());
        String message = "[" + date + "] [WARN]: " + dalog;
        logger.warning(message + "\n");
        try {
        	FileWriter writer = new FileWriter(debugFile, true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.append(message);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
		log(Level.WARNING, Ansi.RED + "[WARN] " + Ansi.RESET  + " " + dalog);
	}
	
	public static String getMCVersion() {
		String strVersion = Bukkit.getVersion();
		strVersion = strVersion.substring(strVersion.indexOf("MC: "), strVersion.length());
		strVersion = strVersion.replace("MC: ", "").replace(")", "");
		return strVersion;
	}
	
	
	public void giveMobHead(LivingEntity mob, String name){
		ItemStack helmet = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta meta = (SkullMeta)helmet.getItemMeta();
			meta.setDisplayName(name + "'s Head");
		meta.setOwner(name); //.setOwner(name);
			helmet.setItemMeta(meta);//																	 e2d4c388-42d5-4a96-b4c9-623df7f5e026
		mob.getEquipment().setHelmet(helmet);
		helmet.setItemMeta(meta);
		mob.getEquipment().setHelmet(helmet);
		if(getServer().getPluginManager().getPlugin("WildStacker") != null){
			@Nonnull
			PersistentDataContainer pdc = mob.getPersistentDataContainer();
			pdc.set(NAMETAG_KEY, PersistentDataType.STRING, "nametag");
		}
	}
	
	public void givePlayerHead(Player player, String playerName){
		ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta meta = (SkullMeta)playerHead.getItemMeta();
			meta.setDisplayName(playerName + "'s 的头颅");
		meta.setOwner(playerName); //.setOwner(name);
		ArrayList<String> lore = new ArrayList();
		if(getConfig().getBoolean("lore.show_plugin_name", true)){
			lore.add(ChatColor.AQUA + "" + this.getName());
		}
		meta.setLore(lore);
			playerHead.setItemMeta(meta);//																	 e2d4c388-42d5-4a96-b4c9-623df7f5e026
		//player.getEquipment().setHelmet(playerHead);
			
		playerHead.setItemMeta(meta);
		player.getWorld().dropItemNaturally(player.getLocation(), playerHead);
	}
	
	public void giveBlockHead(Player player, String blockName) {
		if(debug) {logDebug("giveBlockHead START");}
		ItemStack blockStack = null;
		int isBlock = isBlockHeadName(blockName);
		int isBlock2 = isBlockHeadName2(blockName);
		int isBlock3 = isBlockHeadName3(blockName);
		if(isBlock != -1){
			if(debug) {logDebug("GBH isBlock=" + isBlock);}
			blockStack = blockHeads.getItemStack("blocks.block_" + isBlock + ".itemstack", new ItemStack(Material.AIR));
		}else if(isBlock2 != -1){
			if(debug) {logDebug("GBH isBlock2=" + isBlock2);}
			blockStack = blockHeads2.getItemStack("blocks.block_" + isBlock2 + ".itemstack", new ItemStack(Material.AIR));
		}else if(isBlock3 != -1){
			if(debug) {logDebug("GBH isBlock3=" + isBlock3);}
			blockStack = blockHeads3.getItemStack("blocks.block_" + isBlock3 + ".itemstack", new ItemStack(Material.AIR));
		}else {
			player.sendMessage(this.getName() + " v" + this.getDescription().getVersion() + " Sorry could not find \"" + blockName + "\"");
		}
		if( blockStack != null && blockStack.getType() != Material.AIR ) {
			player.getWorld().dropItemNaturally(player.getLocation(), blockStack);
			if(debug) {logDebug("GBH BlockHead given to " + player.getName());}
		}
		if(debug) {logDebug("giveBlockHead END");}
	}
	
	public boolean isInventoryFull(Player p)	{
	    return !(p.getInventory().firstEmpty() == -1);
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEntityEvent event){// TODO: PlayerInteractEntityEvent
		if(!(event.getPlayer() instanceof Player))
			return;
		try{
			Player player = event.getPlayer();
			if(player.hasPermission("flycutter.nametag")){
				if(getConfig().getBoolean("mob.nametag", false)) {
					Material material = player.getInventory().getItemInMainHand().getType();
					Material material2 = player.getInventory().getItemInOffHand().getType();
					String name = "";
					if(material.equals(Material.NAME_TAG)){
						name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
						if(debug){logDebug("PIEE" + player.getDisplayName() + " Main hand name=" + name);};
					}
					if(material2.equals(Material.NAME_TAG)){
						name = player.getInventory().getItemInOffHand().getItemMeta().getDisplayName();
						if(debug){logDebug("PIEE " + player.getDisplayName() + " Off hand name=" + name);};
					}
					
		/** experimental code */
					/*ItemStack itemstack = player.getInventory().getItemInOffHand();
					getConfig().set("itemstack", itemstack);
					saveConfig();*/
			//		log("itemstack set");
	
					//player.getInventory().addItem(getConfig().getItemStack("itemstack"));
					
					/*Villager villager = (Villager) mob;
					List<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>();
					MerchantRecipe recipe = new MerchantRecipe(getConfig().getItemStack("itemstack"), 1);
					recipe.addIngredient(new ItemStack(Material.EMERALD));
									recipes.add(recipe);
									villager.setRecipes(recipes);*/
			/** experimental code */
									
					//player.sendMessage("Testing");
					if(material.equals(Material.NAME_TAG)||material2.equals(Material.NAME_TAG)){
						if(getServer().getPluginManager().getPlugin("SilenceMobs") != null){
							if(name.toLowerCase().contains("silenceme")||name.toLowerCase().contains("silence me")){
								return;
							}
						}
						//player.sendMessage("Testing 2");
						LivingEntity mob = (LivingEntity) event.getRightClicked();
						//log("mob=" + mob.getType().toString());
						if(mob instanceof Skeleton||mob instanceof Zombie||mob instanceof PigZombie||mob instanceof Villager){
							//log("mob=" + mob.getType().toString());
							/**if(getConfig().getBoolean("enforce_whitelist", false)){ //
								if(getConfig().getString("whitelist.player_head_whitelist", "").toLowerCase().contains(name.toLowerCase())){
									// on whitelist make the head.
									giveMobHead(mob, name);
								}else{
									player.sendMessage("\"" + name + "\" " + lang.get("not_on_whitelist"));
									event.setCancelled(true);
								}
							}else if(getConfig().getBoolean("blacklist.enforce", false)){
								if(!getConfig().getString("blacklist.player_head_blacklist", "").toLowerCase().contains(name.toLowerCase())){
									// not on blacklist, make the head.
									giveMobHead(mob, name);
								}else{
									player.sendMessage("\"" + name + "\" " + lang.get("on_blacklist"));
									event.setCancelled(true);
								}
							}*/
							boolean enforcewhitelist = getConfig().getBoolean("whitelist.enforce", false);
							boolean enforceblacklist = getConfig().getBoolean("blacklist.enforce", false);
							boolean onwhitelist = getConfig().getString("whitelist.player_head_whitelist", "").toLowerCase().contains(name.toLowerCase());
							boolean onblacklist = getConfig().getString("blacklist.player_head_blacklist", "").toLowerCase().contains(name.toLowerCase());
							if(enforcewhitelist&&enforceblacklist){
								if(onwhitelist&&!(onblacklist)){
									giveMobHead(mob, name);
								}else{
									event.setCancelled(true); // return;
									if(debug){log(Level.INFO, "PIE - Name Error 1");}
								}
							}else if(enforcewhitelist&&!enforceblacklist){
								if(onwhitelist){
									giveMobHead(mob, name);
								}else{
									event.setCancelled(true); // return;
									if(debug){log(Level.INFO, "PIE - Name not on whitelist.");}
								}
							}else if(!enforcewhitelist&&enforceblacklist){
								if(!onblacklist){
									giveMobHead(mob, name);
								}else{
									event.setCancelled(true); // return;
									if(debug){log(Level.INFO, "PIE - Name is on blacklist.");}
								}
							}else{
								giveMobHead(mob, name);
							}
						}
					}
				}
			}else{
				//ZombieVillager mob = (ZombieVillager) event.getRightClicked();
				
				//player.sendMessage(mob.getName() + " profession= " + mob.getVillagerProfession());
			}
			//ZombieVillager mob = (ZombieVillager) event.getRightClicked();
			
			//player.sendMessage(mob.getName() + " profession= " + mob.getVillagerProfession());
		}catch (Exception e){
			stacktraceInfo();
			e.printStackTrace();
		}

	}
	 
	public ItemStack dropMobHead(Entity entity, String name, Player killer){// TODO: dropMobHead
		ItemStack helmet = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta meta = (SkullMeta)helmet.getItemMeta();
		meta.setOwningPlayer(Bukkit.getServer().getOfflinePlayer(name)); //.setOwner(name);
		meta.setDisplayName(getConfig().getString("headname.prefix") + name + getConfig().getString("headname.suffix"));
		ArrayList<String> lore = new ArrayList();
		if(getConfig().getBoolean("lore.show_killer", true)){
			List<String> var2 = getConfig().getStringList("headlore");
			List<String> lorelist = new ArrayList();
			for (String lorestr : var2){
				String str;
				str = lorestr.replace("%player%",killer.getDisplayName());
				lorelist.add(str);
			}
			lore.addAll(lorelist);
		}
		if(getConfig().getBoolean("super.show_time",true)){
			Date date = new Date();
			String strDateFormat = getConfig().getString("super.time_format");
			SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
			lore.add(getConfig().getString("super.time_color").replace("&","§") + sdf.format(date));
		}
		lore.add("§0Powered by FlyCutter");
		meta.setLore(lore);
		meta.setLore(lore);
		helmet.setItemMeta(meta);//																	 e2d4c388-42d5-4a96-b4c9-623df7f5e026
		helmet.setItemMeta(meta);
		entity.getWorld().dropItemNaturally(entity.getLocation(), helmet);
		return helmet;
	}
	 
	public boolean DropIt(EntityDeathEvent event, double chancepercent){// TODO: DropIt
		ItemStack itemstack = event.getEntity().getKiller().getInventory().getItemInMainHand();
		if(itemstack != null){
				if(debug){logDebug("DI itemstack=" + itemstack.getType().toString() + " line:1023");}
			int enchantmentlevel = 0;
			if(getConfig().getBoolean("apply_looting", true)){
				enchantmentlevel = itemstack.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
			}
			if(chancepercent == 0){
				enchantmentlevel = 0;
			}
				if(debug){logDebug("DI enchantmentlevel=" + enchantmentlevel + " line:1031");}
			double enchantmentlevelpercent = ((double)enchantmentlevel / 100);
				if(debug){logDebug("DI enchantmentlevelpercent=" + enchantmentlevelpercent + " line:1033");}
			double chance = Math.random();
				if(debug){logDebug("DI chance=" + chance + " line:1035");}
			
				if(debug){logDebug("DI chancepercent=" + chancepercent + " line:1037");}
			chancepercent = chancepercent + enchantmentlevelpercent;
				if(debug){logDebug("DI chancepercent2=" + chancepercent + " line:1039");}
			if (chancepercent > chance){
				return true;
			}
		}
		return false;
	}
	
	public boolean DropIt2( double chancepercent){// TODO: DropIt
			int enchantmentlevel = 0;
			if(chancepercent == 0){
				enchantmentlevel = 0;
			}
				if(debug){logDebug("DI enchantmentlevel=" + enchantmentlevel + " line:1031");}
			double enchantmentlevelpercent = ((double)enchantmentlevel / 100);
				if(debug){logDebug("DI enchantmentlevelpercent=" + enchantmentlevelpercent + " line:1033");}
			double chance = Math.random();
				if(debug){logDebug("DI chance=" + chance + " line:1035");}
			
				if(debug){logDebug("DI chancepercent=" + chancepercent + " line:1037");}
			chancepercent = chancepercent + enchantmentlevelpercent;
				if(debug){logDebug("DI chancepercent2=" + chancepercent + " line:1039");}
			if (chancepercent > chance){
				return true;
			}
		return false;
	}
	
	/**
	@SuppressWarnings("unused")
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event){ // TODO: EnityDeathEvent
		LivingEntity entity = event.getEntity();
		World world = event.getEntity().getWorld();
		
			
		if(world_whitelist != null&&!world_whitelist.isEmpty()&&world_blacklist != null&&!world_blacklist.isEmpty()){
			if(!StrUtils.stringContains(world_whitelist, world.getName().toString())&&StrUtils.stringContains(world_blacklist, world.getName().toString())){
				if(debug){logDebug("EDE - World - On blacklist and Not on whitelist.");}
				return;
			}else if(!StrUtils.stringContains(world_whitelist, world.getName().toString())&&!StrUtils.stringContains(world_blacklist, world.getName().toString())){
				if(debug){logDebug("EDE - World - Not on whitelist.");}
				return;
			}else if(!StrUtils.stringContains(world_whitelist, world.getName().toString())){
				
			}
		}else if(world_whitelist != null&&!world_whitelist.isEmpty()){
			if(!StrUtils.stringContains(world_whitelist, world.getName().toString())){
				if(debug){logDebug("EDE - World - Not on whitelist.");}
				return;
			}
		}else if(world_blacklist != null&&!world_blacklist.isEmpty()){
			if(StrUtils.stringContains(world_blacklist, world.getName().toString())){
				if(debug){logDebug("EDE - World - On blacklist.");}
				return;
			}
		}
			if(entity instanceof Player){
				if(debug){logDebug("EDE Entity is Player line:877");}

				if(entity.getKiller() instanceof Player){
					if(entity.getKiller().hasPermission("flycutter.players")){
						if(DropIt(event, chanceConfig.getDouble("chance_percent.player", 0.50))){
							//Player daKiller = entity.getKiller();
							if(debug){logDebug("EDE Killer is Player line:1073");}
							ItemStack helmet = new ItemStack(Material.PLAYER_HEAD, 1, (short)3);
							SkullMeta meta = (SkullMeta)helmet.getItemMeta();
							meta.setOwningPlayer(Bukkit.getServer().getOfflinePlayer(((Player) entity).getUniqueId()));
							meta.setDisplayName(((Player) entity).getDisplayName() + "'s Head");
							ArrayList<String> lore = new ArrayList();
							if(getConfig().getBoolean("lore.show_killer", true)){
								lore.add(ChatColor.RESET + "Killed by " + ChatColor.RESET + ChatColor.YELLOW + entity.getKiller().getDisplayName());
							}
							if(getConfig().getBoolean("lore.show_plugin_name", true)){
								lore.add(ChatColor.AQUA + "" + this.getName());
							}
							meta.setLore(lore);
								helmet.setItemMeta(meta);//																	 e2d4c388-42d5-4a96-b4c9-623df7f5e026
							helmet.setItemMeta(meta);
						
							entity.getWorld().dropItemNaturally(entity.getLocation(), helmet);
							if(debug){logDebug("EDE " + ((Player) entity).getDisplayName().toString() + " Player Head Dropped");}
						}
						return;
					}
				}
			}else if(event.getEntity() instanceof LivingEntity){
				/** Move this higher 
				double chancepercent = 0.50; //** Set to check config.yml later/
				String s = Double.toString(chancepercent);
				log("chancepercent=" + s.length());
				/** Move this higher */
				/**if(entity.getKiller() instanceof Player){
					String name = event.getEntityType().toString().replace(" ", "_");
					if(debug){logDebug("EDE name=" + name);}
					//ItemStack itemstack = event.getEntity().getKiller().getInventory().getItemInMainHand();
					//if(itemstack != null){
						/**if(debug){logDebug("itemstack=" + itemstack.getType().toString() + " line:159");}
						int enchantmentlevel = itemstack.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);//.containsEnchantment(Enchantment.LOOT_BONUS_MOBS);
						if(debug){logDebug("enchantmentlevel=" + enchantmentlevel + " line:161");}
						double enchantmentlevelpercent = ((double)enchantmentlevel / 100);
						if(debug){logDebug("enchantmentlevelpercent=" + enchantmentlevelpercent + " line:163");}
						double chance = Math.random();
						if(debug){logDebug("chance=" + chance + " line:165");}
						
						if(debug){logDebug("chancepercent=" + chancepercent + " line:167");}
						chancepercent = chancepercent + enchantmentlevelpercent;
						if(debug){logDebug("chancepercent2=" + chancepercent + " line:169");}*/
						//if(chancepercent > 0.00 && chancepercent < 0.99){
								//if (chancepercent > chance){
									//event.getDrops().add(new ItemStack(Material.CREEPER_HEAD, 1));
							//@Nonnull Set<String> isSpawner;
							/**String isNametag = null;
							@Nonnull
							PersistentDataContainer pdc = entity.getPersistentDataContainer();
							isNametag = entity.getPersistentDataContainer().get(NAMETAG_KEY, PersistentDataType.STRING);//.getScoreboardTags();//
							if(debug&&isNametag != null){logDebug("EDE isNametag=" + isNametag.toString());}

							if(entity.getKiller().hasPermission("flycutter.mobs")){
								if(entity.getKiller().hasPermission("flycutter.nametag")&&isNametag != null){
									if(entity.getCustomName() != null&&!(entity.getCustomName().contains("jeb_"))
												&&!(entity.getCustomName().contains("Toast"))){
											if(debug){logDebug("EDE customname=" + entity.getCustomName().toString());}
											if(entity instanceof Skeleton||entity instanceof Zombie||entity instanceof PigZombie){
												if(getServer().getPluginManager().getPlugin("SilenceMobs") != null){
													if(entity.getCustomName().toLowerCase().contains("silenceme")||entity.getCustomName().toLowerCase().contains("silence me")){
													return;
												}
												}
												boolean enforcewhitelist = getConfig().getBoolean("whitelist.enforce", false);
												boolean enforceblacklist = getConfig().getBoolean("blacklist.enforce", false);
												boolean onwhitelist = getConfig().getString("whitelist.player_head_whitelist", "").toLowerCase().contains(entity.getCustomName().toLowerCase());
												boolean onblacklist = getConfig().getString("blacklist.player_head_blacklist", "").toLowerCase().contains(entity.getCustomName().toLowerCase());
												if(DropIt(event, chanceConfig.getDouble("named_mob", 0.10))){
													if(enforcewhitelist&&enforceblacklist){
														if(onwhitelist&&!(onblacklist)){
															dropMobHead(entity, entity.getCustomName(), entity.getKiller());
														if(debug){logDebug("EDE " + entity.getCustomName().toString() + " Head Dropped");}
														}
													}else if(enforcewhitelist&&!enforceblacklist){
														if(onwhitelist){
															dropMobHead(entity, entity.getCustomName(), entity.getKiller());
														if(debug){logDebug("EDE " +entity.getCustomName().toString() + " Head Dropped");}
														}
													}else if(!enforcewhitelist&&enforceblacklist){
														if(!onblacklist){
															dropMobHead(entity, entity.getCustomName(), entity.getKiller());
														if(debug){logDebug("EDE " +entity.getCustomName().toString() + " Head Dropped");}
														}
													}else{
														dropMobHead(entity, entity.getCustomName(), entity.getKiller());
													if(debug){logDebug("EDE " +entity.getCustomName().toString() + " Head Dropped");}
													}
												}
											}
										return;
									}
								}
					 			//String name = event.getEntity().getName().toUpperCase().replace(" ", "_");

					 			if(mob_whitelist != null&&!mob_whitelist.isEmpty()&&mob_blacklist != null&&!mob_blacklist.isEmpty()){
					 				if(!StrUtils.stringContains(mob_whitelist.toUpperCase(), name)){//mob_whitelist.contains(name)
					 					log(Level.INFO, "EDE - Mob - Not on whitelist. 1 Mob=" + name);
					 					return;
					 				}
					 			}else if(mob_whitelist != null&&!mob_whitelist.isEmpty()){
					 				if(!StrUtils.stringContains(mob_whitelist.toUpperCase(), name)&&StrUtils.stringContains(mob_blacklist, name)){//mob_whitelist.contains(name)
					 					log(Level.INFO, "EDE - Mob - Not on whitelist. 2 Mob=" + name);
					 					return;
					 				}
					 			}else if(mob_blacklist != null&&!mob_blacklist.isEmpty()){
					 				if(StrUtils.stringContains(mob_blacklist.toUpperCase(), name)){
					 					log(Level.INFO, "EDE - Mob - On blacklist. Mob=" + name);
					 					return;
					 				}
					 			}
								switch (name) {
					 			case "CREEPER":
					 				Creeper creeper = (Creeper) event.getEntity();
					 				double cchance = chanceConfig.getDouble("chance_percent.creeper", defpercent);
				 					if(creeper.isPowered()) {
				 						name = "CREEPER_CHARGED";
				 						cchance = 1.00;
				 					}
					 				if(DropIt(event, cchance)){
						 				if(getConfig().getBoolean("vanilla_heads.creeper", false)&&name != "CREEPER_CHARGED"){
						 					entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.CREEPER_HEAD));
						 				}else{ // langName
						 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
						 							langName.getString(name.toLowerCase(), MobHeads.valueOf(name).getName() + " Head"), entity.getKiller()));
						 				} // MobHeads.valueOf(name).getName() + " Head"
						 				if(debug){logDebug("EDE Creeper vanilla=" + getConfig().getBoolean("vanilla_heads.creeper", false));}
						 				if(debug){logDebug("EDE Creeper Head Dropped");}
					 				}
					 				break;
					 			case "ZOMBIE":
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.zombie", defpercent))){
					 					if(getConfig().getBoolean("vanilla_heads.zombie", false)){
						 					entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.ZOMBIE_HEAD));
						 				}else{
						 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
						 							langName.getString(name.toLowerCase(), MobHeads.valueOf(name).getName() + " Head"), entity.getKiller()));
						 				}
					 					if(debug){logDebug("EDE Zombie vanilla=" + getConfig().getBoolean("vanilla_heads.zombie", false));}
					 					if(debug){logDebug("EDE Zombie Head Dropped");}
					 				}
					 				break;
					 			case "SKELETON":
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.skeleton", defpercent))){
					 					if(getConfig().getBoolean("vanilla_heads.skeleton", false)){
						 					entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.SKELETON_SKULL));
						 				}else{
						 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
						 							langName.getString(name.toLowerCase(), MobHeads.valueOf(name).getName() + " Head"), entity.getKiller()));
						 				}
					 					if(debug){logDebug("EDE Skeleton vanilla=" + getConfig().getBoolean("vanilla_heads.skeleton", false));}
					 					if(debug){logDebug("EDE Skeleton Head Dropped");}
					 				}
					 				break;
					 			case "WITHER_SKELETON":
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.wither_skeleton", defpercent))){
					 					if(getConfig().getBoolean("vanilla_heads.wither_skeleton", false)){
						 					entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.WITHER_SKELETON_SKULL));
						 				}else{
						 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
						 							langName.getString(name.toLowerCase(), MobHeads.valueOf(name).getName() + " Head"), entity.getKiller()));
						 				}
					 					if(debug){logDebug("EDE Wither Skeleton Head Dropped");}
					 				}
					 				break;
					 			case "ENDER_DRAGON":
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.ender_dragon", defpercent))){
					 					if(getConfig().getBoolean("vanilla_heads.ender_dragon", false)){
						 					entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.DRAGON_HEAD));
						 				}else{
						 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
						 							langName.getString(name.toLowerCase(), MobHeads.valueOf(name).getName() + " Head"), entity.getKiller()));
						 				}
					 					if(debug){logDebug("EDE Ender Dragon Head Dropped");}
					 				}
					 				break;
					 			/**case "TROPICAL_FISH":
					 				TropicalFish daFish = (TropicalFish) entity;
					 				DyeColor daFishBody = daFish.getBodyColor();
					 				DyeColor daFishPatternColor = daFish.getPatternColor();
					 				Pattern	daFishType = daFish.getPattern();
					 				log("bodycolor=" + daFishBody.toString() + "\nPatternColor=" + daFishPatternColor.toString() + "\nPattern=" + daFishType.toString());
					 				//TropicalFishHeads daFishEnum = TropicalFishHeads.getIfPresent(name);
					 				
					 				if(DropIt(event, getConfig().getDouble(name + "_" +	daFishType, defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name + "_" +	daFishType).getTexture(), MobHeads.valueOf(name + "_" +	daFishType).getName(), entity.getKiller()));
					 				}
					 				if(debug){logDebug("Skeleton Head Dropped");}
					 				break;*/
					 			/**case "WITHER":
									//Wither wither = (Wither) event.getEntity();
									int random = randomBetween(1, 4);
									if(debug){logDebug("EDE Wither random=" + random + "");}
									if(DropIt(event, chanceConfig.getDouble("chance_percent.wither", defpercent))){
										entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name + "_" + random).getTexture().toString(), 
					 							langName.getString(name.toLowerCase() + "." + random, MobHeads.valueOf(name + "_" + random).getName() + " Head"), entity.getKiller()));
										if(debug){logDebug("EDE Wither_" + random + " Head Dropped");}
									}
									break;
					 			case "WOLF":
					 				Wolf wolf = (Wolf) event.getEntity();
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
						 				if(wolf.isAngry()){
						 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name + "_ANGRY").getTexture().toString(), 
						 							langName.getString(name.toLowerCase() + "_angry", MobHeads.valueOf(name + "_ANGRY").getName() + " Head"), entity.getKiller()));
						 					if(debug){logDebug("EDE Angry Wolf Head Dropped");}
						 				}else{
						 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
						 							langName.getString(name.toLowerCase(), event.getEntity().getName() + " Head"), entity.getKiller()));
						 					if(debug){logDebug("EDE Wolf Head Dropped");}
						 				}
					 				}
					 				break;
					 			case "FOX":
					 				Fox dafox = (Fox) entity;
					 				String dafoxtype = dafox.getFoxType().toString();
					 				if(debug){logDebug("EDE dafoxtype=" + dafoxtype);}
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.fox." + dafoxtype.toString().toLowerCase(), defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name + "_" + dafoxtype).getTexture().toString(), 
					 							langName.getString(name.toLowerCase() + "." + dafoxtype.toLowerCase(), MobHeads.valueOf(name + "_" + dafoxtype).getName() + " Head"), entity.getKiller()));
					 					if(debug){logDebug("EDE Fox Head Dropped");}
					 				}
					 				
					 				break;
					 			case "CAT":
					 				Cat dacat = (Cat) entity;
					 				String dacattype = dacat.getCatType().toString();
					 				if(debug){logDebug("entity cat=" + dacat.getCatType());}
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.cat." + dacattype.toLowerCase(), defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(),
				 							makeSkull(CatHeads.valueOf(dacattype).getTexture().toString(), 
				 									langName.getString(name.toLowerCase() + "." + dacattype.toLowerCase(), CatHeads.valueOf(dacattype).getName() + " Head"), entity.getKiller()));
					 					if(debug){logDebug("EDE Cat Head Dropped");}
					 				}
					 				break;
					 			case "OCELOT":
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
					 							langName.getString(MobHeads.valueOf(name).getNameString(), MobHeads.valueOf(name).getName() + " Head"), entity.getKiller()));
					 					if(debug){logDebug("EDE " + name + " Head Dropped");}
					 				}
					 				if(debug){logDebug("EDE " + MobHeads.valueOf(name) + " killed");}
					 				
					 				break;
					 			case "BEE":
					 				Bee daBee = (Bee) entity;
					 				int daAnger = daBee.getAnger();
					 				if(debug){logDebug("EDE daAnger=" + daAnger);}
					 				boolean daNectar = daBee.hasNectar();
					 				if(debug){logDebug("EDE daNectar=" + daNectar);}
						 				if(daAnger >= 1&&daNectar == true){
						 					if(DropIt(event, chanceConfig.getDouble("chance_percent.bee.angry_pollinated", defpercent))){
						 						entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf("BEE_ANGRY_POLLINATED").getTexture().toString(), 
						 								langName.getString(name.toLowerCase() + ".angry_pollinated", "Angry Pollinated Bee Head"), entity.getKiller()));
						 						if(debug){logDebug("EDE Angry Pollinated Bee Head Dropped");}
						 					}
						 				}else if(daAnger >= 1&&daNectar == false){
						 					if(DropIt(event, chanceConfig.getDouble("chance_percent.bee.angry", defpercent))){
						 						entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf("BEE_ANGRY").getTexture().toString(), 
						 								langName.getString(name.toLowerCase() + ".angry", "Angry Bee Head"), entity.getKiller()));
						 						if(debug){logDebug("EDE Angry Bee Head Dropped");}
						 					}
						 				}else if(daAnger == 0&&daNectar == true){
						 					if(DropIt(event, chanceConfig.getDouble("chance_percent.bee.pollinated", defpercent))){
						 						entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf("BEE_POLLINATED").getTexture().toString(), 
						 								langName.getString(name.toLowerCase() + ".pollinated", "Pollinated Bee Head"), entity.getKiller()));
						 						if(debug){logDebug("EDE Pollinated Bee Head Dropped");}
						 					}
						 				}else if(daAnger == 0&&daNectar == false){
						 					if(DropIt(event, chanceConfig.getDouble("chance_percent.bee.chance_percent", defpercent))){
						 						entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf("BEE").getTexture().toString(), 
						 								langName.getString(name.toLowerCase() + ".none", "Bee Head"), entity.getKiller()));
						 						if(debug){logDebug("EDE Bee Head Dropped");}
						 					}
						 				}
					 				break;
					 			case "LLAMA":
					 				Llama daLlama = (Llama) entity;
					 				String daLlamaColor = daLlama.getColor().toString();
					 				String daLlamaName = LlamaHeads.valueOf(name + "_" + daLlamaColor).getName() + " Head";//daLlamaColor.toLowerCase().replace("b", "B").replace("c", "C").replace("g", "G").replace("wh", "Wh") + " Llama Head";
					 				//log(name + "_" + daLlamaColor);
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.llama." + daLlamaColor.toLowerCase(), defpercent))){		
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(LlamaHeads.valueOf(name + "_" + daLlamaColor).getTexture().toString(), 
					 							langName.getString(name.toLowerCase() + "." + daLlamaColor.toLowerCase(), daLlamaName), entity.getKiller()));
					 					if(debug){logDebug("EDE Llama Head Dropped");}
					 				}
					 				break;
					 			case "HORSE":
					 				Horse daHorse = (Horse) entity;
					 				String daHorseColor = daHorse.getColor().toString();
					 				String daHorseName = HorseHeads.valueOf(name + "_" + daHorseColor).getName() + " Head";//daHorseColor.toLowerCase().replace("b", "B").replace("ch", "Ch").replace("cr", "Cr").replace("d", "D")
					 						//.replace("g", "G").replace("wh", "Wh").replace("_", " ") + " Horse Head";
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.horse." + daHorseColor.toLowerCase(), defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(HorseHeads.valueOf(name + "_" + daHorseColor).getTexture().toString(), 
					 							langName.getString(name.toLowerCase() + "." + daHorseColor.toLowerCase(), daHorseName), entity.getKiller()));
					 					if(debug){logDebug("EDE Horse Head Dropped");}
					 				}
					 				break;
					 			case "MUSHROOM_COW":
					 				MushroomCow daMushroom = (MushroomCow) entity;
					 				String daCowVariant = daMushroom.getVariant().toString();
					 				String daCowName = daCowVariant.toLowerCase().replace("br", "Br").replace("re", "Re") + " Mooshroom Head";
					 				if(debug){logDebug("EDE " + name + "_" + daCowVariant);}
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.mushroom_cow." + daCowVariant.toLowerCase(), defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name + "_" + daCowVariant).getTexture().toString(), 
					 							langName.getString(name.toLowerCase() + "." + daCowVariant.toLowerCase(), daCowName), entity.getKiller()));
					 					if(debug){logDebug("EDE Mooshroom Head Dropped");}
					 				}
					 				break;
					 			case "PANDA":
					 				Panda daPanda = (Panda) entity;
					 				String daPandaGene = daPanda.getMainGene().toString();
					 				String daPandaName = daPandaGene.toLowerCase().replace("br", "Br").replace("ag", "Ag").replace("la", "La")
					 						.replace("no", "No").replace("p", "P").replace("we", "We").replace("wo", "Wo") + " Panda Head";
					 				if(daPandaGene.equalsIgnoreCase("normal")){daPandaName.replace("normal ", "");}
					 				if(debug){logDebug("EDE " + name + "_" + daPandaGene);}
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.panda." + daPandaGene.toLowerCase(), defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name + "_" + daPandaGene).getTexture().toString(), 
					 							langName.getString(name.toLowerCase() + "." + daPandaGene.toLowerCase(), daPandaName), entity.getKiller()));
					 					if(debug){logDebug("EDE Panda Head Dropped");}
					 				}
					 				break;
					 			case "PARROT":
					 				Parrot daParrot = (Parrot) entity;
					 				String daParrotVariant = daParrot.getVariant().toString();
					 				String daParrotName = daParrotVariant.toLowerCase().replace("b", "B").replace("c", "C").replace("g", "G")
					 						.replace("red", "Red") + " Parrot Head";
					 				if(debug){logDebug("EDE " + name + "_" + daParrotVariant);}
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.parrot." + daParrotVariant.toLowerCase(), defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name + "_" + daParrotVariant).getTexture().toString(), 
					 							langName.getString(name.toLowerCase() + "." + daParrotVariant.toLowerCase(), daParrotName), entity.getKiller()));
					 					if(debug){logDebug("EDE Parrot Head Dropped");}
					 				}
					 				break;
					 			case "RABBIT":
					 				String daRabbitType;
					 				Rabbit daRabbit = (Rabbit) entity;
					 				daRabbitType = daRabbit.getRabbitType().toString();
					 				if(daRabbit.getCustomName() != null){
					 					if(daRabbit.getCustomName().contains("Toast")){
						 					daRabbitType = "Toast";
						 				}
					 				}
					 				String daRabbitName = RabbitHeads.valueOf(name + "_" + daRabbitType).getName() + " Head";
					 				if(debug){logDebug("EDE " + name + "_" + daRabbitType);}
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.rabbit." + daRabbitType.toLowerCase(), defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(RabbitHeads.valueOf(name + "_" + daRabbitType).getTexture().toString(), 
					 							langName.getString(name.toLowerCase() + "." + daRabbitType.toLowerCase(), daRabbitName), entity.getKiller()));
					 					if(debug){logDebug("EDE Rabbit Head Dropped");}
					 				}
					 				break;
					 			case "VILLAGER":
					 				Villager daVillager = (Villager) entity; // Location jobsite = daVillager.getMemory(MemoryKey.JOB_SITE);
					 				String daVillagerType = daVillager.getVillagerType().toString();
					 				String daVillagerProfession = daVillager.getProfession().toString();
					 				if(debug){logDebug("EDE name=" + name);}
					 				if(debug){logDebug("EDE profession=" + daVillagerProfession);}
					 				if(debug){logDebug("EDE type=" + daVillagerType);}
					 				String daName = name + "_" + daVillagerProfession + "_" + daVillagerType;
					 				if(debug){logDebug("EDE " + daName + "		 " + name + "_" + daVillagerProfession + "_" + daVillagerType);}
					 				String daVillagerName = VillagerHeads.valueOf(daName).getName() + " Head";
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.villager." + daVillagerType.toLowerCase() + "." + daVillagerProfession.toLowerCase(), defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(VillagerHeads.valueOf(name + "_" + daVillagerProfession + "_" + daVillagerType).getTexture().toString(), 
					 							langName.getString(name.toLowerCase() + "." + daVillagerType.toLowerCase() + "." + daVillagerProfession.toLowerCase()
					 									, daVillagerName), entity.getKiller()));
					 					if(debug){logDebug("EDE Villager Head Dropped");}
					 				}
					 				break;
					 			case "ZOMBIE_VILLAGER":
					 				ZombieVillager daZombieVillager = (ZombieVillager) entity;
					 				String daZombieVillagerProfession = daZombieVillager.getVillagerProfession().toString();
					 				String daZombieVillagerName = ZombieVillagerHeads.valueOf(name + "_" + daZombieVillagerProfession).getName() + " Head";
					 				if(debug){logDebug("EDE " + name + "_" + daZombieVillagerProfession);}
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.zombie_villager", defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(ZombieVillagerHeads.valueOf(name + "_" + daZombieVillagerProfession).getTexture().toString(), 
					 							langName.getString(name.toLowerCase() + "." + daZombieVillagerProfession.toLowerCase(), daZombieVillagerName), entity.getKiller()));
					 					if(debug){logDebug("EDE Zombie Villager Head Dropped");}
					 				}
					 				break;
					 			case "SHEEP":
					 				Sheep daSheep = (Sheep) entity;
					 				String daSheepColor = daSheep.getColor().toString();
					 				String daSheepName;
					 				
					 				if(daSheep.getCustomName() != null){
						 				if(daSheep.getCustomName().contains("jeb_")){
						 					daSheepColor = "jeb_";
						 				}else{
						 					daSheepColor = daSheep.getColor().toString();
						 				}
					 				}
					 				daSheepName = SheepHeads.valueOf(name + "_" + daSheepColor).getName() + " Head";
					 				if(debug){logDebug("EDE " + daSheepColor + "_" + name);}
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.sheep." + daSheepColor.toLowerCase(), defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(SheepHeads.valueOf(name + "_" + daSheepColor).getTexture().toString(), 
					 							langName.getString(name.toLowerCase() + "." + daSheepColor.toLowerCase(), daSheepName), entity.getKiller()));
					 					if(debug){logDebug("EDE Sheep Head Dropped");}
					 				}
					 				break;
					 			/**case "STRIDER":
					 				Strider strider = (Strider) entity;
					 				
					 				break;*/
					 			/**case "TRADER_LLAMA":
					 				TraderLlama daTraderLlama = (TraderLlama) entity;
					 				String daTraderLlamaColor = daTraderLlama.getColor().toString();
					 				String daTraderLlamaName = LlamaHeads.valueOf(name + "_" + daTraderLlamaColor).getName() + " Head";
					 				if(debug){logDebug("EDE " + daTraderLlamaColor + "_" + name);}
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent.trader_llama." + daTraderLlamaColor.toLowerCase(), defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(LlamaHeads.valueOf(name + "_" + daTraderLlamaColor).getTexture().toString(), 
					 							langName.getString(name.toLowerCase() + "." + daTraderLlamaColor.toLowerCase(), daTraderLlamaName), entity.getKiller()));
					 					if(debug){logDebug("EDE Trader Llama Head Dropped");}
					 				}
					 				break;
					 			default:
					 				//makeSkull(MobHeads.valueOf(name).getTexture(), name);
					 				if(debug){logDebug("EDE name=" + name + " line:1005");}
					 				if(debug){logDebug("EDE texture=" + MobHeads.valueOf(name).getTexture().toString() + " line:1006");}
					 				if(debug){logDebug("EDE location=" + entity.getLocation().toString() + " line:1007");}
					 				if(debug){logDebug("EDE getName=" + event.getEntity().getName() + " line:1008");}
					 				if(debug){logDebug("EDE killer=" + entity.getKiller().toString() + " line:1009");}
					 				if(DropIt(event, chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
					 							langName.getString(name.toLowerCase(), event.getEntity().getName() + " Head"), entity.getKiller()));
					 					if(debug){logDebug("EDE " + name + " Head Dropped");}
					 				}
					 				if(debug){logDebug("EDE " + MobHeads.valueOf(name) + " killed");}
					 				break;
					 			}
							}
						//}
					//}
					return;
				}
			}
	}//*/
	/**
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event){ //onEntitySpawn(EntitySpawnEvent e) { // TODO: onCreatureSpawn
		if(getConfig().getBoolean("wandering_trades.custom_wandering_trader", true)){
			Entity entity = event.getEntity();
					if(entity instanceof WanderingTrader){
						//traderHeads2 = YamlConfiguration.loadConfiguration(traderFile2);
						if(debug){logDebug("CSE WanderingTrader spawned");}
						WanderingTrader trader = (WanderingTrader) entity;
						List<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>();
						List<MerchantRecipe> oldRecipes = new ArrayList<MerchantRecipe>();
						oldRecipes = trader.getRecipes();
						// Loop through player heads
						
						/**int playernum = traderHeads.getInt("players.number") + 1;
						for(int i=1; i<playernum; i++){
							String texture = traderHeads.getString("players.player_" + i + ".texture");
							String name = traderHeads.getString("players.player_" + i + ".name");
							String uuid = traderHeads.getString("players.player_" + i + ".uuid");
							Player player = Bukkit.getPlayer("JoelYahwehOfWar");
							ItemStack itemstack = makeTraderSkull(texture, name, uuid, 1);
							player.getInventory().setItem(1, itemstack);
							itemstack = player.getInventory().getItem(1);
							player.getInventory().setItem(1, new ItemStack(Material.AIR, 1));
							ItemStack price1 = new ItemStack(Material.EMERALD);
							ItemStack price2 = new ItemStack(Material.AIR);
							// save item to traderheads2
							traderHeads2.set("players.player_" + i + ".price_1", price1);
							traderHeads2.set("players.player_" + i + ".price_2", price2);
							traderHeads2.set("players.player_" + i + ".itemstack", itemstack);
							log("player_" + i + " has been updated.");
						}
						// save number to trader_heads2
						traderHeads2.set("players.number", playernum - 1);
						log("traderFile2=" + traderFile2.getPath());
						if(traderHeads2 == null){
							log("null");
						}
						try {
							this.traderHeads2.save(traderFile2);
						} catch (IOException e) {

							e.printStackTrace();
						}
						log("players saved");
						
						int blocknum = traderHeads.getInt("blocks.number") + 1;
						for(int i=1; i<blocknum; i++){
							String texture = traderHeads.getString("blocks.block_" + i + ".texture");
							String name = traderHeads.getString("blocks.block_" + i + ".name");
							String uuid = traderHeads.getString("blocks.block_" + i + ".uuid");
							Material material = Material.matchMaterial(traderHeads.getString("blocks.block_" + i + ".material"));
							Player player = Bukkit.getPlayer("JoelYahwehOfWar");
							ItemStack itemstack = makeTraderSkull(texture, name, uuid, 1);
							player.getInventory().setItem(1, itemstack);
							itemstack = player.getInventory().getItem(1);
							player.getInventory().setItem(1, new ItemStack(Material.AIR, 1));
							ItemStack price1 = new ItemStack(Material.EMERALD);
							ItemStack price2 = new ItemStack(material);
							traderHeads2.set("blocks.block_" + i + ".price_1", price1);
							traderHeads2.set("blocks.block_" + i + ".price_2", price2);
							traderHeads2.set("blocks.block_" + i + ".itemstack", itemstack);
							log("block_" + i + " has been updated.");
						}
						traderHeads2.set("blocks.number", blocknum - 1);
						try {
							this.traderHeads2.save(traderFile2);
							saveConfig();
						} catch (IOException e) {
							e.printStackTrace();
						}
						log("blocks saved");*/
						
						/**
						 *  Player Heads
						 */ 
						/**if(getConfig().getBoolean("wandering_trades.player_heads.enabled", true)){
							int playerRandom = randomBetween(getConfig().getInt("wandering_trades.player_heads.min", 0), getConfig().getInt("wandering_trades.player_heads.max", 3));
							if(debug){logDebug("CSE playerRandom=" + playerRandom);}
							if(playerRandom > 0){
								if(debug){logDebug("CSE playerRandom > 0");}
								int numOfplayerheads = playerHeads.getInt("players.number");
								if(debug){logDebug("CSE numOfplayerheads=" + numOfplayerheads);}
								HashSet<Integer> used = new HashSet<Integer>();
								for(int i=0; i<playerRandom; i++){
									int randomPlayerHead = randomBetween(1, numOfplayerheads);
									while (used.contains(randomPlayerHead)) { //while we have already used the number
										randomPlayerHead = randomBetween(1, numOfplayerheads); //generate a new one because it's already used
								    }
								    //by this time, add will be unique
								    used.add(randomPlayerHead);
										if(debug){logDebug("CSE randomPlayerHead=" + randomPlayerHead);}
									ItemStack price1 = playerHeads.getItemStack("players.player_" + randomPlayerHead + ".price_1", new ItemStack(Material.AIR));
										if(debug){logDebug("CSE price1=" + price1);}
									ItemStack price2 = playerHeads.getItemStack("players.player_" + randomPlayerHead + ".price_2", new ItemStack(Material.AIR));
										if(debug){logDebug("CSE price2=" + price2);}
									ItemStack itemstack = playerHeads.getItemStack("players.player_" + randomPlayerHead + ".itemstack", new ItemStack(Material.AIR));
										if(debug){logDebug("CSE itemstack=" + itemstack);}
									MerchantRecipe recipe = new MerchantRecipe(itemstack, playerHeads.getInt("players.player_" + randomPlayerHead + ".quantity", (int) 3));
									recipe.setExperienceReward(true);
									recipe.addIngredient(price1);
									recipe.addIngredient(price2);
									recipes.add(recipe);
								}
								used.clear();
							}
						}
						/**
						 *  Block Heads
						 */
						/**if(getConfig().getBoolean("wandering_trades.block_heads.enabled", true)){
							int min = getConfig().getInt("wandering_trades.block_heads.min", 0);
							int max;
							if(getMCVersion().startsWith("1.16")||getMCVersion().startsWith("1.17")){
								max = getConfig().getInt("wandering_trades.block_heads.max", 5) / 2;
							}else{
								max = getConfig().getInt("wandering_trades.block_heads.max", 5);
							}
							if(debug){logDebug("CSE BH1 min=" + min + " max=" + max);}
							int blockRandom = randomBetween(min, max);
								if(debug){logDebug("CSE blockRandom=" + blockRandom);}
							if(blockRandom > 0){
									if(debug){logDebug("CSE blockRandom > 0");}
								int numOfblockheads = blockHeads.getInt("blocks.number");
									if(debug){logDebug("CSE numOfblockheads=" + numOfblockheads);}
								HashSet<Integer> used = new HashSet<Integer>();
								for(int i=0; i<blockRandom; i++){
										if(debug){logDebug("CSE i=" + i);}
									int randomBlockHead = randomBetween(1, numOfblockheads);
									while (used.contains(randomBlockHead)) { //while we have already used the number
										randomBlockHead = randomBetween(1, numOfblockheads); //generate a new one because it's already used
								    }
								    //by this time, add will be unique
								    used.add(randomBlockHead);
										if(debug){logDebug("CSE randomBlockHead=" + randomBlockHead);}
									ItemStack price1 = blockHeads.getItemStack("blocks.block_" + randomBlockHead + ".price_1", new ItemStack(Material.AIR));
										if(debug){logDebug("CSE price1=" + price1);}
									ItemStack price2 = blockHeads.getItemStack("blocks.block_" + randomBlockHead + ".price_2", new ItemStack(Material.AIR));
										if(debug){logDebug("CSE price2=" + price2);}
									ItemStack itemstack = blockHeads.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
										if(debug){logDebug("CSE itemstack=" + itemstack);}
									MerchantRecipe recipe = new MerchantRecipe(itemstack, blockHeads.getInt("blocks.block_" + randomBlockHead + ".quantity", (int) 1));
										recipe.setExperienceReward(true);
										recipe.addIngredient(price1);
										recipe.addIngredient(price2);
										recipes.add(recipe);
								}
								used.clear();
							}
						}
						
						if(getMCVersion().startsWith("1.16")||getMCVersion().startsWith("1.17")){
							/**
							 *  Block Heads 2
							 */
							/**if(getConfig().getBoolean("wandering_trades.block_heads.enabled", true)){
								int min = getConfig().getInt("wandering_trades.block_heads.min", 0);
								int max = getConfig().getInt("wandering_trades.block_heads.max", 5) / 2;
									if(debug){logDebug("CSE BH2 min=" + min + " max=" + max);}
								int blockRandom = randomBetween(min, max);
									if(debug){logDebug("CSE blockRandom=" + blockRandom);}
								if(blockRandom > 0){
										if(debug){logDebug("CSE blockRandom > 0");}
									int numOfblockheads = blockHeads2.getInt("blocks.number");
										if(debug){logDebug("CSE numOfblockheads=" + numOfblockheads);}
									HashSet<Integer> used = new HashSet<Integer>();
									for(int i=0; i<blockRandom; i++){
											if(debug){logDebug("CSE i=" + i);}
										int randomBlockHead = randomBetween(1, numOfblockheads);
										while (used.contains(randomBlockHead)) { //while we have already used the number
											randomBlockHead = randomBetween(1, numOfblockheads); //generate a new one because it's already used
									    }
									    //by this time, add will be unique
									    used.add(randomBlockHead);
											if(debug){logDebug("CSE randomBlockHead=" + randomBlockHead);}
										ItemStack price1 = blockHeads2.getItemStack("blocks.block_" + randomBlockHead + ".price_1", new ItemStack(Material.AIR));
											if(debug){logDebug("CSE price1=" + price1);}
										ItemStack price2 = blockHeads2.getItemStack("blocks.block_" + randomBlockHead + ".price_2", new ItemStack(Material.AIR));
											if(debug){logDebug("CSE price2=" + price2);}
										ItemStack itemstack = blockHeads2.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
											if(debug){logDebug("CSE itemstack=" + itemstack);}
										MerchantRecipe recipe = new MerchantRecipe(itemstack, blockHeads2.getInt("blocks.block_" + randomBlockHead + ".quantity", (int) 1));
											recipe.setExperienceReward(true);
											recipe.addIngredient(price1);
											recipe.addIngredient(price2);
											recipes.add(recipe);
									}
									used.clear();
								}
							}
						}
						
						/**
						 *  Custom Trades
						
						if(getConfig().getBoolean("wandering_trades.custom_trades.enabled", false)){
							int customRandom = randomBetween(getConfig().getInt("wandering_trades.custom_trades.min", 0), getConfig().getInt("wandering_trades.custom_trades.max", 5));
							int numOfCustomTrades = traderCustom.getInt("custom_trades.number") + 1;
							//if(debug){logDebug("CSE numOfCustomTrades=" + numOfCustomTrades);}
							//int customRandom = randomBetween(getConfig().getInt("wandering_trades.min_custom_trades", 0), getConfig().getInt("wandering_trades.max_custom_trades", 3));
								if(debug){logDebug("CSE customRandom=" + customRandom);}
							if(customRandom > 0){
								if(debug){logDebug("CSE customRandom > 0");}
								//for(int randomCustomTrade=1; randomCustomTrade<numOfCustomTrades; randomCustomTrade++){
								HashSet<Integer> used = new HashSet<Integer>();
								for(int i=0; i<customRandom; i++){
									int randomCustomTrade = randomBetween(1, numOfCustomTrades);
									while (used.contains(randomCustomTrade)) { //while we have already used the number
										randomCustomTrade = randomBetween(1, numOfCustomTrades); //generate a new one because it's already used
								    }
								    //by this time, add will be unique
								    used.add(randomCustomTrade);
									if(debug){logDebug("CSE randomCustomTrade=" + randomCustomTrade);}
									/** Fix chance later 
									double chance = Math.random();
										if(debug){logDebug("CSE chance=" + chance + " line:1540");}
									if(traderCustom.getDouble("custom_trades.trade_" + randomCustomTrade + ".chance", 0.002) > chance){
											if(debug){logDebug("CSE randomCustomTrade=" + randomCustomTrade);}
										ItemStack price1 = traderCustom.getItemStack("custom_trades.trade_" + randomCustomTrade + ".price_1", new ItemStack(Material.AIR));
											if(debug){logDebug("CSE price1=" + price1.toString());}
										ItemStack price2 = traderCustom.getItemStack("custom_trades.trade_" + randomCustomTrade + ".price_2", new ItemStack(Material.AIR));
											if(debug){logDebug("CSE price2=" + price2.toString());}
										ItemStack itemstack = traderCustom.getItemStack("custom_trades.trade_" + randomCustomTrade + ".itemstack", new ItemStack(Material.AIR));
											if(debug){logDebug("CSE itemstack=" + itemstack.toString());}
										MerchantRecipe recipe = new MerchantRecipe(itemstack, traderCustom.getInt("custom_trades.trade_" + randomCustomTrade + ".quantity", (int) 1));
												recipe.setExperienceReward(true);
												recipe.addIngredient(price1);
												recipe.addIngredient(price2);
												recipes.add(recipe);
									}
								}
								used.clear();
							}
						}
						
					if(getConfig().getBoolean("wandering_trades.keep_default_trades", true)){
						recipes.addAll(oldRecipes);
					}
					trader.setRecipes(recipes);
					}
				}
				
	}//*/
	
	public int randomBetween(int min, int max){
		Random r = new Random();
		int random = r.nextInt(max + 1);
		if((min + random) > max){
			return max;
		}
		return min + random;
	}
	
	public void makeHead(EntityDeathEvent event, Material material){// TODO: makeHead
		 ItemStack itemstack = event.getEntity().getKiller().getInventory().getItemInMainHand();
			if(itemstack != null){
					if(debug){logDebug("itemstack=" + itemstack.getType().toString() + " line:954");}
				int enchantmentlevel = itemstack.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);//.containsEnchantment(Enchantment.LOOT_BONUS_MOBS);
					if(debug){logDebug("enchantmentlevel=" + enchantmentlevel + " line:956");}
				double enchantmentlevelpercent = ((double)enchantmentlevel / 100);
					if(debug){logDebug("enchantmentlevelpercent=" + enchantmentlevelpercent + " line:958");}
				double chance = Math.random();
					if(debug){logDebug("chance=" + chance + " line:960");}
				double chancepercent = 0.25; /** Set to check config.yml later*/
					if(debug){logDebug("chancepercent=" + chancepercent + " line:962");}
				chancepercent = chancepercent + enchantmentlevelpercent;
					if(debug){logDebug("chancepercent2=" + chancepercent + " line:964");}
				if(chancepercent > 0.00 && chancepercent < 0.99){
						if (chancepercent > chance){
							event.getDrops().add(new ItemStack(material, 1));
						}
				}
			}
	}
	
	public ItemStack makeTraderSkull(String textureCode, String headName, String uuid, int amount){// TODO: maketraderSkull
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount);
		if(textureCode == null) return item;
		SkullMeta meta = (SkullMeta) item.getItemMeta();

		GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(textureCode.getBytes()), textureCode);
		profile.getProperties().put("textures", new Property("textures", textureCode));
		profile.getProperties().put("SkullOwner", new Property("id", uuid));
		profile.getProperties().put("display", new Property("Name", headName));
		setGameProfile(meta, profile);
		ArrayList<String> lore = new ArrayList();
		if(getConfig().getBoolean("lore.show_plugin_name", true)){
			lore.add(ChatColor.AQUA + "MoreMobHeads");
		}
		meta.setLore(lore);
		meta.setLore(lore);
		
		//meta.setOwningPlayer(Bukkit.getOfflinePlayer(ownerUUID));
		meta.setDisplayName(headName);
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack makeSkull(String textureCode, String headName, Player killer){// TODO: makeSkull
			ItemStack item = new ItemStack(Material.PLAYER_HEAD);
			if(textureCode == null) return item;
			SkullMeta meta = (SkullMeta) item.getItemMeta();

			GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(textureCode.getBytes()), textureCode);
			profile.getProperties().put("textures", new Property("textures", textureCode));
			profile.getProperties().put("display", new Property("Name", headName));
			setGameProfile(meta, profile);
			ArrayList<String> lore = new ArrayList();

		if(getConfig().getBoolean("lore.show_killer", true)){
			List<String> var2 = getConfig().getStringList("headlore");
			List<String> lorelist = new ArrayList();
			for (String lorestr : var2){
				String str;
				str = lorestr.replace("%player%",killer.getDisplayName());
				lorelist.add(str);
			}
			lore.addAll(lorelist);
		}
		if(getConfig().getBoolean("super.show_time",true)){
			Date date = new Date();
			String strDateFormat = getConfig().getString("super.time_format");
			SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
			lore.add(getConfig().getString("super.time_color").replace("&","§") + sdf.format(date));
		}
		lore.add("§0Powered by FlyCutter");
		meta.setLore(lore);
			meta.setLore(lore);
			
			//meta.setOwningPlayer(Bukkit.getOfflinePlayer(ownerUUID));
			meta.setDisplayName(headName);
			item.setItemMeta(meta);
			return item;
	}
	
	public ItemStack makeSkulls(String textureCode, String headName, int amount){
			ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount);
			if(textureCode == null) return item;
			SkullMeta meta = (SkullMeta) item.getItemMeta();

			GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(textureCode.getBytes()), textureCode);
			profile.getProperties().put("textures", new Property("textures", textureCode));
			profile.getProperties().put("display", new Property("Name", headName));
			setGameProfile(meta, profile);
			ArrayList<String> lore = new ArrayList();
		if(getConfig().getBoolean("super.show_time",true)){
			Date date = new Date();
			String strDateFormat = getConfig().getString("super.time_format");
			SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
			lore.add(getConfig().getString("super.time_color").replace("&","§") + sdf.format(date));
		}
				meta.setLore(lore);
			meta.setLore(lore);
			
			//meta.setOwningPlayer(Bukkit.getOfflinePlayer(ownerUUID));
			meta.setDisplayName(headName);
			item.setItemMeta(meta);
			return item;
	}
	private static Field fieldProfileItem;
	public static void setGameProfile(SkullMeta meta, GameProfile profile){
			try{
				if(fieldProfileItem == null) fieldProfileItem = meta.getClass().getDeclaredField("profile");
				fieldProfileItem.setAccessible(true);
				fieldProfileItem.set(meta, profile);
			}
			catch(NoSuchFieldException e){
				stacktraceInfoStatic();
				e.printStackTrace();}
			catch(SecurityException e){
				stacktraceInfoStatic();
				e.printStackTrace();}
			catch(IllegalArgumentException e){
				stacktraceInfoStatic();
				e.printStackTrace();}
			catch(IllegalAccessException e){
				stacktraceInfoStatic();
				e.printStackTrace();}
	}

	/**
	@SuppressWarnings("unused")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){ // TODO: Commands
			//log("command=" + cmd.getName() + " args=" + args[0] + args[1]);
			if (cmd.getName().equalsIgnoreCase("MMH")){
				if (args.length == 0){
					sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
					sender.sendMessage(ChatColor.WHITE + " ");
					sender.sendMessage(ChatColor.WHITE + " /mmh reload - " + lang.get("reload", "Reloads this plugin."));//subject to server admin approval");
					sender.sendMessage(ChatColor.WHITE + " /mmh toggledebug - " + lang.get("srdebuguse", "Temporarily toggles debug."));//Cancels SinglePlayerSleep");
					if(getConfig().getBoolean("wandering_trades.custom_wandering_trader", true)){
						sender.sendMessage(ChatColor.WHITE + " /mmh playerheads - " + lang.get("playerheads", "Shows how to use the playerheads commands"));
						//sender.sendMessage(ChatColor.WHITE + " /mmh blockheads - " + lang.get("blockheads", "Shows how to use the blockheads commands"));
						sender.sendMessage(ChatColor.WHITE + " /mmh customtrader - " + lang.get("customtrader", "Shows how to use the customtrader commands"));
					}
					sender.sendMessage(ChatColor.WHITE + " /mmh fixhead - " + lang.getString("headfix"));
					sender.sendMessage(ChatColor.WHITE + " /mmh givemh - gives player chosen the chosen mobhead");
					sender.sendMessage(ChatColor.WHITE + " /mmh giveph - gives player chosen player's head");
					sender.sendMessage(ChatColor.WHITE + " /mmh givebh - gives player chosen block's head");
					sender.sendMessage(ChatColor.WHITE + " /mmh display perms/vars - Shows permissions you have, or variables");
					sender.sendMessage(ChatColor.WHITE + " ");
					sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
					return true;
				}
				if(args[0].equalsIgnoreCase("headNBT")){
					if(!(sender instanceof Player)) {
						return true;
					}
					Player player = (Player) sender;
					ItemStack mainHand = player.getInventory().getItemInMainHand();
					ItemStack offHand = player.getInventory().getItemInOffHand();
					if( mainHand != null && mainHand.getType().equals(Material.PLAYER_HEAD) ) {
						NBTItem item = new NBTItem(mainHand);
						log(Level.INFO,"" + item);
						player.sendMessage("" + item);
					}else if( offHand != null && offHand.getType().equals(Material.PLAYER_HEAD) ) {
						NBTItem item = new NBTItem(offHand);
						player.sendMessage("" + item);
						log(Level.INFO,"" + item);
					}else {
						//log(Level.INFO,"You do not have a head in either hand.");
						player.sendMessage("You do not have a head in either hand.");
					}
				}
				// /mmh display permvar playername
				// /     0        1        2
				if(args[0].equalsIgnoreCase("display")){
					if(args[1].equalsIgnoreCase("perms")||args[1].equalsIgnoreCase("permissions")){
						if(sender instanceof Player) {
							Player player = (Player) sender;
							sender.sendMessage("You " + player.getDisplayName() + " have the following permissions.");
							sender.sendMessage("flycutter.players=" + player.hasPermission("flycutter.players"));
							sender.sendMessage("flycutter.mobs=" + player.hasPermission("flycutter.mobs"));
							sender.sendMessage("flycutter.nametag=" + player.hasPermission("flycutter.nametag"));
							sender.sendMessage("flycutter.reload=" + player.hasPermission("flycutter.reload"));
							sender.sendMessage("flycutter.toggledebug=" + player.hasPermission("flycutter.toggledebug"));
							sender.sendMessage("flycutter.showUpdateAvailable=" + player.hasPermission("flycutter.showUpdateAvailable"));
							sender.sendMessage("flycutter.customtrader=" + player.hasPermission("flycutter.customtrader"));
							sender.sendMessage("flycutter.playerheads=" + player.hasPermission("flycutter.playerheads"));
							sender.sendMessage("flycutter.blockheads=" + player.hasPermission("flycutter.blockheads"));
							sender.sendMessage("flycutter.fixhead=" + player.hasPermission("flycutter.fixhead"));
							sender.sendMessage("flycutter.give=" + player.hasPermission("flycutter.give"));
							sender.sendMessage("" + this.getName() + " " + this.getDescription().getVersion() + " display perms end");
						}else {
							if(args.length >= 2) {
								Player player = sender.getServer().getPlayer(args[2]);
								sender.sendMessage("" + player.getDisplayName() + " has the following permissions.");
								sender.sendMessage("flycutter.players=" + player.hasPermission("flycutter.players"));
								sender.sendMessage("flycutter.mobs=" + player.hasPermission("flycutter.mobs"));
								sender.sendMessage("flycutter.nametag=" + player.hasPermission("flycutter.nametag"));
								sender.sendMessage("flycutter.reload=" + player.hasPermission("flycutter.reload"));
								sender.sendMessage("flycutter.toggledebug=" + player.hasPermission("flycutter.toggledebug"));
								sender.sendMessage("flycutter.showUpdateAvailable=" + player.hasPermission("flycutter.showUpdateAvailable"));
								sender.sendMessage("flycutter.customtrader=" + player.hasPermission("flycutter.customtrader"));
								sender.sendMessage("flycutter.playerheads=" + player.hasPermission("flycutter.playerheads"));
								sender.sendMessage("flycutter.blockheads=" + player.hasPermission("flycutter.blockheads"));
								sender.sendMessage("flycutter.fixhead=" + player.hasPermission("flycutter.fixhead"));
								sender.sendMessage("flycutter.give=" + player.hasPermission("flycutter.give"));
								sender.sendMessage("" + this.getName() + " " + this.getDescription().getVersion() + " display perms end");
							}else {
								sender.sendMessage("Console can only check permissions of Players.");
							}
						}
					}else if(args[1].equalsIgnoreCase("vars")||args[1].equalsIgnoreCase("variables")){
						sender.sendMessage("" + this.getName() + " " + this.getDescription().getVersion() + " display perms start");
						sender.sendMessage("debug=" + debug);
						sender.sendMessage("daLang=" + daLang);
						sender.sendMessage("world_whitelist=" + world_whitelist);
						sender.sendMessage("world_blacklist=" + world_blacklist);
						sender.sendMessage("mob_whitelist=" + mob_whitelist);
						sender.sendMessage("mob_blacklist=" + mob_blacklist);
						sender.sendMessage("" + this.getName() + " " + this.getDescription().getVersion() + " display perms end");
					}
				}
				if(args[0].equalsIgnoreCase("reload")){
					String perm = "flycutter.reload";
					boolean hasPerm = sender.hasPermission(perm);
					if(debug){logDebug(sender.getName() + " has the permission " + perm + "=" + hasPerm);}
					if(hasPerm||!(sender instanceof Player)){
						oldconfig = new YamlConfiguration();
						log(Level.INFO, "Checking config file version...");
						try {
							oldconfig.load(new File(getDataFolder() + "" + File.separatorChar + "config.yml"));
						} catch (Exception e2) {
							logWarn("Could not load config.yml");
							stacktraceInfo();
							e2.printStackTrace();
						}
						String checkconfigversion = oldconfig.getString("version", "1.0.0");
						if(checkconfigversion != null){
							if(!checkconfigversion.equalsIgnoreCase("1.0.17")){
								try {
									copyFile_Java7(getDataFolder() + "" + File.separatorChar + "config.yml",getDataFolder() + "" + File.separatorChar + "old_config.yml");
								} catch (IOException e) {
									stacktraceInfo();
									e.printStackTrace();
								}
								saveResource("config.yml", true);
								
								try {
									config.load(new File(getDataFolder(), "config.yml"));
								} catch (IOException | InvalidConfigurationException e1) {
									logWarn("Could not load config.yml");
									stacktraceInfo();
									e1.printStackTrace();
								}
								try {
									oldconfig.load(new File(getDataFolder(), "old_config.yml"));
								} catch (IOException | InvalidConfigurationException e1) {
									logWarn("Could not load old_config.yml");
									stacktraceInfo();
									e1.printStackTrace();
								}
								config.set("auto_update_check", oldconfig.get("auto_update_check", true));
								config.set("debug", oldconfig.get("debug", false));
								config.set("lang", oldconfig.get("lang", "en_US"));
								config.set("colorful_console", oldconfig.get("colorful_console", true));
								config.set("vanilla_heads.creepers", oldconfig.get("vanilla_heads.creepers", false));
								config.set("vanilla_heads.ender_dragon", oldconfig.get("vanilla_heads.ender_dragon", false));
								config.set("vanilla_heads.skeleton", oldconfig.get("vanilla_heads.skeleton", false));
								config.set("vanilla_heads.wither_skeleton", oldconfig.get("vanilla_heads.wither_skeleton", false));
								config.set("vanilla_heads.zombie", oldconfig.get("vanilla_heads.zombie", false));
								config.set("world.whitelist", oldconfig.get("world.whitelist", ""));
								config.set("world.blacklist", oldconfig.get("world.blacklist", ""));
								config.set("mob.whitelist", oldconfig.get("mob.whitelist", ""));
								config.set("mob.blacklist", oldconfig.get("mob.blacklist", ""));
								config.set("mob.nametag", oldconfig.get("mob.nametag", false));
								config.set("lore.show_killer", oldconfig.get("lore.show_killer", true));
								config.set("lore.show_plugin_name", oldconfig.get("lore.show_plugin_name", true));
								config.set("wandering_trades.custom_wandering_trader", oldconfig.get("wandering_trades.custom_wandering_trader", true));
								config.set("wandering_trades.player_heads.enabled", oldconfig.get("wandering_trades.player_heads.enabled", true));
								config.set("wandering_trades.player_heads.min", oldconfig.get("wandering_trades.player_heads.min", 0));
								config.set("wandering_trades.player_heads.max", oldconfig.get("wandering_trades.player_heads.max", 3));
								config.set("wandering_trades.block_heads.enabled", oldconfig.get("wandering_trades.block_heads.enabled", true));
								config.set("wandering_trades.block_heads.min", oldconfig.get("wandering_trades.block_heads.min", 0));
								config.set("wandering_trades.block_heads.max", oldconfig.get("wandering_trades.block_heads.max", 3));
								config.set("wandering_trades.custom_trades.enabled", oldconfig.get("wandering_trades.custom_trades.enabled", false));
								config.set("wandering_trades.custom_trades.min", oldconfig.get("wandering_trades.custom_trades.min", 0));
								config.set("wandering_trades.custom_trades.max", oldconfig.get("wandering_trades.custom_trades.max", 3));
								config.set("apply_looting", oldconfig.get("apply_looting", true));
								config.set("whitelist.enforce", oldconfig.get("whitelist.enforce", true));
								config.set("whitelist.player_head_whitelist", oldconfig.get("whitelist.player_head_whitelist", "names_go_here"));
								config.set("blacklist.enforce", oldconfig.get("enforce_blacklist", true));
								config.set("blacklist.player_head_blacklist", oldconfig.get("blacklist.player_head_blacklist", "names_go_here"));
								//config.set("", oldconfig.get("", true));
								
								try {
									config.save(new File(getDataFolder(), "config.yml"));
								} catch (IOException e) {
									logWarn("Could not save old settings to config.yml");
									stacktraceInfo();
									e.printStackTrace();
								}
								saveResource("chance_config.yml", true);
								log(Level.INFO, "config.yml Updated! old config saved as old_config.yml");
								log(Level.INFO, "chance_config.yml saved.");
							}else{
								try {
									config.load(new File(getDataFolder(), "config.yml"));
								} catch (IOException | InvalidConfigurationException e1) {
									logWarn("Could not load config.yml");
									stacktraceInfo();
									e1.printStackTrace();
								}
							}
							oldconfig = null;
						}
						log(Level.INFO, "加载 config 文件...");
						try {
							getConfig().load(new File(getDataFolder(), "config.yml"));
						} catch (IOException | InvalidConfigurationException e) {
							stacktraceInfo();
							e.printStackTrace();
						}
						try {
							config.load(new File(getDataFolder(), "config.yml"));
						} catch (IOException | InvalidConfigurationException e1) {
							logWarn("Could not load config.yml");
							stacktraceInfo();
							e1.printStackTrace();
						}
						
						world_whitelist = config.getString("world.whitelist", "");
						world_blacklist = config.getString("world.blacklist", "");
						mob_whitelist = config.getString("mob.whitelist", "");
						mob_blacklist = config.getString("mob.blacklist", "");
						colorful_console = getConfig().getBoolean("colorful_console", true);
						
						if(getConfig().getBoolean("wandering_trades.custom_wandering_trader", true)){
							/** Trader heads load *//**
							playerFile = new File(getDataFolder() + "" + File.separatorChar + "player_heads.yml");//\
							if(debug){logDebug("player_heads=" + playerFile.getPath());}
							if(!playerFile.exists()){																	// checks if the yaml does not exist
								saveResource("player_heads.yml", true);
								log(Level.INFO, "player_heads.yml 不存在! 复制 player_heads.yml 到 " + getDataFolder() + "");
								//ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
							}
						log(Level.INFO, "加载 player_heads 文件...");
							playerHeads = new YamlConfiguration();
							try {
								playerHeads.load(playerFile);
							} catch (IOException | InvalidConfigurationException e) {
								stacktraceInfo();
								e.printStackTrace();
							}
						log(Level.INFO, "" + playerHeads.getInt("players.number") + " player_heads Loaded...");
							if(!getMCVersion().startsWith("1.16")&&!getMCVersion().startsWith("1.17")){
								blockFile = new File(getDataFolder() + "" + File.separatorChar + "block_heads.yml");//\
								if(debug){logDebug("block_heads=" + blockFile.getPath());}
								if(!blockFile.exists()){																	// checks if the yaml does not exist
									saveResource("block_heads.yml", true);
									log(Level.INFO, "block_heads.yml 不存在! 复制 block_heads.yml 到 " + getDataFolder() + "");
									//ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
								}
							}
							blockFile116 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16.yml");
							blockFile1162 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16_2.yml");
							if(getMCVersion().startsWith("1.16")||getMCVersion().startsWith("1.17")){
								if(debug){logDebug("block_heads_1_16=" + blockFile116.getPath());}
								if(debug){logDebug("block_heads_1_16_2=" + blockFile1162.getPath());}
								if(!blockFile116.exists()){
									saveResource("block_heads_1_16.yml", true);
									log(Level.INFO, "block_heads_1_16.yml 不存在! 复制 block_heads_1_16.yml 到 " + getDataFolder() + "");
								}
								if(!blockFile1162.exists()){
									saveResource("block_heads_1_16_2.yml", true);
									log(Level.INFO, "block_heads_1_16_2.yml 不存在! 复制 block_heads_1_16_2.yml 到 " + getDataFolder() + "");
								}
								blockFile = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16.yml");
							log(Level.INFO, "加载 block_heads_1_16 files...");
							
							}else{
							log(Level.INFO, "加载 block_heads 文件...");
							}
						
							blockHeads = new YamlConfiguration();
							try {
								blockHeads.load(blockFile);
							} catch (IOException | InvalidConfigurationException e1) {
								stacktraceInfo();
								e1.printStackTrace();
							}
							
							blockHeads2 = new YamlConfiguration();
							try {
								blockHeads2.load(blockFile1162);
							} catch (IOException | InvalidConfigurationException e1) {
								stacktraceInfo();
								e1.printStackTrace();
							}
							
							blockHeads3 = new YamlConfiguration();
							try {
								blockHeads3.load(blockFile117);
							} catch (IOException | InvalidConfigurationException e1) {
								stacktraceInfo();
								e1.printStackTrace();
							}
					
							/** Custom Trades load *//**
							customFile = new File(getDataFolder() + "" + File.separatorChar + "custom_trades.yml");//\
							if(debug){logDebug("customFile=" + customFile.getPath());}
							if(!customFile.exists()){																	// checks if the yaml does not exist
								saveResource("custom_trades.yml", true);
								log(Level.INFO, "custom_trades.yml 不存在! 复制 custom_trades.yml 到 " + getDataFolder() + "");
								//ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
							}
						log(Level.INFO, "加载 custom_trades 文件...");
							traderCustom = new YamlConfiguration();
							try {
								traderCustom.load(customFile);
							} catch (IOException | InvalidConfigurationException e) {
								stacktraceInfo();
								e.printStackTrace();
							}
						}
						log(Level.INFO, "加载 chance_config 文件...");
						chanceFile = new File(getDataFolder() + "" + File.separatorChar + "chance_config.yml");
						try {
							chanceConfig.load(chanceFile);
						} catch (IOException | InvalidConfigurationException e) {
							stacktraceInfo();
							e.printStackTrace();
						}
						//showkiller = getConfig().getBoolean("lore.show_killer", true);
						//showpluginname = getConfig().getBoolean("lore.show_plugin_name", true);
						debug = getConfig().getBoolean("debug", false);
						daLang = getConfig().getString("lang", "en_US");
						sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("reloaded"));
						return true;
					}else if(!hasPerm){
						sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("noperm").toString().replace("<perm>", perm) );
						return false;
					}
				}
				if(args[0].equalsIgnoreCase("toggledebug")||args[0].equalsIgnoreCase("td")){
					String perm = "flycutter.toggledebug";
					boolean hasPerm = sender.hasPermission(perm);
					if(debug){logDebug(sender.getName() + " has the permission " + perm + "=" + hasPerm);}
					if(sender.isOp()||hasPerm||!(sender instanceof Player)){
						debug = !debug;
						sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("debugtrue").toString().replace("boolean", "" + debug));
						return true;
					}else if(!hasPerm){
						sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("noperm").toString().replace("<perm>", perm) );
						return false;
					}
				}
				if(args[0].equalsIgnoreCase("customtrader")||args[0].equalsIgnoreCase("ct")){
					String perm = "flycutter.customtrader";
					boolean hasPerm = sender.hasPermission(perm);
					if(debug){logDebug(sender.getName() + " has the permission " + perm + "=" + hasPerm);}
					if(hasPerm&&sender instanceof Player
							&&getConfig().getBoolean("wandering_trades.custom_wandering_trader", true)){
						log(Level.INFO, "has permission");
						Player player = (Player) sender;
						if(!(args.length >= 2)){
							sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
							sender.sendMessage(ChatColor.WHITE + " ");
							sender.sendMessage(ChatColor.WHITE + " /mmh ct - " + lang.getString("cthelp"));
							sender.sendMessage(ChatColor.WHITE + " /mmh ct add - " + lang.getString("ctadd") + "custom_trades.yml");
							sender.sendMessage(ChatColor.WHITE + " /mmh ct remove # - " + lang.getString("ctremove"));
							sender.sendMessage(ChatColor.WHITE + " /mmh ct replace # - " + lang.getString("ctreplace").replace("<num>", "#"));
							sender.sendMessage(ChatColor.WHITE + " ");
							sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
							return true;
						}else if(args[1].equalsIgnoreCase("add")){
							if(debug) {logDebug("CMD CT ADD Start -----");}
							ItemStack itemstack = player.getInventory().getItemInOffHand();
							ItemStack price1 = player.getInventory().getItem(0);
							ItemStack price2 = player.getInventory().getItem(1);
							if(price1 == null){price1 = new ItemStack(Material.AIR);}
							if(price2 == null){price2 = new ItemStack(Material.AIR);}
							//Material price1 = item1.getType();
							//Material price2 = item2.getType();
							
							if(itemstack.getType() == Material.AIR||price1 == null||price1.getType() == Material.AIR){
								log(Level.INFO, "error air");
								sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
								sender.sendMessage(ChatColor.WHITE + " ");
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline1") + "custom_trades.yml");
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline2"));
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline3"));
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline4") + "/mmh ct add");
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline5") + "custom trade.");
								sender.sendMessage(ChatColor.WHITE + " ");
								sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
								if(debug) {logDebug("CMD CT ADD End Error -----");}
								return false;
							}
							int tradeNumber = (int) traderCustom.get("custom_trades.number", 1);
							traderCustom.set("custom_trades.trade_" + (tradeNumber + 1) + ".price_1", price1);
							traderCustom.set("custom_trades.trade_" + (tradeNumber + 1) + ".price_2", price2);
							traderCustom.set("custom_trades.trade_" + (tradeNumber + 1) + ".itemstack", itemstack);
							traderCustom.set("custom_trades.trade_" + (tradeNumber + 1) + ".quantity", itemstack.getAmount());
							traderCustom.set("custom_trades.trade_" + (tradeNumber + 1) + ".chance", 0.002);
							traderCustom.set("custom_trades.number", (tradeNumber + 1));
							if(debug) {logDebug("CMD CT ADD price1=" + price1.getType());}
							if(debug) {logDebug("CMD CT ADD price2=" + price2.getType());}
							if(debug) {logDebug("CMD CT ADD itemstack=" + itemstack.getType());}
							if(debug) {if(itemstack.getType() == Material.PLAYER_HEAD) {
									ItemMeta skullMeta = itemstack.getItemMeta(); 
									logDebug("CMD CT ADD IS DisplayName=" + skullMeta.getDisplayName());
									if(skullMeta.hasLore()) {
										logDebug("CMD CT ADD IS lore=" + String.join(",",skullMeta.getLore()));
									}
							}}
							if(debug) {logDebug("CMD CT ADD quantity=" + itemstack.getAmount());}
							if(debug) {logDebug("CMD CT ADD chance=0.002");}
							//log("customFile=" + customFile);
							try {
								traderCustom.save(customFile);
								traderCustom.load(customFile);
							} catch (IOException | InvalidConfigurationException e) {
								stacktraceInfo();
								e.printStackTrace();
							}
							if(debug) {logDebug("CMD CT ADD End -----");}
							sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.WHITE + " trade_" + (tradeNumber + 1) + " " + lang.get("ctsuccessadd"));
							return true;
						}else if(args[1].equalsIgnoreCase("remove")){
							if(debug) {logDebug("CMD CT Remove Start -----");}
							if(!(args.length >= 3)){
								sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("ctargument"));
								return false;
							}else{
								if(isInteger(args[2])){
									traderCustom.set("custom_trades.trade_" + args[2] + ".price_1", "");
									traderCustom.set("custom_trades.trade_" + args[2] + ".price_2", "");
									traderCustom.set("custom_trades.trade_" + args[2] + ".itemstack", "");
									traderCustom.set("custom_trades.trade_" + args[2] + ".quantity", "");
									traderCustom.set("custom_trades.trade_" + args[2] + ".chance", "");
									if(debug){logDebug("customFile=" + customFile);}
									try {
										traderCustom.save(customFile);
										traderCustom.load(customFile);
									} catch (IOException | InvalidConfigurationException e) {
										if(debug) {logDebug("CMD CT Remove End Exception -----");}
										sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("cterror"));
										return false;
										//e.printStackTrace();
									}
									if(debug) {logDebug("CMD CT Remove End -----");}
									sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.WHITE + " trade_" + args[2] + " " + lang.get("ctsuccessrem"));
									return true;
								}else{
									if(debug) {logDebug("CMD CT Remove End 2 -----");}
									sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("ctnumreq"));
									return false;
								}
							}
						}else if(args[1].equalsIgnoreCase("replace")){
							if(debug) {logDebug("CMD CT Replace Start -----");}
							if(args.length != 3){
								sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("ctargument"));
								return false;
							}else{
								if(isInteger(args[2])){
									ItemStack itemstack = player.getInventory().getItemInOffHand();
									ItemStack price1 = player.getInventory().getItem(0);
									ItemStack price2 = player.getInventory().getItem(1);
									if(price1 == null){price1 = new ItemStack(Material.AIR);}
									if(price2 == null){price2 = new ItemStack(Material.AIR);}
									if(itemstack.getType() == Material.AIR||price1 == null||price1.getType() == Material.AIR){
										sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
										sender.sendMessage(ChatColor.WHITE + " ");
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline1") + "custom_trades.yml");
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline2"));
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline3"));
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline4") + "/mmh ct add");
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline5") + "custom trade.");
										sender.sendMessage(ChatColor.WHITE + " ");
										sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
										if(debug) {logDebug("CMD CT Replace End Error -----");}
										return false;
									}
									int tradeNumber = Integer.parseInt(args[2]);
									traderCustom.set("custom_trades.trade_" + (tradeNumber) + ".price_1", price1);
									traderCustom.set("custom_trades.trade_" + (tradeNumber) + ".price_2", price2);
									traderCustom.set("custom_trades.trade_" + (tradeNumber) + ".itemstack", itemstack);
									traderCustom.set("custom_trades.trade_" + (tradeNumber) + ".quantity", itemstack.getAmount());
									traderCustom.set("custom_trades.trade_" + (tradeNumber) + ".chance", 0.002);
									if(debug) {logDebug("CMD CT Replace price1=" + price1.getType());}
									if(debug) {logDebug("CMD CT Replace price2=" + price2.getType());}
									if(debug) {logDebug("CMD CT Replace itemstack=" + itemstack.getType());}
									if(debug) {if(itemstack.getType() == Material.PLAYER_HEAD) {
										ItemMeta skullMeta = itemstack.getItemMeta(); 
										logDebug("CMD CT Replace IS DisplayName=" + skullMeta.getDisplayName());
										if(skullMeta.hasLore()) {
											logDebug("CMD CT Replace IS lore=" + String.join(",",skullMeta.getLore()));
										}
									}}
									if(debug) {logDebug("CMD CT Replace quantity=" + itemstack.getAmount());}
									if(debug) {logDebug("CMD CT Replace chance=0.002");}
									
									//log("customFile=" + customFile);
									try {
										traderCustom.save(customFile);
										traderCustom.load(customFile);
									} catch (IOException | InvalidConfigurationException e) {
										if(debug) {logDebug("CMD CT Replace End Exception -----");}
										sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("cterror"));
										return false;
										//e.printStackTrace();
									}
									if(debug) {logDebug("CMD CT Replace End -----");}
									sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.WHITE + " trade_" + args[2] + " " + lang.get("ctsuccessrep"));
									return true;
								}else{
									if(debug) {logDebug("CMD CT Replace End 2 -----");}
									sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("ctnumreq"));
									return false;
								}
							}
						}
					}else if(!(sender instanceof Player)){
						if(debug) {logDebug("CMD CT Replace End Console -----");}
						sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("noconsole"));
						return false;
					}else if(!hasPerm){
						if(debug) {logDebug("CMD CT Replace End !Perm -----");}
						sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("nopermordisabled").toString().replace("<perm>", perm) );
					return false;
					}
				}
				if(args[0].equalsIgnoreCase("playerheads")||args[0].equalsIgnoreCase("ph")){
					String perm = "flycutter.playerheads";
					boolean hasPerm = sender.hasPermission(perm);
					if(debug){logDebug(sender.getName() + " has the permission " + perm + "=" + hasPerm);}
					if(hasPerm&&sender instanceof Player
							&&getConfig().getBoolean("wandering_trades.custom_wandering_trader", true)){
						Player player = (Player) sender;
						if(!(args.length >= 2)){
							sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
							sender.sendMessage(ChatColor.WHITE + " ");
							sender.sendMessage(ChatColor.WHITE + " /mmh ph - " + lang.getString("cthelp"));
							sender.sendMessage(ChatColor.WHITE + " /mmh ph add - " + lang.getString("ctadd") + "player_heads.yml");
							sender.sendMessage(ChatColor.WHITE + " /mmh ph remove # - " + lang.getString("ctremove"));
							sender.sendMessage(ChatColor.WHITE + " /mmh ph replace # - " + lang.getString("ctreplace").replace("<num>", "#"));
							//sender.sendMessage(ChatColor.WHITE + " ");
							sender.sendMessage(ChatColor.WHITE + " ");
							sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
							return true;
						}else if(args[1].equalsIgnoreCase("add")){
							if(debug) {logDebug("CMD PH ADD Start -----");}
							ItemStack itemstack = player.getInventory().getItemInOffHand();
							ItemStack price1 = player.getInventory().getItem(0);
							ItemStack price2 = player.getInventory().getItem(1);
							if(price1 == null){price1 = new ItemStack(Material.AIR);}
							if(price2 == null){price2 = new ItemStack(Material.AIR);}
							//Material price1 = item1.getType();
							//Material price2 = item2.getType();
							
							if(itemstack.getType() == Material.AIR||price1 == null||price1.getType() == Material.AIR||itemstack.getType() != Material.PLAYER_HEAD){
								sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
								sender.sendMessage(ChatColor.WHITE + " ");
								if(itemstack.getType() != Material.PLAYER_HEAD){
									sender.sendMessage(ChatColor.RED + " MUST BE PLAYERHEAD");
									sender.sendMessage(ChatColor.WHITE + " ");
								}
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline1") + "player_heads.yml");
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline2"));
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline3"));
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline4") + "/mmh ph add");
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline5") + "player head.");
								sender.sendMessage(ChatColor.WHITE + " ");
								sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
								if(debug) {logDebug("CMD PH ADD End Error -----");}
								return false;
							}
							int tradeNumber = (int) playerHeads.get("players.number", 1);
							playerHeads.set("players.player_" + (tradeNumber + 1) + ".price_1", price1);
							playerHeads.set("players.player_" + (tradeNumber + 1) + ".price_2", price2);
							playerHeads.set("players.player_" + (tradeNumber + 1) + ".itemstack", itemstack);
							playerHeads.set("players.player_" + (tradeNumber + 1) + ".quantity", itemstack.getAmount());
							if(debug) {logDebug("CMD PH ADD price1=" + price1.getType());}
							if(debug) {logDebug("CMD PH ADD price2=" + price2.getType());}
							if(debug) {logDebug("CMD PH ADD itemstack=" + itemstack.getType());}
							if(debug) {if(itemstack.getType() == Material.PLAYER_HEAD) {
								ItemMeta skullMeta = itemstack.getItemMeta(); 
								logDebug("CMD PH ADD IS DisplayName=" + skullMeta.getDisplayName());
								if(skullMeta.hasLore()) {
									logDebug("CMD PH ADD IS lore=" + String.join(",",skullMeta.getLore()));
								}
							}}
							if(debug) {logDebug("CMD PH ADD quantity=" + itemstack.getAmount());}
							//playerHeads.set("players.player_" + (tradeNumber + 1) + ".chance", 0.002);
							playerHeads.set("players.number", (tradeNumber + 1));
							//log("customFile=" + customFile);
							try {
								playerHeads.save(playerFile);
								playerHeads.load(playerFile);
							} catch (IOException | InvalidConfigurationException e) {
								if(debug) {logDebug("CMD PH ADD End Exception -----");}
								stacktraceInfo();
								e.printStackTrace();
							}
							if(debug) {logDebug("CMD PH ADD End -----");}
							sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.WHITE + " player_" + (tradeNumber + 1) + " " + lang.get("ctsuccessadd"));
							return true;
						}else if(args[1].equalsIgnoreCase("remove")){
							if(debug) {logDebug("CMD PH Remove Start -----");}
							if(!(args.length >= 3)){
								sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("ctargument"));
								return false;
							}else{
								if(isInteger(args[2])){
									playerHeads.set("players.player_" + args[2] + ".price_1", "");
									playerHeads.set("players.player_" + args[2] + ".price_2", "");
									playerHeads.set("players.player_" + args[2] + ".itemstack", "");
									playerHeads.set("players.player_" + args[2] + ".quantity", "");
									//playerHeads.set("custom_trades.trade_" + args[2] + ".chance", "");
									if(debug){logDebug("playerFile=" + playerFile);}
									try {
										playerHeads.save(playerFile);
										playerHeads.load(playerFile);
									} catch (IOException | InvalidConfigurationException e) {
										if(debug) {logDebug("CMD PH Remove End Exception -----");}
										sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("cterror") + "custom_trades.yml!");
										return false;
										//e.printStackTrace();
									}
									if(debug) {logDebug("CMD PH Remove End -----");}
									sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.WHITE + " player_" + args[2] + " " + lang.get("ctsuccessrem"));
									return true;
								}else{
									if(debug) {logDebug("CMD PH Remove End 2 -----");}
									sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("ctnumreq"));
									return false;
								}
							}
						}else if(args[1].equalsIgnoreCase("replace")){
							if(debug) {logDebug("CMD PH Replace Start -----");}
							if(args.length != 3){
								sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("ctargument"));
								return false;
							}else{
								if(isInteger(args[2])){
									ItemStack itemstack = player.getInventory().getItemInOffHand();
									ItemStack price1 = player.getInventory().getItem(0);
									ItemStack price2 = player.getInventory().getItem(1);
									if(price1 == null){price1 = new ItemStack(Material.AIR);}
									if(price2 == null){price2 = new ItemStack(Material.AIR);}
									if(itemstack.getType() == Material.AIR||price1 == null||price1.getType() == Material.AIR||itemstack.getType() != Material.PLAYER_HEAD){
										sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
										sender.sendMessage(ChatColor.WHITE + " ");
										if(itemstack.getType() != Material.PLAYER_HEAD){
											sender.sendMessage(ChatColor.RED + " MUST BE PLAYERHEAD");
											sender.sendMessage(ChatColor.WHITE + " ");
										}
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline1") + "player_heads.yml");
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline2"));
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline3"));
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline4") + "/mmh ph add");
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline5") + "player head.");
										sender.sendMessage(ChatColor.WHITE + " ");
										sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
										if(debug) {logDebug("CMD PH Replace End Error -----");}
										return false;
									}
									int tradeNumber = Integer.parseInt(args[2]);
									playerHeads.set("players.player_" + (tradeNumber) + ".price_1", price1);
									playerHeads.set("players.player_" + (tradeNumber) + ".price_2", price2);
									playerHeads.set("players.player_" + (tradeNumber) + ".itemstack", itemstack);
									playerHeads.set("players.player_" + (tradeNumber) + ".quantity", itemstack.getAmount());
									if(debug) {logDebug("CMD PH Replace price1=" + price1.getType());}
									if(debug) {logDebug("CMD PH Replace price2=" + price2.getType());}
									if(debug) {logDebug("CMD PH Replace itemstack=" + itemstack.getType());}
									if(debug) {if(itemstack.getType() == Material.PLAYER_HEAD) {
										ItemMeta skullMeta = itemstack.getItemMeta(); 
										logDebug("CMD PH Replace IS DisplayName=" + skullMeta.getDisplayName());
										if(skullMeta.hasLore()) {
											logDebug("CMD PH Replace IS lore=" + String.join(",",skullMeta.getLore()));
										}
									}}
									if(debug) {logDebug("CMD PH Replace quantity=" + itemstack.getAmount());}
									//playerHeads.set("players.player_" + (tradeNumber + 1) + ".chance", 0.002);
									//log("customFile=" + customFile);
									try {
										playerHeads.save(playerFile);
										playerHeads.load(playerFile);
									} catch (IOException | InvalidConfigurationException e) {
										if(debug) {logDebug("CMD PH Replace End Exception -----");}
										sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("cterror") + "player_heads.yml!");
										return false;
										//e.printStackTrace();
									}
									if(debug) {logDebug("CMD PH Replace End -----");}
									sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.WHITE + " player_" + args[2] + " " + lang.get("ctsuccessrep"));
									return true;
								}else{
									if(debug) {logDebug("CMD PH Replace End 2 -----");}
									sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("ctnumreq"));
									return false;
								}
							}
						}
					}else if(!(sender instanceof Player)){
						if(debug) {logDebug("CMD PH Replace End Console -----");}
						sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("noconsole"));
						return false;
					}else if(!hasPerm){
						if(debug) {logDebug("CMD PH Replace End !Perm -----");}
						sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("nopermordisabled").toString().replace("<perm>", perm) );
						return false;
					}
				}
				if(args[0].equalsIgnoreCase("fixhead")||args[0].equalsIgnoreCase("fh")){
					String perm = "flycutter.fixhead";
					boolean hasPerm = sender.hasPermission(perm);
					if(debug){logDebug(sender.getName() + " has the permission " + perm + "=" + hasPerm);}
					if(sender instanceof Player) {
						Player player = (Player) sender;
						if(hasPerm) {
							if(!args[1].isEmpty()) {
								if(args[1].equalsIgnoreCase("name")) {
									if(debug) {logDebug("CMD FH Name Start -----");}
									// FixHead NBT
									ItemStack mainHand = player.getInventory().getItemInMainHand();
									if( mainHand != null ){
										if( mainHand.getType().equals(Material.PLAYER_HEAD) ) {
											String texture = mainHand.getItemMeta().getDisplayName();
											
											SkullMeta skullname = (SkullMeta) mainHand.getItemMeta();
											if(skullname.getOwner() != null){
												String name = skullname.getOwner().toString();
												if(debug){logDebug("EPIE name=" + name);}
												if(debug){logDebug("EPIE lore=" + skullname.getLore());}
												if(skullname.getOwner().toString().length() >= 40){
													if(debug){logDebug("EPIE ownerName.lngth >= 40");}
														ItemStack itmStack = mainHand;
														//SkullMeta skullname = (SkullMeta) e.getItem().getItemStack().getItemMeta();
														String daMobName = "null";
														if(skullname != null){
															String isCat = CatHeads.getNameFromTexture(skullname.getOwner().toString());
															String isHorse = HorseHeads.getNameFromTexture(skullname.getOwner().toString());
															String isLlama = LlamaHeads.getNameFromTexture(skullname.getOwner().toString());
															String isMobHead = MobHeads.getNameFromTexture(skullname.getOwner().toString());
															String isRabbit = RabbitHeads.getNameFromTexture(skullname.getOwner().toString());
															String isSheep = SheepHeads.getNameFromTexture(skullname.getOwner().toString());
															String isVillager = VillagerHeads.getNameFromTexture(skullname.getOwner().toString());
															String isZombieVillager = ZombieVillagerHeads.getNameFromTexture(skullname.getOwner().toString());
															String isplayerhead = isPlayerHead(skullname.getOwner().toString());
															String isblockhead = isBlockHead(skullname.getOwner().toString());
															String isblockhead2 = isBlockHead2(skullname.getOwner().toString());
															String isblockhead3 = isBlockHead3(skullname.getOwner().toString());
															if(isCat != null){				daMobName = isCat;	}
															if(isHorse != null){			daMobName = isHorse;	}
															if(isLlama != null){			daMobName = isLlama;	}
															if(isMobHead != null){			daMobName = isMobHead;	}
															if(isRabbit != null){			daMobName = isRabbit;	}
															if(isSheep != null){			daMobName = isSheep;	}
															if(isVillager != null){			daMobName = isVillager;	}
															if(isZombieVillager != null){	daMobName = isZombieVillager;	}
															if(daMobName == null){
																if(blockHeads != null){
																	if(isblockhead != null){	daMobName = isblockhead;	}
																}
																if(blockHeads2 != null){
																	if(isblockhead2 != null){	daMobName = isblockhead2;	}
																}
																if(blockHeads3 != null){
																	if(isblockhead3 != null){	daMobName = isblockhead3;	}
																}
																if(playerHeads != null){
																	if(isplayerhead != null){	daMobName = isplayerhead;	}
																}
															}
															ArrayList<String> lore = new ArrayList();
															//log("" + meta.getOwner().toString());
															//String name = LlamaHeads.getNameFromTexture(meta.getOwner().toString());
															if(debug){logDebug("EPIE mobname from texture=" + daMobName);}
															List<String> skullLore = skullname.getLore();
															if(skullLore != null){
																if(skullLore.toString().contains("Killed by")){
																	lore.addAll(skullname.getLore());
																}
															}
															if(skullLore == null||!skullname.getLore().toString().contains(this.getName())){
																if(getConfig().getBoolean("lore.show_plugin_name", true)){
																	lore.add(ChatColor.AQUA + "" + this.getName());
																}
															}
															if(daMobName != "null"){
																daMobName = langName.getString(daMobName.toLowerCase().replace(" ", "."), daMobName);
															}else{
																daMobName = langName.getString(daMobName.toLowerCase().replace(" ", "."), "404 Name Not Found");
															}
															skullname.setLore(lore);
															skullname.setDisplayName(daMobName);
															itmStack.setItemMeta(skullname);
															//fixHeadNBT(skullname.getOwner(), daMobName, lore);
															if(debug) {logDebug("CMD FH Name End -----");}
															sender.sendMessage("DisplayName of head in your main hand has been fixed.");
															//if(debug){logDebug("test3a");}
															return true;
														}else{
															if(debug) {logDebug("CMD FH Name End Meta Null -----");}
															return false;
													}
												}
											}
										}else {
											if(debug) {logDebug("CMD FH Name End Error -----");}
											sender.sendMessage("An Error occured.");
											return false;
										}
									}
								}
								
								if(args[1].equalsIgnoreCase("stack")) {
									if(debug) {logDebug("CMD FH Stack Start -----");}
									// FixHead Stack
									ItemStack mainHand = player.getInventory().getItemInMainHand();
									ItemStack offHand = player.getInventory().getItemInOffHand();
									if( mainHand != null && offHand != null ){
										if( mainHand.getType().equals(Material.PLAYER_HEAD) && offHand.getType().equals(Material.PLAYER_HEAD) ) {
											ItemStack is = fixHeadStack(offHand, mainHand);
											//is.setAmount(mainHand.getAmount());
											if(is != mainHand) {
												player.getInventory().setItemInMainHand(is);
												if(debug) {logDebug("CMD FH Stack End -----");}
												sender.sendMessage("NBT data of off hand head has been copied to the head in your main hand");
												return true;
											}else {
												if(debug) {logDebug("CMD FH Stack End Error -----");}
												sender.sendMessage("An Error occured. See plugins/MoreMobHeads/logs/mmh_debug.log for details");
												return false;
											}
										}else if( !mainHand.getType().equals(Material.PLAYER_HEAD) && !offHand.getType().equals(Material.PLAYER_HEAD) ){
											if(debug) {logDebug("CMD FH Stack End Error Main Off -----");}
											sender.sendMessage("Items in Main hand, and Off hand are not Player_Head.");
											return false;
										}else if( !mainHand.getType().equals(Material.PLAYER_HEAD) && offHand.getType().equals(Material.PLAYER_HEAD) ){
											if(debug) {logDebug("CMD FH Stack End Error Main -----");}
											sender.sendMessage("Item in Main hand is not a Player_Head.");
											return false;
										}else if( mainHand.getType().equals(Material.PLAYER_HEAD) && !offHand.getType().equals(Material.PLAYER_HEAD) ){
											if(debug) {logDebug("CMD FH Stack End Error Off -----");}
											sender.sendMessage("Item in Off hand is not a Player_Head.");
											return false;
										}
									}
								}
							}
							
						}else if(!hasPerm){
							if(debug) {logDebug("CMD FH Stack End !Perm -----");}
							sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("noperm").toString().replace("<perm>", perm) );
							return false;
						}
					}
				}
				if(args[0].equalsIgnoreCase("giveMH")){
					// /mmh giveMH player mob qty
					// cmd  0      1      2   3
					if( args.length==4 ){
						String perm = "flycutter.give";
						boolean hasPerm = sender.hasPermission(perm);
						if(debug){logDebug(sender.getName() + " has the permission " + perm + "=" + hasPerm);}
						if(hasPerm) {
							Player player = Bukkit.getPlayer(args[1]);
							if(!args[2].isEmpty()) {
								String mob = args[2].toLowerCase();
								log(Level.INFO, "mob=" + mob);
								if(!args[3].isEmpty()) {
									int number = Integer.parseInt(args[3]);
									String[] splitmob = mob.split("\\.");
									switch (splitmob[0]) {
									case "creeper":
										if(getConfig().getBoolean("vanilla_heads.creeper", false)){
						 					player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.CREEPER_HEAD));
						 				}else{ // langName
						 					player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls(MobHeads.valueOf(splitmob[0].toUpperCase()).getTexture().toString(), 
						 							langName.getString(splitmob[0].toLowerCase(), MobHeads.valueOf(splitmob[0].toUpperCase()).getName() + " Head"), number ));
						 				} // MobHeads.valueOf(name).getName() + " Head"
										break;
									case "zombie":
										if(getConfig().getBoolean("vanilla_heads.zombie", false)){
						 					player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.ZOMBIE_HEAD));
						 				}else{ // langName
						 					player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls(MobHeads.valueOf(splitmob[0].toUpperCase()).getTexture().toString(), 
						 							langName.getString(splitmob[0].toLowerCase(), MobHeads.valueOf(splitmob[0].toUpperCase()).getName() + " Head"), number ));
						 				} // MobHeads.valueOf(name).getName() + " Head"
										break;
									case "skeleton":
										if(getConfig().getBoolean("vanilla_heads.skeleton", false)){
						 					player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.SKELETON_SKULL));
						 				}else{ // langName
						 					player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls(MobHeads.valueOf(splitmob[0].toUpperCase()).getTexture().toString(), 
						 							langName.getString(splitmob[0].toLowerCase(), MobHeads.valueOf(splitmob[0].toUpperCase()).getName() + " Head"), number ));
						 				} // MobHeads.valueOf(name).getName() + " Head"
										break;
									case "wither_skeleton":
										if(getConfig().getBoolean("vanilla_heads.wither_skeleton", false)){
						 					player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.WITHER_SKELETON_SKULL));
						 				}else{ // langName
						 					player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls(MobHeads.valueOf(splitmob[0].toUpperCase()).getTexture().toString(), 
						 							langName.getString(splitmob[0].toLowerCase(), MobHeads.valueOf(splitmob[0].toUpperCase()).getName() + " Head"), number ));
						 				} // MobHeads.valueOf(name).getName() + " Head"
										break;
									case "ender_dragon":
										if(getConfig().getBoolean("vanilla_heads.ender_dragon", false)){
						 					player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.DRAGON_HEAD));
						 				}else{ // langName
						 					player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls(MobHeads.valueOf(splitmob[0].toUpperCase()).getTexture().toString(), 
						 							langName.getString(splitmob[0].toLowerCase(), MobHeads.valueOf(splitmob[0].toUpperCase()).getName() + " Head"), number ));
						 				} // MobHeads.valueOf(name).getName() + " Head"
										break;
									case "cat":
										player.getWorld().dropItemNaturally(player.getLocation(),
					 							makeSkulls(CatHeads.valueOf(splitmob[1].toUpperCase()).getTexture().toString(), 
					 									langName.getString(splitmob[0].toLowerCase() + "." + splitmob[1].toLowerCase(), CatHeads.valueOf(splitmob[1].toUpperCase()).getName() + " Head"), number ));
										break;
									case "bee":
										log(Level.INFO, "splitmob.length=" + splitmob.length);
										if(splitmob.length == 1) {
											player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls(MobHeads.valueOf(splitmob[0].toUpperCase()).getTexture().toString(), 
						 							langName.getString(splitmob[0].toLowerCase() + ".none", MobHeads.valueOf(splitmob[0].toUpperCase()).getName() + " Head"), number ));
										}else {
											player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls( MobHeads.valueOf( mob.toUpperCase().replace(".", "_") ).getTexture().toString(), 
						 							langName.getString(mob.toLowerCase().replace(".", "_"), MobHeads.valueOf(mob.toUpperCase().replace(".", "_")).getName() + " Head"), number ));
										}
										break;
									case "villager": // villager type profession, villager profession type
										// name = splitmob[0], type =  splitmob[1], profession = splitmob[2]
										player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls(VillagerHeads.valueOf( splitmob[0].toUpperCase() + "_" + splitmob[2].toUpperCase() + "_" + splitmob[1].toUpperCase() ).getTexture().toString(), 
					 							langName.getString( splitmob[0].toLowerCase() + "." + splitmob[1].toLowerCase() + "." + splitmob[2].toLowerCase()
					 									, VillagerHeads.valueOf(splitmob[0].toUpperCase() + "_" + splitmob[2].toUpperCase() + "_" + splitmob[1].toUpperCase()).getName() + " Head"), number ));
										break;
									case "zombie_villager":
										player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls(ZombieVillagerHeads.valueOf(splitmob[0].toUpperCase() + "_" + splitmob[2].toUpperCase() ).getTexture().toString(), 
					 							langName.getString(splitmob[0].toLowerCase() + "." + splitmob[2].toLowerCase(), ZombieVillagerHeads.valueOf(splitmob[0].toUpperCase() + "_" + splitmob[2].toUpperCase() ).getName() ), number ));
										break;
									case "llama":
									case "trader_llama":
										player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls(LlamaHeads.valueOf( splitmob[0].toUpperCase() + "_" + splitmob[1].toUpperCase() ).getTexture().toString(), 
					 							langName.getString(splitmob[0].toLowerCase() + "." + splitmob[1].toLowerCase(), LlamaHeads.valueOf( splitmob[0].toUpperCase() + "_" + splitmob[1].toUpperCase() ).getName()), number ));
										break;
									case "horse":
										player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls(HorseHeads.valueOf( splitmob[0].toUpperCase() + "_" + splitmob[1].toUpperCase() ).getTexture().toString(), 
					 							langName.getString(splitmob[0].toLowerCase() + "." + splitmob[1].toLowerCase(), HorseHeads.valueOf( splitmob[0].toUpperCase() + "_" + splitmob[1].toUpperCase() ).getName()), number ));
										break;
									case "rabbit":
										player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls(RabbitHeads.valueOf( splitmob[0].toUpperCase() + "_" + splitmob[1].toUpperCase() ).getTexture().toString(), 
					 							langName.getString(splitmob[0].toLowerCase() + "." + splitmob[1].toLowerCase(), RabbitHeads.valueOf( splitmob[0].toUpperCase() + "_" + splitmob[1].toUpperCase() ).getName()), number ));
										break;
									case "sheep":
										String sheeptype;
										if(splitmob[1].equalsIgnoreCase("jeb_")) {
											sheeptype = "jeb_";
										}else {
											sheeptype = splitmob[1].toUpperCase();
										}
										player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls(SheepHeads.valueOf( splitmob[0].toUpperCase() + "_" + sheeptype ).getTexture().toString(), 
					 							langName.getString(splitmob[0].toLowerCase() + "." + splitmob[1].toLowerCase(), SheepHeads.valueOf( splitmob[0].toUpperCase() + "_" + sheeptype ).getName()), number ));
										break;
									case "goat":
									case "axolotl":	
										player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls(MobHeads117.valueOf( splitmob[0].toUpperCase() + "_" + splitmob[1].toUpperCase() ).getTexture().toString(), 
					 							langName.getString(splitmob[0].toLowerCase() + "." + splitmob[1].toLowerCase(), MobHeads117.valueOf( splitmob[0].toUpperCase() + "_" + splitmob[1].toUpperCase() ).getName()), number ));
										break;
									default:
										player.getWorld().dropItemNaturally(player.getLocation(), makeSkulls(MobHeads.valueOf(mob.toUpperCase().replace(".", "_")).getTexture().toString(), 
					 							langName.getString(mob.toLowerCase(), MobHeads.valueOf(mob.toUpperCase().replace(".", "_")).getName() + " Head"), number ));
										break;
									}
								}
							}
						}else if(!hasPerm){
							sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("noperm").toString().replace("<perm>", perm) );
							return false;
						}
					}else {
						sender.sendMessage("Command usage, /mmh givemh playername mobname 1");
						return false;
					}
					
				}
				// /mmh giveph player
				// /mmh giveph player player
				//  0   1      2      3
				if(args[0].equalsIgnoreCase("givePH")){
					if( args.length >= 2 ){
						String perm = "flycutter.give";
						boolean hasPerm = sender.hasPermission(perm);
						if(debug){logDebug(sender.getName() + " has the permission " + perm + "=" + hasPerm);}
						if( hasPerm ){
							if(debug) {logDebug("CMD GPH args.length=" + args.length);}
							if( args.length==2 && sender instanceof Player ){
								givePlayerHead((Player) sender,args[1]);
								if(debug) {logDebug("CMD GPH args1=" + args[1]);}
								return true;
							}else if( args.length==3){
								Player player = Bukkit.getPlayer(args[1]);
								givePlayerHead(player,args[2]);
								if(debug) {logDebug("CMD GPH args1=" + args[1] + ", args2=" +args[2]);}
								return true;
							}else if( args.length==2 && !(sender instanceof Player) ){
								sender.sendMessage("Console cannot give itself Heads. Command usage, \"/mmh giveph playername 1\" or \"/mmh giveph playername playername 1\"");
								return false;
							}
						}else if( !hasPerm ){
							sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("noperm").toString().replace("<perm>", perm) );
							return false;
						}
					}else {
						sender.sendMessage("Command usage, \"/mmh giveph playername 1\" or \"/mmh giveph playername playername 1\"");
						return false;
					}
					return false;
				}
				// /mmh givebh block
				// /mmh givebh player block
				//  0   1      2      3
				if(args[0].equalsIgnoreCase("giveBH")){
					if(debug){logDebug("Start GiveBH");}
					if(debug){logDebug("Command=" + cmd.getName() + ", arguments=" + Arrays.toString(args));}
					if( args.length >= 2 ){
						String perm = "flycutter.give";
						boolean hasPerm = sender.hasPermission(perm);
						if(debug){logDebug(sender.getName() + " has the permission " + perm + "=" + hasPerm);}
						if( hasPerm ){
							if(debug) {logDebug("CMD GBH args.length=" + args.length);}
							if( args.length==2 && sender instanceof Player ){
								giveBlockHead((Player) sender,args[1].replace("_", " "));
								if(debug) {logDebug("CMD GBH args1=" + args[1]);}
								if(debug){logDebug("End GiveBH True 1");}
								return true;
							}else if( args.length==3){
								Player player = Bukkit.getPlayer(args[1]);
								giveBlockHead(player,args[2].replace("_", " "));
								if(debug) {logDebug("CMD GBH args1=" + args[1] + ", args2=" +args[2]);}
								if(debug){logDebug("End GiveBH True 2");}
								return true;
							}else if( args.length==2 && !(sender instanceof Player) ){
								sender.sendMessage("Console cannot give itself Heads. /mmh giveBh <player> <block>");
								if(debug){logDebug("End GiveBH False 1");}
								return false;
							}
						}else if( !hasPerm ){
							sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("noperm").toString().replace("<perm>", perm) );
							if(debug){logDebug("End GiveBH False 2");}
							return false;
						}
					}else {
						sender.sendMessage("Command usage, \"/mmh givebh block\" or \"/mmh giveph playername block\"");
						if(debug){logDebug("End GiveBH False 3");}
						return false;
					}
					if(debug){logDebug("End GiveBH False 4");}
					return false;
				}
				/**if(args[0].equalsIgnoreCase("blockheads")||args[0].equalsIgnoreCase("bh")){
					if(sender.hasPermission("flycutter.blockheads")&&sender instanceof Player
							&&getConfig().getBoolean("wandering_trades.custom_wandering_trader", true)){
						Player player = (Player) sender;
						if(!(args.length >= 2)){
							sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
							sender.sendMessage(ChatColor.WHITE + " ");
							sender.sendMessage(ChatColor.WHITE + " /mmh bh - " + lang.getString("cthelp"));
							sender.sendMessage(ChatColor.WHITE + " /mmh bh add - " + lang.getString("ctadd") + "block_heads.yml");
							sender.sendMessage(ChatColor.WHITE + " /mmh bh remove # - " + lang.getString("ctremove"));
							sender.sendMessage(ChatColor.WHITE + " /mmh bh replace # - " + lang.getString("ctreplace").replace("<num>", "#"));
							//sender.sendMessage(ChatColor.WHITE + " ");
							sender.sendMessage(ChatColor.WHITE + " ");
							sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
							return true;
						}else if(args[1].equalsIgnoreCase("add")){
							ItemStack itemstack = player.getInventory().getItemInOffHand();
							ItemStack price1 = player.getInventory().getItem(0);
							ItemStack price2 = player.getInventory().getItem(1);
							if(price1 == null){price1 = new ItemStack(Material.AIR);}
							if(price2 == null){price2 = new ItemStack(Material.AIR);}
							//Material price1 = item1.getType();
							//Material price2 = item2.getType();
							
							if(itemstack.getType() == Material.AIR||price1 == null||price1.getType() == Material.AIR||itemstack.getType() != Material.PLAYER_HEAD){
								sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
								sender.sendMessage(ChatColor.WHITE + " ");
								if(itemstack.getType() != Material.PLAYER_HEAD){
									sender.sendMessage(ChatColor.RED + " MUST BE PLAYERHEAD");
									sender.sendMessage(ChatColor.WHITE + " ");
								}
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline1") + "block_heads.yml");
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline2"));
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline3"));
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline4") + "/mmh bh add");
								sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline5") + "block head.");
								sender.sendMessage(ChatColor.WHITE + " ");
								sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
								return false;
							}
							int tradeNumber = (int) blockHeads.get("blocks.number", 1);
							blockHeads.set("blocks.block_" + (tradeNumber + 1) + ".price_1", price1);
							blockHeads.set("blocks.block_" + (tradeNumber + 1) + ".price_2", price2);
							blockHeads.set("blocks.block_" + (tradeNumber + 1) + ".itemstack", itemstack);
							blockHeads.set("blocks.block_" + (tradeNumber + 1) + ".quantity", itemstack.getAmount());
							//blockHeads.set("blocks.block_" + (tradeNumber + 1) + ".chance", 0.002);
							blockHeads.set("blocks.number", (tradeNumber + 1));
							//log("customFile=" + customFile);
							try {
								blockHeads.save(blockFile);
							} catch (IOException e) {
								e.printStackTrace();
							}
							sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.WHITE + " block_" + (tradeNumber + 1) + " " + lang.get("ctsuccessadd"));
							return true;
						}else if(args[1].equalsIgnoreCase("remove")){
							if(!(args.length >= 3)){
								sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("ctargument"));
								return false;
							}else{
								if(isInteger(args[2])){
									blockHeads.set("blocks.block_" + args[2] + ".price_1", "");
									blockHeads.set("blocks.block_" + args[2] + ".price_2", "");
									blockHeads.set("blocks.block_" + args[2] + ".itemstack", "");
									blockHeads.set("blocks.block_" + args[2] + ".quantity", "");
									//blockHeads.set("blocks.block_" + args[2] + ".chance", "");
									if(debug){logDebug("blockFile=" + blockFile);}
									try {
										blockHeads.save(blockFile);
									} catch (IOException e) {
										sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("cterror") + "block_heads.yml!");
										return false;
										//e.printStackTrace();
									}
									sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.WHITE + " block_" + args[2] + " " + lang.get("ctsuccessrem"));
									return true;
								}else{
									sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("ctnumreq"));
								}
							}
						}else if(args[1].equalsIgnoreCase("replace")){
							if(args.length != 3){
								sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("ctargument"));
								return false;
							}else{
								if(isInteger(args[2])){
									ItemStack itemstack = player.getInventory().getItemInOffHand();
									ItemStack price1 = player.getInventory().getItem(0);
									ItemStack price2 = player.getInventory().getItem(1);
									if(price1 == null){price1 = new ItemStack(Material.AIR);}
									if(price2 == null){price2 = new ItemStack(Material.AIR);}
									if(itemstack.getType() == Material.AIR||price1 == null||price1.getType() == Material.AIR||itemstack.getType() != Material.PLAYER_HEAD){
										sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
										sender.sendMessage(ChatColor.WHITE + " ");
										if(itemstack.getType() != Material.PLAYER_HEAD){
											sender.sendMessage(ChatColor.RED + " MUST BE PLAYERHEAD");
											sender.sendMessage(ChatColor.WHITE + " ");
										}
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline1") + "block_heads.yml");
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline2"));
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline3"));
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline4") + "/mmh bh add");
										sender.sendMessage(ChatColor.WHITE + " " + lang.getString("ctline5") + "block head.");
										sender.sendMessage(ChatColor.WHITE + " ");
										sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + this.getName() + ChatColor.GREEN + "]===============[]");
										return false;
									}
									int tradeNumber = Integer.parseInt(args[2]);
									blockHeads.set("blocks.block_" + (tradeNumber) + ".price_1", price1);
									blockHeads.set("blocks.block_" + (tradeNumber) + ".price_2", price2);
									blockHeads.set("blocks.block_" + (tradeNumber) + ".itemstack", itemstack);
									blockHeads.set("blocks.block_" + (tradeNumber) + ".quantity", itemstack.getAmount());
									//blockHeads.set("blocks.block_" + (tradeNumber + 1) + ".chance", 0.002);
									//log("customFile=" + customFile);
									try {
										blockHeads.save(blockFile);
									} catch (IOException e) {
										sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("cterror") + "block_heads.yml");
										return false;
										//e.printStackTrace();
									}
									sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.WHITE + " block_" + args[2] + " " + lang.get("ctsuccessrep"));
									return true;
								}else{
									sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("ctnumreq"));
									return false;
								}
							}
						}
					}else if(!(sender instanceof Player)){
						sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("noconsole"));
						return false;
					}else if(!sender.hasPermission("flycutter.blockheads")){
						sender.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("nopermordisabled"));
					return false;
					}
				}*//**
			}
			return false;
	}//*/
	
	/**
	for (Material material : Material.values()) {
		autoCompletes.add(material.name());
	}
	autoCompletes.remove(autoCompletes.indexOf("bedrock"));*/
	/**
	@Override 
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) { // TODO: Tab Complete
		if (command.getName().equalsIgnoreCase("mmh")) {
			List<String> autoCompletes = new ArrayList<>(); //create a new string list for tab completion
			if (args.length == 1) { // reload, toggledebug, playerheads, customtrader, headfix
				autoCompletes.add("reload");
				autoCompletes.add("toggledebug");
				autoCompletes.add("playerheads");
				autoCompletes.add("customtrader");
				autoCompletes.add("fixhead");
				autoCompletes.add("givemh");
				autoCompletes.add("giveph");
				autoCompletes.add("givebh");
				autoCompletes.add("display");
				return autoCompletes; // then return the list
			}
			if(args.length > 1) {
				if( args[0].equalsIgnoreCase("display") && args[1].isEmpty() ) {
					autoCompletes.add("permissions");
					autoCompletes.add("variables");
					return autoCompletes; // then return the list
				}else if( args[0].equalsIgnoreCase("display") && args[1].equalsIgnoreCase("permissions") ) {
					if( args[1].equalsIgnoreCase("permissions") ) {
						return null;
					}
				}
				if( args[0].equalsIgnoreCase("fixhead") || args[0].equalsIgnoreCase("fh") && args[1].isEmpty() ) {
					autoCompletes.add("name");
					autoCompletes.add("stack");
					return autoCompletes; // then return the list
				}
				if( args[0].equalsIgnoreCase("playerheads") || args[0].equalsIgnoreCase("ph") && args[1].isEmpty() ) {
					autoCompletes.add("add");
					autoCompletes.add("remove");
					autoCompletes.add("replace");
					return autoCompletes; // then return the list
				}else if( (args[0].equalsIgnoreCase("playerheads") || args[0].equalsIgnoreCase("ph")) && (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("replace")) ) {
					if( args[1].equalsIgnoreCase("remove") ) {
						autoCompletes.add("0");
						return autoCompletes; // then return the list
					}
					if( args[1].equalsIgnoreCase("replace") ) {
						autoCompletes.add("0");
						return autoCompletes; // then return the list
					}
				}
				if( args[0].equalsIgnoreCase("customtrader") || args[0].equalsIgnoreCase("ct") && args[1].isEmpty() ) {
					autoCompletes.add("add");
					autoCompletes.add("remove");
					autoCompletes.add("replace");
					return autoCompletes; // then return the list
				}else if( (args[0].equalsIgnoreCase("customtrader") || args[0].equalsIgnoreCase("ct")) && (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("replace")) ) {
					if( args[1].equalsIgnoreCase("remove") ) {
						autoCompletes.add("0");
						return autoCompletes; // then return the list
					}
					if( args[1].equalsIgnoreCase("replace") ) {
						autoCompletes.add("0");
						return autoCompletes; // then return the list
					}
				}
				if( args[0].equalsIgnoreCase("givebh") ) {
					if(  args.length < 2 ) {
						// /mmh giveph block
						// /mmh giveph @p block
						// /cmd 0      1  2
						// return null to list all players.
						return null;
					}
					if(  args.length > 2 ) {
						for(int i = 1; i < blockHeads.getInt("blocks.number"); ++i) {
							ItemStack stack = blockHeads.getItemStack("blocks.block_" + i + ".itemstack");
							String name = stack.getItemMeta().getDisplayName().replace(" ", "_");
							autoCompletes.add( ChatColor.stripColor( name ) );
						}
						if(Double.parseDouble(StrUtils.Left(getMCVersion(), 4)) >= 1.16) {
							for(int i = 1; i < blockHeads2.getInt("blocks.number"); ++i) {
								ItemStack stack = blockHeads2.getItemStack("blocks.block_" + i + ".itemstack");
								String name = stack.getItemMeta().getDisplayName().replace(" ", "_");
								autoCompletes.add( ChatColor.stripColor( name ) );
							}
						}
						if(Double.parseDouble(StrUtils.Left(getMCVersion(), 4)) >= 1.17) {
							for(int i = 1; i < blockHeads3.getInt("blocks.number"); ++i) {
								ItemStack stack = blockHeads3.getItemStack("blocks.block_" + i + ".itemstack");
								String name = stack.getItemMeta().getDisplayName().replace(" ", "_");
								autoCompletes.add( ChatColor.stripColor( name ) );
							}
						}
						return autoCompletes;
					}
				}
				if( args[0].equalsIgnoreCase("giveph") ) {
					//return null;
					if(  args.length < 2 ) { 
						// /mmh giveph @p @P
						// /cmd 0      1  2
						// return null to list all players.
						return null;
					}
					if(  args.length == 2 ) { 
						// /mmh giveph @p @P
						// /cmd 0      1  2
						// return null to list all players.
						return null;
					}
					
				}
				if( args[0].equalsIgnoreCase("givemh") ) {
					if(  args.length < 2 ) { 
						// /mmh give @p
						// /cmd 0    1
						// return null to list all players.
						return null;
					}else if(  args.length > 2 ) {
						if(debug) {logDebug("TC arg1!null args.length=" + args.length);}
						if( args.length == 3 ) {
						
							// /mmh give @p moblist #
							// /cmd 0    1  2       3
						    for(String key : chanceConfig.getConfigurationSection("chance_percent").getKeys(true)) {
						        //System.out.println(key);
						        autoCompletes.add(key);
						        //System.out.println(key);
						        if(key.equalsIgnoreCase("wolf")) {
						    		autoCompletes.add("wolf.angry");
						    	}else if(key.equalsIgnoreCase("wither")) {
						    		autoCompletes.add("wither.1");
						    		autoCompletes.add("wither.2");
						    		autoCompletes.add("wither.3");
						    		autoCompletes.add("wither.4");
						    		autoCompletes.remove(autoCompletes.indexOf("wither"));
						    	}else if(key.equalsIgnoreCase("zombie_villager")) {
						    		autoCompletes.add("zombie_villager.armorer");
						    		autoCompletes.add("zombie_villager.butcher");
						    		autoCompletes.add("zombie_villager.cartographer");
						    		autoCompletes.add("zombie_villager.cleric");
						    		autoCompletes.add("zombie_villager.farmer");
						    		autoCompletes.add("zombie_villager.fisherman");
						    		autoCompletes.add("zombie_villager.fletcher");
						    		autoCompletes.add("zombie_villager.leatherworker");
						    		autoCompletes.add("zombie_villager.librarian");
						    		autoCompletes.add("zombie_villager.mason");
						    		autoCompletes.add("zombie_villager.nitwit");
						    		autoCompletes.add("zombie_villager.none");
						    		autoCompletes.add("zombie_villager.shepherd");
						    		autoCompletes.add("zombie_villager.toolsmith");
						    		autoCompletes.add("zombie_villager.weaponsmith");
						    		autoCompletes.remove(autoCompletes.indexOf("zombie_villager"));
						    	}
						    }
						    autoCompletes.remove(autoCompletes.indexOf("axolotl"));
						    autoCompletes.remove(autoCompletes.indexOf("bee.chance_percent"));
						    autoCompletes.remove(autoCompletes.indexOf("cat"));
						    autoCompletes.remove(autoCompletes.indexOf("fox"));
						    autoCompletes.remove(autoCompletes.indexOf("goat"));
						    autoCompletes.remove(autoCompletes.indexOf("horse"));
						    autoCompletes.remove(autoCompletes.indexOf("llama"));
						    autoCompletes.remove(autoCompletes.indexOf("panda"));
						    autoCompletes.remove(autoCompletes.indexOf("parrot"));
						    autoCompletes.remove(autoCompletes.indexOf("rabbit"));
						    autoCompletes.remove(autoCompletes.indexOf("sheep"));
						    autoCompletes.remove(autoCompletes.indexOf("trader_llama"));
						    autoCompletes.remove(autoCompletes.indexOf("mushroom_cow"));
						    autoCompletes.remove(autoCompletes.indexOf("villager"));
						    autoCompletes.remove(autoCompletes.indexOf("villager.desert"));
						    autoCompletes.remove(autoCompletes.indexOf("villager.jungle"));
						    autoCompletes.remove(autoCompletes.indexOf("villager.plains"));
						    autoCompletes.remove(autoCompletes.indexOf("villager.savanna"));
						    autoCompletes.remove(autoCompletes.indexOf("villager.snow"));
						    autoCompletes.remove(autoCompletes.indexOf("villager.swamp"));
						    autoCompletes.remove(autoCompletes.indexOf("villager.taiga"));
						    
						    return autoCompletes;
						}else if(  args.length == 4 ) {
							autoCompletes.add("1");
							return autoCompletes;
						}
					}
				}
			}
		}
		return null;
	}//*/
	
	public boolean isInteger(String s){
	    try{
	        Integer.parseInt(s);
	        return true;
	    }catch (NumberFormatException ex){
	        return false;
	    }
	}
	
	//@SuppressWarnings("unused")
	@EventHandler
	public void OnTake(EntityPickupItemEvent e){
	/** //ItemStack item = new ItemStack(7);
	LivingEntity entity = e.getEntity();
	Item item = e.getItem();
		if(entity instanceof Player){
			//log("" + e.getItem().getItemStack().toString());//e.getItem().getType() instanceof Material.PLAYER_HEAD
			Material headtype = item.getItemStack().getType();
			if (headtype == Material.PLAYER_HEAD&&headtype != Material.CREEPER_HEAD&&headtype != Material.ZOMBIE_HEAD&&headtype != Material.SKELETON_SKULL
					&&headtype != Material.WITHER_SKELETON_SKULL&&headtype != Material.DRAGON_HEAD){//e.getItem().getItemStack().equals(new ItemStack(Material.PLAYER_HEAD))
					if(debug){logDebug("EPIE isPlayerEPIE=true");}
					if(debug){logDebug("EPIE item.length=" +	item.getName().length());}
				SkullMeta skullname = (SkullMeta) item.getItemStack().getItemMeta();
				if(skullname.getOwner() != null){
					String name = skullname.getOwner().toString();
					if(debug){logDebug("EPIE name=" + name);}
					if(debug){logDebug("EPIE lore=" + skullname.getLore());}
					if(skullname.getOwner().toString().length() >= 40){
						if(debug){logDebug("EPIE ownerName.lngth >= 40");}
							ItemStack itmStack = e.getItem().getItemStack();
							SkullMeta meta = (SkullMeta) e.getItem().getItemStack().getItemMeta();
							String daMobName = "null";
							if(meta != null){
								String isCat = CatHeads.getNameFromTexture(meta.getOwner().toString());
								String isHorse = HorseHeads.getNameFromTexture(meta.getOwner().toString());
								String isLlama = LlamaHeads.getNameFromTexture(meta.getOwner().toString());
								String isMobHead = MobHeads.getNameFromTexture(meta.getOwner().toString());
								String isRabbit = RabbitHeads.getNameFromTexture(meta.getOwner().toString());
								String isSheep = SheepHeads.getNameFromTexture(meta.getOwner().toString());
								String isVillager = VillagerHeads.getNameFromTexture(meta.getOwner().toString());
								String isZombieVillager = ZombieVillagerHeads.getNameFromTexture(meta.getOwner().toString());
								String isplayerhead = isPlayerHead(meta.getOwner().toString());
								String isblockhead = isBlockHead(meta.getOwner().toString());
								String isblockhead2 = isBlockHead2(meta.getOwner().toString());
								if(isCat != null){				daMobName = isCat;	}
								if(isHorse != null){			daMobName = isHorse;	}
								if(isLlama != null){			daMobName = isLlama;	}
								if(isMobHead != null){			daMobName = isMobHead;	}
								if(isRabbit != null){			daMobName = isRabbit;	}
								if(isSheep != null){			daMobName = isSheep;	}
								if(isVillager != null){			daMobName = isVillager;	}
								if(isZombieVillager != null){	daMobName = isZombieVillager;	}
								if(daMobName == null){
									if(blockHeads != null){
										if(isblockhead != null){	daMobName = isblockhead;	}
									}
								}
								if(daMobName == null){
									if(blockHeads2 != null){
										if(isblockhead2 != null){	daMobName = isblockhead2;	}
									}
								}
								if(daMobName == null){
									if(playerHeads != null){
										if(isplayerhead != null){	daMobName = isplayerhead;	}
									}
								}
								ArrayList<String> lore = new ArrayList();
								//log("" + meta.getOwner().toString());
								//String name = LlamaHeads.getNameFromTexture(meta.getOwner().toString());
								if(debug){logDebug("EPIE mobname from texture=" + daMobName);}
								List<String> skullLore = skullname.getLore();
								if(skullLore != null){
									if(skullLore.toString().contains("Killed by")){
										lore.addAll(skullname.getLore());
									}
								}
								if(skullLore == null||!skullname.getLore().toString().contains(this.getName())){
									if(getConfig().getBoolean("lore.show_plugin_name", true)){
										lore.add(ChatColor.AQUA + "" + this.getName());
									}
								}
								if(daMobName != "null"){
									daMobName = langName.getString(daMobName.toLowerCase().replace(" ", "."), daMobName);
								}else{
									daMobName = langName.getString(daMobName.toLowerCase().replace(" ", "."), "404 Name Not Found");
								}
								meta.setLore(lore);
								meta.setDisplayName(daMobName);
								itmStack.setItemMeta(meta);
								//if(debug){logDebug("test3a");}
							}else{
								if(debug){logDebug("EPIE test3b meta == null");}
						}
					}
				}
			}else{
				if(debug){logDebug("not player head");}
			}
		}
			//log("test4");*/
	}
	
	public String isPlayerHead(String string){
		try{
			playerFile = new File(getDataFolder() + "" + File.separatorChar + "player_heads.yml");//\
			if(!playerFile.exists()){																	// checks if the yaml does not exist
				return null;
			}
			int numOfCustomTrades = playerHeads.getInt("players.number", 0) + 1;
			if(debug){logDebug("iPH string=" + string);}
			for(int randomPlayerHead=1; randomPlayerHead<numOfCustomTrades; randomPlayerHead++){
				ItemStack itemstack = playerHeads.getItemStack("players.player_" + randomPlayerHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iPH getOwner_" + randomPlayerHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getOwner() != null){
							if(skullmeta.getOwner().toString().contains(string)){
								return itemstack.getItemMeta().getDisplayName();
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			//e.printStackTrace();
			return null;
		}
		//playerHeads
		return null;
	}
	
	public String isBlockHead(String string){ // TODO: isBlockHead
		try{
			if(!(Double.parseDouble(StrUtils.Left(getMCVersion(), 4)) >= 1.16)){
				blockFile = new File(getDataFolder() + "" + File.separatorChar + "block_heads.yml");//\
				if(!blockFile.exists()){																	// checks if the yaml does not exist
					return null;
				}
			}
			blockFile116 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16.yml");
			//blockFile1162 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16_2.yml");
			if(Double.parseDouble(StrUtils.Left(getMCVersion(), 4)) >= 1.16){
				if(!blockFile116.exists()){
					return null;
				}
			}
			int numOfCustomTrades = blockHeads.getInt("blocks.number", 0) + 1;
			if(debug){logDebug("iBH string=" + string);}
			for(int randomBlockHead = 1; randomBlockHead < numOfCustomTrades; randomBlockHead++){
				ItemStack itemstack = blockHeads.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iBH getOwner_" + randomBlockHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getDisplayName() != null){
							if(ChatColor.stripColor(skullmeta.getDisplayName()).equals(string)){
								return itemstack.getItemMeta().getDisplayName();
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			//e.printStackTrace();
			return null;
		}
		//blockHeads
		return null;
	}
	
	public String isBlockHead2(String string){
		try{
			if(!(Double.parseDouble(StrUtils.Left(getMCVersion(), 4)) >= 1.16)){																// checks if the yaml does not exist
					return null;
			}
			blockFile1162 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16_2.yml");
			if(getMCVersion().startsWith("1.16")||getMCVersion().startsWith("1.17")){
				if(!blockFile1162.exists()){
					return null;
				}
			
			}
			int numOfCustomTrades = blockHeads2.getInt("blocks.number", 0) + 1;
			if(debug){logDebug("iBH2 string=" + string);}
			for(int randomBlockHead = 1; randomBlockHead < numOfCustomTrades; randomBlockHead++){
				ItemStack itemstack = blockHeads2.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iBH getOwner_" + randomBlockHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getOwner() != null){
							if(skullmeta.getOwner().toString().contains(string)){
								return itemstack.getItemMeta().getDisplayName();
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			//e.printStackTrace();
			return null;
		}
		//blockHeads
		return null;
	}
	
	public String isBlockHead3(String string){
		try{
			if(!(Double.parseDouble(StrUtils.Left(getMCVersion(), 4)) >= 1.16)){																// checks if the yaml does not exist
					return null;
			}
			blockFile117 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_17.yml");
			if(getMCVersion().startsWith("1.16")||getMCVersion().startsWith("1.17")){
				if(!blockFile117.exists()){
					return null;
				}
			
			}
			int numOfCustomTrades = blockHeads3.getInt("blocks.number", 0) + 1;
			if(debug){logDebug("iBH3 string=" + string);}
			for(int randomBlockHead = 1; randomBlockHead < numOfCustomTrades; randomBlockHead++){
				ItemStack itemstack = blockHeads3.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iBH getOwner_" + randomBlockHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getOwner() != null){
							if(skullmeta.getOwner().toString().contains(string)){
								return itemstack.getItemMeta().getDisplayName();
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			//e.printStackTrace();
			return null;
		}
		//blockHeads
		return null;
	}
	
	public int isBlockHeadName(String string){ // TODO: isBlockHeadName
		if(debug){logDebug("iBHN START");}
		try{
			double mcVer = Double.parseDouble(StrUtils.Left(getMCVersion(), 4));
			if(!(mcVer >= 1.16)){
				blockFile = new File(getDataFolder() + "" + File.separatorChar + "block_heads.yml");//\
				if(!blockFile.exists()){																	// checks if the yaml does not exist
					return -1;
				}
			}else if(mcVer == 1.16){
				blockFile = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16.yml");
			}else if(mcVer == 1.17) {
				blockFile = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_17.yml");
			}

			if(debug){logDebug("iBH blockFile=" + blockFile.toString());}
			if(blockHeads.getInt("blocks.number", 0) == 0) {
				try {
					blockHeads.load(blockFile);
				} catch (IOException | InvalidConfigurationException e1) {
					stacktraceInfo();
					e1.printStackTrace();
				}
			}
			int numOfCustomTrades = blockHeads.getInt("blocks.number", 0) + 1;
			if(debug){logDebug("iBH number=" + numOfCustomTrades);}
			if(debug){logDebug("iBH string=" + string);}
			for(int randomBlockHead = 1; randomBlockHead < numOfCustomTrades; randomBlockHead++){
				ItemStack itemstack = blockHeads.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iBH getOwner_" + randomBlockHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getDisplayName() != null){
							if(ChatColor.stripColor(skullmeta.getDisplayName()).toLowerCase().equals(string.toLowerCase())){
								if(debug){logDebug("iBHN END Sucess!");}
								return randomBlockHead; //itemstack.getItemMeta().getDisplayName();
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			e.printStackTrace();
			if(debug){logDebug("iBHN END Failure=Exception");}
			return -1;
		}
		//blockHeads
		if(debug){logDebug("iBHN END Failure!");}
		return -1;
	}
	public int isBlockHeadName2(String string){
		if(debug){logDebug("iBHN2 START");}
		try{
			double mcVer = Double.parseDouble(StrUtils.Left(getMCVersion(), 4));
			if(!(mcVer >= 1.16)){																// checks if the yaml does not exist
					return -1;
			}else if(mcVer == 1.16) {
				blockFile1162 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16_2.yml");
			}else if(mcVer == 1.17) {
				blockFile1162 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_17_2.yml");
			}
			
			if(getMCVersion().startsWith("1.16")||getMCVersion().startsWith("1.17")){
				if(!blockFile1162.exists()){
					return -1;
				}
			
			}
			if(debug){logDebug("iBH blockFile1162=" + blockFile1162.toString());}
			if(blockHeads2.getInt("blocks.number", 0) == 0) {
				try {
					blockHeads2.load(blockFile1162);
				} catch (IOException | InvalidConfigurationException e1) {
					stacktraceInfo();
					e1.printStackTrace();
				}
			}
			int numOfCustomTrades = blockHeads2.getInt("blocks.number", 0) + 1;
			if(debug){logDebug("iBH2 number=" + numOfCustomTrades);}
			if(debug){logDebug("iBH2 string=" + string);}
			for(int randomBlockHead = 1; randomBlockHead < numOfCustomTrades; randomBlockHead++){
				ItemStack itemstack = blockHeads2.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iBH getOwner_" + randomBlockHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getDisplayName() != null){
							if(ChatColor.stripColor(skullmeta.getDisplayName()).toLowerCase().equals(string.toLowerCase())){
								if(debug){logDebug("iBHN END Sucess!");}
								return randomBlockHead; //itemstack.getItemMeta().getDisplayName();
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			//e.printStackTrace();
			if(debug){logDebug("iBHN END Failure=Exception");}
			return -1;
		}
		//blockHeads
		if(debug){logDebug("iBHN2 END Failure!");}
		return -1;
	}
	
	public int isBlockHeadName3(String string){
		if(debug){logDebug("iBHN3 START");}
		try{
			double mcVer = Double.parseDouble(StrUtils.Left(getMCVersion(), 4));
			if(!(mcVer >= 1.16)){																// checks if the yaml does not exist
					return -1;
			}else if(mcVer == 1.16) {
				return -1;
			}else if(mcVer == 1.17) {
				blockFile117 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_17_3.yml");
			}
			
			if(getMCVersion().startsWith("1.16")||getMCVersion().startsWith("1.17")){
				if(!blockFile117.exists()){
					return -1;
				}
			
			}
			if(debug){logDebug("iBHN3 blockFile117=" + blockFile1162.toString());}
			if(blockHeads3.getInt("blocks.number", 0) == 0) {
				try {
					blockHeads3.load(blockFile117);
				} catch (IOException | InvalidConfigurationException e1) {
					stacktraceInfo();
					e1.printStackTrace();
				}
			}
			int numOfCustomTrades = blockHeads3.getInt("blocks.number", 0) + 1;
			if(debug){logDebug("iBH3 number=" + numOfCustomTrades);}
			if(debug){logDebug("iBH3 string=" + string);}
			for(int randomBlockHead = 1; randomBlockHead < numOfCustomTrades; randomBlockHead++){
				ItemStack itemstack = blockHeads3.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iBH getOwner_" + randomBlockHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getDisplayName() != null){
							if(ChatColor.stripColor(skullmeta.getDisplayName()).toLowerCase().equals(string.toLowerCase())){
								if(debug){logDebug("iBHN END Sucess!");}
								return randomBlockHead; //itemstack.getItemMeta().getDisplayName();
							}else {
								//log(Level.INFO,"" + ChatColor.stripColor(skullmeta.getDisplayName()).toLowerCase());
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			//e.printStackTrace();
			if(debug){logDebug("iBHN3 END Failure=Exception");}
			return -1;
		}
		//blockHeads
		if(debug){logDebug("iBHN3 END Failure!");}
		return -1;
	}

	public static void copyFile_Java7(String origin, String destination) throws IOException {
		Path FROM = Paths.get(origin);
		Path TO = Paths.get(destination);
		//overwrite the destination file if it exists, and copy
		// the file attributes, including the rwx permissions
		CopyOption[] options = new CopyOption[]{
			StandardCopyOption.REPLACE_EXISTING,
			StandardCopyOption.COPY_ATTRIBUTES
		}; 
		Files.copy(FROM, TO, options);
	}
	
	public void copyChance(String file, String file2){
		chanceConfig = new YmlConfiguration();
		oldchanceConfig = new YmlConfiguration();
		try {
			chanceConfig.load(new File(file2));
			oldchanceConfig.load(new File(file));
		} catch (IOException | InvalidConfigurationException e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		log(Level.INFO, "Copying values frome old_chance_config.yml 到 chance_config.yml");
		chanceConfig.set("chance_percent.player"							, oldchanceConfig.get("chance_percent.player", "0.50"));
		chanceConfig.set("chance_percent.named_mob"							, oldchanceConfig.get("chance_percent.named_mob", "0.10"));
		chanceConfig.set("chance_percent.axolotl.blue"						, oldchanceConfig.get("chance_percent.axolotl.blue", "1.00"));
		chanceConfig.set("chance_percent.axolotl.cyan"						, oldchanceConfig.get("chance_percent.axolotl.cyan", "0.20"));
		chanceConfig.set("chance_percent.axolotl.gold"						, oldchanceConfig.get("chance_percent.axolotl.gold", "0.20"));
		chanceConfig.set("chance_percent.axolotl.lucy"						, oldchanceConfig.get("chance_percent.axolotl.lucy", "0.20"));
		chanceConfig.set("chance_percent.axolotl.wild"						, oldchanceConfig.get("chance_percent.axolotl.wild", "0.20"));
		chanceConfig.set("chance_percent.bat"								, oldchanceConfig.get("chance_percent.bat", "0.10"));
		chanceConfig.set("chance_percent.bee.angry_pollinated"				, oldchanceConfig.get("chance_percent.bee.angry_pollinated", "0.20"));
		chanceConfig.set("chance_percent.bee.angry"							, oldchanceConfig.get("chance_percent.bee.angry", "0.20"));
		chanceConfig.set("chance_percent.bee.pollinated"					, oldchanceConfig.get("chance_percent.bee.pollinated", "0.20"));
		chanceConfig.set("chance_percent.bee.chance_percent"				, oldchanceConfig.get("chance_percent.bee.chance_percent", "0.20"));
		chanceConfig.set("chance_percent.blaze"								, oldchanceConfig.get("chance_percent.blaze", "0.005"));
		chanceConfig.set("chance_percent.cat.all_black"						, oldchanceConfig.get("chance_percent.cat.all_black", "0.33"));
		chanceConfig.set("chance_percent.cat.black"							, oldchanceConfig.get("chance_percent.cat.black", "0.33"));
		chanceConfig.set("chance_percent.cat.british_shorthair"				, oldchanceConfig.get("chance_percent.cat.british_shorthair", "0.33"));
		chanceConfig.set("chance_percent.cat.calico"						, oldchanceConfig.get("chance_percent.cat.calico", "0.33"));
		chanceConfig.set("chance_percent.cat.jellie"						, oldchanceConfig.get("chance_percent.cat.jellie", "0.33"));
		chanceConfig.set("chance_percent.cat.persian"						, oldchanceConfig.get("chance_percent.cat.persian", "0.33"));
		chanceConfig.set("chance_percent.cat.ragdoll"						, oldchanceConfig.get("chance_percent.cat.ragdoll", "0.33"));
		chanceConfig.set("chance_percent.cat.red"							, oldchanceConfig.get("chance_percent.cat.red", "0.33"));
		chanceConfig.set("chance_percent.cat.siamese"						, oldchanceConfig.get("chance_percent.cat.siamese", "0.33"));
		chanceConfig.set("chance_percent.cat.tabby"							, oldchanceConfig.get("chance_percent.cat.tabby", "0.33"));
		chanceConfig.set("chance_percent.cat.white"							, oldchanceConfig.get("chance_percent.cat.white", "0.33"));
		
		chanceConfig.set("chance_percent.cave_spider"						, oldchanceConfig.get("chance_percent.cave_spider", "0.005"));
		chanceConfig.set("chance_percent.chicken"							, oldchanceConfig.get("chance_percent.chicken", "0.01"));
		chanceConfig.set("chance_percent.cod"								, oldchanceConfig.get("chance_percent.cod", "0.10"));
		chanceConfig.set("chance_percent.cow"								, oldchanceConfig.get("chance_percent.cow", "0.01"));
		chanceConfig.set("chance_percent.creeper"							, oldchanceConfig.get("chance_percent.creeper", "0.50"));
		chanceConfig.set("chance_percent.creeper_charged"					, oldchanceConfig.get("chance_percent.creeper_charged", "1.00"));
		chanceConfig.set("chance_percent.dolphin"							, oldchanceConfig.get("chance_percent.dolphin", "0.33"));
		chanceConfig.set("chance_percent.donkey"							, oldchanceConfig.get("chance_percent.donkey", "0.20"));
		chanceConfig.set("chance_percent.drowned"							, oldchanceConfig.get("chance_percent.drowned", "0.05"));
		chanceConfig.set("chance_percent.elder_guardian"					, oldchanceConfig.get("chance_percent.elder_guardian", "1.00"));
		chanceConfig.set("chance_percent.ender_dragon"						, oldchanceConfig.get("chance_percent.ender_dragon", "1.00"));
		chanceConfig.set("chance_percent.enderman"							, oldchanceConfig.get("chance_percent.enderman", "0.005"));
		chanceConfig.set("chance_percent.endermite"							, oldchanceConfig.get("chance_percent.endermite", "0.10"));
		chanceConfig.set("chance_percent.evoker"							, oldchanceConfig.get("chance_percent.evoker", "0.25"));
		chanceConfig.set("chance_percent.fox.red"							, oldchanceConfig.get("chance_percent.fox.red", "0.10"));
		chanceConfig.set("chance_percent.fox.snow"							, oldchanceConfig.get("chance_percent.fox.snow", "0.10"));
		chanceConfig.set("chance_percent.ghast"								, oldchanceConfig.get("chance_percent.ghast", "0.0625"));
		chanceConfig.set("chance_percent.giant"								, oldchanceConfig.get("chance_percent.giant", "0.025"));
		chanceConfig.set("chance_percent.glow_squid"						, oldchanceConfig.get("chance_percent.glow_squid", "0.10"));
		chanceConfig.set("chance_percent.goat.mormal"						, oldchanceConfig.get("chance_percent.goat.normal", "0.01"));
		chanceConfig.set("chance_percent.goat.screaming"					, oldchanceConfig.get("chance_percent.goat.screaming", "1.00"));
		chanceConfig.set("chance_percent.guardian"							, oldchanceConfig.get("chance_percent.guardian", "0.005"));
		chanceConfig.set("chance_percent.hoglin"							, oldchanceConfig.get("chance_percent.hoglin", "0.03"));
		chanceConfig.set("chance_percent.horse.black"						, oldchanceConfig.get("chance_percent.horse.black", "0.27"));
		chanceConfig.set("chance_percent.horse.brown"						, oldchanceConfig.get("chance_percent.horse.brown", "0.27"));
		chanceConfig.set("chance_percent.horse.chestnut"					, oldchanceConfig.get("chance_percent.horse.chestnut", "0.27"));
		chanceConfig.set("chance_percent.horse.creamy"						, oldchanceConfig.get("chance_percent.horse.creamy", "0.27"));
		chanceConfig.set("chance_percent.horse.dark_brown"					, oldchanceConfig.get("chance_percent.horse.dark_brown", "0.27"));
		chanceConfig.set("chance_percent.horse.gray"						, oldchanceConfig.get("chance_percent.horse.gray", "0.27"));
		chanceConfig.set("chance_percent.horse.white"						, oldchanceConfig.get("chance_percent.horse.white", "0.27"));
		chanceConfig.set("chance_percent.husk"								, oldchanceConfig.get("chance_percent.husk", "0.06"));
		chanceConfig.set("chance_percent.illusioner"						, oldchanceConfig.get("chance_percent.illusioner", "0.25"));
		chanceConfig.set("chance_percent.iron_golem"						, oldchanceConfig.get("chance_percent.iron_golem", "0.05"));
		chanceConfig.set("chance_percent.llama.brown"						, oldchanceConfig.get("chance_percent.llama.brown", "0.24"));
		chanceConfig.set("chance_percent.llama.creamy"						, oldchanceConfig.get("chance_percent.llama.creamy", "0.24"));
		chanceConfig.set("chance_percent.llama.gray"						, oldchanceConfig.get("chance_percent.llama.gray", "0.24"));
		chanceConfig.set("chance_percent.llama.white"						, oldchanceConfig.get("chance_percent.llama.white", "0.24"));
		chanceConfig.set("chance_percent.magma_cube"						, oldchanceConfig.get("chance_percent.magma_cube", "0.005"));
		chanceConfig.set("chance_percent.mule"								, oldchanceConfig.get("chance_percent.mule", "0.20"));
		chanceConfig.set("chance_percent.mushroom_cow.red"					, oldchanceConfig.get("chance_percent.mushroom_cow.red", "0.10"));
		chanceConfig.set("chance_percent.mushroom_cow.brown"				, oldchanceConfig.get("chance_percent.mushroom_cow.brown", "0.10"));
		chanceConfig.set("chance_percent.ocelot"							, oldchanceConfig.get("chance_percent.cat.wild_ocelot", "0.20"));
		chanceConfig.set("chance_percent.panda.aggressive"					, oldchanceConfig.get("chance_percent.panda.aggressive", "0.27"));
		chanceConfig.set("chance_percent.panda.brown"						, oldchanceConfig.get("chance_percent.panda.brown", "0.27"));
		chanceConfig.set("chance_percent.panda.lazy"						, oldchanceConfig.get("chance_percent.panda.lazy", "0.27"));
		chanceConfig.set("chance_percent.panda.normal"						, oldchanceConfig.get("chance_percent.panda.normal", "0.27"));
		chanceConfig.set("chance_percent.panda.playful"						, oldchanceConfig.get("chance_percent.panda.playful", "0.27"));
		chanceConfig.set("chance_percent.panda.weak"						, oldchanceConfig.get("chance_percent.panda.weak", "0.27"));
		chanceConfig.set("chance_percent.panda.worried"						, oldchanceConfig.get("chance_percent.panda.worried", "0.27"));
		chanceConfig.set("chance_percent.parrot.blue"						, oldchanceConfig.get("chance_percent.parrot.blue", "0.25"));
		chanceConfig.set("chance_percent.parrot.cyan"						, oldchanceConfig.get("chance_percent.parrot.cyan", "0.25"));
		chanceConfig.set("chance_percent.parrot.gray"						, oldchanceConfig.get("chance_percent.parrot.gray", "0.25"));
		chanceConfig.set("chance_percent.parrot.green"						, oldchanceConfig.get("chance_percent.parrot.green", "0.25"));
		chanceConfig.set("chance_percent.parrot.red"						, oldchanceConfig.get("chance_percent.parrot.red", "0.25"));
		chanceConfig.set("chance_percent.phantom"							, oldchanceConfig.get("chance_percent.phantom", "0.10"));
		chanceConfig.set("chance_percent.pig"								, oldchanceConfig.get("chance_percent.pig", "0.01"));
		chanceConfig.set("chance_percent.piglin"							, oldchanceConfig.get("chance_percent.piglin", "0.04"));
		chanceConfig.set("chance_percent.pig_zombie"						, oldchanceConfig.get("chance_percent.pig_zombie", "0.005"));
		chanceConfig.set("chance_percent.pillager"							, oldchanceConfig.get("chance_percent.pillager", "0.025"));
		chanceConfig.set("chance_percent.polar_bear"						, oldchanceConfig.get("chance_percent.polar_bear", "0.20"));
		chanceConfig.set("chance_percent.pufferfish"						, oldchanceConfig.get("chance_percent.pufferfish", "0.15"));
		chanceConfig.set("chance_percent.rabbit.black"						, oldchanceConfig.get("chance_percent.rabbit.black", "0.26"));
		chanceConfig.set("chance_percent.rabbit.black_and_white"			, oldchanceConfig.get("chance_percent.rabbit.black_and_white", "0.26"));
		chanceConfig.set("chance_percent.rabbit.brown"						, oldchanceConfig.get("chance_percent.rabbit.brown", "0.26"));
		chanceConfig.set("chance_percent.rabbit.gold"						, oldchanceConfig.get("chance_percent.rabbit.gold", "0.26"));
		chanceConfig.set("chance_percent.rabbit.salt_and_pepper"			, oldchanceConfig.get("chance_percent.rabbit.salt_and_pepper", "0.26"));
		chanceConfig.set("chance_percent.rabbit.the_killer_bunny"			, oldchanceConfig.get("chance_percent.rabbit.the_killer_bunny", "1.00"));
		chanceConfig.set("chance_percent.rabbit.toast"						, oldchanceConfig.get("chance_percent.rabbit.toast", "0.26"));
		chanceConfig.set("chance_percent.rabbit.white"						, oldchanceConfig.get("chance_percent.rabbit.white", "0.26"));
		chanceConfig.set("chance_percent.ravager"							, oldchanceConfig.get("chance_percent.ravager", "0.25"));
		chanceConfig.set("chance_percent.salmon"							, oldchanceConfig.get("chance_percent.salmon", "0.10"));
		chanceConfig.set("chance_percent.sheep.black"						, oldchanceConfig.get("chance_percent.sheep.black", "0.0175"));
		chanceConfig.set("chance_percent.sheep.blue"						, oldchanceConfig.get("chance_percent.sheep.blue", "0.0175"));
		chanceConfig.set("chance_percent.sheep.brown"						, oldchanceConfig.get("chance_percent.sheep.brown", "0.0175"));
		chanceConfig.set("chance_percent.sheep.cyan"						, oldchanceConfig.get("chance_percent.sheep.cyan", "0.0175"));
		chanceConfig.set("chance_percent.sheep.gray"						, oldchanceConfig.get("chance_percent.sheep.gray", "0.0175"));
		chanceConfig.set("chance_percent.sheep.green"						, oldchanceConfig.get("chance_percent.sheep.green", "0.0175"));
		chanceConfig.set("chance_percent.sheep.jeb_"						, oldchanceConfig.get("chance_percent.sheep.jeb_", "0.0175"));
		chanceConfig.set("chance_percent.sheep.light_blue"					, oldchanceConfig.get("chance_percent.sheep.light_blue", "0.0175"));
		chanceConfig.set("chance_percent.sheep.light_gray"					, oldchanceConfig.get("chance_percent.sheep.light_gray", "0.0175"));
		chanceConfig.set("chance_percent.sheep.lime"						, oldchanceConfig.get("chance_percent.sheep.lime", "0.0175"));
		chanceConfig.set("chance_percent.sheep.magenta"						, oldchanceConfig.get("chance_percent.sheep.magenta", "0.0175"));
		chanceConfig.set("chance_percent.sheep.orange"						, oldchanceConfig.get("chance_percent.sheep.orange", "0.0175"));
		chanceConfig.set("chance_percent.sheep.pink"						, oldchanceConfig.get("chance_percent.sheep.pink", "0.0175"));
		chanceConfig.set("chance_percent.sheep.purple"						, oldchanceConfig.get("chance_percent.sheep.purple", "0.0175"));
		chanceConfig.set("chance_percent.sheep.red"							, oldchanceConfig.get("chance_percent.sheep.red", "0.0175"));
		chanceConfig.set("chance_percent.sheep.white"						, oldchanceConfig.get("chance_percent.sheep.white", "0.0175"));
		chanceConfig.set("chance_percent.sheep.yellow"						, oldchanceConfig.get("chance_percent.sheep.yellow", "0.0175"));
		chanceConfig.set("chance_percent.shulker"							, oldchanceConfig.get("chance_percent.shulker", "0.05"));
		chanceConfig.set("chance_percent.silverfish"						, oldchanceConfig.get("chance_percent.silverfish", "0.05"));
		chanceConfig.set("chance_percent.skeleton"							, oldchanceConfig.get("chance_percent.skeleton", "0.025"));
		chanceConfig.set("chance_percent.skeleton_horse"					, oldchanceConfig.get("chance_percent.skeleton_horse", "0.20"));
		chanceConfig.set("chance_percent.slime"								, oldchanceConfig.get("chance_percent.slime", "0.005"));
		chanceConfig.set("chance_percent.snowman"							, oldchanceConfig.get("chance_percent.snowman", "0.05"));
		chanceConfig.set("chance_percent.spider"							, oldchanceConfig.get("chance_percent.spider", "0.005"));
		chanceConfig.set("chance_percent.squid"								, oldchanceConfig.get("chance_percent.squid", "0.05"));
		chanceConfig.set("chance_percent.stray"								, oldchanceConfig.get("chance_percent.stray", "0.06"));
		chanceConfig.set("chance_percent.strider"							, oldchanceConfig.get("chance_percent.strider", "0.10"));
		chanceConfig.set("chance_percent.trader_llama.brown"				, oldchanceConfig.get("chance_percent.trader_llama.brown", "0.24"));
		chanceConfig.set("chance_percent.trader_llama.creamy"				, oldchanceConfig.get("chance_percent.trader_llama.creamy", "0.24"));
		chanceConfig.set("chance_percent.trader_llama.gray"					, oldchanceConfig.get("chance_percent.trader_llama.gray", "0.24"));
		chanceConfig.set("chance_percent.trader_llama.white"				, oldchanceConfig.get("chance_percent.trader_llama.white", "0.24"));
		chanceConfig.set("chance_percent.tropical_fish"						, oldchanceConfig.get("chance_percent.tropical_fish", "0.10"));
		chanceConfig.set("chance_percent.turtle"							, oldchanceConfig.get("chance_percent.turtle", "0.10"));
		chanceConfig.set("chance_percent.vex"								, oldchanceConfig.get("chance_percent.vex", "0.10"));
		chanceConfig.set("chance_percent.villager.desert.armorer"			, oldchanceConfig.get("chance_percent.villager.desert.armorer", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.butcher"			, oldchanceConfig.get("chance_percent.villager.desert.butcher", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.cartographer"		, oldchanceConfig.get("chance_percent.villager.desert.cartographer", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.cleric"			, oldchanceConfig.get("chance_percent.villager.desert.cleric", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.farmer"			, oldchanceConfig.get("chance_percent.villager.desert.farmer", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.fisherman"			, oldchanceConfig.get("chance_percent.villager.desert.fisherman", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.fletcher"			, oldchanceConfig.get("chance_percent.villager.desert.fletcher", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.leatherworker"		, oldchanceConfig.get("chance_percent.villager.desert.leatherworker", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.librarian"			, oldchanceConfig.get("chance_percent.villager.desert.librarian", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.mason"				, oldchanceConfig.get("chance_percent.villager.desert.mason", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.nitwit"			, oldchanceConfig.get("chance_percent.villager.desert.nitwit", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.none"				, oldchanceConfig.get("chance_percent.villager.desert.none", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.shepherd"			, oldchanceConfig.get("chance_percent.villager.desert.shepherd", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.toolsmith"			, oldchanceConfig.get("chance_percent.villager.desert.toolsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.weaponsmith"		, oldchanceConfig.get("chance_percent.villager.desert.weaponsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.armorer"			, oldchanceConfig.get("chance_percent.villager.jungle.armorer", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.butcher"			, oldchanceConfig.get("chance_percent.villager.jungle.butcher", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.cartographer"		, oldchanceConfig.get("chance_percent.villager.jungle.cartographer", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.cleric"			, oldchanceConfig.get("chance_percent.villager.jungle.cleric", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.farmer"			, oldchanceConfig.get("chance_percent.villager.jungle.farmer", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.fisherman"			, oldchanceConfig.get("chance_percent.villager.jungle.fisherman", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.fletcher"			, oldchanceConfig.get("chance_percent.villager.jungle.fletcher", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.leatherworker"		, oldchanceConfig.get("chance_percent.villager.jungle.leatherworker", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.librarian"			, oldchanceConfig.get("chance_percent.villager.jungle.librarian", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.mason"				, oldchanceConfig.get("chance_percent.villager.jungle.mason", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.nitwit"			, oldchanceConfig.get("chance_percent.villager.jungle.nitwit", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.none"				, oldchanceConfig.get("chance_percent.villager.jungle.none", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.shepherd"			, oldchanceConfig.get("chance_percent.villager.jungle.shepherd", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.toolsmith"			, oldchanceConfig.get("chance_percent.villager.jungle.toolsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.weaponsmith"		, oldchanceConfig.get("chance_percent.villager.jungle.weaponsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.armorer"			, oldchanceConfig.get("chance_percent.villager.plains.armorer", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.butcher"			, oldchanceConfig.get("chance_percent.villager.plains.butcher", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.cartographer"		, oldchanceConfig.get("chance_percent.villager.plains.cartographer", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.cleric"			, oldchanceConfig.get("chance_percent.villager.plains.cleric", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.farmer"			, oldchanceConfig.get("chance_percent.villager.plains.farmer", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.fisherman"			, oldchanceConfig.get("chance_percent.villager.plains.fisherman", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.fletcher"			, oldchanceConfig.get("chance_percent.villager.plains.fletcher", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.leatherworker"		, oldchanceConfig.get("chance_percent.villager.plains.leatherworker", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.librarian"			, oldchanceConfig.get("chance_percent.villager.plains.librarian", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.mason"				, oldchanceConfig.get("chance_percent.villager.plains.mason", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.nitwit"			, oldchanceConfig.get("chance_percent.villager.plains.nitwit", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.none"				, oldchanceConfig.get("chance_percent.villager.plains.none", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.shepherd"			, oldchanceConfig.get("chance_percent.villager.plains.shepherd", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.toolsmith"			, oldchanceConfig.get("chance_percent.villager.plains.toolsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.weaponsmith"		, oldchanceConfig.get("chance_percent.villager.plains.weaponsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.armorer"			, oldchanceConfig.get("chance_percent.villager.savanna.armorer", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.butcher"			, oldchanceConfig.get("chance_percent.villager.savanna.butcher", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.cartographer"		, oldchanceConfig.get("chance_percent.villager.savanna.cartographer", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.cleric"			, oldchanceConfig.get("chance_percent.villager.savanna.cleric", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.farmer"			, oldchanceConfig.get("chance_percent.villager.savanna.farmer", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.fisherman"		, oldchanceConfig.get("chance_percent.villager.savanna.fisherman", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.fletcher"			, oldchanceConfig.get("chance_percent.villager.savanna.fletcher", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.leatherworker"	, oldchanceConfig.get("chance_percent.villager.savanna.leatherworker", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.librarian"		, oldchanceConfig.get("chance_percent.villager.savanna.librarian", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.mason"			, oldchanceConfig.get("chance_percent.villager.savanna.mason", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.nitwit"			, oldchanceConfig.get("chance_percent.villager.savanna.nitwit", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.none"				, oldchanceConfig.get("chance_percent.villager.savanna.none", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.shepherd"			, oldchanceConfig.get("chance_percent.villager.savanna.shepherd", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.toolsmith"		, oldchanceConfig.get("chance_percent.villager.savanna.toolsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.weaponsmith"		, oldchanceConfig.get("chance_percent.villager.savanna.weaponsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.armorer"				, oldchanceConfig.get("chance_percent.villager.snow.armorer", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.butcher"				, oldchanceConfig.get("chance_percent.villager.snow.butcher", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.cartographer"		, oldchanceConfig.get("chance_percent.villager.snow.cartographer", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.cleric"				, oldchanceConfig.get("chance_percent.villager.snow.cleric", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.farmer"				, oldchanceConfig.get("chance_percent.villager.snow.farmer", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.fisherman"			, oldchanceConfig.get("chance_percent.villager.snow.fisherman", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.fletcher"			, oldchanceConfig.get("chance_percent.villager.snow.fletcher", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.leatherworker"		, oldchanceConfig.get("chance_percent.villager.snow.leatherworker", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.librarian"			, oldchanceConfig.get("chance_percent.villager.snow.librarian", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.mason"				, oldchanceConfig.get("chance_percent.villager.snow.mason", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.nitwit"				, oldchanceConfig.get("chance_percent.villager.snow.nitwit", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.none"				, oldchanceConfig.get("chance_percent.villager.snow.none", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.shepherd"			, oldchanceConfig.get("chance_percent.villager.snow.shepherd", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.toolsmith"			, oldchanceConfig.get("chance_percent.villager.snow.toolsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.weaponsmith"			, oldchanceConfig.get("chance_percent.villager.snow.weaponsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.armorer"			, oldchanceConfig.get("chance_percent.villager.swamp.armorer", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.butcher"			, oldchanceConfig.get("chance_percent.villager.swamp.butcher", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.cartographer"		, oldchanceConfig.get("chance_percent.villager.swamp.cartographer", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.cleric"				, oldchanceConfig.get("chance_percent.villager.swamp.cleric", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.farmer"				, oldchanceConfig.get("chance_percent.villager.swamp.farmer", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.fisherman"			, oldchanceConfig.get("chance_percent.villager.swamp.fisherman", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.fletcher"			, oldchanceConfig.get("chance_percent.villager.swamp.fletcher", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.leatherworker"		, oldchanceConfig.get("chance_percent.villager.swamp.leatherworker", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.librarian"			, oldchanceConfig.get("chance_percent.villager.swamp.librarian", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.mason"				, oldchanceConfig.get("chance_percent.villager.swamp.mason", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.nitwit"				, oldchanceConfig.get("chance_percent.villager.swamp.nitwit", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.none"				, oldchanceConfig.get("chance_percent.villager.swamp.none", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.shepherd"			, oldchanceConfig.get("chance_percent.villager.swamp.shepherd", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.toolsmith"			, oldchanceConfig.get("chance_percent.villager.swamp.toolsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.weaponsmith"		, oldchanceConfig.get("chance_percent.villager.swamp.weaponsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.armorer"			, oldchanceConfig.get("chance_percent.villager.taiga.armorer", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.butcher"			, oldchanceConfig.get("chance_percent.villager.taiga.butcher", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.cartographer"		, oldchanceConfig.get("chance_percent.villager.taiga.cartographer", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.cleric"				, oldchanceConfig.get("chance_percent.villager.taiga.cleric", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.farmer"				, oldchanceConfig.get("chance_percent.villager.taiga.farmer", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.fisherman"			, oldchanceConfig.get("chance_percent.villager.taiga.fisherman", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.fletcher"			, oldchanceConfig.get("chance_percent.villager.taiga.fletcher", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.leatherworker"		, oldchanceConfig.get("chance_percent.villager.taiga.leatherworker", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.librarian"			, oldchanceConfig.get("chance_percent.villager.taiga.librarian", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.mason"				, oldchanceConfig.get("chance_percent.villager.taiga.mason", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.nitwit"				, oldchanceConfig.get("chance_percent.villager.taiga.nitwit", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.none"				, oldchanceConfig.get("chance_percent.villager.taiga.none", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.shepherd"			, oldchanceConfig.get("chance_percent.villager.taiga.shepherd", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.toolsmith"			, oldchanceConfig.get("chance_percent.villager.taiga.toolsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.weaponsmith"		, oldchanceConfig.get("chance_percent.villager.taiga.weaponsmith", "1.00"));
		chanceConfig.set("chance_percent.vindicator"						, oldchanceConfig.get("chance_percent.vindicator", "0.05"));
		chanceConfig.set("chance_percent.wandering_trader"					, oldchanceConfig.get("chance_percent.wandering_trader", "1.00"));
		chanceConfig.set("chance_percent.warden"							, oldchanceConfig.get("chance_percent.warden", "1.00"));
		chanceConfig.set("chance_percent.witch"								, oldchanceConfig.get("chance_percent.witch", "0.005"));
		chanceConfig.set("chance_percent.wither"							, oldchanceConfig.get("chance_percent.wither", "1.00"));
		chanceConfig.set("chance_percent.wither_skeleton"					, oldchanceConfig.get("chance_percent.wither_skeleton", "0.025"));
		chanceConfig.set("chance_percent.wolf"								, oldchanceConfig.get("chance_percent.wolf", "0.02"));
		chanceConfig.set("chance_percent.zoglin"							, oldchanceConfig.get("chance_percent.zoglin", "0.20"));
		chanceConfig.set("chance_percent.zombie"							, oldchanceConfig.get("chance_percent.zombie", "0.025"));
		chanceConfig.set("chance_percent.zombie_horse"						, oldchanceConfig.get("chance_percent.zombie_horse", "1.00"));
		chanceConfig.set("chance_percent.zombie_pigman"						, oldchanceConfig.get("chance_percent.zombie_pigman", "0.005"));
		chanceConfig.set("chance_percent.zombified_piglin"					, oldchanceConfig.get("chance_percent.zombified_piglin", "0.005"));
		chanceConfig.set("chance_percent.zombie_villager"					, oldchanceConfig.get("chance_percent.zombie_villager", "0.50"));
		try {
			chanceConfig.save(file2);
		} catch (IOException e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		log(Level.INFO, "chance_config.yml has been updated!");
		oldchanceConfig = null;
	}
	
	public void stacktraceInfo(){
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " Include this with the stacktrace when reporting issues.");
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " This server is running " + Bukkit.getName() + " version " + Bukkit.getVersion() + " (Implementing API version " + Bukkit.getBukkitVersion() + ")");
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " vardebug=" + debug + " debug=" + getConfig().get("debug","error") + " in " + this.getDataFolder() + "/config.yml");
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " jarfile name=" + this.getFile().getAbsoluteFile());
		debug = true;
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " DEBUG has been set as true until plugin reload or /mmh td, or /mmh reload.");
	}
	public static void stacktraceInfoStatic(){
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " Include this with the stacktrace when reporting issues.");
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " This server is running " + Bukkit.getName() + " version " + Bukkit.getVersion() + " (Implementing API version " + Bukkit.getBukkitVersion() + ")");
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " vardebug=" + debug);
		debug = true;
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " DEBUG has been set as true until plugin reload or /mmh td, or /mmh reload.");
	}
	
	// Persistent Heads
	private final NamespacedKey NAME_KEY = new NamespacedKey(this, "head_name");
    private final NamespacedKey LORE_KEY = new NamespacedKey(this, "head_lore");
    private final PersistentDataType<String,String[]> LORE_PDT = new JsonDataType<>(String[].class);
    //@SuppressWarnings("unused")
	//private final PersistentDataType LORE_PDT2 = new JsonDataType<>(String.class);
    //private final NamespacedKey DISPLAY_KEY = new NamespacedKey(this, "head_display");
    //private final NamespacedKey SKULLOWNER_KEY = new NamespacedKey(this, "head_skullowner");
    
    // TODO: Persistent Heads
    
    /** Prevents player from removing player-head NBT by water logging them */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        handleEvent(event::getBlock, event, false);
    }
    
    /** Prevents player from removing player-head NBT using running water */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLiquidFlow(BlockFromToEvent event) {
        handleEvent(event::getToBlock, event, true);
    }
    
    /**@EventHandler(priority = EventPriority.LOWEST)
    public void onBlockExplodeEvent(BlockExplodeEvent event) {
		List<Block> a = event.blockList();
		for (Block block: event.blockList()) { // Ideally should only be one...
			@Nonnull BlockState blockState = block.getState();
	        Material blockType = blockState.getType();
	        if (blockType != Material.PLAYER_HEAD && blockType != Material.PLAYER_WALL_HEAD) return;
	        TileState skullState = (TileState) blockState;
	        @Nonnull PersistentDataContainer skullPDC = skullState.getPersistentDataContainer();
	        @Nullable String name = skullPDC.get(NAME_KEY, PersistentDataType.STRING);
	        @Nullable String[] lore = skullPDC.get(LORE_KEY, LORE_PDT);
	        if (name == null) return;
	        block.breakNaturally();
		}
    	
    }//*/
    
    private void handleEvent(Supplier<Block> blockSupplier, Cancellable event, boolean cancelEvent) {
        Block block = blockSupplier.get();
        @Nonnull BlockState blockState = block.getState();
        if (blockState.getType() != Material.PLAYER_HEAD && blockState.getType() != Material.PLAYER_WALL_HEAD) return;
        Skull skullState = (Skull) blockState;
        @Nonnull PersistentDataContainer skullPDC = skullState.getPersistentDataContainer();
        @Nullable String name = skullPDC.get(NAME_KEY, PersistentDataType.STRING);
        @Nullable String[] lore = skullPDC.get(LORE_KEY, LORE_PDT);
        if (name == null) return;
        @Nonnull Optional<ItemStack> skullStack = block.getDrops().stream().filter(is -> is.getType() == Material.PLAYER_HEAD).findAny();
        if (skullStack.isPresent()) {
            if (updateDrop(block, name, lore, skullStack.get())) return; // This shouldn't happen
            if (cancelEvent) event.setCancelled(true);
        }

        BlockState blockState1 = block.getWorld().getBlockAt(block.getLocation()).getState();
        blockState1.update(true, true);
        if(debug) {log(Level.INFO, "HE - Persistent head completed.");};
    }

    private boolean updateDrop(Block block, @Nullable String name, @Nullable String[] lore, @Nonnull ItemStack itemstack) {
        @Nullable ItemMeta meta = itemstack.getItemMeta();
        if (meta == null) return true;
        meta.setDisplayName(name);
        if (lore != null) meta.setLore(Arrays.asList(lore));
        itemstack.setItemMeta(meta);

        block.getWorld().dropItemNaturally(block.getLocation(), itemstack);
        block.getDrops().clear();
        block.setType(Material.AIR);
        if(debug) {log(Level.INFO, "UD - Persistent head completed.");};
        return false;
    }
    // Persistent Heads
    
	/**@EventHandler()
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
    	Block block = event.getBlock();
    	Location loc = block.getLocation();
    	@Nonnull BlockState blockState = block.getState();
        Material blockType = blockState.getType();
        if (blockType != Material.PLAYER_HEAD && blockType != Material.PLAYER_WALL_HEAD) return;
        TileState skullState = (TileState) blockState;
        @Nonnull PersistentDataContainer skullPDC = skullState.getPersistentDataContainer();
        @Nullable String name = skullPDC.get(NAME_KEY, PersistentDataType.STRING);
        @Nullable String[] lore = skullPDC.get(LORE_KEY, LORE_PDT);
        if (name == null) return;
    	Collection<ItemStack> drops = block.getDrops();
		ItemStack[] stackArray = drops.toArray(new ItemStack[0]);
    	@Nonnull ItemStack itemstack = stackArray[0];
        if (itemstack.getType() == Material.PLAYER_HEAD) {
            @Nullable ItemMeta meta = itemstack.getItemMeta();
            if (meta == null) {
            	logWarn("meta=null");
            	return; // This shouldn't happen
            }
            meta.setDisplayName(name);
            if (lore != null) meta.setLore(Arrays.asList(lore));
            itemstack.setItemMeta(meta);
            
        	block.getWorld().dropItemNaturally(block.getLocation(), itemstack);
        	block.getDrops().clear();
        	block.setType(Material.AIR);
        }

        BlockState blockS = block.getWorld().getBlockAt(loc).getState();
        blockS.update(true, true);
    }
	
    @EventHandler
    public void onLiquidFlow(BlockFromToEvent event) {
        Block block = event.getToBlock();
        Location loc = block.getLocation();
        
        @Nonnull BlockState blockState = block.getState();
        Material blockType = blockState.getType();
        if (blockType != Material.PLAYER_HEAD && blockType != Material.PLAYER_WALL_HEAD) return;
        TileState skullState = (TileState) blockState;
        @Nonnull PersistentDataContainer skullPDC = skullState.getPersistentDataContainer();
        @Nullable String name = skullPDC.get(NAME_KEY, PersistentDataType.STRING);
        @Nullable String[] lore = skullPDC.get(LORE_KEY, LORE_PDT);
        if (name == null) return;
        Collection<ItemStack> drops = block.getDrops();
        ItemStack[] stackArray = drops.toArray(new ItemStack[0]);
        @Nonnull ItemStack itemstack = stackArray[0];
        if (itemstack.getType() == Material.PLAYER_HEAD) {
        	@Nullable ItemMeta meta = itemstack.getItemMeta();
        	if (meta == null) return; // This shouldn't happen
        	meta.setDisplayName(name);
        	if (lore != null) meta.setLore(Arrays.asList(lore));
        	itemstack.setItemMeta(meta);
        	
        	block.getWorld().dropItemNaturally(block.getLocation(), itemstack);
        	block.getDrops().clear();
        	block.setType(Material.AIR);
        	event.setCancelled(true);
        }

        BlockState blockS = block.getWorld().getBlockAt(loc).getState();
        blockS.update(true, true);
    }// */
    
    @SuppressWarnings("unused")
	public ItemStack fixHeadStack(ItemStack offHand, ItemStack mainHand){
    	NBTItem nbti = new NBTItem(offHand);
    	Set<String> SkullKeys = nbti.getKeys();
    	int damage = nbti.getInteger("Damage");
    	NBTCompound display = nbti.getCompound("display");
    	NBTCompound SkullOwner = nbti.getCompound("SkullOwner");
    	if(debug){logDebug("FHS Offhand display=" + display.toString());}
    	if(debug){logDebug("FHS Offhand SkullOwner=" + SkullOwner.toString());}
    	
    	NBTItem nbti2 = new NBTItem(mainHand);
    	Set<String> SkullKeys2 = nbti2.getKeys();
    	int damage2 = nbti2.getInteger("Damage");
    	NBTCompound display2 = nbti2.getCompound("display");
    	NBTCompound SkullOwner2 = nbti2.getCompound("SkullOwner");
    	if(debug){logDebug("FHS Mainhand display=" + display2.toString());}
    	if(debug){logDebug("FHS Mainhand SkullOwner=" + SkullOwner2.toString());}
    	
        if( display.equals(display2) && SkullOwner.equals(SkullOwner2) && damage != damage2){
        	ItemStack is = new ItemStack(offHand);
        	is.setAmount(mainHand.getAmount());
        	return is;
        }else if( display.equals(display2) && SkullOwner.equals(SkullOwner2) && damage == damage2){
        	return mainHand;
        }
        return null;
	}
    public ItemStack fixHeadNBT(String textureValue, String displayName, ArrayList<String> lore) {
    	//String textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY1MjQxNjZmN2NlODhhNTM3MTU4NzY2YTFjNTExZTMyMmE5M2E1ZTExZGJmMzBmYTZlODVlNzhkYTg2MWQ4In19fQ=="; // Pulled from the head link, scroll to the bottom and the "Other Value" field has this texture id.

    	ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1); // Creating the ItemStack, your input may vary.
    	NBTItem nbti = new NBTItem(head); // Creating the wrapper.

    	NBTCompound disp = nbti.addCompound("display");
    	disp.setString("Name", displayName); // Setting the name of the Item
    	if(lore.isEmpty()) {
    		if(getConfig().getBoolean("lore.show_plugin_name", true)){
				lore.add(ChatColor.AQUA + "MoreMobHeads");
			}
    	}
    	if(!lore.isEmpty()) {
	    	NBTList l = disp.getStringList("Lore");
	    	l.add(lore); // Adding a bit of lore.
    	}
    	
    	NBTCompound skull = nbti.addCompound("SkullOwner"); // Getting the compound, that way we can set the skin information
    	skull.setString("Name", displayName); // Owner's name
    	//skull.setString("Id", uuid);
    	// The UUID, note that skulls with the same UUID but different textures will misbehave and only one texture will load
    	// (They'll share it), if skulls have different UUIDs and same textures they won't stack. See UUID.randomUUID();

    	NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
    	texture.setString("Value",  textureValue);

    	head = nbti.getItem(); // Refresh the ItemStack
    	return head;
    }
    
    public ItemStack makeHead(String textureValue, String displayName, String uuid, @Nullable ArrayList<String> lore) {
    	ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1); // Creating the ItemStack, your input may vary.
    	NBTItem nbti = new NBTItem(head); // Creating the wrapper.

    	NBTCompound disp = nbti.addCompound("display");
    	disp.setString("Name", displayName); // Setting the name of the Item

    	if(lore.isEmpty()) {
    		if(getConfig().getBoolean("lore.show_plugin_name", true)){
				lore.add(ChatColor.AQUA + "MoreMobHeads");
			}
    	}
    	if(!lore.isEmpty()) {
	    	NBTList l = disp.getStringList("Lore");
	    	l.add(lore); // Adding a bit of lore.
    	}

    	NBTCompound skull = nbti.addCompound("SkullOwner"); // Getting the compound, that way we can set the skin information
    	skull.setString("Name", displayName); // Owner's name
    	skull.setString("Id", uuid);

    	NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
    	texture.setString("Value",  textureValue);

    	head = nbti.getItem(); // Refresh the ItemStack
    	return head;
    }
    
    public void configReload() {
    	oldconfig = new YamlConfiguration();
		log(Level.INFO, "Checking config file version...");
		try {
			oldconfig.load(new File(getDataFolder() + "" + File.separatorChar + "config.yml"));
		} catch (Exception e2) {
			logWarn("Could not load config.yml");
			stacktraceInfo();
			e2.printStackTrace();
		}
		String checkconfigversion = oldconfig.getString("version", "1.0.0");
		log(Level.INFO, "加载 config 文件...");
		try {
			getConfig().load(new File(getDataFolder(), "config.yml"));
		} catch (IOException | InvalidConfigurationException e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		try {
			config.load(new File(getDataFolder(), "config.yml"));
		} catch (IOException | InvalidConfigurationException e1) {
			logWarn("Could not load config.yml");
			stacktraceInfo();
			e1.printStackTrace();
		}
		
		world_whitelist = config.getString("world.whitelist", "");
		world_blacklist = config.getString("world.blacklist", "");
		mob_whitelist = config.getString("mob.whitelist", "");
		mob_blacklist = config.getString("mob.blacklist", "");
		colorful_console = getConfig().getBoolean("colorful_console", true);

			


		log(Level.INFO, "加载 chance_config 文件...");
		chanceFile = new File(getDataFolder() + "" + File.separatorChar + "chance_config.yml");
		try {
			chanceConfig.load(chanceFile);
		} catch (IOException | InvalidConfigurationException e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		//showkiller = getConfig().getBoolean("lore.show_killer", true);
		//showpluginname = getConfig().getBoolean("lore.show_plugin_name", true);
		debug = getConfig().getBoolean("debug", false);
		daLang = getConfig().getString("lang", "en_US");
    }
    
    public String getTextureFromEntity(LivingEntity entity) {
    	String name = entity.getName().toUpperCase();
    	switch (name) {
			case "CREEPER":
				Creeper creeper = (Creeper) entity;
				double cchance = chanceConfig.getDouble("chance_percent.creeper", defpercent);
				if(creeper.isPowered()) {
					name = "CREEPER_CHARGED";
					cchance = 1.00;
				}
				if(DropIt2(cchance)){
	 				return MobHeads.valueOf(name).getTexture().toString();
				}
				break;
			case "ZOMBIE":
				if(DropIt2(chanceConfig.getDouble("chance_percent.zombie", defpercent))){
 					return MobHeads.valueOf(name).getTexture().toString();
				}
				break;
			case "SKELETON":
				if(DropIt2(chanceConfig.getDouble("chance_percent.skeleton", defpercent))){
					return MobHeads.valueOf(name).getTexture().toString();
				}
				break;
			case "WITHER_SKELETON":
				if(DropIt2(chanceConfig.getDouble("chance_percent.wither_skeleton", defpercent))){
					return MobHeads.valueOf(name).getTexture().toString();
				}
				break;
			case "ENDER_DRAGON":
				if(DropIt2(chanceConfig.getDouble("chance_percent.ender_dragon", defpercent))){
					return MobHeads.valueOf(name).getTexture().toString();
				}
				break;
			/**case "TROPICAL_FISH":
				TropicalFish daFish = (TropicalFish) entity;
				DyeColor daFishBody = daFish.getBodyColor();
				DyeColor daFishPatternColor = daFish.getPatternColor();
				Pattern	daFishType = daFish.getPattern();
				log("bodycolor=" + daFishBody.toString() + "\nPatternColor=" + daFishPatternColor.toString() + "\nPattern=" + daFishType.toString());
				//TropicalFishHeads daFishEnum = TropicalFishHeads.getIfPresent(name);
				
				if(DropIt2(getConfig().getDouble(name + "_" +	daFishType, defpercent))){
					entity.getWorld().DropIt2emNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name + "_" +	daFishType).getTexture(), MobHeads.valueOf(name + "_" +	daFishType).getName(), entity.getKiller()));
				}
				if(debug){logDebug("Skeleton Head Dropped");}
				break;*/
			case "WITHER":
				//Wither wither = (Wither) event.getEntity();
				int random = randomBetween(1, 4);
				if(DropIt2(chanceConfig.getDouble("chance_percent.wither", defpercent))){
					return MobHeads.valueOf(name + "_" + random).getTexture().toString();
				}
				break;
			case "WOLF":
				Wolf wolf = (Wolf) entity;
				if(DropIt2(chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
 				if(wolf.isAngry()){
 					return MobHeads.valueOf(name + "_ANGRY").getTexture().toString();
 				}else{
 					return MobHeads.valueOf(name).getTexture().toString();
 				}
				}
				break;
			case "FOX":
				Fox dafox = (Fox) entity;
				String dafoxtype = dafox.getFoxType().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.fox." + dafoxtype.toString().toLowerCase(), defpercent))){
					return MobHeads.valueOf(name + "_" + dafoxtype).getTexture().toString();
				}
				
				break;
			case "CAT":
				Cat dacat = (Cat) entity;
				String dacattype = dacat.getCatType().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.cat." + dacattype.toLowerCase(), defpercent))){
					return CatHeads.valueOf(dacattype).getTexture().toString();
				}
				break;
			case "OCELOT":
				if(DropIt2(chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
					return MobHeads.valueOf(name).getTexture().toString();
				}
				
				break;
			case "BEE":
				Bee daBee = (Bee) entity;
				int daAnger = daBee.getAnger();
				boolean daNectar = daBee.hasNectar();
 				if(daAnger >= 1&&daNectar == true){
 					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.angry_pollinated", defpercent))){
 						return MobHeads.valueOf("BEE_ANGRY_POLLINATED").getTexture().toString();
 					}
 				}else if(daAnger >= 1&&daNectar == false){
 					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.angry", defpercent))){
 						return MobHeads.valueOf("BEE_ANGRY").getTexture().toString();
 					}
 				}else if(daAnger == 0&&daNectar == true){
 					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.pollinated", defpercent))){
 						return MobHeads.valueOf("BEE_POLLINATED").getTexture().toString();
 					}
 				}else if(daAnger == 0&&daNectar == false){
 					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.chance_percent", defpercent))){
 						return MobHeads.valueOf("BEE").getTexture().toString();
 					}
 				}
				break;
			case "LLAMA":
				Llama daLlama = (Llama) entity;
				String daLlamaColor = daLlama.getColor().toString();
				//log(name + "_" + daLlamaColor);
				if(DropIt2(chanceConfig.getDouble("chance_percent.llama." + daLlamaColor.toLowerCase(), defpercent))){		
					return LlamaHeads.valueOf(name + "_" + daLlamaColor).getTexture().toString();
				}
				break;
			case "HORSE":
				Horse daHorse = (Horse) entity;
				String daHorseColor = daHorse.getColor().toString();
						//.replace("g", "G").replace("wh", "Wh").replace("_", " ") + " Horse Head";
				if(DropIt2(chanceConfig.getDouble("chance_percent.horse." + daHorseColor.toLowerCase(), defpercent))){
					return HorseHeads.valueOf(name + "_" + daHorseColor).getTexture().toString();
				}
				break;
			case "MOOSHROOM":
				name = "MUSHROOM_COW";
			case "MUSHROOM_COW":
				MushroomCow daMushroom = (MushroomCow) entity;
				String daCowVariant = daMushroom.getVariant().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.mushroom_cow." + daCowVariant.toLowerCase(), defpercent))){
					return MobHeads.valueOf(name + "_" + daCowVariant).getTexture().toString();
				}
				break;
			case "PANDA":
				Panda daPanda = (Panda) entity;
				String daPandaGene = daPanda.getMainGene().toString();
				String daPandaName = daPandaGene.toLowerCase().replace("br", "Br").replace("ag", "Ag").replace("la", "La")
						.replace("no", "No").replace("p", "P").replace("we", "We").replace("wo", "Wo") + " Panda Head";
				if(daPandaGene.equalsIgnoreCase("normal")){daPandaName.replace("normal ", "");}
				if(DropIt2(chanceConfig.getDouble("chance_percent.panda." + daPandaGene.toLowerCase(), defpercent))){
					return MobHeads.valueOf(name + "_" + daPandaGene).getTexture().toString();
				}
				break;
			case "PARROT":
				Parrot daParrot = (Parrot) entity;
				String daParrotVariant = daParrot.getVariant().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.parrot." + daParrotVariant.toLowerCase(), defpercent))){
					return MobHeads.valueOf(name + "_" + daParrotVariant).getTexture().toString();
				}
				break;
			case "RABBIT":
				String daRabbitType;
				Rabbit daRabbit = (Rabbit) entity;
				daRabbitType = daRabbit.getRabbitType().toString();
				if(daRabbit.getCustomName() != null){
					if(daRabbit.getCustomName().contains("Toast")){
 					daRabbitType = "Toast";
 				}
				}
				if(DropIt2(chanceConfig.getDouble("chance_percent.rabbit." + daRabbitType.toLowerCase(), defpercent))){
					return RabbitHeads.valueOf(name + "_" + daRabbitType).getTexture().toString();
				}
				break;
			case "VILLAGER":
				Villager daVillager = (Villager) entity; // Location jobsite = daVillager.getMemory(MemoryKey.JOB_SITE);
				String daVillagerType = daVillager.getVillagerType().toString();
				String daVillagerProfession = daVillager.getProfession().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.villager." + daVillagerType.toLowerCase() + "." + daVillagerProfession.toLowerCase(), defpercent))){
					return VillagerHeads.valueOf(name + "_" + daVillagerProfession + "_" + daVillagerType).getTexture().toString();
				}
				break;
			case "ZOMBIE_VILLAGER":
				ZombieVillager daZombieVillager = (ZombieVillager) entity;
				String daZombieVillagerProfession = daZombieVillager.getVillagerProfession().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.zombie_villager", defpercent))){
					return ZombieVillagerHeads.valueOf(name + "_" + daZombieVillagerProfession).getTexture().toString();
				}
				break;
			case "SHEEP":
				Sheep daSheep = (Sheep) entity;
				String daSheepColor = daSheep.getColor().toString();
				
				if(daSheep.getCustomName() != null){
 				if(daSheep.getCustomName().contains("jeb_")){
 					daSheepColor = "jeb_";
 				}else{
 					daSheepColor = daSheep.getColor().toString();
 				}
				}
				if(DropIt2(chanceConfig.getDouble("chance_percent.sheep." + daSheepColor.toLowerCase(), defpercent))){
					return SheepHeads.valueOf(name + "_" + daSheepColor).getTexture().toString();
				}
				break;
			/**case "STRIDER":
				Strider strider = (Strider) entity;
				
				break;*/
			case "TRADER_LLAMA":
				TraderLlama daTraderLlama = (TraderLlama) entity;
				String daTraderLlamaColor = daTraderLlama.getColor().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.trader_llama." + daTraderLlamaColor.toLowerCase(), defpercent))){
					return LlamaHeads.valueOf(name + "_" + daTraderLlamaColor).getTexture().toString();
				}
				break;
			case "AXOLOTL":
				Axolotl daAxolotl = (Axolotl) entity;
				String daAxolotlVariant = daAxolotl.getVariant().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.axolotl." + daAxolotlVariant.toLowerCase(), defpercent))){
					return MobHeads117.valueOf(name + "_" + daAxolotlVariant).getTexture().toString();
				}
				break;
			case "GOAT":
				Goat daGoat = (Goat) entity;
				String daGoatVariant;
				if(daGoat.isScreaming()) {
					// Giving screaming goat head
					daGoatVariant = "SCREAMING";
				}else {
					// give goat head
					daGoatVariant = "NORMAL";
				}
				if(DropIt2(chanceConfig.getDouble("chance_percent.goat." + daGoatVariant.toLowerCase(), defpercent))){
					return MobHeads117.valueOf(name + "_" + daGoatVariant).getTexture().toString();
				}
				break;
			default:
				//makeSkull(MobHeads.valueOf(name).getTexture(), name);
				if(DropIt2(chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
					return MobHeads.valueOf(name).getTexture().toString();
				}
				break;
			}
		return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWI3YWY5ZTQ0MTEyMTdjN2RlOWM2MGFjYmQzYzNmZDY1MTk3ODMzMzJhMWIzYmM1NmZiZmNlOTA3MjFlZjM1In19fQ==";
    }
    
    public String getTexturefromEntityType(EntityType entityType, boolean randef) {
    	String name = entityType.toString();
    	switch (name) {
    	case "CREEPER":
			double cchance = chanceConfig.getDouble("chance_percent.creeper", defpercent);
			if(randef) {
				name = "CREEPER_CHARGED";
				cchance = 1.00;
			}
			if(DropIt2(cchance)){
 				return MobHeads.valueOf(name).getTexture().toString();
			}
			break;
		case "ZOMBIE":
			if(DropIt2(chanceConfig.getDouble("chance_percent.zombie", defpercent))){
					return MobHeads.valueOf(name).getTexture().toString();
			}
			break;
		case "SKELETON":
			if(DropIt2(chanceConfig.getDouble("chance_percent.skeleton", defpercent))){
				return MobHeads.valueOf(name).getTexture().toString();
			}
			break;
		case "WITHER_SKELETON":
			if(DropIt2(chanceConfig.getDouble("chance_percent.wither_skeleton", defpercent))){
				return MobHeads.valueOf(name).getTexture().toString();
			}
			break;
		case "ENDER_DRAGON":
			if(DropIt2(chanceConfig.getDouble("chance_percent.ender_dragon", defpercent))){
				return MobHeads.valueOf(name).getTexture().toString();
			}
			break;//*/
		/**case "TROPICAL_FISH":
			TropicalFish daFish = (TropicalFish) entity;
			DyeColor daFishBody = daFish.getBodyColor();
			DyeColor daFishPatternColor = daFish.getPatternColor();
			Pattern	daFishType = daFish.getPattern();
			log("bodycolor=" + daFishBody.toString() + "\nPatternColor=" + daFishPatternColor.toString() + "\nPattern=" + daFishType.toString());
			//TropicalFishHeads daFishEnum = TropicalFishHeads.getIfPresent(name);
			
			if(DropIt2(getConfig().getDouble(name + "_" +	daFishType, defpercent))){
				entity.getWorld().DropIt2emNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name + "_" +	daFishType).getTexture(), MobHeads.valueOf(name + "_" +	daFishType).getName(), entity.getKiller()));
			}
			if(debug){logDebug("Skeleton Head Dropped");}
			break;*/
		case "WITHER":
			//Wither wither = (Wither) event.getEntity();
			int random = randomBetween(1, 4);
			if(randef) {
				name = name.concat("_" + random);
			}else {
				name = name.concat("_1");
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.wither", defpercent))){
				return MobHeads.valueOf(name).getTexture().toString();
			}
			break;
		case "WOLF":
			String[] dawolftype = {"","_ANGRY"};
			int randomwolf;
			if(randef) {
				randomwolf = randomBetween(0, 1);
			}else {
				randomwolf = 0;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
				return MobHeads.valueOf(name + dawolftype[randomwolf]).getTexture().toString();
			}
			break;
		case "FOX":
			String[] dafoxtype = {"RED","SNOW"};
			int randomfox;
			if(randef) {
				randomfox = randomBetween(0, 1);
			}else {
				randomfox = 0;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.fox." + dafoxtype[randomfox].toString().toLowerCase(), defpercent))){
				return MobHeads.valueOf(name + "_" + dafoxtype[randomfox]).getTexture().toString();
			}
			
			break;
		case "CAT":
			int randomcat;
			//String dacattype = dacat.getCatType().toString();
			String[] dacattype = {"ALL_BLACK","BLACK","BRITISH_SHORTHAIR","CALICO","JELLIE","PERSIAN","RAGDOLL","RED","SIAMESE","TABBY","WHITE"};
			if(randef) {
				randomcat = randomBetween(0, 10);
			}else {
				randomcat = 10;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.cat." + dacattype[randomcat].toLowerCase(), defpercent))){
				return CatHeads.valueOf(dacattype[randomcat]).getTexture().toString();
			}
			break;
		case "OCELOT":
			if(DropIt2(chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
				return MobHeads.valueOf(name).getTexture().toString();
			}
			
			break;
		case "BEE":
			int randombee;
			if(randef) {
				randombee = randomBetween(1, 4);
			}else {
				randombee = 1;
			}
				if(randombee == 4){
					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.angry_pollinated", defpercent))){
						return MobHeads.valueOf("BEE_ANGRY_POLLINATED").getTexture().toString();
					}
				}else if(randombee == 3){
					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.angry", defpercent))){
						return MobHeads.valueOf("BEE_ANGRY").getTexture().toString();
					}
				}else if(randombee == 2){
					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.pollinated", defpercent))){
						return MobHeads.valueOf("BEE_POLLINATED").getTexture().toString();
					}
				}else if(randombee == 1){
					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.chance_percent", defpercent))){
						return MobHeads.valueOf("BEE").getTexture().toString();
					}
				}
			break;
		case "LLAMA":
			String[] daLlamaColor = {"BROWN","CREAMY","GRAY","WHITE"};
			int randomllama;
			if(randef) {
				randomllama = randomBetween(0, 3);
			}else {
				randomllama = 4;
			}
			//log(name + "_" + daLlamaColor);
			if(DropIt2(chanceConfig.getDouble("chance_percent.llama." + daLlamaColor[randomllama].toLowerCase(), defpercent))){		
				return LlamaHeads.valueOf(name + "_" + daLlamaColor[randomllama]).getTexture().toString();
			}
			break;
		case "TRADER_LLAMA":
			String[] daTraderLlamaColor = {"BROWN","CREAMY","GRAY","WHITE"};
			int randomtllama;
			if(randef) {
				randomtllama = randomBetween(0, 3);
			}else {
				randomtllama = 4;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.trader_llama." + daTraderLlamaColor[randomtllama].toLowerCase(), defpercent))){
				return LlamaHeads.valueOf(name + "_" + daTraderLlamaColor[randomtllama]).getTexture().toString();
			}
			break;
		case "HORSE":
			String[] daHorseColor = {"BLACK","BROWN","CHESTNUT","CREAMY","DARK_BROWN","GRAY","WHITE"};
			int randomhorse;
			if(randef) {
				randomhorse = randomBetween(0, 6);
			}else {
				randomhorse = 1;
			}
					//.replace("g", "G").replace("wh", "Wh").replace("_", " ") + " Horse Head";
			if(DropIt2(chanceConfig.getDouble("chance_percent.horse." + daHorseColor[randomhorse].toLowerCase(), defpercent))){
				return HorseHeads.valueOf(name + "_" + daHorseColor).getTexture().toString();
			}
			break;
		case "MOOSHROOM":
			 	name = "MUSHROOM_COW";
		case "MUSHROOM_COW":
			String[] daCowVariant = {"BROWN","RED"};
			int randomcow;
			if(randef) {
				randomcow = randomBetween(0, 6);
			}else {
				randomcow = 1;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.mushroom_cow." + daCowVariant[randomcow].toLowerCase(), defpercent))){
				return MobHeads.valueOf(name + "_" + daCowVariant[randomcow]).getTexture().toString();
			}
			break;
		case "PANDA":
			String[] daPandaGene = {"AGRESSIVE","BROWN","LAZY","NORMAL","PLAYFUL","WEAK","WORRIED"};
			int randompan;
			if(randef) {
				randompan = randomBetween(0, 6);
			}else {
				randompan = 3;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.panda." + daPandaGene[randompan].toLowerCase(), defpercent))){
				return MobHeads.valueOf(name + "_" + daPandaGene[randompan]).getTexture().toString();
			}
			break;
		case "PARROT":
			String[] daParrotVariant = {"BLUE","CYAN","GRAY","GREEN","RED"};
			int randompar;
			if(randef) {
				randompar = randomBetween(0, 4);
			}else {
				randompar = 3;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.parrot." + daParrotVariant[randompar].toLowerCase(), defpercent))){
				return MobHeads.valueOf(name + "_" + daParrotVariant[randompar]).getTexture().toString();
			}
			break;
		case "RABBIT":
			String[] daRabbitType = {"BLACK","BLACK_AND_WHITE","BROWN","GOLD","SALT_AND_PEPPER","THE_KILLER_BUNNY","WHITE","Toast"};
			int randomrab;
			if(randef) {
				randomrab = randomBetween(0, 7);
			}else {
				randomrab = 3;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.rabbit." + daRabbitType[randomrab].toLowerCase(), defpercent))){
				return RabbitHeads.valueOf(name + "_" + daRabbitType[randomrab]).getTexture().toString();
			}
			break;
		case "VILLAGER":
			String[] daVillagerType = {"DESERT","JUNGLE","PLAINS","SAVANNA","SNOW","SWAMP","TAIGA"};
			String[] daVillagerProfession = {"ARMORER","BUTCHER","CARTOGRAPHER","CLERIC","FARMER","FISHERMAN","FLETCHER"
					,"LEATHERWORKER","LIBRARIAN","MASON","NITWIT","NONE","SHEPHERD","TOOLSMITH","WEAPONSMITH"};
			int randomviltyp;
			int randomvilpro;
			if(randef) {
				randomviltyp = randomBetween(0, 6);
				randomvilpro = randomBetween(0, 14);
			}else {
				randomviltyp = 2;
				randomvilpro = 11;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.villager." + daVillagerType[randomviltyp].toLowerCase() + "." + 
					daVillagerProfession[randomvilpro].toLowerCase(), defpercent))){
				return VillagerHeads.valueOf(name + "_" + daVillagerProfession[randomvilpro] + "_" + daVillagerType[randomviltyp]).getTexture().toString();
			}
			break;
		case "ZOMBIE_VILLAGER":
			String[] daZombieVillagerProfession = {"ARMORER","BUTCHER","CARTOGRAPHER","CLERIC","FARMER","FISHERMAN","FLETCHER"
					,"LEATHERWORKER","LIBRARIAN","MASON","NITWIT","NONE","SHEPHERD","TOOLSMITH","WEAPONSMITH"};
			int randomzvpro;
			if(randef) {
				randomzvpro = randomBetween(0, 14);
			}else {
				randomzvpro = 11;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.zombie_villager", defpercent))){
				return ZombieVillagerHeads.valueOf(name + "_" + daZombieVillagerProfession[randomzvpro]).getTexture().toString();
			}
			break;
		case "SHEEP":
			String[] daSheepColor = {"BLACK","BLUE","BROWN","CYAN","GRAY","GREEN","jeb_","LIGHT_BLUE","LIGHT_GRAY","LIME","MAGENTA","ORANGE"
					,"PINK","PURPLE","RED","WHITE","YELLOW"};
			int randomshe;
			if(randef) {
				randomshe = randomBetween(0, 16);
			}else {
				randomshe = 6;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.sheep." + daSheepColor[randomshe].toLowerCase(), defpercent))){
				return SheepHeads.valueOf(name + "_" + daSheepColor[randomshe]).getTexture().toString();
			}
			break;//*/
		/**case "STRIDER":
			Strider strider = (Strider) entity;
			
			break;*/
		case "AXOLOTL":
			String[] daAxolotlVariant = {"BLUE","CYAN","GOLD","LUCY","WILD"};
			int randomaxl;
			if(randef) {
				randomaxl = randomBetween(0, 4);
			}else {
				randomaxl = 3;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.axolotl." + daAxolotlVariant[randomaxl].toLowerCase(), defpercent))){
				return MobHeads117.valueOf(name + "_" + daAxolotlVariant[randomaxl]).getTexture().toString();
			}
			break;
		case "GOAT":
			String[] daGoatVariant = {"NORMAL","SCREAMING"};
			int randomgoat;
			if(randef) {
				randomgoat = randomBetween(0, 1);
			}else {
				randomgoat = 0;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.goat." + daGoatVariant[randomgoat].toLowerCase(), defpercent))){
				return MobHeads117.valueOf(name + "_" + daGoatVariant[randomgoat]).getTexture().toString();
			}
			break;
		default:
			//makeSkull(MobHeads.valueOf(name).getTexture(), name);
			if(DropIt2(chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
				return MobHeads.valueOf(name).getTexture().toString();
			}
			break;
    	
    	}
    	return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWI3YWY5ZTQ0MTEyMTdjN2RlOWM2MGFjYmQzYzNmZDY1MTk3ODMzMzJhMWIzYmM1NmZiZmNlOTA3MjFlZjM1In19fQ==";
    }//*/
    
}
