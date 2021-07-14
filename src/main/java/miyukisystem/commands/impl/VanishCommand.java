package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.Main;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class VanishCommand extends CommandService {

    public VanishCommand() {
        super("Vanish", "miyukisystem.vanish", false);
    }

    // Em desenvolvimento, pode ocorrer vários bugs.

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        val player = (Player) sender;

        if(!player.hasMetadata("miyukisystem_vanish")) {
            player.setMetadata("miyukysystem_vanish", new FixedMetadataValue(Main.Companion.getInstance(), true));
            Bukkit.getOnlinePlayers().forEach(players -> players.hidePlayer(Main.Companion.getInstance(), player));
            MessageManagerKt.sendCustomMessage(player, "VanishJoined");
        } else {
            player.removeMetadata("miyukysystem_vanish", Main.Companion.getInstance());
            Bukkit.getOnlinePlayers().forEach(players -> players.hidePlayer(Main.Companion.getInstance(), player));
            MessageManagerKt.sendCustomMessage(player, "VanishLeaved");
        }

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
