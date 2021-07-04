package miyukisystem.manager.impl

import miyukisystem.manager.ManagerService
import miyukisystem.util.Config

class ConfigManager {

    companion object : ManagerService {

        val messages = Config("messages.yml")
        val commands = Config("commands.yml")

        override fun load() {
            messages.saveDefaultConfig()
            commands.saveDefaultConfig()
            reload()
        }

        override fun reload() {
            messages.reloadConfig()
            commands.reloadConfig()
        }

    }

}