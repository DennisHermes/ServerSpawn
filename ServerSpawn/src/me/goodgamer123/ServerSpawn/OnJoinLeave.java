package me.goodgamer123.ServerSpawn;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnJoinLeave implements Listener {
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		  
		if (API.teleportOnJoin() || !p.hasPlayedBefore()) {
			p.teleport(API.getServerSpawn());
		}
		
		if (API.customMessages()) {
			e.setJoinMessage(API.getJoinMessage(e.getPlayer()));
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (API.customMessages()) {
			e.setQuitMessage(API.getQuitMessage(e.getPlayer()));
		}
	}
}
