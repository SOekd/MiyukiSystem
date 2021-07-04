package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FeedCommand extends CommandService {

    public FeedCommand() {
        super("Feed");
    }

    // ta com os path

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player) && args.length != 1) {
            sender.sendMessage("NoConsole");
            return false;
        }

        if (!(sender.hasPermission("miyukisystem.feed"))) {
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

        return false;
    }
}
