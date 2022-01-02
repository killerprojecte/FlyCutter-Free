package flyproject.flycutterfree.handlers;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

import javax.annotation.Nonnull;

import flyproject.flycutterfree.FlyCutter;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Creeper;
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
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import flyproject.flycutterfree.util.CatHeads;
import flyproject.flycutterfree.util.HorseHeads;
import flyproject.flycutterfree.util.LlamaHeads;
import flyproject.flycutterfree.util.MobHeads;
import flyproject.flycutterfree.util.MobHeads117;
import flyproject.flycutterfree.util.RabbitHeads;
import flyproject.flycutterfree.util.SheepHeads;
import flyproject.flycutterfree.util.StrUtils;
import flyproject.flycutterfree.util.VillagerHeads;
import flyproject.flycutterfree.util.YmlConfiguration;
import flyproject.flycutterfree.util.ZombieVillagerHeads;

import de.tr7zw.changeme.nbtapi.NBTItem;

/**
1.8		1_8_R1		1.8.3	1_8_R2
1.8.8 	1_8_R3
1.9		1_9_R1		1.9.4	1_9_R2	
1.10	1_10_R1
1.11	1_11_R1
1.12	1_12_R1
1.13	1_13_R1		1.13.1	1_13_R2
1.14	1_14_R1
1.15	1_15_R1
1.16.1	1_16_R1		1.16.2	1_16_R2
1.17	1_17_R1
*/

public class EventHandler_1_17_R1 implements CommandExecutor, TabCompleter , Listener { 
	/** Variables */
	FlyCutter mmh;
	double defpercent = 0.013;
	String world_whitelist;
	String world_blacklist;
	String mob_whitelist;
	String mob_blacklist;
	boolean debug;
	YmlConfiguration chanceConfig;
	List<MerchantRecipe> playerhead_recipes = new ArrayList<MerchantRecipe>();
	int BHNum, BHNum2, BHNum3;
	
	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	public EventHandler_1_17_R1(final FlyCutter plugin){
		/** Set variables */
		mmh = plugin;
		mmh.getCommand("flycutter").setExecutor(this);
		world_whitelist = plugin.world_whitelist;
		world_blacklist = plugin.mob_blacklist;
		mob_whitelist = plugin.mob_whitelist;
		mob_blacklist = plugin.mob_blacklist;
		debug = plugin.debug;
		chanceConfig = plugin.chanceConfig;
	}
	
	/** Events go here */
	@SuppressWarnings({ "deprecation", "unchecked", "unused", "rawtypes" })
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDeathEvent(EntityDeathEvent event){// TODO: EnityDeathEvent
		LivingEntity entity = event.getEntity();
		World world = event.getEntity().getWorld();
		List<ItemStack> Drops = event.getDrops();
		
		if(world_whitelist != null&&!world_whitelist.isEmpty()&&world_blacklist != null&&!world_blacklist.isEmpty()){
			if(!StrUtils.stringContains(world_whitelist, world.getName().toString())&&StrUtils.stringContains(world_blacklist, world.getName().toString())){
				if(debug){mmh.logDebug("EDE - World - On blacklist and Not on whitelist.");}
				return;
			}else if(!StrUtils.stringContains(world_whitelist, world.getName().toString())&&!StrUtils.stringContains(world_blacklist, world.getName().toString())){
				if(debug){mmh.logDebug("EDE - World - Not on whitelist.");}
				return;
			}else if(!StrUtils.stringContains(world_whitelist, world.getName().toString())){
				
			}
		}else if(world_whitelist != null&&!world_whitelist.isEmpty()){
			if(!StrUtils.stringContains(world_whitelist, world.getName().toString())){
				if(debug){mmh.logDebug("EDE - World - Not on whitelist.");}
				return;
			}
		}else if(world_blacklist != null&&!world_blacklist.isEmpty()){
			if(StrUtils.stringContains(world_blacklist, world.getName().toString())){
				if(debug){mmh.logDebug("EDE - World - On blacklist.");}
				return;
			}
		}
			if(entity instanceof Player){
				if(debug){mmh.logDebug("EDE Entity is Player line:877");}

				if(entity.getKiller() instanceof Player){
					if(entity.getKiller().hasPermission("flycutter.players")){
						if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.player", 0.50))){
							//Player daKiller = entity.getKiller();
							if(debug){mmh.logDebug("EDE Killer is Player line:1073");}
							ItemStack helmet = new ItemStack(Material.PLAYER_HEAD, 1, (short)3);
							SkullMeta meta = (SkullMeta)helmet.getItemMeta();
							meta.setOwningPlayer(Bukkit.getServer().getOfflinePlayer(((Player) entity).getUniqueId()));
							meta.setDisplayName(mmh.getConfig().getString("headname.prefix") + ((Player) entity).getDisplayName() + mmh.getConfig().getString("headname.suffix"));
							ArrayList<String> lore = new ArrayList();
							if(mmh.getConfig().getBoolean("lore.show_killer", true)){
								List<String> var2 = mmh.getConfig().getStringList("headlore");
								List<String> lorelist = new ArrayList();
								for (String lorestr : var2){
									String str;
									str = lorestr.replace("%player%",event.getEntity().getKiller().getDisplayName());
									lorelist.add(str);
								}
								lore.addAll(lorelist);
							}
							if(mmh.getConfig().getBoolean("super.show_time",true)){
								Date date = new Date();
								String strDateFormat = mmh.getConfig().getString("super.time_format");
								SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
								lore.add(mmh.getConfig().getString("super.time_color").replace("&","§") + sdf.format(date));
							}
							lore.add("§0Powered by FlyCutter");
							meta.setLore(lore);
								helmet.setItemMeta(meta);//																	 e2d4c388-42d5-4a96-b4c9-623df7f5e026
							helmet.setItemMeta(meta);
						
							//entity.getWorld().dropItemNaturally(entity.getLocation(), helmet);
							Drops.add(helmet);
							if(debug){mmh.logDebug("EDE " + ((Player) entity).getDisplayName().toString() + " Player Head Dropped");}
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
				if(entity.getKiller() instanceof Player){
					String name = event.getEntityType().toString().replace(" ", "_");
					if(debug){mmh.logDebug("EDE name=" + name);}
					//ItemStack itemstack = event.getEntity().getKiller().getInventory().getItemInMainHand();
					//if(itemstack != null){
						/**if(debug){mmh.logDebug("itemstack=" + itemstack.getType().toString() + " line:159");}
						int enchantmentlevel = itemstack.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);//.containsEnchantment(Enchantment.LOOT_BONUS_MOBS);
						if(debug){mmh.logDebug("enchantmentlevel=" + enchantmentlevel + " line:161");}
						double enchantmentlevelpercent = ((double)enchantmentlevel / 100);
						if(debug){mmh.logDebug("enchantmentlevelpercent=" + enchantmentlevelpercent + " line:163");}
						double chance = Math.random();
						if(debug){mmh.logDebug("chance=" + chance + " line:165");}
						
						if(debug){mmh.logDebug("chancepercent=" + chancepercent + " line:167");}
						chancepercent = chancepercent + enchantmentlevelpercent;
						if(debug){mmh.logDebug("chancepercent2=" + chancepercent + " line:169");}*/
						//if(chancepercent > 0.00 && chancepercent < 0.99){
								//if (chancepercent > chance){
									//event.getDrops().add(new ItemStack(Material.CREEPER_HEAD, 1));
							//@Nonnull Set<String> isSpawner;
							String isNametag = null;
							@Nonnull
							PersistentDataContainer pdc = entity.getPersistentDataContainer();
							isNametag = entity.getPersistentDataContainer().get(mmh.NAMETAG_KEY, PersistentDataType.STRING);//.getScoreboardTags();//
							if(debug&&isNametag != null){mmh.logDebug("EDE isNametag=" + isNametag.toString());}

							if(entity.getKiller().hasPermission("flycutter.mobs")){
								if(entity.getKiller().hasPermission("flycutter.nametag")&&isNametag != null){
									if(entity.getCustomName() != null&&!(entity.getCustomName().contains("jeb_"))
												&&!(entity.getCustomName().contains("Toast"))){
											if(debug){mmh.logDebug("EDE customname=" + entity.getCustomName().toString());}
											if(entity instanceof Skeleton||entity instanceof Zombie||entity instanceof PigZombie){
												if(mmh.getServer().getPluginManager().getPlugin("SilenceMobs") != null){
													if(entity.getCustomName().toLowerCase().contains("silenceme")||entity.getCustomName().toLowerCase().contains("silence me")){
													return;
												}
												}
												boolean enforcewhitelist = mmh.getConfig().getBoolean("whitelist.enforce", false);
												boolean enforceblacklist = mmh.getConfig().getBoolean("blacklist.enforce", false);
												boolean onwhitelist = mmh.getConfig().getString("whitelist.player_head_whitelist", "").toLowerCase().contains(entity.getCustomName().toLowerCase());
												boolean onblacklist = mmh.getConfig().getString("blacklist.player_head_blacklist", "").toLowerCase().contains(entity.getCustomName().toLowerCase());
												if(mmh.DropIt(event, chanceConfig.getDouble("named_mob", 0.10))){
													if(enforcewhitelist&&enforceblacklist){
														if(onwhitelist&&!(onblacklist)){
															Drops.add(mmh.dropMobHead(entity, entity.getCustomName(), entity.getKiller()));
														if(debug){mmh.logDebug("EDE " + entity.getCustomName().toString() + " Head Dropped");}
														}
													}else if(enforcewhitelist&&!enforceblacklist){
														if(onwhitelist){
															Drops.add(mmh.dropMobHead(entity, entity.getCustomName(), entity.getKiller()));
														if(debug){mmh.logDebug("EDE " +entity.getCustomName().toString() + " Head Dropped");}
														}
													}else if(!enforcewhitelist&&enforceblacklist){
														if(!onblacklist){
															Drops.add(mmh.dropMobHead(entity, entity.getCustomName(), entity.getKiller()));
														if(debug){mmh.logDebug("EDE " +entity.getCustomName().toString() + " Head Dropped");}
														}
													}else{
														Drops.add(mmh.dropMobHead(entity, entity.getCustomName(), entity.getKiller()));
													if(debug){mmh.logDebug("EDE " +entity.getCustomName().toString() + " Head Dropped");}
													}
												}
											}
										return;
									}
								}
					 			//String name = event.getEntity().getName().toUpperCase().replace(" ", "_");

					 			if(mob_whitelist != null&&!mob_whitelist.isEmpty()&&mob_blacklist != null&&!mob_blacklist.isEmpty()){
					 				if(!StrUtils.stringContains(mob_whitelist.toUpperCase(), name)){//mob_whitelist.contains(name)
					 					mmh.log(Level.INFO, "EDE - Mob - Not on whitelist. 1 Mob=" + name);
					 					return;
					 				}
					 			}else if(mob_whitelist != null&&!mob_whitelist.isEmpty()){
					 				if(!StrUtils.stringContains(mob_whitelist.toUpperCase(), name)&&StrUtils.stringContains(mob_blacklist, name)){//mob_whitelist.contains(name)
					 					mmh.log(Level.INFO, "EDE - Mob - Not on whitelist. 2 Mob=" + name);
					 					return;
					 				}
					 			}else if(mob_blacklist != null&&!mob_blacklist.isEmpty()){
					 				if(StrUtils.stringContains(mob_blacklist.toUpperCase(), name)){
					 					mmh.log(Level.INFO, "EDE - Mob - On blacklist. Mob=" + name);
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
					 				if(mmh.DropIt(event, cchance)){
						 				if(mmh.getConfig().getBoolean("vanilla_heads.creeper", false)&&name != "CREEPER_CHARGED"){
						 					Drops.add( new ItemStack(Material.CREEPER_HEAD));
						 				}else{ // mmh.langName
						 					Drops.add( mmh.makeSkull(MobHeads.valueOf(name).getTexture().toString(),
						 							mmh.langName.getString(name.toLowerCase(), MobHeads.valueOf(name).getName() + " Head"), entity.getKiller()));
						 				} // MobHeads.valueOf(name).getName() + " Head"
						 				if(debug){mmh.logDebug("EDE Creeper vanilla=" + mmh.getConfig().getBoolean("vanilla_heads.creeper", false));}
						 				if(debug){mmh.logDebug("EDE Creeper Head Dropped");}
					 				}
					 				break;
					 			case "ZOMBIE":
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.zombie", defpercent))){
					 					if(mmh.getConfig().getBoolean("vanilla_heads.zombie", false)){
					 						Drops.add(new ItemStack(Material.ZOMBIE_HEAD));
						 				}else{
						 					
						 					Drops.add(mmh.makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
						 							mmh.langName.getString(name.toLowerCase(), MobHeads.valueOf(name).getName() + " Head"), entity.getKiller()));
						 					/**entity.getWorld().dropItemNaturally(entity.getLocation(), mmh.makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
						 							mmh.langName.getString(name.toLowerCase(), MobHeads.valueOf(name).getName() + " Head"), entity.getKiller()));//*/
						 				}
					 					if(debug){mmh.logDebug("EDE Zombie vanilla=" + mmh.getConfig().getBoolean("vanilla_heads.zombie", false));}
					 					if(debug){mmh.logDebug("EDE Zombie Head Dropped");}
					 				}
					 				break;
					 			case "SKELETON":
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.skeleton", defpercent))){
					 					if(mmh.getConfig().getBoolean("vanilla_heads.skeleton", false)){
					 						Drops.add( new ItemStack(Material.SKELETON_SKULL));
						 				}else{
						 					Drops.add( mmh.makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
						 							mmh.langName.getString(name.toLowerCase(), MobHeads.valueOf(name).getName() + " Head"), entity.getKiller()));
						 				}
					 					if(debug){mmh.logDebug("EDE Skeleton vanilla=" + mmh.getConfig().getBoolean("vanilla_heads.skeleton", false));}
					 					if(debug){mmh.logDebug("EDE Skeleton Head Dropped");}
					 				}
					 				break;
					 			case "WITHER_SKELETON":
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.wither_skeleton", defpercent))){
					 					if(mmh.getConfig().getBoolean("vanilla_heads.wither_skeleton", false)){
					 						Drops.add( new ItemStack(Material.WITHER_SKELETON_SKULL));
						 				}else{
						 					Drops.add( mmh.makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
						 							mmh.langName.getString(name.toLowerCase(), MobHeads.valueOf(name).getName() + " Head"), entity.getKiller()));
						 				}
					 					if(debug){mmh.logDebug("EDE Wither Skeleton Head Dropped");}
					 				}
					 				break;
					 			case "ENDER_DRAGON":
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.ender_dragon", defpercent))){
					 					if(mmh.getConfig().getBoolean("vanilla_heads.ender_dragon", false)){
					 						Drops.add( new ItemStack(Material.DRAGON_HEAD));
						 				}else{
						 					Drops.add( mmh.makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
						 							mmh.langName.getString(name.toLowerCase(), MobHeads.valueOf(name).getName() + " Head"), entity.getKiller()));
						 				}
					 					if(debug){mmh.logDebug("EDE Ender Dragon Head Dropped");}
					 				}
					 				break;
					 			/**case "TROPICAL_FISH":
					 				TropicalFish daFish = (TropicalFish) entity;
					 				DyeColor daFishBody = daFish.getBodyColor();
					 				DyeColor daFishPatternColor  = daFish.getPatternColor();
					 				Pattern	daFishType = daFish.getPattern();
					 				log("bodycolor=" + daFishBody.toString() + "\nPatternColor=" + daFishPatternColor.toString() + "\nPattern=" + daFishType.toString());
					 				//TropicalFishHeads daFishEnum = TropicalFishHeads.getIfPresent(name);
					 				
					 				if(mmh.DropIt(event, mmh.getConfig().getDouble(name + "_" +	daFishType, defpercent))){
					 					entity.getWorld().dropItemNaturally(entity.getLocation(), mmh.makeSkull(MobHeads.valueOf(name + "_" +	daFishType).getTexture(), MobHeads.valueOf(name + "_" +	daFishType).getName(), entity.getKiller()));
					 				}
					 				if(debug){mmh.logDebug("Skeleton Head Dropped");}
					 				break;*/
					 			case "WITHER":
									//Wither wither = (Wither) event.getEntity();
									int random = mmh.randomBetween(1, 4);
									if(debug){mmh.logDebug("EDE Wither random=" + random + "");}
									if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.wither", defpercent))){
										Drops.add( mmh.makeSkull(MobHeads.valueOf(name + "_" + random).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase() + "." + random, MobHeads.valueOf(name + "_" + random).getName() + " Head"), entity.getKiller()));
										if(debug){mmh.logDebug("EDE Wither_" + random + " Head Dropped");}
									}
									break;
					 			case "WOLF":
					 				Wolf wolf = (Wolf) event.getEntity();
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
						 				if(wolf.isAngry()){
						 					Drops.add( mmh.makeSkull(MobHeads.valueOf(name + "_ANGRY").getTexture().toString(), 
						 							mmh.langName.getString(name.toLowerCase() + "_angry", MobHeads.valueOf(name + "_ANGRY").getName() + " Head"), entity.getKiller()));
						 					if(debug){mmh.logDebug("EDE Angry Wolf Head Dropped");}
						 				}else{
						 					Drops.add( mmh.makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
						 							mmh.langName.getString(name.toLowerCase(), event.getEntity().getName() + " Head"), entity.getKiller()));
						 					if(debug){mmh.logDebug("EDE Wolf Head Dropped");}
						 				}
					 				}
					 				break;
					 			case "FOX":
					 				Fox dafox = (Fox) entity;
					 				String dafoxtype = dafox.getFoxType().toString();
					 				if(debug){mmh.logDebug("EDE dafoxtype=" + dafoxtype);}
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.fox." + dafoxtype.toString().toLowerCase(), defpercent))){
					 					Drops.add( mmh.makeSkull(MobHeads.valueOf(name + "_" + dafoxtype).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase() + "." + dafoxtype.toLowerCase(), MobHeads.valueOf(name + "_" + dafoxtype).getName() + " Head"), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE Fox Head Dropped");}
					 				}
					 				
					 				break;
					 			case "CAT":
					 				Cat dacat = (Cat) entity;
					 				String dacattype = dacat.getCatType().toString();
					 				if(debug){mmh.logDebug("entity cat=" + dacat.getCatType());}
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.cat." + dacattype.toLowerCase(), defpercent))){
					 					Drops.add(mmh.makeSkull(CatHeads.valueOf(dacattype).getTexture().toString(),
				 									mmh.langName.getString(name.toLowerCase() + "." + dacattype.toLowerCase(), CatHeads.valueOf(dacattype).getName() + " Head"), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE Cat Head Dropped");}
					 				}
					 				break;
					 			case "OCELOT":
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
					 					Drops.add( mmh.makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
					 							mmh.langName.getString(MobHeads.valueOf(name).getNameString(), MobHeads.valueOf(name).getName() + " Head"), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE " + name + " Head Dropped");}
					 				}
					 				if(debug){mmh.logDebug("EDE " + MobHeads.valueOf(name) + " killed");}
					 				
					 				break;
					 			case "BEE":
					 				Bee daBee = (Bee) entity;
					 				int daAnger = daBee.getAnger();
					 				if(debug){mmh.logDebug("EDE daAnger=" + daAnger);}
					 				boolean daNectar = daBee.hasNectar();
					 				if(debug){mmh.logDebug("EDE daNectar=" + daNectar);}
						 				if(daAnger >= 1&&daNectar == true){
						 					if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.bee.angry_pollinated", defpercent))){
						 						Drops.add( mmh.makeSkull(MobHeads.valueOf("BEE_ANGRY_POLLINATED").getTexture().toString(), 
						 								mmh.langName.getString(name.toLowerCase() + ".angry_pollinated", "Angry Pollinated Bee Head"), entity.getKiller()));
						 						if(debug){mmh.logDebug("EDE Angry Pollinated Bee Head Dropped");}
						 					}
						 				}else if(daAnger >= 1&&daNectar == false){
						 					if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.bee.angry", defpercent))){
						 						Drops.add( mmh.makeSkull(MobHeads.valueOf("BEE_ANGRY").getTexture().toString(), 
						 								mmh.langName.getString(name.toLowerCase() + ".angry", "Angry Bee Head"), entity.getKiller()));
						 						if(debug){mmh.logDebug("EDE Angry Bee Head Dropped");}
						 					}
						 				}else if(daAnger == 0&&daNectar == true){
						 					if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.bee.pollinated", defpercent))){
						 						Drops.add( mmh.makeSkull(MobHeads.valueOf("BEE_POLLINATED").getTexture().toString(), 
						 								mmh.langName.getString(name.toLowerCase() + ".pollinated", "Pollinated Bee Head"), entity.getKiller()));
						 						if(debug){mmh.logDebug("EDE Pollinated Bee Head Dropped");}
						 					}
						 				}else if(daAnger == 0&&daNectar == false){
						 					if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.bee.chance_percent", defpercent))){
						 						Drops.add( mmh.makeSkull(MobHeads.valueOf("BEE").getTexture().toString(), 
						 								mmh.langName.getString(name.toLowerCase() + ".none", "Bee Head"), entity.getKiller()));
						 						if(debug){mmh.logDebug("EDE Bee Head Dropped");}
						 					}
						 				}
					 				break;
					 			case "LLAMA":
					 				Llama daLlama = (Llama) entity;
					 				String daLlamaColor = daLlama.getColor().toString();
					 				String daLlamaName = LlamaHeads.valueOf(name + "_" + daLlamaColor).getName() + " Head";//daLlamaColor.toLowerCase().replace("b", "B").replace("c", "C").replace("g", "G").replace("wh", "Wh") + " Llama Head";
					 				//log(name + "_" + daLlamaColor);
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.llama." + daLlamaColor.toLowerCase(), defpercent))){		
					 					Drops.add( mmh.makeSkull(LlamaHeads.valueOf(name + "_" + daLlamaColor).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase() + "." + daLlamaColor.toLowerCase(), daLlamaName), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE Llama Head Dropped");}
					 				}
					 				break;
					 			case "HORSE":
					 				Horse daHorse = (Horse) entity;
					 				String daHorseColor = daHorse.getColor().toString();
					 				String daHorseName = HorseHeads.valueOf(name + "_" + daHorseColor).getName() + " Head";//daHorseColor.toLowerCase().replace("b", "B").replace("ch", "Ch").replace("cr", "Cr").replace("d", "D")
					 						//.replace("g", "G").replace("wh", "Wh").replace("_", " ") + " Horse Head";
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.horse." + daHorseColor.toLowerCase(), defpercent))){
					 					Drops.add( mmh.makeSkull(HorseHeads.valueOf(name + "_" + daHorseColor).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase() + "." + daHorseColor.toLowerCase(), daHorseName), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE Horse Head Dropped");}
					 				}
					 				break;
					 			case "MOOSHROOM":
					 				name = "MUSHROOM_COW";
					 			case "MUSHROOM_COW":
					 				MushroomCow daMushroom = (MushroomCow) entity;
					 				String daCowVariant = daMushroom.getVariant().toString();
					 				String daCowName = daCowVariant.toLowerCase().replace("br", "Br").replace("re", "Re") + " Mooshroom Head";
					 				if(debug){mmh.logDebug("EDE " + name + "_" + daCowVariant);}
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.mushroom_cow." + daCowVariant.toLowerCase(), defpercent))){
					 					Drops.add( mmh.makeSkull(MobHeads.valueOf(name + "_" + daCowVariant).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase() + "." + daCowVariant.toLowerCase(), daCowName), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE Mooshroom Head Dropped");}
					 				}
					 				break;
					 			case "PANDA":
					 				Panda daPanda = (Panda) entity;
					 				String daPandaGene = daPanda.getMainGene().toString();
					 				String daPandaName = daPandaGene.toLowerCase().replace("br", "Br").replace("ag", "Ag").replace("la", "La")
					 						.replace("no", "No").replace("p", "P").replace("we", "We").replace("wo", "Wo") + " Panda Head";
					 				if(daPandaGene.equalsIgnoreCase("normal")){daPandaName.replace("normal ", "");}
					 				if(debug){mmh.logDebug("EDE " + name + "_" + daPandaGene);}
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.panda." + daPandaGene.toLowerCase(), defpercent))){
					 					Drops.add( mmh.makeSkull(MobHeads.valueOf(name + "_" + daPandaGene).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase() + "." + daPandaGene.toLowerCase(), daPandaName), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE Panda Head Dropped");}
					 				}
					 				break;
					 			case "PARROT":
					 				Parrot daParrot = (Parrot) entity;
					 				String daParrotVariant = daParrot.getVariant().toString();
					 				String daParrotName = daParrotVariant.toLowerCase().replace("b", "B").replace("c", "C").replace("g", "G")
					 						.replace("red", "Red") + " Parrot Head";
					 				if(debug){mmh.logDebug("EDE " + name + "_" + daParrotVariant);}
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.parrot." + daParrotVariant.toLowerCase(), defpercent))){
					 					Drops.add( mmh.makeSkull(MobHeads.valueOf(name + "_" + daParrotVariant).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase() + "." + daParrotVariant.toLowerCase(), daParrotName), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE Parrot Head Dropped");}
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
					 				if(debug){mmh.logDebug("EDE " + name + "_" + daRabbitType);}
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.rabbit." + daRabbitType.toLowerCase(), defpercent))){
					 					Drops.add( mmh.makeSkull(RabbitHeads.valueOf(name + "_" + daRabbitType).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase() + "." + daRabbitType.toLowerCase(), daRabbitName), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE Rabbit Head Dropped");}
					 				}
					 				break;
					 			case "VILLAGER":
					 				Villager daVillager = (Villager) entity; // Location jobsite = daVillager.getMemory(MemoryKey.JOB_SITE);
					 				String daVillagerType = daVillager.getVillagerType().toString();
					 				String daVillagerProfession = daVillager.getProfession().toString();
					 				if(debug){mmh.logDebug("EDE name=" + name);}
					 				if(debug){mmh.logDebug("EDE profession=" + daVillagerProfession);}
					 				if(debug){mmh.logDebug("EDE type=" + daVillagerType);}
					 				String daName = name + "_" + daVillagerProfession + "_" + daVillagerType;
					 				if(debug){mmh.logDebug("EDE " + daName + "		 " + name + "_" + daVillagerProfession + "_" + daVillagerType);}
					 				String daVillagerName = VillagerHeads.valueOf(daName).getName() + " Head";
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.villager." + daVillagerType.toLowerCase() + "." + daVillagerProfession.toLowerCase(), defpercent))){
					 					Drops.add( mmh.makeSkull(VillagerHeads.valueOf(name + "_" + daVillagerProfession + "_" + daVillagerType).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase() + "." + daVillagerType.toLowerCase() + "." + daVillagerProfession.toLowerCase()
					 									, daVillagerName), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE Villager Head Dropped");}
					 				}
					 				break;
					 			case "ZOMBIE_VILLAGER":
					 				ZombieVillager daZombieVillager = (ZombieVillager) entity;
					 				String daZombieVillagerProfession = daZombieVillager.getVillagerProfession().toString();
					 				String daZombieVillagerName = ZombieVillagerHeads.valueOf(name + "_" + daZombieVillagerProfession).getName() + " Head";
					 				if(debug){mmh.logDebug("EDE " + name + "_" + daZombieVillagerProfession);}
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.zombie_villager", defpercent))){
					 					Drops.add( mmh.makeSkull(ZombieVillagerHeads.valueOf(name + "_" + daZombieVillagerProfession).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase() + "." + daZombieVillagerProfession.toLowerCase(), daZombieVillagerName), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE Zombie Villager Head Dropped");}
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
					 				if(debug){mmh.logDebug("EDE " + daSheepColor + "_" + name);}
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.sheep." + daSheepColor.toLowerCase(), defpercent))){
					 					Drops.add( mmh.makeSkull(SheepHeads.valueOf(name + "_" + daSheepColor).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase() + "." + daSheepColor.toLowerCase(), daSheepName), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE Sheep Head Dropped");}
					 				}
					 				break;
					 			/**case "STRIDER":
					 				Strider strider = (Strider) entity;
					 				
					 				break;*/
					 			case "TRADER_LLAMA":
					 				TraderLlama daTraderLlama = (TraderLlama) entity;
					 				String daTraderLlamaColor = daTraderLlama.getColor().toString();
					 				String daTraderLlamaName = LlamaHeads.valueOf(name + "_" + daTraderLlamaColor).getName() + " Head";
					 				if(debug){mmh.logDebug("EDE " + daTraderLlamaColor + "_" + name);}
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.trader_llama." + daTraderLlamaColor.toLowerCase(), defpercent))){
					 					Drops.add( mmh.makeSkull(LlamaHeads.valueOf(name + "_" + daTraderLlamaColor).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase() + "." + daTraderLlamaColor.toLowerCase(), daTraderLlamaName), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE Trader Llama Head Dropped");}
					 				}
					 				break;
					 			case "AXOLOTL":
					 				Axolotl daAxolotl = (Axolotl) entity;
					 				String daAxolotlVariant = daAxolotl.getVariant().toString();
					 				String daAxolotlName = MobHeads117.valueOf(name + "_" + daAxolotlVariant).getName() + " Head";
					 				if(debug){mmh.logDebug("EDE " + daAxolotlVariant + "_" + name);}
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.axolotl." + daAxolotlVariant.toLowerCase(), defpercent))){
					 					Drops.add( mmh.makeSkull(MobHeads117.valueOf(name + "_" + daAxolotlVariant).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase() + "." + daAxolotlVariant.toLowerCase(), daAxolotlName), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE Trader Llama Head Dropped");}
					 				}
					 				break;
					 			case "GOAT":
					 				Goat daGoat = (Goat) entity;
					 				String daGoatVariant;
					 				String daGoatName;// = MobHeads117.valueOf(name + "_" + daAxolotlVariant).getName() + " Head";
					 				if(daGoat.isScreaming()) {
					 					// Giving screaming goat head
					 					daGoatVariant = "SCREAMING";
					 					daGoatName = MobHeads117.valueOf(name + "_" + daGoatVariant).getName() + " Head";
					 				}else {
					 					// give goat head
					 					daGoatVariant = "NORMAL";
					 					daGoatName = MobHeads117.valueOf(name + "_" + daGoatVariant).getName() + " Head";
					 				}
					 				if(debug){mmh.logDebug("EDE " + daGoatVariant + "_" + name);}
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent.goat." + daGoatVariant.toLowerCase(), defpercent))){
					 					Drops.add( mmh.makeSkull(MobHeads117.valueOf(name + "_" + daGoatVariant).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase() + "." + daGoatVariant.toLowerCase(), daGoatName), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE Trader Llama Head Dropped");}
					 				}
					 				break;
					 			default:
					 				//mmh.makeSkull(MobHeads.valueOf(name).getTexture(), name);
					 				if(debug){mmh.logDebug("EDE name=" + name + " line:1005");}
					 				if(debug){mmh.logDebug("EDE texture=" + MobHeads.valueOf(name).getTexture().toString() + " line:1006");}
					 				if(debug){mmh.logDebug("EDE location=" + entity.getLocation().toString() + " line:1007");}
					 				if(debug){mmh.logDebug("EDE getName=" + event.getEntity().getName() + " line:1008");}
					 				if(debug){mmh.logDebug("EDE killer=" + entity.getKiller().toString() + " line:1009");}
					 				if(mmh.DropIt(event, chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
					 					Drops.add( mmh.makeSkull(MobHeads.valueOf(name).getTexture().toString(), 
					 							mmh.langName.getString(name.toLowerCase(), event.getEntity().getName() + " Head"), entity.getKiller()));
					 					if(debug){mmh.logDebug("EDE " + name + " Head Dropped");}
					 				}
					 				if(debug){mmh.logDebug("EDE " + MobHeads.valueOf(name) + " killed");}
					 				break;
					 			}
							}
						//}
					//}
					return;
				}
			}
	}
	
	@SuppressWarnings({ "unused", "static-access", "deprecation", "rawtypes", "unchecked" })
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){ // TODO: Commands
			//log("command=" + cmd.getName() + " args=" + args[0] + args[1]);
		//log("command=" + cmd.getName() + " args=" + args[0] + args[1]);
		if (cmd.getName().equalsIgnoreCase("flycutter")){
			if (args.length == 0){
				sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + mmh.getName() + ChatColor.GREEN + "]===============[]");
				sender.sendMessage(ChatColor.WHITE + " ");
				sender.sendMessage(ChatColor.WHITE + " /flycutter reload - " + mmh.lang.get("reload", "重载配置文件"));//subject to server admin approval");
				sender.sendMessage(ChatColor.WHITE + " /cmhk [玩家] - 获取玩家头颅");
				sender.sendMessage(ChatColor.GREEN + "[]===============[" + ChatColor.YELLOW + mmh.getName() + ChatColor.GREEN + "]===============[]");
				return true;
			}
				if(args[0].equalsIgnoreCase("reload")){
					String perm = "flycutter.reload";
					boolean hasPerm = sender.hasPermission(perm);
					if(debug){mmh.logDebug(sender.getName() + " has the permission " + perm + "=" + hasPerm);}
					if(hasPerm||!(sender instanceof Player)){
						mmh.configReload();
						sender.sendMessage(ChatColor.YELLOW + mmh.getName() + ChatColor.RED + " " + mmh.lang.get("reloaded"));
						return true;
					}else if(!hasPerm){
						sender.sendMessage(ChatColor.YELLOW + mmh.getName() + ChatColor.RED + " " + mmh.lang.get("noperm").toString().replace("<perm>", perm) );
						return false;
					}
				}
			}
			return false;
	}
	
    @SuppressWarnings("static-access")
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
						if(debug) {mmh.logDebug("TC arg1!null args.length=" + args.length);}
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
	}
	
    private void log(Level lvl, String msg) {
    	mmh.log(lvl, msg, null);
    }
    private void logDebug(String msg) {
    	mmh.logDebug(msg);
    }
    
}