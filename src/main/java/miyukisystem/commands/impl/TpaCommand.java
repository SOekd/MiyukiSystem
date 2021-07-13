package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.TPA;
import miyukisystem.manager.impl.TpaManager;
import miyukisystem.util.AsyncUtil;
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
            sender.sendMessage("NoConsole");
            return false;
        }

        Player player = (Player) sender;

        if(args.length != 1) {
            player.sendMessage("IncorrectTpaCommand");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            player.sendMessage("Offline");
            return false;
        }

        if(target == player) {
            player.sendMessage("TpaYourself");
            return false;
        }

        val tpa = new TPA(player.getName(), target.getName());

        TpaManager.Companion.set(tpa);
        target.sendMessage("TpaOther");
        player.sendMessage("TpaPlayer");

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (player == null || !player.hasPermission("miyukisystem.tpa")) return Collections.emptyList();
        return Bukkit.getOnlinePlayers().stream()
                .filter(player::canSee)
                .map(HumanEntity::getName)
                .collect(Collectors.toList());
    }
}
