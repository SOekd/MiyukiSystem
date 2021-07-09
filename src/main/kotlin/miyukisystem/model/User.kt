package miyukisystem.model

import miyukisystem.manager.Cacheable
import miyukisystem.manager.impl.UserManager
import miyukisystem.util.Config
import miyukisystem.util.async
import org.bukkit.Location
import java.io.File

class User(
    val playerName: String,
    val tpaEnabled: Boolean,
    val homes: MutableMap<String, Location>,
    val kits: MutableMap<String, Long>
) : Cacheable {


    fun save(saveFile: Boolean = true) {
        UserManager.set(this)
        if (saveFile) {
            async {
                saveFile()
            }
        }
    }

    fun saveFile() {
    }

    override fun getKey(): String = playerName.toLowerCase()

}
