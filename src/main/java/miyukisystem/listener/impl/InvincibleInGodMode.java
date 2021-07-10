package miyukisystem.listener.impl;

import lombok.val;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class InvincibleInGodMode implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        val player = (Player) e.getEntity();

        if(player.hasMetadata("miyukisystem_god")) {
            e.setCancelled(true);
        }

    }
}
