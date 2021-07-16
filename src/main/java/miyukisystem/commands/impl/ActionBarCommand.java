package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.PlayerManagerKt;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ActionBarCommand extends CommandService {

    public ActionBarCommand() {
        super("ActionBar", "miyukisystem.actionbar", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length < 1) {
            MessageManagerKt.sendCustomMessage(sender, "IncorrectActionBarCommand");
            return false;
        }

        val player = Bukkit.getPlayer(args[0]);

        val array = Arrays.copyOfRange(args, 1, args.length);
        val message = MessageManagerKt.translateColorCodes(String.join(" ", array));

        if (player == null) {

            if (args[0].equalsIgnoreCase("*")) {

                Bukkit.getOnlinePlayers().forEach(it -> PlayerManagerKt.sendActionBar(it, message));

                return false;
            }

            MessageManagerKt.sendCustomMessage(sender, "Offline");
            return false;
        }

        PlayerManagerKt.sendActionBar(player, message);
        MessageManagerKt.sendCustomMessage(sender, "ActionBarSent");

        return false;
    }

    // colocar tabcomplete
    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
