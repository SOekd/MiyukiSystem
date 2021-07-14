package miyukisystem.commands.impl;

import miyukisystem.Main;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class TrashCommand extends CommandService {

    public TrashCommand() {
        super("Trash", "miyukisystem.trash", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        Player player = (Player) sender;
        String titleMenu = Main.instance.getConfig().getString("TrashTitleMenu");
        int sizeMenu = Main.instance.getConfig().getInt("TrashSize");
        Inventory trash = Bukkit.createInventory(null, sizeMenu, titleMenu);
        player.openInventory(trash);
        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
