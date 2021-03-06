package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.TpaManager;
import miyukisystem.manager.impl.UserManager;
import miyukisystem.model.TPA;
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

        val player = (Player) sender;
        val playerUser = UserManager.Companion.get(player.getName());

        if (args.length != 1) {
            MessageManagerKt.sendCustomMessage(player, "IncorrectTpaCommand");
            return false;
        }

        if(!playerUser.getTpaEnabled()) {
            MessageManagerKt.sendCustomMessage(player, "YourTpaIsDisabled");
            return false;
        }

        val target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            MessageManagerKt.sendCustomMessage(player, "PlayerOffline");
            return false;
        }

        if (target == player) {
            MessageManagerKt.sendCustomMessage(player, "TpaYourself");
            return false;
        }

        val targetUser = UserManager.Companion.get(target.getName());

        if(!targetUser.getTpaEnabled()) {
            MessageManagerKt.sendCustomMessage(player, "TargetTpaIsDisabled");
            return false;
        }

        val tpa = new TPA(player.getName(), target.getName());
        val placeHolders = new HashMap<String, String>();
        placeHolders.put("{player}", player.getName());
        placeHolders.put("{target}", target.getName());

        TpaManager.Companion.set(tpa);
        MessageManagerKt.sendCustomMessage(target, "TpaOther", placeHolders);
        MessageManagerKt.sendCustomMessage(player, "TpaPlayer", placeHolders);

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
