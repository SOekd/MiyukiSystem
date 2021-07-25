package miyukisystem.manager.impl

import miyukisystem.manager.DataService
import miyukisystem.manager.ManagerService
import miyukisystem.model.WhitelistPlayer
import java.util.*

class WhitelistManager {

    companion object : DataService<WhitelistPlayer>, ManagerService {

        override fun has(key: String): Boolean = ConfigManager.data.config.contains("Whitelist.$key")

        override fun get(key: String): WhitelistPlayer {
            val section = ConfigManager.data.config.getConfigurationSection("Whitelist.$key")!!
            return WhitelistPlayer(
                section.getString("Name")!!,
                section.getBoolean("CanBreak"),
                section.getBoolean("CanPlace"),
                section.getBoolean("CanExecuteCommands")
            )
        }

        override fun set(value: WhitelistPlayer) {
            val config = ConfigManager.data.config

            val section = "Whitelist.${value.getKey()}"
            config.set("$section.Name", value.playerName)
            config.set("$section.CanBreak", value.canBreak)
            config.set("$section.CanPlace", value.canPlace)
            config.set("$section.CanExecuteCommands", value.canExecuteCommands)
            save()
        }

        override fun set(vararg value: WhitelistPlayer) {
            value.forEach { set(it) }
        }

        override fun remove(key: String) {
            ConfigManager.data.config.set("Whitelist.$key", null)
            save()
        }

        override fun getAll(): List<WhitelistPlayer> {
            val config = ConfigManager.data.config
            if (!config.isConfigurationSection("Whitelist")) return Collections.emptyList()
            return config.getConfigurationSection("Whitelist")!!.getKeys(false).map { get(it) }
        }

        fun getState(): Boolean = ConfigManager.data.config.getBoolean("WhitelistState")

        fun setState(state: Boolean) {
            ConfigManager.data.config.set("WhitelistState", state)
            save()
        }

        private fun save() {
            ConfigManager.data.saveConfig()
        }

        override fun load() { }

        override fun reload() { }

    }

}