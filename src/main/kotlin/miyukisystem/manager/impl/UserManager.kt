package miyukisystem.manager.impl

import miyukisystem.Main
import miyukisystem.database.Database
import miyukisystem.manager.CachedDataService
import miyukisystem.manager.ManagerService
import miyukisystem.model.User
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class UserManager {

    companion object : CachedDataService<User>(), ManagerService {


        override fun load() {
            Bukkit.getPluginManager().registerEvents(object : Listener {

                @EventHandler
                fun onPlayerJoin(event: PlayerJoinEvent) {
                    val player = event.player

                    Database.USERS.has(player.name).thenAccept { hasUser ->
                        if (hasUser) {
                            Database.USERS.get(player.name).thenAccept { set(it) }
                        } else {
                            val user = User(
                                player.name,
                                true,
                                HashMap(),
                                HashMap()
                            )
                            set(user)
                        }
                    }
                }

                @EventHandler
                fun onPlayerQuit(event: PlayerQuitEvent) {
                    val player = event.player
                    if (has(player.name))
                        remove(player.name)
                }

            }, Main.instance)
        }

        override fun reload() { }

    }

}