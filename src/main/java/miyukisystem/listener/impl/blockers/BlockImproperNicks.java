package miyukisystem.listener.impl.blockers;

import lombok.val;
import miyukisystem.manager.impl.ConfigManager;
import miyukisystem.manager.impl.MessageManager;
import miyukisystem.manager.impl.MessageManagerKt;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class BlockImproperNicks implements Listener {

    private final YamlConfiguration config = ConfigManager.Companion.getConfig().config;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        for (String nickName : config.getStringList("BlockedNicks")) {
            if (nickName.equals(event.getPlayer().getName())) {
                val message = MessageManager.Companion.getMessage("ImproperNick");
                val kickMessage = String.join(" \n", message);
                event.getPlayer().kickPlayer(kickMessage);

                val placeHolders = new HashMap<String, String>();
                placeHolders.put("{player}", event.getPlayer().getName());
                placeHolders.put("{ip}", String.valueOf(event.getPlayer().getAddress()));

                MessageManagerKt.sendCustomMessage(Bukkit.getConsoleSender(), "ImproperNickLogged", placeHolders);
            }
        }

    }

}
