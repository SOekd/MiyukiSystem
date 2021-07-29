package miyukisystem.listener.impl.blockers;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class BlockAnvilDecay implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("Blockers.AnvilDecay")) return;

        if (event.getEntityType() == EntityType.FALLING_BLOCK && event.getTo() == Material.AIR) {
            if (event.getBlock().getType() == Material.ANVIL) {
                event.setCancelled(true);
                event.getBlock().getState().update(false, false);
            }
        }
    }

}
