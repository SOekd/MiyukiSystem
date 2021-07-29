package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ColorsCommand extends CommandService {

    public ColorsCommand() {
        super("Colors", "miyukisystem.colors", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        MessageManagerKt.sendCustomMessage(sender, "Colors");

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
