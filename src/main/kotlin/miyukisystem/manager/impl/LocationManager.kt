package miyukisystem.manager.impl

import miyukisystem.manager.Cacheable
import miyukisystem.manager.DataService
import miyukisystem.manager.ManagerService
import miyukisystem.util.toCustomString
import miyukisystem.util.toLocation
import org.bukkit.Location

class LocationManager {

    companion object: DataService<CachedLocation>, ManagerService {

        private val config = ConfigManager.locations.config

        override fun has(key: String): Boolean = config.contains(key)

        override fun get(key: String): CachedLocation = CachedLocation(key, config.getString(key).toLocation())

        override fun set(value: CachedLocation) {
            config.set(value.getKey(), value.location.toCustomString())
            save()
        }

        override fun set(vararg value: CachedLocation) {
            value.forEach { set(it) }
        }

        override fun remove(key: String) {
            config.set(key, null)
            save()
        }

        override fun getAll(): List<CachedLocation> {
            TODO("Not yet implemented")
        }

        private fun save() {
            ConfigManager.locations.saveConfig()
        }

        override fun load() {
            val config = ConfigManager.locations
            config.saveDefaultConfig()
            config.reloadConfig()
        }

        override fun reload() {
            load()
        }

    }

}

data class CachedLocation(val path: String, val location: Location?) : Cacheable {

    override fun getKey(): String = path


}