package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class TpAllCommand extends CommandService {

    public TpAllCommand() {
        super("TpAll", "miyukisystem.tpall", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length > 1) {
            sender.sendMessage("IncorrectTpAllCommand");
            return false;
        }

        if (args.length == 1) {

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage("Offline");
                return false;
            }

            Bukkit.getOnlinePlayers().forEach(it -> {
                if (it != target)
                    it.teleport(target);
            });

            target.sendMessage("AllPlayersTeleportedToYou");
            sender.sendMessage("ForcedTpAll"); // {player} retorna o target.getName
        }

        if (args.length == 0) {

            Player p = (Player) sender;

            Bukkit.getOnlinePlayers().forEach(it -> {
                if (it != p)
                    it.teleport(p);
            });

            p.sendMessage("AllPlayersTeleportedToYou");
        }

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
