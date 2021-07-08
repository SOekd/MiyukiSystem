package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class SpeedCommand extends CommandService {

    public SpeedCommand() {
        super("Speed", "miyukisystem.speed");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player) && args.length != 2) {
            sender.sendMessage("NoConsole");
            return false;
        }

        if (args.length > 2 || args.length == 0) {
            sender.sendMessage("IncorrectSpeedCommand");
            return false;
        }

        Player player;
        int speed;

        if (args.length == 1) {
            player = (Player) sender;
            try {
                speed = Integer.parseInt(args[0]);
            } catch (NumberFormatException exception) {
                sender.sendMessage("OnlyNumber");
                return false;
            }
            player.setFlySpeed(speed);
            player.setWalkSpeed(speed);
        }

        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage("Offline");
                return false;
            }

            try {
                speed = Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                sender.sendMessage("OnlyNumber");
                return false;
            }
            target.setFlySpeed(speed);
            target.setWalkSpeed(speed);
        }

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }

}
