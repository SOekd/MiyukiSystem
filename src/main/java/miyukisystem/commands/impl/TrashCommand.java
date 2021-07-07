package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.util.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class TrashCommand extends CommandService {

    public TrashCommand() {
        super("Trash", "miyukisystem.trash");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        if (!(sender.hasPermission("miyukisystem.trash"))) {
            sender.sendMessage("NoPermission");
            return false;
        }

        Player player = (Player) sender;
        Inventory trash = Bukkit.createInventory(null, 54, "Lixeiro");
        ActionBar.sendActionBar(player, "OpeningTrash");
        player.openInventory(trash);
        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
