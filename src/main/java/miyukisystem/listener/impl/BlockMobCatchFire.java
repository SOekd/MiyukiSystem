package miyukisystem.listener.impl;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;

public class BlockMobCatchFire implements Listener {

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("Blockers.MobsCatchFire")) return;

        if (!(event instanceof EntityCombustByEntityEvent) && !(event instanceof EntityCombustByBlockEvent)) {
            event.setCancelled(true);
        }

    }
}
