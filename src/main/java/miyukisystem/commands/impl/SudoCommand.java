package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SudoCommand extends CommandService {

    public SudoCommand() {
        super("Sudo", "miyukisystem.sudo", false);
    }


    // arrumar (nao t funfando obviamente)

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length < 2) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectSudoCommand");
            return false;
        }

        val target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            MessageManagerKt.sendCustomMessage(sender, "PlayerOffline");
            return false;
        }

        val message = String.join(" ", args);
        target.performCommand(message);

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        val player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0 || player == null || !player.hasPermission("miyukisystem.sudo")) return Collections.emptyList();
        val lastWord = args[args.length - 1];
        return Bukkit.getOnlinePlayers().stream()
                .filter(it -> player.canSee(it) && StringUtils.startsWithIgnoreCase(it.getName(), lastWord))
                .map(HumanEntity::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
