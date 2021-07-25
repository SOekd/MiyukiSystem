package miyukisystem.manager.impl

import miyukisystem.manager.DataService
import miyukisystem.manager.ManagerService
import miyukisystem.model.WhitelistPlayer
import java.util.*

class WhitelistManager {

    companion object : DataService<WhitelistPlayer>, ManagerService {

        override fun has(key: String): Boolean = ConfigManager.data.config.contains("Whitelist.${key.lowercase()}")

        override fun get(key: String): WhitelistPlayer {
            val section = "Whitelist.${key.lowercase()}"
            val config = ConfigManager.data.config
            return WhitelistPlayer(
                config.getString("$section.Name")!!,
                config.getBoolean("$section.CanBreak"),
                config.getBoolean("$section.CanPlace"),
                config.getBoolean("$section.CanExecuteCommands")
            )
        }

        override fun set(value: WhitelistPlayer) {
            val section = "Whitelist.${value.getKey().lowercase()}"
            val config = ConfigManager.data.config
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