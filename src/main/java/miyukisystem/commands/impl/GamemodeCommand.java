package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GamemodeCommand extends CommandService {

    public GamemodeCommand(String name) {
        super("Gamemode");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player) && args.length != 2) {
            sender.sendMessage("NoConsole");
            return false;
        }

        // botar /gamemode 1 /gamemode survival /gamemode sobrevivencia
        // /gamemode 1 nick

        return false;
    }
}
