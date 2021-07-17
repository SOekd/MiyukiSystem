package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.Main;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GodCommand extends CommandService {

    // Em desenvolvimento pode ocorrer bugs

    public GodCommand() {
        super("God", "miyukisystem.god", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        val plugin = Main.Companion.getInstance();

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                MessageManagerKt.sendCustomMessage(sender, "NoConsole");
                return false;
            }

            val player = (Player) sender;

            if (player.hasMetadata("miyukisystem_god")) {
                player.removeMetadata("miyukisystem_god", plugin);
                MessageManagerKt.sendCustomMessage(player, "DisabledGodMode");
            } else {
                player.setMetadata("miyukisystem_god", new FixedMetadataValue(Main.Companion.getInstance(), "meta"));
                MessageManagerKt.sendCustomMessage(player, "EnabledGodMode");
            }
            return true;
        }


        if (!(sender.hasPermission("miyukisystem.god.other"))) {
            MessageManagerKt.sendCustomMessage(sender, "NoPermission");
            return false;
        }

        val target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            MessageManagerKt.sendCustomMessage(sender, "PlayerOffline");
            return false;
        }

        val placeHolders = new HashMap<String, String>();
        placeHolders.put("{target}", target.getName());

        if (target.hasMetadata("miyukisystem_god")) {
            target.removeMetadata("miyukisystem_god", plugin);
            MessageManagerKt.sendCustomMessage(sender, "DisabledGodModeOther", placeHolders);
        } else {
            target.setMetadata("miyukisystem_god", new FixedMetadataValue(Main.Companion.getInstance(), "meta"));
            MessageManagerKt.sendCustomMessage(sender, "EnabledGodModeOther", placeHolders);
        }
        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        val player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0 || player == null || !player.hasPermission("miyukisystem.god.other"))
            return Collections.emptyList();
        val lastWord = args[args.length - 1];
        return Bukkit.getOnlinePlayers().stream()
                .filter(it -> player.canSee(it) && StringUtils.startsWithIgnoreCase(it.getName(), lastWord))
                .map(HumanEntity::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
