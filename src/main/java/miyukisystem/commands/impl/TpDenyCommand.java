package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.TpaManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TpDenyCommand extends CommandService {

    public TpDenyCommand() {
        super("TpDeny", "miyukisystem.tpdeny"); // talvez deixar sem a perm de tpa pra esses comandos de tpa.
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
                return false;
            }

            // TpaManager.Companion.unSet(target.getName());
            player.sendMessage("DenyTpaOther");
            target.sendMessage("DenyTpaPlayer");

        }

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
