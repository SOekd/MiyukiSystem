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

public class FeedCommand extends CommandService {

    public FeedCommand() {
        super("Feed", "miyukisystem.feed");
    }

    // ta com os path

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player) && args.length != 1) {
            sender.sendMessage("NoConsole");
            return false;
        }

        Player player;
        if (args.length > 0) {

            if (!(sender.hasPermission("miyukisystem.feed.other"))) {
                sender.sendMessage("NoPermission");
                return false;
            }

            player = Bukkit.getPlayer(args[0]);

            if (player == null) {
                sender.sendMessage("Offline");
                return false;
            }

            if (player.getFoodLevel() >= 20) {
                sender.sendMessage("FullFoodPlayer");
                return false;
            }

            sender.sendMessage("FeededPlayer"); // {player} -> retorna o nick da pessoa q foi saciada
        } else {

            player = (Player) sender;

            if (player.getFoodLevel() >= 20) {
                player.sendMessage("FullFood");
                return false;
            }

            player.sendMessage("Feeded");

        }

        player.setFoodLevel(20);
        player.setSaturation(10);
        player.setExhaustion(0F);

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (player == null || !player.hasPermission("miyukisystem.feed.other")) return Collections.emptyList();
        return Bukkit.getOnlinePlayers().stream()
                .filter(player::canSee)
                .map(HumanEntity::getName)
                .collect(Collectors.toList());
    }
}
