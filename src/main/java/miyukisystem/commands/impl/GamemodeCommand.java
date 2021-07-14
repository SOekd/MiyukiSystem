package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GamemodeCommand extends CommandService {

    public GamemodeCommand() {
        super("Gamemode", "miyukisystem.gamemode", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player) && args.length != 2) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        // botar permissão pra cada gamemode. (miyukisyste.creative, miyukisystem.survival)

        // botar /gamemode 1 /gamemode survival /gamemode sobrevivencia
        // /gamemode 1 nick

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (player == null || !player.hasPermission("miyukisystem.gamemode")) return Collections.emptyList();
        if (args.length > 0) {
            if (!player.hasPermission("miyukisystem.gamemode.other")) return Collections.emptyList();
            return Bukkit.getOnlinePlayers().stream()
                    .filter(player::canSee)
                    .map(HumanEntity::getName)
                    .collect(Collectors.toList());
        }
        return Arrays.asList("survival", "creative", "adventure", "spectator");
    }
}
