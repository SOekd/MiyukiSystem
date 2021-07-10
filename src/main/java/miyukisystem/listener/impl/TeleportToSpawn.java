package miyukisystem.listener.impl;

import miyukisystem.manager.impl.PlayerManagerKt;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TeleportToSpawn implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        // if (!ConfigManager.Companion.getConfig().config.getBoolean("Spawn"))
        PlayerManagerKt.toSpawn(event.getPlayer());

    }

}
