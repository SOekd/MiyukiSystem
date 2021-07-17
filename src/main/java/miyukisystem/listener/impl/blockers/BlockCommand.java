package miyukisystem.listener.impl.blockers;

import lombok.val;
import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.stream.Collectors;

public class BlockCommand implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        val player = e.getPlayer();

        if (player.hasPermission("miyukisystem.bypass.blockedcommands")) return;

        val command = e.getMessage().split(" ")[0].toLowerCase();

        val blockedCommands = ConfigManager.Companion.getCommands().config.getStringList("BlockedCommands").stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        if (blockedCommands.contains(command)) {
            player.sendMessage("BlockedCommand");
            e.setCancelled(true);
        }
    }
}
