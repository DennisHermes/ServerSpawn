package me.goodgamer123.ServerSpawn;

import java.io.File;

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
			ServerSpawn.set("ServerSpawn.X", "");
        	ServerSpawn.set("ServerSpawn.Y", "");
    		ServerSpawn.set("ServerSpawn.X", "");
    		ServerSpawn.set("ServerSpawn.Pitch", "");
    		ServerSpawn.set("ServerSpawn.Yaw", "");
    		ServerSpawn.set("ServerSpawn.World", "");
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
	
	static void saveFiles() {
		try {
			ServerSpawn.save(ServerSpawnFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
