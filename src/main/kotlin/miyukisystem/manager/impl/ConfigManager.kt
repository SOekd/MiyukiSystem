package miyukisystem.manager.impl

import miyukisystem.Main
import miyukisystem.manager.ManagerService
import miyukisystem.util.Config
import java.io.File

class ConfigManager {

    companion object : ManagerService {

        val config = Config("config.yml")
        val messages = Config("messages.yml")
        val commands = Config("commands.yml")
        val subCommands = Config("subcommands.yml")
        val data = Config("data.yml")

        override fun load() {
            if (File(Main.instance.dataFolder, "ajuda.yml").exists())
                Main.instance.saveResource("ajuda.yml", false)

            config.saveDefaultConfig()
            messages.saveDefaultConfig()
            commands.saveDefaultConfig()
            data.saveDefaultConfig()
            subCommands.saveDefaultConfig()
            reload()
        }

        override fun reload() {
            config.reloadConfig()
            messages.reloadConfig()
            commands.reloadConfig()
            data.reloadConfig()
            subCommands.reloadConfig()
        }

    }

}