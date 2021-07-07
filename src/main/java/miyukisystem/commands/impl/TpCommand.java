package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.util.AsyncUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TpCommand extends CommandService {

    public static HashMap<String, List<String>> tps = new HashMap<>();

    public TpCommand() {
        super("Tpa", "miyukisystem.tpa");
    } // talvez deixar sem a perm tpaccept pra esses comandos de tpa.

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        Player player = (Player) sender;

        if(args.length != 1) {
            player.sendMessage("IncorrectTpaCommand");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            player.sendMessage("OfflinePlayer");
            return false;
        }

        if(args[0].equals(sender.getName())) {
            player.sendMessage("Yourself");
            return false;
        }

        if(tps.containsKey(player.getName())) {
            tps.get(target.getName()).add(player.getName());
            return false;
        }

        List<String> tpsPlayers = new ArrayList<>();

        tpsPlayers.add(player.getName());
        tps.put(target.getName(), tpsPlayers);
        player.sendMessage("TpaOther");
        player.sendMessage("TpaPlayer");

        AsyncUtil.Companion.runAsyncLater(20L * 60, () -> {
            tps.get(player.getName()).remove(player.getName());

            if(tps.get(target.getName()).isEmpty()) {
                tps.remove(target.getName());
            }

            target.sendMessage("ExpiredTpaOther");
            player.sendMessage("ExpiredTpaPlayer");
        });

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
