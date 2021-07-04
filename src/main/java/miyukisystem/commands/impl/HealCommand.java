package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealCommand extends CommandService {

    public HealCommand() {
        super("Heal");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player) && args.length != 1) {
            sender.sendMessage("NoConsole");
            return false;
        }

        if (!(sender.hasPermission("miyukisystem.heal"))) {
            sender.sendMessage("NoPermission");
            return false;
        }

        Player player;

        if (args.length > 0) {
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
}
