package miyukisystem.manager.impl

import miyukisystem.manager.DataService
import miyukisystem.manager.ManagerService
import miyukisystem.model.WhitelistPlayer
import java.util.*

class WhitelistManager {

    companion object : DataService<WhitelistPlayer>, ManagerService {

        val config = ConfigManager.data.config

        override fun has(key: String): Boolean = config.contains("Whitelist.$key")

        override fun get(key: String): WhitelistPlayer {
            val section = config.getConfigurationSection("Whitelist.$key")!!
            return WhitelistPlayer(
                section.getString("Name")!!,
                section.getBoolean("CanBreak"),
                section.getBoolean("CanPlace"),
                section.getBoolean("CanExecuteCommands")
            )
        }

        override fun set(value: WhitelistPlayer) {
            val section = config.getConfigurationSection("Whitelist.${value.getKey()}")!!
            section.set("Name", value.playerName)
            section.set("CanBreak", value.canBreak)
            section.set("CanPlace", value.canPlace)
            section.set("CanExecuteCommands", value.canExecuteCommands)
            save()
        }

        override fun set(vararg value: WhitelistPlayer) {
            value.forEach { set(it) }
        }

        override fun remove(key: String) {
            config.set("Whitelist.$key", null)
            save()
        }

        override fun getAll(): List<WhitelistPlayer> {
            if (!config.isConfigurationSection("Whitelist")) return Collections.emptyList()
            return config.getConfigurationSection("Whitelist")!!.getKeys(false).map { get(it) }
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