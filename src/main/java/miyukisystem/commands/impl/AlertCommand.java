package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlertCommand extends CommandService {

    public AlertCommand() {
        super("Alert", "miyukisystem.alert");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length < 1) {
            sender.sendMessage("IncorrectAlertCommand");
            return false;
        }

        String warn = String.join(" ", args).replace('&', '§');

        Bukkit.broadcastMessage(warn);
        sender.sendMessage("AlertSent");

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }

}
