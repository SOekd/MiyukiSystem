package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class TeleportCommand extends CommandService {

    public TeleportCommand() {
        super("Teleport", "miyukisystem.teleport");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length < 1 || args.length > 5) {
            sender.sendMessage("IncorrectTeleportCommand");
            return false;
        }

        double x, y, z;

        if (args.length == 1) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("NoConsole");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage("Offline");
                return false;
            }

            if (target.getName().equals(sender.getName())) {
                sender.sendMessage("TeleportedToYourself");
                return false;
            }

            Player player = (Player) sender;
            player.teleport(target);
            player.sendMessage("TeleportedSuccess"); // {player} retorna o nome do player q vc teleportou
            return false;
        }

        if (args.length == 2) {

            Player player = Bukkit.getPlayer(args[0]);

            if (player == null) {
                sender.sendMessage("Offline");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                sender.sendMessage("Offline");
                return false;
            }

            if (player.getName().equals(target.getName())) {
                sender.sendMessage("");
                return false;
            }

            player.teleport(target);

        }

        if (args.length == 3) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("NoConsole");
                return false;
            }
            try {
                x = Double.parseDouble(args[0]);
                y = Double.parseDouble(args[1]);
                z = Double.parseDouble(args[2]);
            } catch (NumberFormatException exception) {
                sender.sendMessage("InvalidCoords");
                return false;
            }

            Player player = (Player) sender;
            Location locationTarget = new Location(player.getWorld(), x, y, z);
            player.teleport(locationTarget);
            player.sendMessage("TeleportedSuccess");
        }

        if (args.length == 4) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("NoConsole");
                return false;
            }

            Player player = (Player) sender;

            World world = Bukkit.getWorld(args[0]);
            try {
                x = Double.parseDouble(args[1]);
                y = Double.parseDouble(args[2]);
                z = Double.parseDouble(args[3]);
            } catch (NumberFormatException exception) {
                sender.sendMessage("InvalidCoords");
                return false;
            }

            Location locationTarget = new Location(world, x, y, z);
            player.teleport(locationTarget);
            player.sendMessage("TeleportedSuccessWithCoords");
        }

        if (args.length == 5) {

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage("Offline");
                return false;
            }

            World world = Bukkit.getWorld(args[1]);
            try {
                x = Double.parseDouble(args[2]);
                y = Double.parseDouble(args[3]);
                z = Double.parseDouble(args[4]);
            } catch (NumberFormatException exception) {
                sender.sendMessage("InvalidNumber");
                return false;
            }

            Location locationTarget = new Location(world, x, y, z);
            target.teleport(locationTarget);
            sender.sendMessage("PlayerTeleportedSuccessWithCoords"); // {player} retorna o nome do target

        }

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
