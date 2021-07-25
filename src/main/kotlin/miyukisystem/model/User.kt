package miyukisystem.model

import miyukisystem.database.Database
import miyukisystem.manager.Cacheable
import miyukisystem.manager.impl.UserManager
import miyukisystem.util.async
import org.bukkit.Location

class User(
    val playerName: String,
    val tpaEnabled: Boolean,
    val homes: MutableMap<String, Location>,
    val kits: MutableMap<String, Long>
) : Cacheable {


    fun save() {
        UserManager.set(this)

        // Testar para ver se irá funcionar corretamente.
        async {
            Database.USERS.insertOrUpdate(this)
        }
    }


    override fun getKey(): String = playerName.toLowerCase()

}
