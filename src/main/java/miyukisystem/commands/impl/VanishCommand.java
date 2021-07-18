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
import java.util.HashMap;
import java.util.List;

public class VanishCommand extends CommandService {

    public VanishCommand() {
        super("Vanish", "miyukisystem.vanish", false);
    }

    // Em desenvolvimento, pode ocorrer vários bugs.

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        val plugin = Main.Companion.getInstance();

        if(args.length == 0) {

            if (!(sender instanceof Player)) {
                MessageManagerKt.sendCustomMessage(sender, "NoConsole");
                return false;
            }

            val player = (Player) sender;
            val players = Bukkit.getOnlinePlayers();

            if(!player.hasMetadata("miyukisystem_vanish")) {
                player.setMetadata("miyukisystem_vanish", new FixedMetadataValue(plugin, "meta"));
                players.forEach(it -> {
                    if(!it.hasPermission("miyukisystem.bypass.vanish"))
                    it.hidePlayer(player);
                });
                MessageManagerKt.sendCustomMessage(player, "VanishJoined");
            } else {
                player.removeMetadata("miyukisystem_vanish", plugin);
                players.forEach(it -> it.showPlayer(player));
                MessageManagerKt.sendCustomMessage(player, "VanishLeaved");
            }
            return true;
        }

        val player = (Player) sender;
        val players = Bukkit.getOnlinePlayers();
        val target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            MessageManagerKt.sendCustomMessage(player, "PlayerOffline");
            return false;
        }

        val placeHolders = new HashMap<String, String>();
        placeHolders.put("{target}", target.getName());

        if (!target.hasMetadata("miyukisystem_vanish")) {
            target.setMetadata("miyukisystem_vanish", new FixedMetadataValue(plugin, "meta"));
            players.forEach(it -> it.hidePlayer(target));
            MessageManagerKt.sendCustomMessage(player, "VanishJoinedOther", placeHolders);
        } else {
            target.removeMetadata("miyukisystem_vanish", plugin);
            players.forEach(it -> it.showPlayer(target));
            MessageManagerKt.sendCustomMessage(player, "VanishLeavedOther", placeHolders);
        }

        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
