package miyukisystem.manager.impl

import miyukisystem.manager.DataService
import miyukisystem.manager.ManagerService
import miyukisystem.model.CachedLocation
import miyukisystem.util.toCustomString
import miyukisystem.util.toLocation
import java.util.*

class LocationManager {

    companion object: DataService<CachedLocation>, ManagerService {

        private val config = ConfigManager.data.config

        override fun has(key: String): Boolean = config.contains("Locations.$key")

        override fun get(key: String): CachedLocation = CachedLocation("Locations.$key", config.getString(key).toLocation())

        override fun set(value: CachedLocation) {
            config.set("Locations.${value.getKey()}", value.location.toCustomString())
            save()
        }

        override fun set(vararg value: CachedLocation) {
            value.forEach { set(it) }
        }

        override fun remove(key: String) {
            config.set("Locations.$key", null)
            save()
        }


        override fun getAll(): List<CachedLocation> {
            if (!config.isConfigurationSection("Locations")) return Collections.emptyList()
            return config.getConfigurationSection("Locations")!!.getKeys(false).map { get(it) }
        }

        private fun save() {
            ConfigManager.data.saveConfig()
        }

        override fun load() {
            val config = ConfigManager.data
            config.saveDefaultConfig()
            config.reloadConfig()
        }

        override fun reload() {
            load()
        }

    }

}