package miyukisystem.listener;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class LeavesDecayListener implements Listener {

    @EventHandler
    public void onLeafFall(LeavesDecayEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("Blockers.LeavesDecay")) return;

        event.setCancelled(true);

    }

}
