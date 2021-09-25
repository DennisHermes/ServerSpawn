package me.goodgamer123.ServerSpawn;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		  
		if (API.teleportOnJoin() || !p.hasPlayedBefore()) {
			p.teleport(API.getServerSpawn());
		}
		
		if (API.customMessages()) {
			e.setJoinMessage(API.getJoinMessage());
		}
	}
}
