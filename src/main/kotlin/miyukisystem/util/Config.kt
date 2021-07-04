package miyukisystem.util

import miyukisystem.Main
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class Config(private val path: String) {

    lateinit var config: YamlConfiguration

    init {
        reloadConfig()
    }

    fun saveConfig() {
        config.save(File(Main.instance.dataFolder, path))
    }

    fun saveDefaultConfig() {
        val file = File(Main.instance.dataFolder, path)
        if (!file.exists()) {
            if (Main.instance.getResource(path) == null) {
                file.parentFile.mkdirs()
                file.createNewFile()
            } else
                Main.instance.saveResource(path, false)
            reloadConfig()
        }
    }

    fun reloadConfig() {
        config = YamlConfiguration.loadConfiguration(File(Main.instance.dataFolder, path))
    }


}
