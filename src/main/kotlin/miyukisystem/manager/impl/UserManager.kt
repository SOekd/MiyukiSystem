package miyukisystem.manager.impl

import miyukisystem.Main
import miyukisystem.manager.CachedDataService
import miyukisystem.manager.DataService
import miyukisystem.manager.ManagerService
import miyukisystem.model.User
import miyukisystem.util.Config
import miyukisystem.util.async
import miyukisystem.util.sync
import miyukisystem.util.toLocation
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.io.File

class UserManager {

    companion object : CachedDataService<User>(), ManagerService {


        override fun load() {
            Bukkit.getPluginManager().registerEvents(object : Listener {

                @EventHandler
                fun onPlayerJoin(event: PlayerJoinEvent) {
                    // terminar o mysql antes.
                }

                @EventHandler
                fun onPlayerQuit(event: PlayerQuitEvent) {

                }

            }, Main.instance)
        }

        override fun reload() { }

    }

}