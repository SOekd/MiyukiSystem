package miyukisystem.listener.impl;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class BlockFallIntoVoid implements Listener {

    @EventHandler
    public void onFallVoid(EntityDamageEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("Blockers.FallIntoVoid")) return;

        if (event.getCause() == DamageCause.VOID && event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getLocation().getBlockY() < 0) {
                event.setCancelled(true);
                player.setFallDistance(1);
                // teleportar até o spawn
            }
        }
    }
}
