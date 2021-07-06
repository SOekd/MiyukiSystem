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

public class HealCommand extends CommandService {

    public HealCommand() {
        super("Heal", "miyukisystem.heal");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player) && args.length != 1) {
            sender.sendMessage("NoConsole");
            return false;
        }

        Player player;

        if (args.length > 0) {

            if (!(sender.hasPermission("miyukisystem.heal.other"))) {
                sender.sendMessage("NoPermission");
                return false;
            }

            player = Bukkit.getPlayer(args[0]);

            if (player == null) {
                sender.sendMessage("Offline");
                return false;
            }

            if (player.getHealth() >= 20.0) {
                sender.sendMessage("FullLifePlayer");
                return false;
            }

            sender.sendMessage("HealedPlayer");
        } else {

            player = (Player) sender;

            if (player.getHealth() >= 20.0) {
                player.sendMessage("FullLife");
                return false;
            }

            player.sendMessage("Healed");
        }

        player.setHealth(20.0);

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (player == null || !player.hasPermission("miyukisystem.heal.other")) return Collections.emptyList();
        return Bukkit.getOnlinePlayers().stream()
                .filter(player::canSee)
                .map(HumanEntity::getName)
                .collect(Collectors.toList());
    }
}
