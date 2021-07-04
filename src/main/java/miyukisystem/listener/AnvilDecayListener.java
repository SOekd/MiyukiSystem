package miyukisystem.listener;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class AnvilDecayListener implements Listener {

    /*
     * Código criado por iPyronic
     * Link: https://www.spigotmc.org/threads/prevent-sand-from-falling-upon-placing-sand.133386/
     */

    @EventHandler(ignoreCancelled = true)
    public void onAnvilDecay(EntityChangeBlockEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("AnvilDecay")) return;

        if (event.getEntityType() == EntityType.FALLING_BLOCK && event.getTo() == Material.AIR) {
            if (event.getBlock().getType() == Material.ANVIL) {
                event.setCancelled(true);
                event.getBlock().getState().update(false, false);
            }
        }
    }
}