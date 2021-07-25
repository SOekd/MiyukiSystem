package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TeleportCommand extends CommandService {

    public TeleportCommand() {
        super("Teleport", "miyukisystem.teleport", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length < 1 || args.length > 4) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectTeleportCommand");
            return false;
        }

        if (args.length == 1) {

            if (!(sender instanceof Player)) {
                MessageManagerKt.sendCustomMessage(sender, "NoConsole");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                MessageManagerKt.sendCustomMessage(sender, "PlayerOffline");
                return false;
            }

            if (target.getName().equals(sender.getName())) {
                MessageManagerKt.sendCustomMessage(sender, "TeleportedToYourself");
                return false;
            }

            val placeHolders = new HashMap<String, String>();
            placeHolders.put("{player}", target.getName());

            Player player = (Player) sender;
            player.teleport(target.getLocation());
            MessageManagerKt.sendCustomMessage(player, "TeleportedSuccess", placeHolders); // {player} retorna o nome do player q vc teleportou
            return false;
        }

        if (args.length == 2) {

            Player player = Bukkit.getPlayer(args[0]);

            if (player == null) {
                MessageManagerKt.sendCustomMessage(sender, "PlayerOffline");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                MessageManagerKt.sendCustomMessage(sender, "PlayerOffline");
                return false;
            }

            if (player.getName().equals(target.getName())) {
                sender.sendMessage("");
                return false;
            }

            player.teleport(target);

        }

        double x, y, z;

        if (args.length == 3) {

            if (!(sender instanceof Player)) {
                MessageManagerKt.sendCustomMessage(sender, "NoConsole");
                return false;
            }

            try {
                x = Double.parseDouble(args[0]);
                y = Double.parseDouble(args[1]);
                z = Double.parseDouble(args[2]);
            } catch (NumberFormatException exception) {
                MessageManagerKt.sendCustomMessage(sender, "InvalidCoords");
                return false;
            }

            Player player = (Player) sender;
            Location locationTarget = new Location(player.getWorld(), x, y, z);
            player.teleport(locationTarget);
            MessageManagerKt.sendCustomMessage(player, "TeleportedSuccessWithCoords");
        }

        if (args.length == 4) {

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                MessageManagerKt.sendCustomMessage(sender, "PlayerOffline");
                return false;
            }

            try {
                x = Double.parseDouble(args[1]);
                y = Double.parseDouble(args[2]);
                z = Double.parseDouble(args[3]);
            } catch (NumberFormatException exception) {
                MessageManagerKt.sendCustomMessage(sender, "InvalidCoords");
                return false;
            }

            val placeHolders = new HashMap<String, String>();
            placeHolders.put("{player}", target.getName());

            Location locationTarget = new Location(target.getWorld(), x, y, z);
            target.teleport(locationTarget);
            MessageManagerKt.sendCustomMessage(sender, "PlayerTeleportedSuccessWithCoords", placeHolders);
        }

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        val player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0 || player == null || !player.hasPermission("miyukisystem.teleport")) return Collections.emptyList();
        val lastWord = args[args.length - 1];
        return Bukkit.getOnlinePlayers().stream()
                .filter(it -> player.canSee(it) && StringUtils.startsWithIgnoreCase(it.getName(), lastWord))
                .map(HumanEntity::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
