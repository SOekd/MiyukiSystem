package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class CheckIPCommand extends CommandService {


    public CheckIPCommand() {
        super("CheckIP", "miyukisystem.checkip", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length != 1) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectCheckIPCommand");
            return false;
        }

        val target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            MessageManagerKt.sendCustomMessage(sender, "PlayerOffline");
            return false;
        }

        val placeHolders = new HashMap<String, String>();
        placeHolders.put("{target}", target.getName());
        placeHolders.put("{ip}", String.valueOf(target.getAddress()));

        MessageManagerKt.sendCustomMessage(sender, "CheckingIP", placeHolders);

        return false;
    }

    // colocar tab complete
    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
