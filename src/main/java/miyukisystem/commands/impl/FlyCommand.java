package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
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

public class FlyCommand extends CommandService {

    public FlyCommand() {
        super("Fly", "miyukisystem.fly", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player) && args.length != 1) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        if (args.length > 0) {

            if (!(sender.hasPermission("miyukisystem.fly.other"))) {
                MessageManagerKt.sendCustomMessage(sender, "NoPermission");
                return false;
            }

            val target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                MessageManagerKt.sendCustomMessage(sender, "Offline");
                return false;
            }

            val placeHolders = new HashMap<String, String>();
            placeHolders.put("{player}", target.getName());
            target.setAllowFlight(!target.getAllowFlight());

            MessageManagerKt.sendCustomMessage(sender, target.getAllowFlight() ? "FlyTargetActivated" : "FlyTargetDisabled", placeHolders);

            return true;
        }

        Player player = (Player) sender;
        player.setAllowFlight(!player.getAllowFlight());
        MessageManagerKt.sendCustomMessage(player, player.getAllowFlight() ? "FlyActivated" : "FlyDisabled");
        player.sendMessage(player.getAllowFlight() ? "FlyActivated" : "FlyDisabled"); // necessita de testes, não sei se irá funcionar

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        val player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0 || player == null || !player.hasPermission("miyukisystem.fly.other")) return Collections.emptyList();
        val lastWord = args[args.length - 1];
        return Bukkit.getOnlinePlayers().stream()
                .filter(it -> player.canSee(it) && StringUtils.startsWithIgnoreCase(it.getName(), lastWord))
                .map(HumanEntity::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
