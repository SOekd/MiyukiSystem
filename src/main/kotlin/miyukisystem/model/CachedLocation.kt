package miyukisystem.model

import miyukisystem.manager.Cacheable
import org.bukkit.Location

data class CachedLocation(val path: String, val location: Location?) : Cacheable {

    override fun getKey(): String = path

}