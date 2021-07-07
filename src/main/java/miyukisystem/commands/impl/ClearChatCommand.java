package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ClearChatCommand extends CommandService {

    public ClearChatCommand() {
        super("ClearChat", "miyukisystem.clearchat");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender.hasPermission("miyukisystem.clearchat"))) return false;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(StringUtils.repeat(" \n", 100)); // necessita de testes, não sei se irá funcionar.
        }

        Bukkit.broadcastMessage("ChatCleared");

        return false;
    }


    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
