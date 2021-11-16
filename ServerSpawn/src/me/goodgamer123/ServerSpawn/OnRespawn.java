package me.goodgamer123.ServerSpawn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class OnRespawn implements Listener {

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		if (API.respawnAtServerSpawn()) e.setRespawnLocation(API.getServerSpawn());
	}
	
}
