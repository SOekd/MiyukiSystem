package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.util.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class EnderChestCommand extends CommandService {

    public EnderChestCommand() {
        super("EnderChest");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 1 && player.hasPermission("miyukisystem.enderchest.admin")) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage("Offline");
                return false;
            }

            Inventory enderChest = target.getEnderChest();
            ActionBar.sendActionBar(player, "OpeningEnderChestPlayer"); // {player} retorna o target.getName();
            player.openInventory(enderChest);
            return false;
        }

        if (!player.hasPermission("miyukisystem.enderchest")) {
            player.sendMessage("NoPermission");
            return false;
        }

        Inventory enderChest = player.getEnderChest();
        ActionBar.sendActionBar(player, "OpeningEnderChest");
        player.openInventory(enderChest);

        return false;
    }
}
