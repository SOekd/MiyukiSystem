package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VanishCommand extends CommandService {

    public VanishCommand() {
        super("Vanish");
    }

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
}
