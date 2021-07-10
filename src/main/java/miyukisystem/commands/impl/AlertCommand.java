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

public class AlertCommand extends CommandService {

    public AlertCommand() {
        super("Alert", "miyukisystem.alert", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length < 1) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectAlertCommand");
            return false;
        }

        String warn = String.join(" ", args).replace('&', '§');

        val placeHolders = new HashMap<String, String>();
        placeHolders.put("{alerta}", warn);

        Bukkit.getOnlinePlayers().forEach(it -> MessageManagerKt.sendCustomMessage(it, "Alert", placeHolders));
        MessageManagerKt.sendCustomMessage(sender, "AlertSent");

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }

}
