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

import java.util.List;

public class GodCommand extends CommandService {

    public GodCommand(@NotNull String name, @NotNull String perm, boolean async) {
        super("god", "miyukisystem.god", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if(args.length == 0) {
            if (!(sender instanceof Player)) {
                MessageManagerKt.sendCustomMessage(sender, "NoConsole");
                return false;
            }

            val player = (Player) sender;

            if(player.hasMetadata("miyukisystem_god")) {
                player.removeMetadata("miyukisystem_god", Main.Companion.getInstance());
                player.sendMessage("DisabledGodMode");
            } else {
                player.setMetadata("miyukysystem_mod", new FixedMetadataValue(Main.Companion.getInstance(), true));
                player.sendMessage("EnabledGodMode");
            }

            return false;
        }

        val target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            sender.sendMessage("Offline");
            return false;
        }

        if(target.hasMetadata("miyukisystem_god")) {
            target.removeMetadata("miyukisystem_god", Main.Companion.getInstance());
            sender.sendMessage("DisabledGodModeOther");
        } else {
            target.setMetadata("miyukysystem_mod", new FixedMetadataValue(Main.Companion.getInstance(), true));
            sender.sendMessage("EnabledGodModeOther");
        }

        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
