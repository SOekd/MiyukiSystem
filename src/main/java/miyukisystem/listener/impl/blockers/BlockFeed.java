package miyukisystem.listener.impl.blockers;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class BlockFeed implements Listener {

    @EventHandler
    public void onChangeFoodLevel(FoodLevelChangeEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("Blockers.Feed")) return;

        event.setCancelled(true);
    }
}
