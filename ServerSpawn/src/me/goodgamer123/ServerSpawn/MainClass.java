package me.goodgamer123.ServerSpawn;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.goodgamer123.ServerSpawn.API.ServerSpawnLanguage;
import me.goodgamer123.ServerSpawn.API.ServerSpawnMessages;
import me.goodgamer123.ServerSpawn.API.spawnCommand;

public final class MainClass extends JavaPlugin implements Listener {
  
	File newConfig;
	FileConfiguration newConfigz;
	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(new OnJoinLeave(), this);
		
		getCommand("custommessages").setExecutor(this);
		getCommand("serverspawn").setExecutor(this);
		getCommand("fake").setExecutor(this);
		getCommand("joinmessage").setExecutor(this);
		getCommand("leavemessage").setExecutor(this);
		getCommand("spawncommands").setExecutor(this);
		getCommand("colorcodes").setExecutor(this);
		
		getCommand("custommessages").setTabCompleter(new TabCompleterClass());
		getCommand("serverspawn").setTabCompleter(new TabCompleterClass());
		getCommand("fake").setTabCompleter(new TabCompleterClass());
		getCommand("joinmessage").setTabCompleter(new TabCompleterClass());
		getCommand("leavemessage").setTabCompleter(new TabCompleterClass());
		getCommand("spawncommands").setTabCompleter(new TabCompleterClass());
		
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
	    		} else if (args[0].equalsIgnoreCase("settings")) {
	    			if (args.length <= 1) {
	    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
	    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawnSettingsIncorrectArg));
	    			} else {
		    			if (args[1].equalsIgnoreCase("prefix")) {
		    				if (args.length <= 2) {
			    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
			    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawnPrefixIncorrectArg));
			    			} else {
			    				if (args[1].equalsIgnoreCase("set")) {
			    					if (args.length <= 3) {
			    						p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
					    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawnPrefixSetIncorrectArg));
			    					} else {
			    						String message = "";
			    			    		for(int i = 3; i < args.length; i++) {
			    			    			message = message + args[i] + " ";
			    			    		}
			    			    		API.setPrefix(message);
			    						p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.SetPrefixMessage));
			    					}
			    				} else if (args[1].equalsIgnoreCase("enable")) {
			    					if (API.prefixEnabled()) {
			    						p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.PrefixNotChanged));
			    					} else {
			    						API.setPrefixEnabled(true);
			    						p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.PrefixChanged));
			    					}
			    				} else if (args[1].equalsIgnoreCase("disable")) {
			    					if (!API.prefixEnabled()) {
			    						p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.PrefixNotChanged));
			    					} else {
			    						API.setPrefixEnabled(false);
			    						p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.PrefixChanged));
			    					}
			    				} else {
			    					p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
				    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawnPrefixIncorrectArg));
			    				}
			    			}
				    	} else if (args[1].equalsIgnoreCase("language")) {
				    		if (args.length <= 2) {
			    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
			    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawnLanguageIncorrectArg));
			    			} else {
			    				if (args[1].equalsIgnoreCase("english")) {
			    					if (API.getLanguage().equals(ServerSpawnLanguage.ENGLISH)) {
					    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.LanguageNotChanged));
			    					} else {
			    						API.setLanguage(ServerSpawnLanguage.ENGLISH);
			    						p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.LanguageChanged));
			    					}
			    				} else if (args[1].equalsIgnoreCase("dutch")) {
			    					if (API.getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
					    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.LanguageNotChanged));
			    					} else {
			    						API.setLanguage(ServerSpawnLanguage.DUTCH);
			    						p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.LanguageChanged));
			    					}
			    				} else {
			    					p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
				    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawnLanguageIncorrectArg));
			    				}
			    			}
				    	} else {
				    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
		    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawnSettingsIncorrectArg));
				    	}
	    			}
	    		} else {
	    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
	    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawnIncorrectArg));
	    		}
	    	}
	    }
	    
	//===========================================================================================================================//
	    
		else if (cmd.getName().equalsIgnoreCase("fake")) {
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
	    
		else if (cmd.getName().equalsIgnoreCase("custommessages")) {
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
	    
	    else if (cmd.getName().equalsIgnoreCase("joinmessage")) {
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
	    
	    else if (cmd.getName().equalsIgnoreCase("leavemessage")) {
	    	if (args.length <= 0) {
	    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
	    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.QuitMessageIncorrectArg));
	    	} else {
	    		if (args[0].equalsIgnoreCase("get")) {
					p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.GetQuitMessage));
					p.sendMessage(API.getJoinMessage(p));
	    		} else if (args[0].equalsIgnoreCase("set")) {
	    			if (args.length <= 1) {
	    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
	    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.SetQuitMessageIncorrectArg));
	    			} else {
		    			String message = "";
			    		for(int i = 1; i < args.length; i++) {
			    			message = message + args[i] + " ";
			    		}
			    		API.setQuitMessage(message);
						p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.GetQuitMessage));
						p.sendMessage(API.getJoinMessage(p));
	    			}
	    		} else {
	    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
	    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.QuitMessageIncorrectArg));
	    		}
	    	}
	    }
	    
	//===========================================================================================================================//
	    
	    else if (cmd.getName().equalsIgnoreCase("hub")) {
	    	if (API.spawnCommandIsEnabled(spawnCommand.Hub)) {
	    		if (API.getServerSpawn() == null) {
	    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawnNotSet));
	    		} else {
	    			p.teleport(API.getServerSpawn());
	    		}
	    	} else {
	    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.SpawnCommandDisabled));
	    	}
	    }
	    
	    else if (cmd.getName().equalsIgnoreCase("spawn")) {
	    	if (API.spawnCommandIsEnabled(spawnCommand.Spawn)) {
	    		if (API.getServerSpawn() == null) {
	    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawnNotSet));
	    		} else {
	    			p.teleport(API.getServerSpawn());
	    		}
	    	} else {
	    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.SpawnCommandDisabled));
	    	}
	    }
	    
	    else if (cmd.getName().equalsIgnoreCase("lobby")) {
	    	if (API.spawnCommandIsEnabled(spawnCommand.Lobby)) {
	    		if (API.getServerSpawn() == null) {
	    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ServerSpawnNotSet));
	    		} else {
	    			p.teleport(API.getServerSpawn());
	    		}
	    	} else {
	    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.SpawnCommandDisabled));
	    	}
	    } 
	
	//===========================================================================================================================//
	    
	    else if (cmd.getName().equalsIgnoreCase("spawncommands")) {
	    	if (args.length <= 0) {
	    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
	    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.SpawnCommandsIncorrectArg));
	    	} else {
	    		if (args[0].equalsIgnoreCase("hub")) {
	    			if (args[1].equalsIgnoreCase("enable")) {
		    			if (API.spawnCommandIsEnabled(spawnCommand.Hub)) {
		    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.NotChangedHubCommand));
		    			} else {
		    				API.setSpawnCommand(spawnCommand.Hub, true);
		    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ChangedHubCommand));
		    			}
	    			} else if (args[1].equalsIgnoreCase("disable")) {
	    				if (!API.spawnCommandIsEnabled(spawnCommand.Hub)) {
		    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.NotChangedHubCommand));
		    			} else {
		    				API.setSpawnCommand(spawnCommand.Hub, false);
		    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ChangedHubCommand));
		    			}
	    			} else {
	    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
			    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.SpawnCommandsIncorrectArg));
	    			}
	    		} else if (args[0].equalsIgnoreCase("spawn")) {
	    			if (args[1].equalsIgnoreCase("enable")) {
		    			if (API.spawnCommandIsEnabled(spawnCommand.Spawn)) {
		    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.NotChangedSpawnCommand));
		    			} else {
		    				API.setSpawnCommand(spawnCommand.Spawn, true);
		    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ChangedSpawnCommand));
		    			}
	    			} else if (args[1].equalsIgnoreCase("disable")) {
	    				if (!API.spawnCommandIsEnabled(spawnCommand.Spawn)) {
		    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.NotChangedSpawnCommand));
		    			} else {
		    				API.setSpawnCommand(spawnCommand.Spawn, false);
		    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ChangedSpawnCommand));
		    			}
	    			} else {
	    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
			    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.SpawnCommandsIncorrectArg));
	    			}
	    		} else if (args[0].equalsIgnoreCase("lobby")) {
	    			if (args[1].equalsIgnoreCase("enable")) {
		    			if (API.spawnCommandIsEnabled(spawnCommand.Lobby)) {
		    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.NotChangedLobbyCommand));
		    			} else {
		    				API.setSpawnCommand(spawnCommand.Lobby, true);
		    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ChangedLobbyCommand));
		    			}
	    			} else if (args[1].equalsIgnoreCase("disable")) {
	    				if (!API.spawnCommandIsEnabled(spawnCommand.Lobby)) {
		    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.NotChangedLobbyCommand));
		    			} else {
		    				API.setSpawnCommand(spawnCommand.Lobby, false);
		    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.ChangedLobbyCommand));
		    			}
	    			} else {
	    				p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
			    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.SpawnCommandsIncorrectArg));
	    			}
	    		} else {
	    			p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.IncorrectArg));
		    		p.sendMessage(API.getPrefix() + API.getMessage(ServerSpawnMessages.SpawnCommandsIncorrectArg));
	    		}
	    	}
	    }
	    
	//===========================================================================================================================//
	    
	    else if (cmd.getName().equalsIgnoreCase("colorcodes")) {
	      API.sendChatColorList(p);
	    }
	    
	//===========================================================================================================================//
	    
	    else {
	    	//TODO
	    }
		
	//===========================================================================================================================//
		
		return false; 
	}
}
