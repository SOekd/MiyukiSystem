package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ClearChatCommand extends CommandService {

    public ClearChatCommand() {
        super("ClearChat", "miyukisystem.clearchat", false);
    }

    // Comando 100% feito.

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        val players = Bukkit.getOnlinePlayers();
        players.forEach(it -> it.sendMessage(StringUtils.repeat(" \n", 100)));
        players.forEach(it -> MessageManagerKt.sendCustomMessage(it, "ClearedChat"));

        return false;
    }


    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
