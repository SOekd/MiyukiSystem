package miyukisystem.listener.impl;

import miyukisystem.manager.impl.PlayerManager1;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TeleportToSpawn implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        // if (!ConfigManager.Companion.getConfig().config.getBoolean("Spawn"))
        PlayerManager1.teleportToSpawn(event.getPlayer());

    }

}
