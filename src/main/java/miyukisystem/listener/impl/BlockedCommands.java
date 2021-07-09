package miyukisystem.listener.impl;

import lombok.val;
import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BlockedCommands implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        val args = e.getMessage().split(" ");
        val player = e.getPlayer();

        if(player.hasPermission("miyukisystem.bypass.blockedcommands")) return;

        if(ConfigManager.Companion.getConfig().config.getStringList("BlockedCommands").contains(args[0].toLowerCase())) {
            player.sendMessage("BlockedCommand");
            e.setCancelled(true);
        }
    }
}
