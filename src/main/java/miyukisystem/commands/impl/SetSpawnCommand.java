package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.ConfigManager;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.util.LocationUtilKt;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class SetSpawnCommand extends CommandService {

    public SetSpawnCommand() {
        super("SetSpawn", "miyukisystem.setspawn", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        if (args.length != 0) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectSetSpawnCommand");
            return false;
        }

        Player player = (Player) sender;

        Location spawn = player.getLocation();
        YamlConfiguration config = ConfigManager.Companion.getLocations().config;
        config.set("Spawn", LocationUtilKt.toCustomString(spawn));
        ConfigManager.Companion.getLocations().saveConfig();

        MessageManagerKt.sendCustomMessage(player, "SettedSpawn");

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
