package miyukisystem.listener.impl.blockers;

import lombok.val;
import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class BlockGetIntoVehicles implements Listener {

    @EventHandler
    public void onVehicleEnterEvent(VehicleEnterEvent e) {

        val entity = e.getEntered();
        val config = ConfigManager.Companion.getConfig().config;

        if(!(entity instanceof Player)) {
            if (config.getBoolean("Blockers.EntityGetIntoVehicles")) e.setCancelled(true);
        } else {
            if (config.getBoolean("Blockers.PlayerGetIntoVehicles")) e.setCancelled(true);
        }
    }
}
