package me.goodgamer123.ServerSpawn;

import java.io.File;

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
		
		if (!ServerSpawnYml.exists()) {
			ServerSpawn.set("Server spawn", null);
    		ServerSpawn.set("Teleport on join", true);
    		ServerSpawn.set("Custom messages", true);
			saveFiles();
		}
		

		final File MessagesYml = new File(MainClass.getDataFolder() + "/Messages.yml");
		MessagesFile = new File(MainClass.getDataFolder(), "/Messages.yml");
		Messages = YamlConfiguration.loadConfiguration(MessagesFile);
		
		if (!MessagesYml.exists()) {
			Messages.set("Custom messages.Join message", "&0[&2+&0] &8%playername%");
			Messages.set("Custom messages.Quit message", "&0[&4-&0] &8%playername%");
			Messages.set("Prefix", "&0&l[&2Server&aSpawn&0] ");
			Messages.set("Language", ServerSpawnLanguage.ENGLISH);
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
		return (ServerSpawnLanguage) Messages.get("Language");
	}
	
	public static String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', ServerSpawn.getString("Prefix"));
	}
	
	//Messages
	
	public static enum ServerSpawnMessages {
		ServerSpawn,
		ChangedTeleportOnJoin,
		NotChangedTeleportOnJoin,
		NeedToBeAPlayer,
		IncorrectArg,
		ServerSpawnIncorrectArg,
		FakeIncorrectArg,
		CustomMessagesIncorrectArg, 
		ChangedCustomMessages,
		NotChangedCustomMessages
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
			
		} else if (messageType.equals(ServerSpawnMessages.NeedToBeAPlayer)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
				ChatColor.RED + "Je moet een speler zijn om dit te kunnen doen!";
			else return
				ChatColor.RED + "You need to be a player to do this!";
			
		} else if (messageType.equals(ServerSpawnMessages.IncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
				ChatColor.RED + "žlOnjuist argument!";
			else return
				ChatColor.RED + "žlIncorrect argument!";
			
		} else if (messageType.equals(ServerSpawnMessages.ServerSpawnIncorrectArg)) {
			
			if (getLanguage().equals(ServerSpawnLanguage.DUTCH)) return 
				ChatColor.RED + "Gebruik /serverspawn [set | get | enable | disable].";
			else return
				ChatColor.RED + "Use /serverspawn [set | get | enable | disable].";
			
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
			
		} else {
			return null;
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
