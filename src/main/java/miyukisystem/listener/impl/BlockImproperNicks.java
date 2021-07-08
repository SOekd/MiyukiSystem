package miyukisystem.listener.impl;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class BlockImproperNicks implements Listener {

    private final YamlConfiguration config = ConfigManager.Companion.getConfig().config;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        for (String name : config.getStringList("BlockedNicks")) {
            if (name.equals(event.getPlayer().getName())) {
                event.getPlayer().kickPlayer("ImproperNick");
            }
        }

    }

}
