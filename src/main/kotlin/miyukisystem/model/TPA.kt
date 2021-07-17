package miyukisystem.model

import miyukisystem.manager.Cacheable

data class TPA(val from: String, val to: String) : Cacheable {

    override fun getKey(): String = from

}