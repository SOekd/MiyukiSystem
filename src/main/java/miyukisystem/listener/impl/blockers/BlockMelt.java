package miyukisystem.listener.impl.blockers;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

public class BlockMelt implements Listener {

    @EventHandler
    public void onMelt(BlockFadeEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("Blockers.Melt")) return;

        if (event.getBlock().getType() == Material.ICE || event.getBlock().getType() == Material.SNOW) event.setCancelled(true);

    }
}
