package miyukisystem.commands.impl.vanish;

import miyukisystem.Main;
import miyukisystem.commands.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VanishCommand extends CommandService {

    public VanishCommand() {
        super("Vanish", "miyukisystem.vanish");
    }

    // SOekd

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        if (!(sender.hasPermission("miyukisystem.vanish"))) {
            sender.sendMessage("NoPermission");
            return false;
        }

        Player player = (Player) sender;

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
