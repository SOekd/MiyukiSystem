package miyukisystem.manager.impl

import miyukisystem.manager.ManagerService
import miyukisystem.util.Config

class ConfigManager {

    companion object : ManagerService {

        val ajuda = Config("ajuda.yml")
        val config = Config("config.yml")
        val messages = Config("messages.yml")
        val commands = Config("commands.yml")

        override fun load() {
            config.saveDefaultConfig()
            messages.saveDefaultConfig()
            commands.saveDefaultConfig()
            reload()
        }

        override fun reload() {
            config.reloadConfig()
            messages.reloadConfig()
            commands.reloadConfig()
        }

    }

}