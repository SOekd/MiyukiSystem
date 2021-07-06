package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FlyCommand extends CommandService {

    public FlyCommand() {
        super("fly", "miyukisystem.fly");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player) ) {
            sender.sendMessage("NoConsole");
            return false;
        }

        if (args.length > 0) {

            if (!(sender.hasPermission("miyukisystem.fly.other"))) {
                sender.sendMessage("NoPermission");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage("Offline");
                return false;
            }

            target.setAllowFlight(!target.getAllowFlight());
            sender.sendMessage(target.getAllowFlight() ? "&aO modo voar de &7{player} &afoi ativado." : "&cO modo voar de &7{player} &cfoi desativado.");

            return true;
        }

        Player player = (Player) sender;
        player.setAllowFlight(!player.getAllowFlight());
        player.sendMessage(player.getAllowFlight() ? "FlyActivated" : "FlyDisabled"); // necessita de testes, não sei se irá funcionar

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
