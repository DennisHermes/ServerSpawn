package me.goodgamer123.ServerSpawn;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

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
	    	if (args.length <= 0) {
	    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.CustomMessagesIncorrectArg));
	    	} else {
	    		if (args[0].equalsIgnoreCase("enable")) {
	    			if (API.customMessages()) {
	    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.NotChangedCustomMessages));
					} else {
						API.setCustomMessages(true);
						p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ChangedCustomMessages));
					}
	    		} else if (args[0].equalsIgnoreCase("disable")) {
	    			if (!API.customMessages()) {
	    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.NotChangedCustomMessages));
					} else {
						API.setCustomMessages(false);
						p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ChangedCustomMessages));
					}
	    		} else {
	    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
	    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.CustomMessagesIncorrectArg));
	    		}
	    	}
	    }
	    
	//===========================================================================================================================//
	    
	    if (cmd.getName().equalsIgnoreCase("joinmessage")) {
	    	if (args.length <= 0) {
	    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
	    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.JoinMessageIncorrectArg));
	    	} else {
	    		if (args[0].equalsIgnoreCase("get")) {
					p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.GetJoinMessage));
					p.sendMessage(API.getJoinMessage(p));
	    		} else if (args[0].equalsIgnoreCase("set")) {
	    			if (args.length <= 1) {
	    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
	    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.SetJoinMessageIncorrectArg));
	    			} else {
		    			String message = "";
			    		for(int i = 1; i < args.length; i++) {
			    			message = message + args[i] + " ";
			    		}
			    		API.setJoinMessage(message);
						p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.GetJoinMessage));
						p.sendMessage(API.getJoinMessage(p));
	    			}
	    		} else {
	    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
	    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.JoinMessageIncorrectArg));
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
	      API.sendChatColorList(p);
	    }
	    
		return false; 
	}
}
