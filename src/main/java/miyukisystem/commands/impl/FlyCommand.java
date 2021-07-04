package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand extends CommandService {

    public FlyCommand() {
        super("fly");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player) ) {
            sender.sendMessage("NoConsole");
            return false;
        }

        if (!sender.hasPermission("miyukisystem.fly")) {
            sender.sendMessage("NoPermission");
            return false;
        }

        Player player = (Player) sender;
        player.setAllowFlight(!player.getAllowFlight());
        player.sendMessage(player.getAllowFlight() ? "FlyActivated" : "FlyDisabled"); // necessita de testes, não sei se irá funcionar

        return false;
    }
}
