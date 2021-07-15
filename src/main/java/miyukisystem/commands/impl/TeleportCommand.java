package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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

        // Argumento 1 = quer teleportar para algum player.
        if (args.length == 1) {

            if (!(sender instanceof Player)) {
                MessageManagerKt.sendCustomMessage(sender, "NoConsole");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                MessageManagerKt.sendCustomMessage(sender, "Offline");
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

        // se o argumento é 2, ele quer teleportar um player até outro player.
        if (args.length == 2) {

            Player player = Bukkit.getPlayer(args[0]);

            if (player == null) {
                MessageManagerKt.sendCustomMessage(sender, "Offline");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                MessageManagerKt.sendCustomMessage(sender, "Offline");
                return false;
            }

            if (player.getName().equals(target.getName())) {
                sender.sendMessage("");
                return false;
            }

            player.teleport(target);

        }

        double x, y, z;

        // se o args é 3 ele quer teleportar ate uma coord
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

        // se é 4, ele quer teleportar um player até uma coordenada.
        if (args.length == 4) {

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                MessageManagerKt.sendCustomMessage(sender, "Offline");
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
        return Collections.emptyList();
    }
}
