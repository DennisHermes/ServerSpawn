package me.goodgamer123.ServerSpawn;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class API {
	
	static File ServerSpawnFile;
	static FileConfiguration ServerSpawn;
	static File MessagesFile;
	static FileConfiguration Messages;
	
	static Plugin MainClass;
	
	static void loadFiles(Plugin plugin) {
		MainClass = plugin;
		
		final File ServerSpawnYml = new File(MainClass.getDataFolder() + "/ServerSpawn.yml");
		ServerSpawnFile = new File(MainClass.getDataFolder(), "/ServerSpawn.yml");
		ServerSpawn = YamlConfiguration.loadConfiguration(ServerSpawnFile);
		
		final File MessagesYml = new File(MainClass.getDataFolder() + "/Messages.yml");
		MessagesFile = new File(MainClass.getDataFolder(), "/Messages.yml");
		Messages = YamlConfiguration.loadConfiguration(MessagesFile);
		
		if (!ServerSpawnYml.exists()) {
			ServerSpawn.set("Server spawn", Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
    		ServerSpawn.set("Teleport on join", true);
    		ServerSpawn.set("Custom messages", true);
    		ServerSpawn.set("Spawn Commands Enabled.Hub", true);
    		ServerSpawn.set("Spawn Commands Enabled.Spawn", true);
    		ServerSpawn.set("Spawn Commands Enabled.Lobby", true);
			saveFiles();
		}
		
		if (!MessagesYml.exists()) {
			Messages.set("Custom messages.Join message", "&0[&2+&0] &8%playername%");
			Messages.set("Custom messages.Quit message", "&0[&4-&0] &8%playername%");
			Messages.set("Prefix.Message", "&0&l[&2Server&aSpawn&0] ");
			Messages.set("Prefix.Enabled", true);
			Messages.set("Language", ServerSpawnLanguage.ENGLISH.toString());
			saveFiles();
		}
	}

	//
	//Gatherers
	//
	
	//Spawnpoint
	
	public static Location getServerSpawn() {
		if (ServerSpawn.getLocation("Server spawn") == null) return null; else {
			return ServerSpawn.getLocation("Server spawn");
		}
	}
	
	//Join message
	
	public static String getJoinMessage(Player p) {
		String joinMessage = ChatColor.translateAlternateColorCodes('&', ServerSpawn.getString("Custom messages.Join message"));
		joinMessage = placeholderReplace(joinMessage, p);
		return joinMessage;
	}
	
	public static String getRawJoinMessage() {
		return ServerSpawn.getString("Custom messages.Join message");
	}
	
	//Quit message
	
	public static String getQuitMessage(Player p) {
		String quitMessage = ChatColor.translateAlternateColorCodes('&', ServerSpawn.getString("Custom messages.Quit message"));
		quitMessage = placeholderReplace(quitMessage, p);
		return quitMessage;
	}
	
	public static String getRawQuitMessage() {
		return ServerSpawn.getString("Custom messages.Quit message");
	}
	
	//Additional message info
	
	public static ServerSpawnLanguage getLanguage() {
		if (Messages.getString("Language").equals(ServerSpawnLanguage.DUTCH.toString())) return ServerSpawnLanguage.DUTCH;
		else return ServerSpawnLanguage.ENGLISH;
	}
	
	public static String getPrefix() {
		if (prefixEnabled()) return ChatColor.translateAlternateColorCodes('&', Messages.getString("Prefix.Message"));
		else return "";
	}
	
	//Messages
	
	public static enum ServerSpawnMessages {
		ServerSpawn,
		ChangedTeleportOnJoin,
		NotChangedTeleportOnJoin,
		NeedToBeAPlayer,
		IncorrectArg,
		ServerSpawnIncorrectArg,
		ServerSpawnSettingsIncorrectArg,
		ServerSpawnPrefixIncorrectArg,
		ServerSpawnPrefixSetIncorrectArg,
		ServerSpawnLanguageIncorrectArg,
		FakeIncorrectArg,
		CustomMessagesIncorrectArg, 
		ChangedCustomMessages,
		NotChangedCustomMessages, 
		GetJoinMessage,
		GetQuitMessage,
		SetPrefixMessage,
		SpawnCommandDisabled,
		ChangedHubCommand,
		NotChangedHubCommand,
		ChangedSpawnCommand,
		NotChangedSpawnCommand,
		ChangedLobbyCommand,
		NotChangedLobbyCommand,
		PrefixChanged,
		PrefixNotChanged,
		LanguageChanged,
		LanguageNotChanged,
		ServerSpawnNotSet,
		JoinMessageIncorrectArg,
		SetJoinMessageIncorrectArg,
		QuitMessageIncorrectArg,
		SetQuitMessageIncorrectArg,
		SpawnCommandsIncorrectArg
	}
	
	public static String getMessage(ServerSpawnMessages messageType) {
		if (messageType.equals(ServerSpawnMessages.ServerSpawn)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return
				ChatColor.GREEN + "De server spawn is gezet naar" + 
				ChatColor.DARK_GREEN + " x: " + Math.round(getServerSpawn().getX() * 100.0) / 100.0 + ChatColor.GREEN + "," + 
				ChatColor.DARK_GREEN + " y: " + Math.round(getServerSpawn().getY() * 100.0) / 100.0 + ChatColor.GREEN + "," + 
				ChatColor.DARK_GREEN + " z: " + Math.round(getServerSpawn().getZ() * 100.0) / 100.0 + ChatColor.GREEN + "in wereld " +
				ChatColor.DARK_GREEN + getServerSpawn().getWorld().getName() + ChatColor.GREEN + " met" +
				ChatColor.DARK_GREEN + " pitch: " + Math.round(getServerSpawn().getPitch() * 100.0) / 100.0 + ChatColor.GREEN + " en" +
				ChatColor.DARK_GREEN + " yaw: " + Math.round(getServerSpawn().getYaw() * 100.0) / 100.0 + ChatColor.GREEN + ".";
			else return 
				ChatColor.GREEN + "The server spawn is set to" + 
				ChatColor.DARK_GREEN + " x: " + Math.round(getServerSpawn().getX() * 100.0) / 100.0 + ChatColor.GREEN + "," + 
				ChatColor.DARK_GREEN + " y: " + Math.round(getServerSpawn().getY() * 100.0) / 100.0 + ChatColor.GREEN + "," + 
				ChatColor.DARK_GREEN + " z: " + Math.round(getServerSpawn().getZ() * 100.0) / 100.0 + ChatColor.GREEN + "in world " +
				ChatColor.DARK_GREEN + getServerSpawn().getWorld().getName() + ChatColor.GREEN + " with" +
				ChatColor.DARK_GREEN + " pitch: " + Math.round(getServerSpawn().getPitch() * 100.0) / 100.0 + ChatColor.GREEN + " and" +
				ChatColor.DARK_GREEN + " yaw: " + Math.round(getServerSpawn().getYaw() * 100.0) / 100.0 + ChatColor.GREEN + ".";
			
		} else if (messageType.equals(ServerSpawnMessages.ChangedTeleportOnJoin)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				String teleportOnJoin;
				if (teleportOnJoin()) teleportOnJoin = "ingeschakeld"; else teleportOnJoin = "uitgeschakeld";
				return ChatColor.GREEN + "Teleporteren bij toetreding is nu " + ChatColor.DARK_GREEN + teleportOnJoin + ChatColor.GREEN + "!";
			} else {
				String teleportOnJoin;
				if (teleportOnJoin()) teleportOnJoin = "enabled"; else teleportOnJoin = "disabled";
				return ChatColor.GREEN + "Teleport on join is now " + ChatColor.DARK_GREEN + teleportOnJoin + ChatColor.GREEN + "!";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.NotChangedTeleportOnJoin)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				String teleportOnJoin;
				if (teleportOnJoin()) teleportOnJoin = "ingeschakeld"; else teleportOnJoin = "uitgeschakeld";
				return ChatColor.RED + "Teleporteren bij toetreding is al " + ChatColor.DARK_RED + teleportOnJoin + ChatColor.RED + "!";
			} else {
				String teleportOnJoin;
				if (teleportOnJoin()) teleportOnJoin = "enabled"; else teleportOnJoin = "disabled";
				return ChatColor.RED + "Teleport on join is allready " + ChatColor.DARK_RED + teleportOnJoin + ChatColor.RED + "!";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.ChangedCustomMessages)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				String customMessages;
				if (customMessages()) customMessages = "ingeschakeld"; else customMessages = "uitgeschakeld";
				return ChatColor.GREEN + "Teleporteren bij toetreding is nu " + ChatColor.DARK_GREEN + customMessages + ChatColor.GREEN + "!";
			} else {
				String customMessages;
				if (customMessages()) customMessages = "enabled"; else customMessages = "disabled";
				return ChatColor.GREEN + "Teleport on join is now " + ChatColor.DARK_GREEN + customMessages + ChatColor.GREEN + "!";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.NotChangedCustomMessages)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				String customMessages;
				if (customMessages()) customMessages = "ingeschakeld"; else customMessages = "uitgeschakeld";
				return ChatColor.RED + "Teleporteren bij toetreding is al " + ChatColor.DARK_RED + customMessages + ChatColor.RED + "!";
			} else {
				String customMessages;
				if (customMessages()) customMessages = "enabled"; else customMessages = "disabled";
				return ChatColor.RED + "Teleport on join is allready " + ChatColor.DARK_RED + customMessages + ChatColor.RED + "!";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.GetJoinMessage)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				return ChatColor.GREEN + "Het huidige toetredings bericht is:";
			} else {
				return ChatColor.GREEN + "The current join message is:";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.GetQuitMessage)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				return ChatColor.GREEN + "Het huidige verlatings bericht is:";
			} else {
				return ChatColor.GREEN + "The current leave message is:";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.SetPrefixMessage)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				return ChatColor.GREEN + "De prefix is ingesteld op: " + getPrefix();
			} else {
				return ChatColor.GREEN + "The prefix is set to: " + getPrefix();
			}
			
		} else if (messageType.equals(ServerSpawnMessages.SpawnCommandDisabled)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
				ChatColor.RED + "Dit commando is uitgeschakeld!";
			else return
				ChatColor.RED + "This command is disabled!";
			
		} else if (messageType.equals(ServerSpawnMessages.ChangedHubCommand)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				String HubCommand;
				if (spawnCommandIsEnabled(spawnCommand.Hub)) HubCommand = "ingeschakeld"; else HubCommand = "uitgeschakeld";
				return ChatColor.GREEN + "Het /hub commando is nu " + ChatColor.DARK_GREEN + HubCommand + ChatColor.GREEN + "!";
			} else {
				String HubCommand;
				if (spawnCommandIsEnabled(spawnCommand.Hub)) HubCommand = "enabled"; else HubCommand = "disabled";
				return ChatColor.GREEN + "The /hub command is now " + ChatColor.DARK_GREEN + HubCommand + ChatColor.GREEN + "!";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.NotChangedHubCommand)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				String HubCommand;
				if (spawnCommandIsEnabled(spawnCommand.Hub)) HubCommand = "ingeschakeld"; else HubCommand = "uitgeschakeld";
				return ChatColor.RED + "Het /hub commando is al " + ChatColor.DARK_RED + HubCommand + ChatColor.RED + "!";
			} else {
				String HubCommand;
				if (spawnCommandIsEnabled(spawnCommand.Hub)) HubCommand = "enabled"; else HubCommand = "disabled";
				return ChatColor.RED + "The /hub command is allready " + ChatColor.DARK_RED + HubCommand + ChatColor.RED + "!";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.ChangedSpawnCommand)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				String SpawnCommand;
				if (spawnCommandIsEnabled(spawnCommand.Spawn)) SpawnCommand = "ingeschakeld"; else SpawnCommand = "uitgeschakeld";
				return ChatColor.GREEN + "Het /spawn commando is nu " + ChatColor.DARK_GREEN + SpawnCommand + ChatColor.GREEN + "!";
			} else {
				String SpawnCommand;
				if (spawnCommandIsEnabled(spawnCommand.Spawn)) SpawnCommand = "enabled"; else SpawnCommand = "disabled";
				return ChatColor.GREEN + "The /spawn command is now " + ChatColor.DARK_GREEN + SpawnCommand + ChatColor.GREEN + "!";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.NotChangedSpawnCommand)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				String SpawnCommand;
				if (spawnCommandIsEnabled(spawnCommand.Spawn)) SpawnCommand = "ingeschakeld"; else SpawnCommand = "uitgeschakeld";
				return ChatColor.RED + "Het /spawn commando is al " + ChatColor.DARK_RED + SpawnCommand + ChatColor.RED + "!";
			} else {
				String SpawnCommand;
				if (spawnCommandIsEnabled(spawnCommand.Spawn)) SpawnCommand = "enabled"; else SpawnCommand = "disabled";
				return ChatColor.RED + "The /spawn command is allready " + ChatColor.DARK_RED + SpawnCommand + ChatColor.RED + "!";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.ChangedLobbyCommand)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				String LobbyCommand;
				if (spawnCommandIsEnabled(spawnCommand.Lobby)) LobbyCommand = "ingeschakeld"; else LobbyCommand = "uitgeschakeld";
				return ChatColor.GREEN + "Het /lobby commando is nu " + ChatColor.DARK_GREEN + LobbyCommand + ChatColor.GREEN + "!";
			} else {
				String LobbyCommand;
				if (spawnCommandIsEnabled(spawnCommand.Lobby)) LobbyCommand = "enabled"; else LobbyCommand = "disabled";
				return ChatColor.GREEN + "The /lobby command is now " + ChatColor.DARK_GREEN + LobbyCommand + ChatColor.GREEN + "!";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.NotChangedSpawnCommand)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				String LobbyCommand;
				if (spawnCommandIsEnabled(spawnCommand.Lobby)) LobbyCommand = "ingeschakeld"; else LobbyCommand = "uitgeschakeld";
				return ChatColor.RED + "Het /lobby commando is al " + ChatColor.DARK_RED + LobbyCommand + ChatColor.RED + "!";
			} else {
				String LobbyCommand;
				if (spawnCommandIsEnabled(spawnCommand.Lobby)) LobbyCommand = "enabled"; else LobbyCommand = "disabled";
				return ChatColor.RED + "The /lobby command is allready " + ChatColor.DARK_RED + LobbyCommand + ChatColor.RED + "!";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.PrefixChanged)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				String Prefix;
				if (prefixEnabled()) Prefix = "ingeschakeld"; else Prefix = "uitgeschakeld";
				return ChatColor.GREEN + "De prefix is nu " + ChatColor.DARK_GREEN + Prefix + ChatColor.GREEN + "!";
			} else {
				String Prefix;
				if (prefixEnabled()) Prefix = "enabled"; else Prefix = "disabled";
				return ChatColor.GREEN + "The prefix is now " + ChatColor.DARK_GREEN + Prefix + ChatColor.GREEN + "!";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.PrefixNotChanged)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				String Prefix;
				if (prefixEnabled()) Prefix = "ingeschakeld"; else Prefix = "uitgeschakeld";
				return ChatColor.RED + "De prefix was al " + ChatColor.DARK_RED + Prefix + ChatColor.RED + "!";
			} else {
				String Prefix;
				if (prefixEnabled()) Prefix = "enabled"; else Prefix = "disabled";
				return ChatColor.RED + "The prefix is allready " + ChatColor.DARK_RED + Prefix + ChatColor.RED + "!";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.LanguageChanged)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				String Language;
				if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) Language = "Nederlands"; else Language = "Engels";
				return ChatColor.GREEN + "De taal is nu ingesteld op " + ChatColor.DARK_GREEN + Language + ChatColor.GREEN + "!";
			} else {
				String Language;
				if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) Language = "Dutch"; else Language = "English";
				return ChatColor.GREEN + "The language is now set to " + ChatColor.DARK_GREEN + Language + ChatColor.GREEN + "!";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.LanguageNotChanged)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
				String Language;
				if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) Language = "Nederlands"; else Language = "Engels";
				return ChatColor.RED + "De taal is al ingesteld op " + ChatColor.DARK_RED + Language + ChatColor.RED + "!";
			} else {
				String Language;
				if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) Language = "Dutch"; else Language = "English";
				return ChatColor.RED + "The language is allready set to " + ChatColor.DARK_RED + Language + ChatColor.RED + "!";
			}
			
		} else if (messageType.equals(ServerSpawnMessages.ServerSpawnNotSet)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
				ChatColor.RED + "De server spawn is nog niet gezet!";
			else return
				ChatColor.RED + "The server spawn in not set!";
			
		} else if (messageType.equals(ServerSpawnMessages.NeedToBeAPlayer)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
				ChatColor.RED + "Je moet een speler zijn om dit te kunnen doen!";
			else return
				ChatColor.RED + "You need to be a player to do this!";
			
		} else if (messageType.equals(ServerSpawnMessages.IncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
				ChatColor.RED + "§lOnjuist argument!";
			else return
				ChatColor.RED + "§lIncorrect argument!";
			
		} else if (messageType.equals(ServerSpawnMessages.ServerSpawnIncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
				ChatColor.RED + "Gebruik /serverspawn [set | get | enable | disable | settings].";
			else return
				ChatColor.RED + "Use /serverspawn [set | get | enable | disable | settings].";
			
		} else if (messageType.equals(ServerSpawnMessages.ServerSpawnSettingsIncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
				ChatColor.RED + "Gebruik /serverspawn settings [prefix | language].";
			else return
				ChatColor.RED + "Use /serverspawn settings [prefix | language].";
			
		} else if (messageType.equals(ServerSpawnMessages.ServerSpawnPrefixIncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
				ChatColor.RED + "Gebruik /serverspawn settings prefix [set | enable | disable].";
			else return
				ChatColor.RED + "Use /serverspawn settings prefix [set | enable | disable].";
			
		} else if (messageType.equals(ServerSpawnMessages.ServerSpawnPrefixSetIncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
				ChatColor.RED + "Gebruik /serverspawn settings prefix set [message].";
			else return
				ChatColor.RED + "Use /serverspawn settings prefix set [message].";
			
		} else if (messageType.equals(ServerSpawnMessages.ServerSpawnLanguageIncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
				ChatColor.RED + "Gebruik /serverspawn settings language [English | Dutch].";
			else return
				ChatColor.RED + "Use /serverspawn settings language [English | Dutch].";
			
		} else if (messageType.equals(ServerSpawnMessages.FakeIncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
				ChatColor.RED + "Gebruik /fake [join | leave].";
			else return
				ChatColor.RED + "Use /fake [join | leave].";
			
		} else if (messageType.equals(ServerSpawnMessages.CustomMessagesIncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
					ChatColor.RED + "Gebruik /custommessage [enable | disable].";
			else return
				ChatColor.RED + "Use /custommessage [enable | disable].";
			
		} else if (messageType.equals(ServerSpawnMessages.JoinMessageIncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
					ChatColor.RED + "Gebruik /joinmessage [set | get].";
			else return
					ChatColor.RED + "Use /joinmessage [set | get].";
			
		} else if (messageType.equals(ServerSpawnMessages.SetJoinMessageIncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
					ChatColor.RED + "Gebruik /joinmessage set <bericht>.";
			else return
					ChatColor.RED + "Use /joinmessage set <message>.";
			
		} else if (messageType.equals(ServerSpawnMessages.QuitMessageIncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
					ChatColor.RED + "Gebruik /joinmessage [set | get].";
			else return
					ChatColor.RED + "Use /joinmessage [set | get].";
			
		} else if (messageType.equals(ServerSpawnMessages.SetQuitMessageIncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
					ChatColor.RED + "Gebruik /leavemessage set <bericht>.";
			else return
					ChatColor.RED + "Use /leavemessage set <message>.";
			
		} else if (messageType.equals(ServerSpawnMessages.SpawnCommandsIncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
					ChatColor.RED + "Gebruik /spawncommands [hub | spawn | lobby] [enable | disable].";
			else return
					ChatColor.RED + "Use /spawncommands [hub | spawn | lobby] [enable | disable].";
			
		} else return null;
	}
	
	public static void sendChatColorList(Player p) {
		if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) {
			p.sendMessage(ChatColor.DARK_RED + "Donker rood: &4");
			p.sendMessage(ChatColor.RED + "Rood: &c");
			p.sendMessage(ChatColor.GOLD + "Goud: &6");
			p.sendMessage(ChatColor.YELLOW + "Geel: &e");
			p.sendMessage(ChatColor.DARK_GREEN + "Donker groen: &2");
			p.sendMessage(ChatColor.GREEN + "Groen: &a");
			p.sendMessage(ChatColor.DARK_AQUA + "Donker Aqua: &3");
			p.sendMessage(ChatColor.AQUA + "Aqua: &b");
			p.sendMessage(ChatColor.DARK_BLUE + "Donker blauw: &1");
			p.sendMessage(ChatColor.BLUE + "Blauw: &9");
			p.sendMessage(ChatColor.DARK_PURPLE + "Donker paars: &5");
			p.sendMessage(ChatColor.LIGHT_PURPLE + "Paars: &d");
			p.sendMessage(ChatColor.WHITE + "Wit: &f");
			p.sendMessage(ChatColor.DARK_GRAY + "Doker grijs: &8");
			p.sendMessage(ChatColor.GRAY + "Grijs: &7");
			p.sendMessage(ChatColor.BLACK + "Zwart: &0");
			p.sendMessage(ChatColor.WHITE + "Reset Colors: &r");
			p.sendMessage(ChatColor.BOLD + "Dikgedrukt: &l");
			p.sendMessage(ChatColor.ITALIC + "Schuingedrukt: &o");
			p.sendMessage(ChatColor.UNDERLINE + "Onderstreept: &n");
			p.sendMessage(ChatColor.STRIKETHROUGH + "Doorstreepts: &m");
			p.sendMessage(ChatColor.WHITE + "Draaiend: &k");
		} else {
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
	}
	
	//
	//Booleans
	//
	
	public static boolean teleportOnJoin() {
		return ServerSpawn.getBoolean("Teleport on join");
	}
	
	public static boolean customMessages() {
		return ServerSpawn.getBoolean("Custom messages");
	}
	
	public static boolean prefixEnabled() {
		return Messages.getBoolean("Prefix.Enabled");
	}
	
	public static enum spawnCommand {
		Hub,
		Spawn,
		Lobby
	}
	
	public static boolean spawnCommandIsEnabled(spawnCommand spawnCommandName) {
		if (spawnCommandName.equals(spawnCommand.Hub)) return ServerSpawn.getBoolean("Spawn Commands Enabled.Hub");
		else if (spawnCommandName.equals(spawnCommand.Spawn)) return ServerSpawn.getBoolean("Spawn Commands Enabled.Spawn");
		else return ServerSpawn.getBoolean("Spawn Commands Enabled.Lobby");
	}
	
	//
	//Setting data
	//
	
	public static void setServerSpawn(Location location) {
		ServerSpawn.set("Server spawn", location);
		saveFiles();
	}
	
	public static void setTeleportOnJoin(boolean bool) {
		ServerSpawn.set("Teleport on join", bool);
		saveFiles();
	}
	
	public static void setCustomMessages(boolean bool) {
		ServerSpawn.set("Custom messages", bool);
		saveFiles();
	}
	
	public static void setJoinMessage(String message) {
		ServerSpawn.set("Custom messages.Join message", message);
		saveFiles();
	}
	
	public static void setQuitMessage(String message) {
		ServerSpawn.set("Custom messages.Join message", message);
		saveFiles();
	}
	
	public static void setPrefixEnabled(boolean bool) {
		Messages.set("Prefix.Enabled", bool);
	}
	
	public static void setPrefix(String message) {
		Messages.set("Prefix.Message", message);
	}
	
	public static void setSpawnCommand(spawnCommand spawnCommandName, boolean bool) {
		if (spawnCommandName.equals(spawnCommand.Hub)) ServerSpawn.set("Spawn Commands Enabled.Hub", bool);
		else if (spawnCommandName.equals(spawnCommand.Spawn)) ServerSpawn.set("Spawn Commands Enabled.Spawn", bool);
		else ServerSpawn.set("Spawn Commands Enabled.Lobby", bool);
		saveFiles();
	}
	
	public static void setLanguage(ServerSpawnLanguage language) {
		Messages.set("Language", language);
		saveFiles();
	}
	
	//
	//Data transmitting
	//
	
	public static enum ServerSpawnLanguage {
		ENGLISH,
		DUTCH
	}
	
	public static String placeholderReplace(String string, Player p) {
		string = string.replace("%playername%", p.getName());
		return string;
	}
	
	static void saveFiles() {
		try {
			ServerSpawn.save(ServerSpawnFile);
			Messages.save(MessagesFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
