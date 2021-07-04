package miyukisystem.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KeepLevelExpListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();

        if (!(player.hasPermission("miyukisystem.keepxp"))) return;

        event.setKeepLevel(true);
        event.setDroppedExp(0);
    }

}
