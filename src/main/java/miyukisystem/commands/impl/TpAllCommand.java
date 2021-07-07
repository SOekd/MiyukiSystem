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
        super("TpAll", "miyukisystem.tpall");
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

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player != target) {
                    player.teleport(target);
                }
            }

            // mensagem de sucesso
        }

        if (args.length == 0) {

            Player p = (Player) sender;

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player != p) {
                    player.teleport(p);
                }
            }

            // mensagem de sucesso
        }



        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
