package miyukisystem.listener.impl;

import lombok.val;
import miyukisystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class VanishNewPlayers implements Listener {

    // Esconder os jogadores de vanish para novos jogadores.

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        val player = e.getPlayer();
        val plugin = Main.Companion.getInstance();

        Bukkit.getOnlinePlayers().forEach(it -> {
            if(it.hasMetadata("miyukisystem_vanish")) {
                player.hidePlayer(plugin, it);
            }
        });
    }
}
