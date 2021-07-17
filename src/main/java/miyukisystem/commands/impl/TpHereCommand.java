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

public class TpHereCommand extends CommandService {

    public TpHereCommand() {
        super("TpHere", "miyukisystem.tphere", false);
    }

    // Comando 100% feito.

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        if (args.length != 1) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectTpHereCommand");
            return false;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            MessageManagerKt.sendCustomMessage(sender, "Offline");
            return false;
        }

        target.teleport(player.getLocation());

        val placeHolders = new HashMap<String, String>();
        placeHolders.put("{player}", target.getName());

        MessageManagerKt.sendCustomMessage(sender, "TpHereSuccess", placeHolders);

        return false;
    }

    // fazer o tab complete
    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
