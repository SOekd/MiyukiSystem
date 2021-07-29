package miyukisystem.listener.impl;

import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class VanishNewPlayers implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        val player = e.getPlayer();

        Bukkit.getOnlinePlayers().forEach(it -> {
            if(!it.hasPermission("miyukisystem.bypass.vanish") && it.hasMetadata("miyukisystem_vanish")) {
                player.hidePlayer(it);
            }
        });
    }
}
