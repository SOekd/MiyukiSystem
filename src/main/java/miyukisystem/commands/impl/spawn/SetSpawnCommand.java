package miyukisystem.commands.impl.spawn;

import miyukisystem.commands.CommandService;
import miyukisystem.util.LocationUtilKt;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class SetSpawnCommand extends CommandService {

    // 0 60 0 > location padrão (setada por padrão)

    public SetSpawnCommand() {
        super("SetSpawn", "miyukisystem.setspawn");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        Player player = (Player) sender;

        Location spawn = player.getLocation();
        // seta no locationmanager

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
