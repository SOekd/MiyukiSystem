package miyukisystem.listener;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class FreezeWaterListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onFreeze(BlockFormEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("FreezeWater")) return;

        if (event.getBlock().getType() == Material.WATER) {
            event.setCancelled(true);
        }

    }
}
