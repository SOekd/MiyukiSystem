package miyukisystem.listener.impl;

import com.cryptomorin.xseries.SkullUtils;
import kotlin.random.Random;
import miyukisystem.manager.impl.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DropPlayerHeadOnDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!ConfigManager.Companion.getConfig().config.getBoolean("DropPlayerHeadOnDeath")) return;
        if (ConfigManager.Companion.getConfig().config.getInt("DropPlayerHeadChance") > Random.Default.nextInt(1, 100)) return;
        event.getEntity().getInventory().addItem(SkullUtils.getSkull(event.getEntity().getUniqueId()));
    }
}
