package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
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

            for(String str : TpCommand.tps.get(pn)) {
                Player target = Bukkit.getPlayer(str);
                // SOekd
                if(target == null) {
                    TpCommand.tps.get(player.getName()).remove(str);
                }
            }

            if(TpCommand.tps.get(player.getName()).isEmpty()) {
                player.sendMessage("NoHaveRequests");
                TpCommand.tps.remove(player.getName());
                return false;
            }

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
                if(TpCommand.tps.get(player.getName()).contains(args[0])) {
                    player.sendMessage("NoHaveRequests");
                    TpCommand.tps.get(player.getName()).remove(args[0]);
                    return false;
                }
                return false;
            }

            if(!TpCommand.tps.get(player.getName()).contains(target.getName())) {
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