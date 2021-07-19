package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class EnchantingTableCommand extends CommandService {

    public EnchantingTableCommand() {
        super("EnchantingTable", "miyukisystem.enchantingtable", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        val player = (Player) sender;
        player.closeInventory();
        player.openEnchanting(player.getLocation(), true);

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }

}
