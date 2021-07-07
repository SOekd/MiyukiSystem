package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FlyCommand extends CommandService {

    public FlyCommand() {
        super("Fly", "miyukisystem.fly");
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
        Player player = sender instanceof Player ? (Player) sender : null;
        if (player == null || !player.hasPermission("miyukisystem.fly.other")) return Collections.emptyList();
        return Bukkit.getOnlinePlayers().stream()
                .filter(player::canSee)
                .map(HumanEntity::getName)
                .collect(Collectors.toList());
    }
}
