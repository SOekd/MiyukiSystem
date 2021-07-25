package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.LocationManager;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.model.CachedLocation;
import org.bukkit.command.CommandSender;
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

        val player = (Player) sender;

        val spawn = player.getLocation();
        LocationManager.Companion.set(new CachedLocation("Spawn", spawn));
        MessageManagerKt.sendCustomMessage(player, "SettedSpawnSuccess");

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
