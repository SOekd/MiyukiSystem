package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.PlayerManagerKt;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InvSeeCommand extends CommandService {

    public InvSeeCommand() {
        super("InvSee", "miyukisystem.invsee", false);
    }

    // adicionar para aparecer a armadura

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        if (args.length != 1) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectInvSeeCommand");
            return false;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            MessageManagerKt.sendCustomMessage(sender, "Offline");
            return false;
        }

        if (target == player) {
            MessageManagerKt.sendCustomMessage(sender, "");
            return false;
        }

        Inventory targetInventory = target.getInventory();
        PlayerManagerKt.sendActionBar(target, "OpeningInventory"); // path do messages.yml. {player} retorna target.getName();
        player.openInventory(targetInventory);

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (player == null || !player.hasPermission("miyukisystem.invsee")) return Collections.emptyList();
        return Bukkit.getOnlinePlayers().stream()
                .filter(player::canSee)
                .map(HumanEntity::getName)
                .collect(Collectors.toList());
    }
}
