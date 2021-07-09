package miyukisystem.listener.impl;

import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class BlockImproperNicks implements Listener {

    private final YamlConfiguration config = ConfigManager.Companion.getConfig().config;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        for (String nickName : config.getStringList("BlockedNicks")) {
            if (nickName.equals(event.getPlayer().getName())) {
                event.getPlayer().kickPlayer("ImproperNick");
                Bukkit.getConsoleSender().sendMessage("ImproperNickLogged"); // {player} retorna o event.getPlayer().getName();
            }
        }

    }

}
