package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.util.ActionBar;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WorkbenchCommand extends CommandService {

    public WorkbenchCommand() {
        super("Workbench", "miyukisystem.workbench");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("NoConsole");
            return false;
        }

        Player player = (Player) sender;
        ActionBar.sendActionBar(player,"OpeningWorkbench");
        player.closeInventory();
        player.openWorkbench(player.getLocation(), true);

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
