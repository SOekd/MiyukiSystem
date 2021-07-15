package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FeedCommand extends CommandService {

    public FeedCommand() {
        super("Feed", "miyukisystem.feed", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player) && args.length != 1) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        Player player;
        if (args.length > 0) {

            if (!(sender.hasPermission("miyukisystem.feed.other"))) {
                MessageManagerKt.sendCustomMessage(sender, "NoPermission");
                return false;
            }

            player = Bukkit.getPlayer(args[0]);

            if (player == null) {
                MessageManagerKt.sendCustomMessage(sender, "Offline");
                return false;
            }

            if (player.getFoodLevel() >= 20) {
                MessageManagerKt.sendCustomMessage(sender, "FullFoodPlayer");
                return false;
            }

            val placeHolders = new HashMap<String, String>();
            placeHolders.put("{player}", player.getName());
            MessageManagerKt.sendCustomMessage(sender, "FeededPlayer", placeHolders);
        } else {

            player = (Player) sender;

            if (player.getFoodLevel() >= 20) {
                MessageManagerKt.sendCustomMessage(player, "FullFood");
                return false;
            }

            MessageManagerKt.sendCustomMessage(player, "Feeded");

        }

        player.setFoodLevel(20);
        player.setSaturation(10);
        player.setExhaustion(0F);

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        val player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0 || player == null || !player.hasPermission("miyukisystem.feed.other")) return Collections.emptyList();
        val lastWord = args[args.length - 1];
        return Bukkit.getOnlinePlayers().stream()
                .filter(it -> player.canSee(it) && StringUtils.startsWithIgnoreCase(it.getName(), lastWord))
                .map(HumanEntity::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
