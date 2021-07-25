package miyukisystem.model

import com.cryptomorin.xseries.messages.ActionBar
import miyukisystem.manager.Cacheable
import miyukisystem.manager.impl.title
import org.bukkit.Location
import org.bukkit.entity.Player

class Warp(
    val name: String,
    var location: Location,
    var permission: String,
    val title: Pair<String, String>?,
    val actionBar: String?,
    val message: Array<String>
) : Cacheable {

    fun teleport(player: Player) {
        player.teleport(location)
        if (title != null) {
            player.title(title.first, title.second)
        }
        if (actionBar != null)
            ActionBar.sendActionBar(player, actionBar)
        if (message.isNotEmpty())
            player.sendMessage(message)
    }

    override fun getKey(): String = name.toLowerCase()

}