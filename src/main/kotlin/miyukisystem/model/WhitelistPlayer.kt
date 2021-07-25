package miyukisystem.model

import miyukisystem.manager.Cacheable
import miyukisystem.manager.impl.WhitelistManager

data class WhitelistPlayer(
    var playerName: String,
    var canBreak: Boolean = false,
    var canPlace: Boolean = false,
    var canExecuteCommands: Boolean = false
) : Cacheable {

    override fun getKey(): String = playerName.lowercase()

    fun save() {
        WhitelistManager.set(this)
    }

}