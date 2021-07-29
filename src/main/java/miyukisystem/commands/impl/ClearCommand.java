package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.PlayerManagerKt;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ClearCommand extends CommandService {

    public ClearCommand() {
        super("Clear", "miyukisystem.clear", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length > 0) {

            if (!(sender.hasPermission("miyukisystem.clear.other"))) {
                MessageManagerKt.sendCustomMessage(sender, "NoPermission");
                return false;
            }

            val target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                MessageManagerKt.sendCustomMessage(sender, "PlayerOffline");
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

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        val player = (Player) sender;

        if (!PlayerManagerKt.isInventoryEmpty(player, true)) {
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
        val player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0 || player == null || !player.hasPermission("miyukisystem.clear.other")) return Collections.emptyList();
        val lastWord = args[args.length - 1];
        return Bukkit.getOnlinePlayers().stream()
                .filter(it -> player.canSee(it) && StringUtils.startsWithIgnoreCase(it.getName(), lastWord))
                .map(HumanEntity::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}