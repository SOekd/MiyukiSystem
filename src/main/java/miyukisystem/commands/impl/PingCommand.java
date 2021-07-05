package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCommand extends CommandService {

    public PingCommand() {
        super("Ping");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        if (!(sender.hasPermission("miyukisystem.ping"))) {
            sender.sendMessage("Pong");
            return false;
        }

        Player player = (Player) sender;
        player.sendMessage("Ping".replace("{ping}", String.valueOf(player.getPing())));

        return false;
    }
}
