package me.goodgamer123.ServerSpawn;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
		final File MessagesYml = new File(MainClass.getDataFolder() + "/Messages.yml");
		
		ServerSpawnFile = new File(MainClass.getDataFolder(), "/ServerSpawn.yml");
		ServerSpawn = YamlConfiguration.loadConfiguration(ServerSpawnFile);
		
		if (!ServerSpawnYml.exists()) {
			ServerSpawn.set("Server spawn", null);
    		ServerSpawn.set("Teleport on join", true);
    		ServerSpawn.set("Custom messages", true);
			saveFiles();
		}
		
		MessagesFile = new File(MainClass.getDataFolder(), "/Messages.yml");
		Messages = YamlConfiguration.loadConfiguration(MessagesFile);
		
		if (!MessagesYml.exists()) {
			Messages.set("Custom messages.Join message", "&0[&2+&0] &8%player%");
			Messages.set("Custom messages.Leave message", "&0[&4-&0] &8%player%");
			saveFiles();
		}
	}

	//
	//Gatherers
	//
	
	public static Location getServerSpawn() {
		if (ServerSpawn.getLocation("Server spawn") == null) return null; else {
			return ServerSpawn.getLocation("Server spawn");
		}
	}
	
	public static String getJoinMessage() {
		String joinMessage = ChatColor.translateAlternateColorCodes('&', ServerSpawn.getString("Custom messages.Join message"));
		joinMessage = placeholderReplace(joinMessage);
		return joinMessage;
	}
	
	public static String getRawJoinMessage() {
		return ServerSpawn.getString("Custom messages.Join message");
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
	//Data transmitting
	//
	
	public static String placeholderReplace(String string) {
		//TODO
		return string;
	}
	static void saveFiles() {
		try {
			ServerSpawn.save(ServerSpawnFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
