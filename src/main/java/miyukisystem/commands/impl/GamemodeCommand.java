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

import java.util.*;
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
            MessageManagerKt.sendCustomMessage(sender, "GameModeNull");
            return false;
        }

        if (args.length == 2) {

            val target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                MessageManagerKt.sendCustomMessage(sender, "PlayerOffline");
                return false;
            }

            val placeHolders = new HashMap<String, String>();
            placeHolders.put("{gamemode}", getName(gameMode));
            placeHolders.put("{player}", target.getName());

            if (!(sender.hasPermission("miyukisystem.gamemode." + gameMode.name().toLowerCase()))) {
                MessageManagerKt.sendCustomMessage(sender, "NoPermission");
                return false;
            }

            target.setGameMode(gameMode);
            MessageManagerKt.sendCustomMessage(sender, "GameModeTargetChanged", placeHolders);

            return true;
        }

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        val player = (Player) sender;
        val placeHolders = new HashMap<String, String>();
        placeHolders.put("{gamemode}", getName(gameMode));

        if (!(sender.hasPermission("miyukisystem.gamemode." + gameMode.name().toLowerCase()))) {
            MessageManagerKt.sendCustomMessage(sender, "NoPermission");
            return false;
        }

        player.setGameMode(gameMode);
        MessageManagerKt.sendCustomMessage(sender, "GameModeChanged", placeHolders);

        return true;
    }

    private GameMode matchGamemode(String gameMode) {
        gameMode = gameMode.toLowerCase();
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

    private String getName(GameMode gameMode) {
        switch (gameMode) {
            case SURVIVAL:
                return MessageManager.Companion.getMessage("Survival")[0];
            case CREATIVE:
                return MessageManager.Companion.getMessage("Creative")[0];
            case SPECTATOR:
                return MessageManager.Companion.getMessage("Spectator")[0];
            case ADVENTURE:
                return MessageManager.Companion.getMessage("Adventure")[0];
            default:
                return "";
        }
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        val player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0 || player == null || !player.hasPermission("miyukisystem.gamemode"))
            return Collections.emptyList();
        val lastWord = args[args.length - 1];
        if (args.length == 1) {
            val modes = Arrays.asList("Survival", "Creative", "Adventure", "Spectator");
            return modes.stream()
                    .filter(it -> StringUtils.startsWithIgnoreCase(it, lastWord))
                    .collect(Collectors.toList());
        } else {
            if (!player.hasPermission("miyukisystem.gamemode.other")) return Collections.emptyList();
            return Bukkit.getOnlinePlayers().stream()
                    .filter(it -> player.canSee(it) && StringUtils.startsWithIgnoreCase(it.getName(), lastWord))
                    .map(HumanEntity::getName)
                    .sorted()
                    .collect(Collectors.toList());
        }
    }
}
