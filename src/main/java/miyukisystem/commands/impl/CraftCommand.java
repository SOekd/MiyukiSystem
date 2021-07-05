package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.util.ActionBar;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CraftCommand extends CommandService {

    public CraftCommand() {
        super("craft");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        if (!(sender.hasPermission("miyukisystem.craft"))) {
            sender.sendMessage("NoPermission");
            return false;
        }

        Player player = (Player) sender;
        ActionBar.sendActionBar(player,"OpeningWorkbench");
        player.closeInventory();
        player.openWorkbench(player.getLocation(), true);

        return false;
    }
}
