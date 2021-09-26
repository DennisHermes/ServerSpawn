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

public final class MainClass extends JavaPlugin implements Listener {
  
	File newConfig;
	FileConfiguration newConfigz;
	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
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
      sender.sendMessage(ChatColor.DARK_RED + "je moet een speler zijn om dit te kunnen doen!");
      return false;
    } 
    
    Player p = (Player)sender;
    
    if (cmd.getName().equalsIgnoreCase("serverspawn")) {
    	if (args.length <= 0) {
    		p.sendMessage(ChatColor.RED + "§lIncorrect argument!");
    		p.sendMessage(ChatColor.RED + "Use /serverspawn [set | get | enable | disable]");
    	} else {
    		if (args[0].equalsIgnoreCase("set")) {
    			
    			double X = Math.round(p.getLocation().getX() * 100);
    			double Y = Math.round(p.getLocation().getY() * 100);
    			double Z = Math.round(p.getLocation().getZ() * 100);
    			float Pitch = Math.round(p.getLocation().getPitch() * 100);
    			float Yaw = Math.round(p.getLocation().getYaw() * 100);
    			String World = p.getWorld().getName();
    			
    			X = X / 100;
    			Y = Y / 100;
    			Z = Z / 100;
    			Pitch = Pitch / 100;
    			Yaw = Yaw / 100;
    			
    			p.sendMessage(ChatColor.DARK_AQUA + "§lThe serverspawn is set to:");
    			p.sendMessage(ChatColor.DARK_AQUA +"§lX cordinate: " + ChatColor.BLUE + X);
    			p.sendMessage(ChatColor.DARK_AQUA + "§lY cordinate: " + ChatColor.BLUE + Y);
    			p.sendMessage(ChatColor.DARK_AQUA + "§lZ cordinate: " + ChatColor.BLUE + Z);
    			p.sendMessage(ChatColor.DARK_AQUA + "§lPitch cordinate: " + ChatColor.BLUE + Pitch);
    			p.sendMessage(ChatColor.DARK_AQUA + "§lYaw cordinate: " + ChatColor.BLUE + Yaw);
    			p.sendMessage(ChatColor.DARK_AQUA + "§lWorld: " + ChatColor.BLUE + World);
    			
    			File customYml = new File(MainClass.getPlugin(MainClass.class).getDataFolder() + "/ServerSpawn.yml");
    			FileConfiguration config = YamlConfiguration.loadConfiguration(customYml);
    			
    			config.set("ServerSpawn.X", X);
    			config.set("ServerSpawn.Y", Y);
    			config.set("ServerSpawn.Z", Z);
    			config.set("ServerSpawn.Pitch", Pitch);
    			config.set("ServerSpawn.Yaw", Yaw);
    			config.set("ServerSpawn.World", World);
    			try {
					config.save(customYml);
				} catch (IOException e) {
					e.printStackTrace();
				}
    			
    		} else if (args[0].equalsIgnoreCase("get")) {
    			
    			File customYml = new File(MainClass.getPlugin(MainClass.class).getDataFolder() + "/ServerSpawn.yml");
    			FileConfiguration config = YamlConfiguration.loadConfiguration(customYml);
    			
    			double X = config.getDouble("ServerSpawn.X");
    			double Y = config.getDouble("ServerSpawn.Y");
    			double Z = config.getDouble("ServerSpawn.Z");
    			float Pitch = (float) config.getDouble("ServerSpawn.Pitch");
    			float Yaw = (float) config.getDouble("ServerSpawn.Yaw");
    			String WorldName = config.getString("ServerSpawn.World");
    			
    			p.sendMessage(ChatColor.DARK_AQUA + "§lThe serverspawn is set to:");
    			p.sendMessage(ChatColor.DARK_AQUA +"§lX cordinate: " + ChatColor.BLUE + X);
    			p.sendMessage(ChatColor.DARK_AQUA + "§lY cordinate: " + ChatColor.BLUE + Y);
    			p.sendMessage(ChatColor.DARK_AQUA + "§lZ cordinate: " + ChatColor.BLUE + Z);
    			p.sendMessage(ChatColor.DARK_AQUA + "§lPitch cordinate: " + ChatColor.BLUE + Pitch);
    			p.sendMessage(ChatColor.DARK_AQUA + "§lYaw cordinate: " + ChatColor.BLUE + Yaw);
    			p.sendMessage(ChatColor.DARK_AQUA + "§lWorld: " + ChatColor.BLUE + WorldName);
    			
    		} else if (args[0].equalsIgnoreCase("enable")) {
    			
    			File customYml = new File(MainClass.getPlugin(MainClass.class).getDataFolder() + "/ServerSpawn.yml");
    			FileConfiguration config = YamlConfiguration.loadConfiguration(customYml);
    			
    			boolean enabled = config.getBoolean("Teleport on join");
    			if (enabled == true) {
    				p.sendMessage(ChatColor.RED + "Teleport on join is already enabled!");
    			} else {
    				p.sendMessage(ChatColor.GREEN + "Teleport on join is now enabled!");
    				config.set("Teleport on join", true);
    				try {
						config.save(customYml);
					} catch (IOException e) {
						e.printStackTrace();
					}
    			}
    			
    		} else if (args[0].equalsIgnoreCase("disable")) {
    			
    			File customYml = new File(MainClass.getPlugin(MainClass.class).getDataFolder() + "/ServerSpawn.yml");
    			FileConfiguration config = YamlConfiguration.loadConfiguration(customYml);
    			
    			boolean enabled = config.getBoolean("Teleport on join");
    			if (enabled == false) {
    				p.sendMessage(ChatColor.RED + "Teleport on join is already disabled!");
    			} else {
    				p.sendMessage(ChatColor.GREEN + "Teleport on join is now disabled!");
    				config.set("Teleport on join", false);
    				try {
						config.save(customYml);
					} catch (IOException e) {
						e.printStackTrace();
					}
    			}
    		} else {
    			p.sendMessage(ChatColor.RED + "§lIncorrect argument!");
    			p.sendMessage(ChatColor.RED + "Use /serverspawn [set | get | enable | disable]");
    		}
    	}
    }
    
//===========================================================================================================================//
    
    if (cmd.getName().equalsIgnoreCase("fake")) {
    	if (args.length <= 0) {
    		p.sendMessage(ChatColor.RED + "§lIncorrect argument!");
			p.sendMessage(ChatColor.RED + "Use /fake [join | leave]");
    	} else {
    		if (args[0].equalsIgnoreCase("join")) {
    			for (Player OnlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
    				
    				File customYml = new File(MainClass.getPlugin(MainClass.class).getDataFolder() + "/ServerSpawn.yml");
        			FileConfiguration config = YamlConfiguration.loadConfiguration(customYml);
        			
    				if (config.getString("Custom messages").equalsIgnoreCase("disabled")) {
    					OnlinePlayers.sendMessage(ChatColor.YELLOW + this.getName(p.getUniqueId()) + " joined the game");
    				} else {
    					String message = config.getString("Custom Joinmessage");
    					message = ChatColor.translateAlternateColorCodes('&', message);
    					message = message.replace("%player%", this.getName(p.getUniqueId()));
    					message = message.replace("%Player%", this.getName(p.getUniqueId()));
    					OnlinePlayers.sendMessage(message);
    				}
    			}
    		} else if (args[0].equalsIgnoreCase("leave")) {
    			
    			File customYml = new File(MainClass.getPlugin(MainClass.class).getDataFolder() + "/ServerSpawn.yml");
    			FileConfiguration config = YamlConfiguration.loadConfiguration(customYml);
    			
    			for (Player OnlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
    				if (config.getString("Custom messages").equalsIgnoreCase("disabled")) {
    					OnlinePlayers.sendMessage(ChatColor.YELLOW + this.getName(p.getUniqueId()) + " left the game");
    				} else {
    					String message = config.getString("Custom Leavemessage");
    					message = ChatColor.translateAlternateColorCodes('&', message);
    					message = message.replace("%player%", this.getName(p.getUniqueId()));
    					message = message.replace("%Player%", this.getName(p.getUniqueId()));
    					OnlinePlayers.sendMessage(message);
    				}
    			}
    		} else {
    			p.sendMessage(ChatColor.RED + "§lIncorrect argument!");
    			p.sendMessage(ChatColor.RED + "Use /fake [join | leave]");
    		}
    	}
    }
    
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
