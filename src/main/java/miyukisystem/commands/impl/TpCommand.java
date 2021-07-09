package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.TPA;
import miyukisystem.manager.impl.TpaManager;
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

        TPA tpa = new TPA(player.getName(), target.getName());

        TpaManager.Companion.set(tpa);
        target.sendMessage("TpaOther");
        player.sendMessage("TpaPlayer");

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
