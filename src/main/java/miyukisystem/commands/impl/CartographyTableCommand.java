package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class CartographyTableCommand extends CommandService {

    public CartographyTableCommand() {
        super("CartographyTable", "miyukisystem.cartographytable", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        val player = (Player) sender;

        player.closeInventory();

        try {
            player.openCartographyTable(player.getLocation(), true);
        } catch (Exception exception) {
            player.sendMessage("§9§lMiyukiSystem  §cEste comando não pode ser executado na versão atual do servidor.");
        }

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
