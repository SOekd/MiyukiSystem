package miyukisystem.listener;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class CreatePortalListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCreatingPortal(PortalCreateEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("CreatePortal")) return;

        event.setCancelled(true);
    }

}
