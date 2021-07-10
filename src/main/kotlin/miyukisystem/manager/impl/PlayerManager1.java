package miyukisystem.manager.impl;

import miyukisystem.util.LocationUtilKt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerManager1 {

    private static final YamlConfiguration config = ConfigManager.Companion.getLocations().config;

    public static void teleportToSpawn(Player player) {

        if (config.contains("Spawn")) {
            Location spawn = LocationUtilKt.toLocation(config.getString("Spawn"));
            player.teleport(spawn);
        } else {
            Location spawn = new Location(Bukkit.getWorld("world"), 0, 60, 0);
            player.teleport(spawn);
        }

    }
}
