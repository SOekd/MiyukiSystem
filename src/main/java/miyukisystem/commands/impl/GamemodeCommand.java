package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManager;
import miyukisystem.manager.impl.MessageManagerKt;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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

        if (args.length < 1 || args.length > 2) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectGameModeCommand");
            return false;
        }

        val gameMode = matchGamemode(args[0]);

        if (gameMode == null) {
            sender.sendMessage("inexistente");
            return false;
        }

        if (args.length == 2) {



            return true;
        }

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        val player = (Player) sender;

        switch (gameMode) {
            case CREATIVE:
                if (!(sender.hasPermission("miyukisystem.gamemode.creative"))) {
                    MessageManagerKt.sendCustomMessage(sender, "NoPermission");
                    return false;
                }
                player.setGameMode(GameMode.CREATIVE);
                break;
            case SURVIVAL:
            case ADVENTURE:
            case SPECTATOR:
        }

        // botar permissão pra cada gamemode. (miyukisyste.creative, miyukisystem.survival)

        // botar /gamemode 1 /gamemode survival /gamemode sobrevivencia
        // /gamemode 1 nick

        return true;
    }

    private GameMode matchGamemode(String gameMode) {
        if (gameMode.startsWith("su") || gameMode.startsWith("so") || gameMode.equals("0")) {
            return GameMode.SURVIVAL;
        } else if (gameMode.startsWith("cr") || gameMode.equals("1")) {
            return GameMode.CREATIVE;
        } else if (gameMode.startsWith("a") || gameMode.equals("2")) {
            return GameMode.ADVENTURE;
        } else if (gameMode.startsWith("sp") || gameMode.equals("3")) {
            return GameMode.SPECTATOR;
        } else {
            return null;
        }
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        val player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0 || player == null || !player.hasPermission("miyukisystem.gamemode")) return Collections.emptyList();
        val lastWord = args[args.length - 1];
        if (args.length == 1) {
            val modes = Arrays.asList("Survival", "Creative", "Adventure", "Spectator");
            return modes.stream()
                    .filter(it -> StringUtils.startsWithIgnoreCase(it, lastWord))
                    .collect(Collectors.toList());
        }
        else {
            if (!player.hasPermission("miyukisystem.gamemode.other")) return Collections.emptyList();
            return Bukkit.getOnlinePlayers().stream()
                    .filter(it -> player.canSee(it) && StringUtils.startsWithIgnoreCase(it.getName(), lastWord))
                    .map(HumanEntity::getName)
                    .sorted()
                    .collect(Collectors.toList());
        }
    }
}
