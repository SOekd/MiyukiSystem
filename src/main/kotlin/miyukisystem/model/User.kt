package miyukisystem.model

import miyukisystem.database.Database
import miyukisystem.manager.Cacheable
import miyukisystem.manager.impl.UserManager
import org.bukkit.Location

class User(
    val playerName: String,
    var tpaEnabled: Boolean,
    val homes: MutableMap<String, Location>,
    val kits: MutableMap<String, Long>
) : Cacheable {

    fun save() {
        UserManager.set(this)

        Database.USERS.update(this)
    }

    override fun getKey(): String = playerName.lowercase()

}
