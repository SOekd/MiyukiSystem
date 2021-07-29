package miyukisystem.listener.impl.blockers;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class BlockPortalCreation implements Listener {

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("Blockers.CreatePortal")) return;

        event.setCancelled(true);
    }

}
