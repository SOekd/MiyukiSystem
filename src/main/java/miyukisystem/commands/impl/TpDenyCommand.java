package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.TPA;
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

public class TpDenyCommand extends CommandService {

    public TpDenyCommand() {
        super("TpDeny", "miyukisystem.tpa", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        Player player = (Player) sender;

        if(args.length < 1) {

            val targetTPA = TpaManager.Companion.lastReceived(player.getName());

            if (targetTPA == null) {
                MessageManagerKt.sendCustomMessage(player, "NoHaveRequests");
                return false;
            }

            val targetName = targetTPA.getFrom();
            val target = Bukkit.getPlayer(targetName);

            TpaManager.Companion.remove(targetName);

            val placeHolders = new HashMap<String, String>();
            placeHolders.put("{target}", player.getName()); // Lembrando que aqui é o contrário.
            placeHolders.put("{player}", targetName);

            MessageManagerKt.sendCustomMessage(player, "DenyTpaOther", placeHolders);

            if (target!= null) {
                MessageManagerKt.sendCustomMessage(target, "DenyTpaPlayer", placeHolders);
            }
        } else {

            val target = Bukkit.getPlayer(args[0]);

            if(target == null) {
                MessageManagerKt.sendCustomMessage(player, "Offline");
                return false;
            }

            if(target.equals(player)) {
                MessageManagerKt.sendCustomMessage(player, "TpDenyYourself");
                return false;
            }

            if (TpaManager.Companion.getAll().stream().noneMatch(it -> it.getFrom().equals(target.getName()))) {
                MessageManagerKt.sendCustomMessage(player, "NoHaveRequests");
                return false;
            }

            TpaManager.Companion.remove(target.getName());

            val placeHolders = new HashMap<String, String>();
            placeHolders.put("{target}", player.getName()); // Lembrando que aqui é o contrário.
            placeHolders.put("{player}", target.getName());

            MessageManagerKt.sendCustomMessage(player, "DenyTpaOther", placeHolders);
            MessageManagerKt.sendCustomMessage(target, "DenyTpaPlayer", placeHolders);

        }
        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        val player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0 || player == null || !player.hasPermission("miyukisystem.tpa")) return Collections.emptyList();
        val lastWord = args[args.length - 1];
        return TpaManager.Companion.getAll().stream()
                .filter(it -> it.getTo().equalsIgnoreCase(player.getName()) && StringUtils.startsWithIgnoreCase(it.getFrom(), lastWord))
                .map(TPA::getFrom)
                .sorted()
                .collect(Collectors.toList());
    }
}
