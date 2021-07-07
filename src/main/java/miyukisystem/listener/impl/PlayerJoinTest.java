package miyukisystem.listener.impl;

import miyukisystem.listener.ListenerService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinTest extends ListenerService {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        event.getPlayer().sendMessage("teste listener.");
    }

}
