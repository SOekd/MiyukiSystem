package miyukisystem.commands.impl;

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

public class TpAcceptCommand extends CommandService {

    public TpAcceptCommand() {
        super("TpAccept", "miyukisystem.tpaccept"); // talvez deixar sem a perm de tpa pra esses comandos de tpa.
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

            if (target == null) {
                // aviso que expirou pq o cara deslogou.
                return false;
            }

            player.sendMessage("AcceptTpaOther");
            target.sendMessage("AcceptTpaPlayer");
            target.teleport(player.getLocation());

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

            if(!TpaManager.Companion.has(target.getName())) {
                player.sendMessage("NoHaveRequests");
                return false;
            }

            player.sendMessage("AcceptTpaOther");
            target.sendMessage("AcceptTpaPlayer");
            target.teleport(player.getLocation());

        }

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (player == null || !player.hasPermission("miyukisystem.tpaccept")) return Collections.emptyList();
        return TpaManager.Companion.getAll().stream()
                .filter(it -> it.getTo().equalsIgnoreCase(player.getName()))
                .map(TPA::getFrom)
                .collect(Collectors.toList());
    }
}
