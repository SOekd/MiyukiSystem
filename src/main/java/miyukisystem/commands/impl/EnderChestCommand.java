package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.util.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EnderChestCommand extends CommandService {

    public EnderChestCommand() {
        super("EnderChest", "miyukisystem.enderchest");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        Player player = (Player) sender;

        if (args.length > 0) {

            if (!(sender.hasPermission("miyukisystem.enderchest.other"))) {
                sender.sendMessage("NoPermission");
                return false;
            }

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

        Inventory enderChest = player.getEnderChest();
        ActionBar.sendActionBar(player, "OpeningEnderChest");
        player.openInventory(enderChest);

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (player == null || !player.hasPermission("miyukisystem.enderchest.other")) return Collections.emptyList();
        return Bukkit.getOnlinePlayers().stream()
                .filter(player::canSee)
                .map(HumanEntity::getName)
                .collect(Collectors.toList());
    }
}
