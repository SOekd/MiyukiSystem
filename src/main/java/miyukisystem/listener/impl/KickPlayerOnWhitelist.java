package miyukisystem.listener.impl;

import lombok.val;
import miyukisystem.manager.impl.ConfigManager;
import miyukisystem.manager.impl.MessageManager;
import miyukisystem.manager.impl.WhitelistManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class KickPlayerOnWhitelist implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        val config = ConfigManager.Companion.getData().config;
        val player = e.getPlayer();
        val manager = WhitelistManager.Companion;

        if(!manager.getState()) return;

        if(config.getBoolean("WhitelistState")) {
            if(!manager.has(player.getName())) {
                player.kickPlayer(String.join("\n", MessageManager.Companion.getMessage("WhitelistOnKick")));
            }
        }
    }
}
