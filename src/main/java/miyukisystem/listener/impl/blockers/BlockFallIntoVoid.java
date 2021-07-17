package miyukisystem.listener.impl.blockers;

import miyukisystem.manager.impl.ConfigManager;
import miyukisystem.manager.impl.PlayerManagerKt;
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
                PlayerManagerKt.toSpawn(player);
            }
        }
    }

}
