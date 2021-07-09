package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.TpaManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TpAcceptCommand extends CommandService {

    public TpAcceptCommand() {
        super("TpAccept", "miyukisystem.tpaccept"); // talvez deixar sem a perm de tpa pra esses comandos de tpa.
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        Player player = (Player) sender;
        String pn = player.getName();

        if(args.length < 1) {

            // SOekd (Stream)

            Player target = Bukkit.getPlayer(TpCommand.tps.get(pn).get(0));

            if(target == null) return false;

            player.sendMessage("AcceptTpaOther");
            target.sendMessage("AcceptTpaPlayer");
            target.teleport(player.getLocation());
        } else {

            if(args[0].equals(pn)) {
                player.sendMessage("Yourself");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if(target == null) {
                player.sendMessage("OfflinePlayer");
                return false;
            }

            if(!TpaManager.Companion.has(target.getName())) {
                player.sendMessage("NoHaveRequests");
                return false;
            }

            player.sendMessage("AcceptTpaOther");
            target.sendMessage("AcceptTpaPlayer");
            target.teleport(player.getLocation());

        }

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
