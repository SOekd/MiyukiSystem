package miyukisystem.listener.impl;

import lombok.val;
import miyukisystem.manager.impl.ConfigManager;
import miyukisystem.manager.impl.WhitelistManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class Motd implements Listener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent e) {

        val config = ConfigManager.Companion.getConfig().config;

        if(StringUtils.isEmpty(config.getString("Motd.WhitelistMotd")) || StringUtils.isEmpty(config.getString("Motd.NormalMotd"))) return;

        if (Bukkit.hasWhitelist() || WhitelistManager.Companion.getState()) {
            e.setMotd(config.getString("Motd.WhitelistMotd"));
            e.setMaxPlayers(config.getInt("Motd.WhitelistMaxPlayers"));
        } else {
            e.setMotd(config.getString("Motd.NormalMotd"));
            e.setMaxPlayers(config.getInt("Motd.NormalMaxPlayers"));
        }
    }
}
