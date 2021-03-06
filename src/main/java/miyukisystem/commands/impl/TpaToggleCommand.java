package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import miyukisystem.manager.impl.UserManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class TpaToggleCommand extends CommandService {

    public TpaToggleCommand() {
        super("TpaToggle", "miyukisystem.tpatoggle", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        val player = (Player) sender;

        val user = UserManager.Companion.get(player.getName());

        user.setTpaEnabled(!user.getTpaEnabled());
        user.save();
        MessageManagerKt.sendCustomMessage(player, !user.getTpaEnabled() ? "TpaDisabled" : "TpaEnabled");

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
