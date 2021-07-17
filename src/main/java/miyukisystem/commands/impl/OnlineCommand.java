package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class OnlineCommand extends CommandService {

    public OnlineCommand() {
        super("Online", "miyukisystem.online", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        val placeHolders = new HashMap<String, String>();
        placeHolders.put("{online}", String.valueOf(Bukkit.getOnlinePlayers().size()));

        MessageManagerKt.sendCustomMessage(sender, "Online", placeHolders);

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
