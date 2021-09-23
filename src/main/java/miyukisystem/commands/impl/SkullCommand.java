package miyukisystem.commands.impl;

import com.cryptomorin.xseries.SkullUtils;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.MessageManagerKt;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class SkullCommand extends CommandService {

    public SkullCommand() {
        super("Skull", "miyukisystem.skull", false);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            MessageManagerKt.sendCustomMessage(sender, "NoConsole");
            return false;
        }

        val player = (Player) sender;

        player.getInventory().addItem(args[0].length() > 16 ? NBTEditor.getHead(args[0]) : SkullUtils.getSkull(Bukkit.getOfflinePlayer(args[0]).getUniqueId()));
        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
