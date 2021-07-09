package miyukisystem.listener.impl;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class Motd implements Listener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent e) {
        if(Bukkit.hasWhitelist()) {
            e.setMotd("WhitelistMotd");
            e.setMaxPlayers(0); // colocar um getInt para pegar dá config.
        } else {
            e.setMotd("NormalMotd");
            e.setMaxPlayers(200); // colocar um getInt para pegar dá config.
        }
    }
}
