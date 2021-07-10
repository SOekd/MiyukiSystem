package miyukisystem.commands.impl;

import com.google.common.collect.Iterables;
import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.TPA;
import miyukisystem.manager.impl.TpaManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TpDenyCommand extends CommandService {

    public TpDenyCommand() {
        super("TpDeny", "miyukisystem.tpa", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        Player player = (Player) sender;

        if(args.length < 1) {

            val targetTPA = TpaManager.Companion.lastReceived(player.getName());

            if (targetTPA == null) {
                // mensagem que não recebeu nenhum tpa.
                return false;
            }

            val targetName = targetTPA.getFrom();
            val target = Bukkit.getPlayer(targetName);

            TpaManager.Companion.remove(targetName);

            player.sendMessage("DenyTpaOther");

            if (target!= null) {
                target.sendMessage("DenyTpaPlayer");
            }
        } else {

            Player target = Bukkit.getPlayer(args[0]);

            if(target == null) {
                player.sendMessage("OfflinePlayer");
                return false;
            }

            if(target.equals(player)) {
                player.sendMessage("Yourself");
                return false;
            }

            TpaManager.Companion.remove(target.getName());

            player.sendMessage("DenyTpaOther");
            target.sendMessage("DenyTpaPlayer");

        }

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (player == null || !player.hasPermission("miyukisystem.tpdeny")) return Collections.emptyList();
        return TpaManager.Companion.getAll().stream()
                .filter(it -> it.getTo().equalsIgnoreCase(player.getName()))
                .map(TPA::getFrom)
                .collect(Collectors.toList());
    }
}
