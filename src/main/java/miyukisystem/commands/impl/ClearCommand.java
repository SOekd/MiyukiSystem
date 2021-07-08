package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ClearCommand extends CommandService {

    public ClearCommand() {
        super("Clear", "miyukisystem.clear");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage("NoConsole");
            return false;
        }

        if (args.length > 1) {
            sender.sendMessage("IncorrectClearCommand");
            return false;
        }

        if (args.length == 1) {

            if (!(sender.hasPermission("miyukisystem.clear.other"))) {
                sender.sendMessage("NoPermission");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage("Offline");
                return false;
            }

            if (!inventoryIsEmpty(target)) {
                sender.sendMessage("AlreadyTargetCleared"); // {player} retorna o target.getName
            } else {
                clearInventory(target);
                sender.sendMessage("TargetCleared");
            }

            return false;
        }

        Player player = (Player) sender;

        if (!inventoryIsEmpty(player)) {
            clearInventory(player);
            player.sendMessage("Cleared");
        } else {
            sender.sendMessage("AlreadyCleared");
        }

        return false;
    }

    private boolean inventoryIsEmpty(Player player) {
        // soekd
        return false;
    }

    private void clearInventory(Player player) {
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.setItemOnCursor(null);
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
