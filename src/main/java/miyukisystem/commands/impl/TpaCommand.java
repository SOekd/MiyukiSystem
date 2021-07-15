package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.TPA;
import miyukisystem.manager.impl.TpaManager;
import miyukisystem.util.AsyncUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TpaCommand extends CommandService {

    public static HashMap<String, List<String>> tps = new HashMap<>();

    public TpaCommand() {
        super("Tpa", "miyukisystem.tpa", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            MessageManagerKt.sendCustomMessage(player, "IncorrectTpaCommand");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            MessageManagerKt.sendCustomMessage(player, "Offline");
            return false;
        }

        if (target == player) {
            MessageManagerKt.sendCustomMessage(player, "TpaYourself");
            return false;
        }

        val tpa = new TPA(player.getName(), target.getName());

        TpaManager.Companion.set(tpa);
        MessageManagerKt.sendCustomMessage(target, "TpaOther");
        MessageManagerKt.sendCustomMessage(player, "TpaPlayer");

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        val player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0 || player == null || !player.hasPermission("miyukisystem.tpa")) return Collections.emptyList();
        val lastWord = args[args.length - 1];
        return Bukkit.getOnlinePlayers().stream()
                .filter(it -> player.canSee(it) && StringUtils.startsWithIgnoreCase(it.getName(), lastWord))
                .map(HumanEntity::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
