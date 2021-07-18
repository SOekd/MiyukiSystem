package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.TpaManager;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TpAcceptCommand extends CommandService {

    public TpAcceptCommand() {
        super("TpAccept", "miyukisystem.tpa", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        val player = (Player) sender;

        if (args.length < 1) {

            val targetTPA = TpaManager.Companion.lastReceived(player.getName());

            if (targetTPA == null) {
                MessageManagerKt.sendCustomMessage(sender, "NoHaveRequests");
                return false;
            }

            val targetName = targetTPA.getFrom();
            val target = Bukkit.getPlayer(targetName);

            if (target == null) {
                MessageManagerKt.sendCustomMessage(sender, "ExpiredTpa");
                return false;
            }

            TpaManager.Companion.remove(target.getName());

            val placeHolders = new HashMap<String, String>();
            placeHolders.put("{target}", player.getName()); // Lembrando que aqui é o contrário.
            placeHolders.put("{player}", target.getName());

            MessageManagerKt.sendCustomMessage(target, "AcceptTpaPlayer", placeHolders);
            MessageManagerKt.sendCustomMessage(player, "AcceptTpaOther", placeHolders);
            target.teleport(player.getLocation());

        } else {

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                MessageManagerKt.sendCustomMessage(player, "PlayerOffline");
                return false;
            }

            if (target.equals(player)) {
                MessageManagerKt.sendCustomMessage(player, "TpAcceptYourself");
                return false;
            }

            if (!TpaManager.Companion.has(target.getName())) {
                MessageManagerKt.sendCustomMessage(player, "NoHaveRequests");
                return false;
            }

            TpaManager.Companion.remove(target.getName());

            val placeHolders = new HashMap<String, String>();
            placeHolders.put("{target}", player.getName()); // Lembrando que aqui é o contrário.
            placeHolders.put("{player}", target.getName());

            MessageManagerKt.sendCustomMessage(player, "AcceptTpaOther", placeHolders);
            MessageManagerKt.sendCustomMessage(target, "AcceptTpaPlayer", placeHolders);
            target.teleport(player.getLocation());

        }

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        val player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0 || player == null || !player.hasPermission("miyukisystem.tpaccept"))
            return Collections.emptyList();
        val lastWord = args[args.length - 1];
        return Bukkit.getOnlinePlayers().stream()
                .filter(it -> player.canSee(it) && StringUtils.startsWithIgnoreCase(it.getName(), lastWord))
                .map(HumanEntity::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
