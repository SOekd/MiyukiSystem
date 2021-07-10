package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.PlayerManagerKt;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SpawnCommand extends CommandService {

    public SpawnCommand() {
        super("Spawn", "", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        if (args.length > 1 && sender.hasPermission("miyukisystem.spawn.other")) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectSpawnAdminCommand");
            return false;
        }

        if (args.length > 1) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectSpawnCommand");
            return false;
        }

        // se o cara apertar tab no primeiro argumento, vir a lista de players (SOekd) pra teleportar o cara pro spawn (se o cara tiver a perm miyukisystem.spawn.other)

        Player player = (Player) sender;

        if (args.length == 0) {

            PlayerManagerKt.toSpawn(player);

            MessageManagerKt.sendCustomMessage(player, "TeleportedSpawnSuccess");
            player.playSound(player.getLocation(), Sound.valueOf("LEVEL_UP"), 1.0f, 1.0f);

        }

        if (args.length == 1) {

            if (!(sender.hasPermission("miyukisystem.spawn.other"))) {
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

            PlayerManagerKt.toSpawn(target);
            MessageManagerKt.sendCustomMessage(sender, "SentToSpawn", placeHolders);
            MessageManagerKt.sendCustomMessage(sender, "ForcedTeleportSpawn");
        }

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
