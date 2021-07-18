package miyukisystem.commands.impl;

import lombok.val;
import miyukisystem.commands.CommandService;
import miyukisystem.manager.impl.ConfigManager;
import miyukisystem.manager.impl.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class KickAllCommand extends CommandService {

    public KickAllCommand() {
        super("KickAll", "miyukisystem.kickall", false);
    }


    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {

        val config = ConfigManager.Companion.getConfig().config;

        val kickMessage = ChatColor.translateAlternateColorCodes('&', config.getString("KickAllMessage"));
        val kickSuccess = ChatColor.translateAlternateColorCodes('&', config.getString("KickAllSuccess"));

        for (Player player : Bukkit.getOnlinePlayers()) {

            if (player != sender) {
                player.kickPlayer(kickMessage);
            }

        }

        sender.sendMessage(config.getString(kickSuccess));
        return false;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
