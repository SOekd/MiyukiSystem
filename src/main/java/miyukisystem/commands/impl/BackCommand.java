package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BackCommand extends CommandService {

    public BackCommand() {
        super("Back", "miyukisystem.back");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
