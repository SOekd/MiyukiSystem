package miyukisystem.listener.impl.blockers;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BlockPortalTeleport implements Listener {

    YamlConfiguration config = ConfigManager.Companion.getConfig().config;

    @EventHandler
    public void onTeleport(PlayerPortalEvent event) {

        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL && config.getBoolean("Blockers.Portal.Nether")) {
            event.setCancelled(true);
        }

        if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL && config.getBoolean("Blockers.Portal.End")) {
            event.setCancelled(true);
        }

    }
}
