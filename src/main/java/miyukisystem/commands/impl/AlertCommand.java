package miyukisystem.commands.impl;

import miyukisystem.commands.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AlertCommand extends CommandService {

    public AlertCommand() {
        super("Alerta");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender.hasPermission("miyukisystem.alert"))) {
            sender.sendMessage("NoPermission");
            return false;
        }

        if (args.length < 1) {
            sender.sendMessage("IncorrectAlertCommand");
            return false;
        }

        String warn = String.join(" ", args).replace('&', '§');

        Bukkit.broadcastMessage(warn);
        sender.sendMessage("AlertSent");

        return false;
    }
}
