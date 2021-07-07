package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.ConfigManager;
import miyukisystem.util.LocationUtilKt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SpawnCommand extends CommandService {

    public SpawnCommand() {
        super("Spawn", ""); // não precisa de perm.
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        if (args.length > 1 && sender.hasPermission("miyukisystem.spawn.other")) {
            sender.sendMessage("IncorrectSpawnAdminCommand");
            return false;
        }

        if (args.length > 1) {
            sender.sendMessage("IncorrectSpawnCommand");
            return false;
        }

        // se o cara apertar tab no primeiro argumento, vir a lista de players (SOekd) pra teleportar o cara pro spawn (se o cara tiver a perm miyukisystem.spawn.other)

        Player player = (Player) sender;
        YamlConfiguration config = ConfigManager.Companion.getLocations().config;
        Location spawn = LocationUtilKt.toLocation(config.getString("Spawn"));

        if (args.length == 0) {

            if (config.contains("Spawn")) {
                player.teleport(spawn);
            } else {
                World world = Bukkit.getWorld("world");
                Location notSpawn = new Location(world, 0, 60, 0);
                player.teleport(notSpawn);
            }

            player.sendMessage("TeleportedSpawnSuccess");
            player.playSound(player.getLocation(), Sound.valueOf("LEVEL_UP"), 1.0f, 1.0f);

        }

        if (args.length == 1) {

            if (!(sender.hasPermission("miyukisystem.sendspawn"))) {
                sender.sendMessage("NoPermission");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage("Offline");
                return false;
            }

            target.teleport(spawn);
            player.sendMessage("SentToSpawn"); // {player} retorna o target.getName()
            target.sendMessage("ForcedTeleportSpawn");
        }

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
