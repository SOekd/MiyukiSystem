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
                    async {
                        val player = event.player
                        if (has(player.name)) return@async
                        val config = Config("playerdata${File.separator}${player.name.toLowerCase()}.yml")
                        config.saveDefaultConfig()
                        config.reloadConfig()
                        val playerConfig = config.config
                        val user = User(
                            player.name,
                            if (playerConfig.contains("Kits") && playerConfig.isConfigurationSection("Kits")) {
                                val kits = HashMap<String, Long>()
                                playerConfig.getConfigurationSection("Kits")!!.getKeys(false).forEach {
                                    val time = playerConfig.getLong("Kits.$it")
                                    if (time > System.currentTimeMillis())
                                        kits[it] = time
                                    else {
                                        playerConfig.set("Kits.$it", null)
                                        config.saveConfig()
                                    }
                                }
                                kits
                            } else HashMap(),
                            if (playerConfig.contains("TpaEnabled")) playerConfig.getBoolean("TpaEnabled")
                            else false,
                            if (playerConfig.contains("Vanish") && player.hasPermission("miyukisystem.vanish"))
                                playerConfig.getBoolean("Vanish")
                            else false,
                            if (playerConfig.contains("Homes") && playerConfig.isConfigurationSection("Homes")) {
                                val homes = HashMap<String, Location>()
                                playerConfig.getConfigurationSection("Homes")!!.getKeys(false).forEach {
                                    homes[it] = it.toLocation()!!
                                }
                                homes
                            } else HashMap()
                        )
                        sync {
                            set(user)
                        }
                    }
                }

                @EventHandler
                fun onPlayerQuit(event: PlayerQuitEvent) {
                    val player = event.player
                    if (!has(player.name)) return
                    get(player.name).save()
                }

            }, Main.instance)
        }

        override fun reload() { }

    }

}