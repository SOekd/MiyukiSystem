package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.PlayerManagerKt;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClearCommand extends CommandService {

    public ClearCommand() {
        super("Clear", "miyukisystem.clear", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player) && args.length == 0) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        if (args.length > 1) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectClearCommand");
            return false;
        }

        if (args.length == 1) {

            if (!(sender.hasPermission("miyukisystem.clear.other"))) {
                MessageManagerKt.sendCustomMessage(sender, "NoPermission");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                MessageManagerKt.sendCustomMessage(sender, "Offline");
                return false;
            }

            val placeHolders = new HashMap<String, String>();
            placeHolders.put("{player}", target.getName());

            if (PlayerManagerKt.isInventoryEmpty(target, false)) {
                MessageManagerKt.sendCustomMessage(sender, "AlreadyTargetCleared", placeHolders);
            } else {
                PlayerManagerKt.clearInventory(target);
                MessageManagerKt.sendCustomMessage(sender, "TargetCleared", placeHolders);
            }

            return false;
        }

        Player player = (Player) sender;

        if (PlayerManagerKt.isInventoryEmpty(player, false)) {
            PlayerManagerKt.clearInventory(player);
            MessageManagerKt.sendCustomMessage(sender, "Cleared");
        } else {
            MessageManagerKt.sendCustomMessage(sender, "AlreadyCleared");
        }
        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
