package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class TitleCommand extends CommandService {

    public TitleCommand() {
        super("Title", "miyukisystem.title", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length < 1) {
            sender.sendMessage("IncorrectTitleCommand");
            return false;
        }

        String msg = String.join(" ", args).replace('&', '§');

        String[] title = msg.split("<nl>");

        if (!(msg.contains("<nl>"))) {
            Bukkit.getOnlinePlayers().forEach(it -> it.sendTitle(msg, "", 20, 60, 20));
        } else {
            Bukkit.getOnlinePlayers().forEach(it -> it.sendTitle(title[0], title[1], 20, 60, 20));
        }

        MessageManagerKt.sendCustomMessage(sender, "TitleSent");

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
