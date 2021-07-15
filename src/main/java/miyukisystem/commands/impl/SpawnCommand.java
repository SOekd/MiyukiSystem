package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.PlayerManagerKt;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SpawnCommand extends CommandService {

    public SpawnCommand() {
        super("Spawn", "", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length == 0) {

            if (!(sender instanceof Player)) {
                MessageManagerKt.sendCustomMessage(sender, "IncorrectSpawnAdminCommand");
                return false;
            }

            val player = (Player) sender;

            PlayerManagerKt.toSpawn(player);

            MessageManagerKt.sendCustomMessage(player, "TeleportedSpawnSuccess");
            player.playSound(player.getLocation(), Sound.valueOf("LEVEL_UP"), 1.0f, 1.0f);
            return true;
        }

        if (!(sender.hasPermission("miyukisystem.spawn.other"))) {
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

        PlayerManagerKt.toSpawn(target);
        MessageManagerKt.sendCustomMessage(sender, "SentToSpawn", placeHolders);
        MessageManagerKt.sendCustomMessage(sender, "ForcedTeleportSpawn");

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        val player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0 || player == null || !player.hasPermission("miyukisystem.spawn.other")) return Collections.emptyList();
        val lastWord = args[args.length - 1];
        return Bukkit.getOnlinePlayers().stream()
                .filter(it -> player.canSee(it) && StringUtils.startsWithIgnoreCase(it.getName(), lastWord))
                .map(HumanEntity::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
