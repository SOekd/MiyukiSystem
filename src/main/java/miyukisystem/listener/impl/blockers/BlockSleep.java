package miyukisystem.listener.impl.blockers;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class BlockSleep implements Listener {

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("Blockers.Sleep")) return;

        event.setCancelled(true);

    }
}
