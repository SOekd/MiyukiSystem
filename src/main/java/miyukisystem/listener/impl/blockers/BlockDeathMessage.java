package miyukisystem.listener.impl.blockers;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class BlockDeathMessage implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("Blockers.DeathMessage")) return;

        event.setDeathMessage(null);
    }
}
