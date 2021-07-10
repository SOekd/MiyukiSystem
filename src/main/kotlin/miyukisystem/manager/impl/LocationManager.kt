package miyukisystem.manager.impl

import miyukisystem.manager.DataService
import miyukisystem.manager.ManagerService
import org.bukkit.Location

class LocationManager {

    companion object: DataService<ConfigLocation>(), ManagerService {
        override fun has(key: String): Boolean {
            TODO("Not yet implemented")
        }

        override fun get(key: String): ConfigLocation {
            TODO("Not yet implemented")
        }

        override fun set(value: ConfigLocation) {
            TODO("Not yet implemented")
        }

        override fun set(vararg value: ConfigLocation) {
            TODO("Not yet implemented")
        }

        override fun remove(key: String) {
            TODO("Not yet implemented")
        }

        override fun getAll(): List<ConfigLocation> {
            TODO("Not yet implemented")
        }

        override fun load() {
            TODO("Not yet implemented")
        }

        override fun reload() {
            TODO("Not yet implemented")
        }

    }

}

data class ConfigLocation(val path: String, val location: Location)