package miyukisystem.listener.impl.blockers;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BlockItemExplosion implements Listener {

    @EventHandler
    public void onExplode(EntityDamageByEntityEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("Blockers.ItemExplosion")) return;

        if (event.getEntity() instanceof Item) event.setCancelled(true);

    }
}
