package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.PlayerManagerKt;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class TitleCommand extends CommandService {

    public TitleCommand() {
        super("Title", "miyukisystem.title", false);
    }

    // Comando 100% feito.

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length == 0) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectTitleCommand");
            return false;
        }

        String message = String.join(" ", args).replace('&', '§');

        String[] title = message.split("\\|");

        if (title.length == 1)
            Bukkit.getOnlinePlayers().forEach(it -> PlayerManagerKt.title(it, title[0], ""));
        else
            Bukkit.getOnlinePlayers().forEach(it -> PlayerManagerKt.title(it, title[0], title[1]));

        MessageManagerKt.sendCustomMessage(sender, "TitleSent");

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
