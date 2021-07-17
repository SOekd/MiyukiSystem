package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TpAllCommand extends CommandService {

    public TpAllCommand() {
        super("TpAll", "miyukisystem.tpall", false);
    }

    // Comando 100% feito.

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length > 1) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectTpAllCommand");
            return false;
        }

        if (args.length == 1) {

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                MessageManagerKt.sendCustomMessage(sender, "Offline");
                return false;
            }

            Bukkit.getOnlinePlayers().forEach(it -> {
                if (it != target)
                    it.teleport(target);
            });

            val placeHolders = new HashMap<String, String>();
            placeHolders.put("{player}", target.getName());

            MessageManagerKt.sendCustomMessage(target, "AllPlayersTeleportedToYou");
            MessageManagerKt.sendCustomMessage(sender, "ForcedTpAll", placeHolders);
        }

        if (args.length == 0) {

            Player p = (Player) sender;

            Bukkit.getOnlinePlayers().forEach(it -> {
                if (it != p)
                    it.teleport(p);
            });
        }

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
