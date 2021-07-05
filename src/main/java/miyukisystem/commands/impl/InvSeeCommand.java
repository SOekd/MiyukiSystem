package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class InvSeeCommand extends CommandService {

    public InvSeeCommand() {
        super("Invsee");
    }

    // adicionar para aparecer a armadura

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        if (!(sender.hasPermission("miyukisystem.invsee"))) {
            sender.sendMessage("NoPermission");
            return false;
        }

        if (args.length != 1) {
            sender.sendMessage("IncorrectInvSeeCommand");
            return false;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage("Offline");
            return false;
        }

        Inventory targetInventory = target.getInventory();
        player.openInventory(targetInventory);

        return false;
    }

}
