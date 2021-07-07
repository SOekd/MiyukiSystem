package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TpDenyCommand extends CommandService {

    public TpDenyCommand(@NotNull String name, @NotNull String perm) {
        super("tpdeny", "miyukisystem.tpdeny");
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

            for(String str : TpaCommand.tps.get(pn)) {
                Player target = Bukkit.getPlayer(str);
// SOekd
                if(target == null) {
                    TpaCommand.tps.get(player.getName()).remove(str);
                }
            }

            if(TpaCommand.tps.get(player.getName()).isEmpty()) {
                player.sendMessage("NoHaveRequests");
                TpaCommand.tps.remove(player.getName());
                return false;
            }

            Player target = Bukkit.getPlayer(TpaCommand.tps.get(pn).get(0));

            if(target == null) return false;

            player.sendMessage("DenyTpaOther");
            target.sendMessage("DenyTpaPlayer");
        } else {

            if(args[0].equals(pn)) {
                player.sendMessage("Yourself");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if(target == null) {
                player.sendMessage("OfflinePlayer");
                if(TpaCommand.tps.get(player.getName()).contains(args[0])) {
                    player.sendMessage("NoHaveRequests");
                    TpaCommand.tps.get(player.getName()).remove(args[0]);
                    return false;
                }
                return false;
            }

            if(!TpaCommand.tps.get(player.getName()).contains(player.getName())) {
                player.sendMessage("NoHaveRequests");
                return false;
            }

            player.sendMessage("DenyTpaOther");
            player.sendMessage("DenyTpaPlayer");

        }

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
