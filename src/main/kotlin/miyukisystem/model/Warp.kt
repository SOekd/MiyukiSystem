package miyukisystem.model

import miyukisystem.manager.Cacheable
import miyukisystem.manager.impl.translateColorCodes
import miyukisystem.util.ActionBar
import org.bukkit.Location
import org.bukkit.entity.Player

class Warp(
    val name: String,
    var location: Location,
    private val title: Pair<String, String>?,
    private val actionBar: String?,
    private val message: Array<String>
) : Cacheable {

    fun teleport(player: Player) {
        player.teleport(location)
        if (title != null) {
            player.sendTitle(title.first, title.second)
        }
        if (actionBar != null)
            ActionBar.sendActionBar(player, actionBar)
        if (message.isNotEmpty())
            player.sendMessage(message)
    }

    override fun getKey(): String = name.toLowerCase()

}