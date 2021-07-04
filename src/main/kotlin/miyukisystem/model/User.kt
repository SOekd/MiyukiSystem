package miyukisystem.model

import miyukisystem.manager.Cacheable
import miyukisystem.manager.impl.UserManager
import miyukisystem.util.Config
import miyukisystem.util.async
import org.bukkit.Location
import java.io.File

class User(
    val playerName: String,
    val kits: HashMap<String, Long>,
    val homes: HashMap<String, Location>,
) : Cacheable {


    fun save(saveFile: Boolean = true) {
        UserManager.set(this)
        if (saveFile)
            saveFile()
    }

    private fun saveFile() {
        async {
            val config = Config("playerdata${File.separator}${playerName.toLowerCase()}.yml")
            val playerConfig = config.config
            playerConfig.set("PlayerName", playerName)
            kits.forEach { (name, time) -> if (time > System.currentTimeMillis()) playerConfig.set("Kits.$name", time) }
            homes.forEach { (name, location) -> playerConfig.set("Homes.$name", location) }
            config.saveConfig()
        }
    }

    override fun getKey(): String = playerName

}
