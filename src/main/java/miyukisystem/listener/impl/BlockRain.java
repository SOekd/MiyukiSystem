package miyukisystem.listener.impl;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class BlockRain implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onWeatherChange(WeatherChangeEvent event) {

        if (!ConfigManager.Companion.getConfig().config.getBoolean("Blockers.RainCancel")) return;

        if (event.toWeatherState()) {
            event.setCancelled(true);
        }
    }
}
