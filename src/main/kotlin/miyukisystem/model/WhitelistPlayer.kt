package miyukisystem.model

import miyukisystem.manager.Cacheable
import miyukisystem.manager.impl.WhitelistManager

data class WhitelistPlayer(
    val playerName: String,
    val canBreak: Boolean = false,
    val canPlace: Boolean = false,
    val canExecuteCommands: Boolean = false
) : Cacheable {

    override fun getKey(): String = playerName.toLowerCase()

    fun save() {
        WhitelistManager.set(this)
    }

}