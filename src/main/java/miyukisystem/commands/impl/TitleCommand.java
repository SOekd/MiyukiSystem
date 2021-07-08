package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class TitleCommand extends CommandService {

    public TitleCommand() {
        super("Title", "miyukisystem.title");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length < 1) {
            sender.sendMessage("IncorrectTitleCommand");
            return false;
        }

        String msg = String.join(" ", args).replace('&', '§');

        String[] txt = msg.split("<nl>");

        if (!(msg.contains("<nl>"))) {
            // envia o title pra todos os players
        } else {
            // envia o title para todos os players
        }

        sender.sendMessage("TitleSent");

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
