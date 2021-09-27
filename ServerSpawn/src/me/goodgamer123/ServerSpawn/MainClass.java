package me.goodgamer123.ServerSpawn;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.IOUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import me.goodgamer123.ServerSpawn.API.ServerSpawnMessages;

public final class MainClass extends JavaPlugin implements Listener {
  
	File newConfig;
	FileConfiguration newConfigz;
	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(new OnJoin(), this);
		
		getCommand("custommessages").setExecutor(this);
		getCommand("serverspawn").setExecutor(this);
		getCommand("fake").setExecutor(this);
		getCommand("joinmessage").setExecutor(this);
		getCommand("leavemessage").setExecutor(this);
		getCommand("colorcodes").setExecutor(this);
		
		this.getCommand("custommessages").setTabCompleter(new TabCompleterClass());
		this.getCommand("serverspawn").setTabCompleter(new TabCompleterClass());
		this.getCommand("fake").setTabCompleter(new TabCompleterClass());
		this.getCommand("joinmessage").setTabCompleter(new TabCompleterClass());
		this.getCommand("leavemessage").setTabCompleter(new TabCompleterClass());
		
		API.loadFiles(this);
	}
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.NeedToBeAPlayer));
      return false;
    } 
    
    Player p = (Player)sender;
    
    if (cmd.getName().equalsIgnoreCase("serverspawn")) {
    	if (args.length <= 0) {
    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawnIncorrectArg));
    	} else {
    		if (args[0].equalsIgnoreCase("set")) {
    			API.setServerSpawn(p.getLocation());
    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawn));
    		} else if (args[0].equalsIgnoreCase("get")) {
    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawn));
    		} else if (args[0].equalsIgnoreCase("enable")) {
    			if (API.teleportOnJoin()) {
    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.NotChangedTeleportOnJoin));
    			} else {
    				API.setTeleportOnJoin(true);
    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ChangedTeleportOnJoin));
    			}
    		} else if (args[0].equalsIgnoreCase("disable")) {
    			if (!API.teleportOnJoin()) {
    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.NotChangedTeleportOnJoin));
    			} else {
    				API.setTeleportOnJoin(false);
    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ChangedTeleportOnJoin));
    			}
    		} else {
    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawnIncorrectArg));
    		}
    	}
    }
    
//===========================================================================================================================//
    
	if (cmd.getName().equalsIgnoreCase("fake")) {
		if (args.length <= 0) {
			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.FakeIncorrectArg));
		} else {
			if (args[0].equalsIgnoreCase("join")) {
				if (API.customMessages()) Bukkit.broadcastMessage(API.getJoinMessage(p)); else Bukkit.broadcastMessage(ChatColor.YELLOW + p.getName() + " joined the game");
    		} else if (args[0].equalsIgnoreCase("leave")) {
    			if (API.customMessages()) Bukkit.broadcastMessage(API.getQuitMessage(p)); else Bukkit.broadcastMessage(ChatColor.YELLOW + p.getName() + " left the game");
    		} else {
    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.FakeIncorrectArg));
    		}
    	}
    }
    
//===========================================================================================================================//
    
    if (cmd.getName().equalsIgnoreCase("custommessages")) {
    	
    	File customYml = new File(MainClass.getPlugin(MainClass.class).getDataFolder() + "/ServerSpawn.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(customYml);
    	
    	if (args.length <= 0) {
    		p.sendMessage(ChatColor.RED + "§lIncorrect argument!");
			p.sendMessage(ChatColor.RED + "Use /custommessage [enable | disable]");
    	} else {
    		if (args[0].equalsIgnoreCase("enable")) {
    			if (config.getString("Custom messages").equalsIgnoreCase("Enabled")) {
    				p.sendMessage(ChatColor.RED +"Custom join and leave messages are already enabled");
				} else {
					p.sendMessage(ChatColor.GREEN + "Custom messages enabled!");
					config.set("Custom messages", "Enabled");
					try {
						config.save(customYml);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
    		} else if (args[0].equalsIgnoreCase("disable")) {
    			if (config.getString("Custom messages").equalsIgnoreCase("Disabled")) {
    				p.sendMessage(ChatColor.RED +"Custom join and leave messages are already disabled");
				} else {
					p.sendMessage(ChatColor.GREEN + "Custom messages disabled!");
					config.set("Custom messages", "Disabled");
					try {
						config.save(customYml);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
    		} else {
    			p.sendMessage(ChatColor.RED + "§lIncorrect argument!");
    			p.sendMessage(ChatColor.RED + "Use /custommessage [enable | disable]");
    		}
    	}
    }
    
//===========================================================================================================================//
    
    if (cmd.getName().equalsIgnoreCase("joinmessage")) {
    	
    	File customYml = new File(MainClass.getPlugin(MainClass.class).getDataFolder() + "/ServerSpawn.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(customYml);
    	
    	if (args.length <= 0) {
    		p.sendMessage(ChatColor.RED + "§lIncorrect argument!");
			p.sendMessage(ChatColor.RED + "Use /joinmessage [set | get]");
    	} else {
    		if (args[0].equalsIgnoreCase("get")) {
    			
    			String message = config.getString("Custom Joinmessage");
				message = ChatColor.translateAlternateColorCodes('&', message);
				message = message.replace("%player%", "(playername)");
				message = message.replace("%Player%", "(playername)");
				p.sendMessage(message);
				
    		} else if (args[0].equalsIgnoreCase("set")) {
    			
    			String message = "";
	    		for(int i = 1; i < args.length; i++) {
	    			message = message + args[i] + " ";
	    		}
	    		config.set("Custom Joinmessage", message);
	    		try {
					config.save(customYml);
				} catch (IOException e) {
					e.printStackTrace();
				}
	    		message = ChatColor.translateAlternateColorCodes('&', message);
				message = message.replace("%player%", "(playername)");
				message = message.replace("%Player%", "(playername)");
				p.sendMessage(ChatColor.BLUE + "Custom join message set to:");
				p.sendMessage(message);
    		} else {
    			p.sendMessage(ChatColor.RED + "§lIncorrect argument!");
    			p.sendMessage(ChatColor.RED + "Use /joinmessage [set | get]");
    		}
    	}
    }
    
//===========================================================================================================================//
    
    if (cmd.getName().equalsIgnoreCase("leavemessage")) {
    	
    	File customYml = new File(MainClass.getPlugin(MainClass.class).getDataFolder() + "/ServerSpawn.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(customYml);
    	
    	if (args.length <= 0) {
    		p.sendMessage(ChatColor.RED + "§lIncorrect argument!");
			p.sendMessage(ChatColor.RED + "Use /leavemessage [set | get]");
    	} else {
    		if (args[0].equalsIgnoreCase("get")) {
    			
    			String message = config.getString("Custom Leavemessage");
				message = ChatColor.translateAlternateColorCodes('&', message);
				message = message.replace("%player%", "(playername)");
				message = message.replace("%Player%", "(playername)");
				p.sendMessage(message);
				
    		} else if (args[0].equalsIgnoreCase("set")) {
    			
    			String message = "";
	    		for(int i = 1; i < args.length; i++) {
	    			message = message + args[i] + " ";
	    		}
	    		
	    		config.set("Custom Leavemessage", message);
	    		try {
					config.save(customYml);
				} catch (IOException e) {
					e.printStackTrace();
				}
	    		message = ChatColor.translateAlternateColorCodes('&', message);
				message = message.replace("%player%", "(playername)");
				message = message.replace("%Player%", "(playername)");
				p.sendMessage(ChatColor.BLUE + "Custom leave message set to:");
				p.sendMessage(message);
    		}
    	}
    }
    
//===========================================================================================================================//
    
    if (cmd.getName().equalsIgnoreCase("hub")) {
    	
    	File customYml = new File(MainClass.getPlugin(MainClass.class).getDataFolder() + "/ServerSpawn.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(customYml);
    	
		if (config.getString("ServerSpawn.World").equalsIgnoreCase("")) {
			p.sendMessage(ChatColor.RED + "The ServerSpawn is not set!");
		} else {
			double X = config.getDouble("ServerSpawn.X");
			double Y = config.getDouble("ServerSpawn.Y");
			double Z = config.getDouble("ServerSpawn.Z");
			float Pitch = (float) config.getDouble("ServerSpawn.Pitch");
			float Yaw = (float) config.getDouble("ServerSpawn.Yaw");
			String WorldName = config.getString("ServerSpawn.World");
			World World = Bukkit.getWorld(WorldName);
			Location loc = new Location(World, X, Y, Z, Yaw, Pitch);
			p.teleport(loc);
		}
    }
    
    if (cmd.getName().equalsIgnoreCase("spawn")) {
    	
    	File customYml = new File(MainClass.getPlugin(MainClass.class).getDataFolder() + "/ServerSpawn.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(customYml);
    	
		if (config.getString("ServerSpawn.World").equalsIgnoreCase("")) {
			p.sendMessage(ChatColor.RED + "The ServerSpawn is not set!");
		} else {
			double X = config.getDouble("ServerSpawn.X");
			double Y = config.getDouble("ServerSpawn.Y");
			double Z = config.getDouble("ServerSpawn.Z");
			float Pitch = (float) config.getDouble("ServerSpawn.Pitch");
			float Yaw = (float) config.getDouble("ServerSpawn.Yaw");
			String WorldName = config.getString("ServerSpawn.World");
			World World = Bukkit.getWorld(WorldName);
			Location loc = new Location(World, X, Y, Z, Yaw, Pitch);
			p.teleport(loc);
		}
    }
    
    if (cmd.getName().equalsIgnoreCase("lobby")) {
    	
    	File customYml = new File(MainClass.getPlugin(MainClass.class).getDataFolder() + "/ServerSpawn.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(customYml);
    	
		if (config.getString("ServerSpawn.World").equalsIgnoreCase("")) {
			p.sendMessage(ChatColor.RED + "The ServerSpawn is not set!");
		} else {
			double X = config.getDouble("ServerSpawn.X");
			double Y = config.getDouble("ServerSpawn.Y");
			double Z = config.getDouble("ServerSpawn.Z");
			float Pitch = (float) config.getDouble("ServerSpawn.Pitch");
			float Yaw = (float) config.getDouble("ServerSpawn.Yaw");
			String WorldName = config.getString("ServerSpawn.World");
			World World = Bukkit.getWorld(WorldName);
			Location loc = new Location(World, X, Y, Z, Yaw, Pitch);
			p.teleport(loc);
		}
    } 

//===========================================================================================================================//
    
    if (cmd.getName().equalsIgnoreCase("colorcodes")) {
      p.sendMessage(ChatColor.DARK_RED + "Dark red: &4");
      p.sendMessage(ChatColor.RED + "Red: &c");
      p.sendMessage(ChatColor.GOLD + "Gold: &6");
      p.sendMessage(ChatColor.YELLOW + "Yellow: &e");
      p.sendMessage(ChatColor.DARK_GREEN + "Dark green: &2");
      p.sendMessage(ChatColor.GREEN + "Green: &a");
      p.sendMessage(ChatColor.AQUA + "Aqua: &b");
      p.sendMessage(ChatColor.DARK_AQUA + "Dark Aqua: &3");
      p.sendMessage(ChatColor.DARK_BLUE + "Dark blue: &1");
      p.sendMessage(ChatColor.BLUE + "Blue: &9");
      p.sendMessage(ChatColor.LIGHT_PURPLE + "light purple: &d");
      p.sendMessage(ChatColor.DARK_PURPLE + "Dark purple: &5");
      p.sendMessage(ChatColor.WHITE + "White: &f");
      p.sendMessage(ChatColor.GRAY + "Gray: &7");
      p.sendMessage(ChatColor.DARK_GRAY + "Dark gray: &8");
      p.sendMessage(ChatColor.BLACK + "Black: &0");
      p.sendMessage(ChatColor.WHITE + "Reset Colors: &r");
      p.sendMessage(ChatColor.BOLD + "Bold: &l");
      p.sendMessage(ChatColor.ITALIC + "Italic: &o");
      p.sendMessage(ChatColor.UNDERLINE + "Underlined: &n");
      p.sendMessage(ChatColor.STRIKETHROUGH + "Crossed out: &m");
      p.sendMessage(ChatColor.WHITE + "Spinning: &k");
    }
    
	return false; 
  }
  
  	public void saveNewConfig(){
		try{
			newConfigz.save(newConfig);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
  
  	@SuppressWarnings("deprecation")
	public String getName(UUID ID) {
		String uuid = ID.toString();
		String url = "https://api.mojang.com/user/profiles/"+uuid.replace("-", "")+"/names";
        try {
            String nameJson = IOUtils.toString(new URL(url));           
            JSONArray nameValue = (JSONArray) JSONValue.parseWithException(nameJson);
            String playerSlot = nameValue.get(nameValue.size()-1).toString();
            JSONObject nameObject = (JSONObject) JSONValue.parseWithException(playerSlot);
            return nameObject.get("name").toString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return Bukkit.getOfflinePlayer(ID).getName();
	}
  	
}
