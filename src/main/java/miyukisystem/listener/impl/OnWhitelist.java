package miyukisystem.listener.impl;

import lombok.val;
import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnWhitelist implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        val config = ConfigManager.Companion.getData().config;

        val player = e.getPlayer();

    }
}
