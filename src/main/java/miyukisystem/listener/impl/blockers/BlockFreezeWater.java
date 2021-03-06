package miyukisystem.listener.impl.blockers;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class BlockFreezeWater implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onFreeze(BlockFormEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("Blockers.FreezeWater")) return;

        if (event.getBlock().getType() == Material.WATER) {
            event.setCancelled(true);
        }

    }
}
