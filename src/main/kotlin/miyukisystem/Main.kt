package miyukisystem

import io.github.slimjar.app.builder.ApplicationBuilder
import miyukisystem.commands.CommandService
import miyukisystem.database.Database
import miyukisystem.hook.Vault
import miyukisystem.listener.ListenerRegistry
import miyukisystem.manager.ManagerService
import miyukisystem.manager.impl.ConfigManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.nio.file.Paths

class Main : JavaPlugin() {

    companion object {

        lateinit var instance: Main

    }


    override fun onLoad() {

        Bukkit.getConsoleSender().sendMessage("§9§lMiyukiSystem  §cCarregando/Baixando as bibliotecas do plugin...")
        Bukkit.getConsoleSender().sendMessage("§9§lMiyukiSystem  §cEsta acao pode levar um tempo, aguarde.")
        ApplicationBuilder
            .appending("MiyukiSystem")
            .downloadDirectoryPath(Paths.get(File(dataFolder, "libs").absolutePath))
            .build()
        "§9§lMiyukiSystem  §aTodas as bibliotecas foram baixadas e carregadas com sucesso.".sendToConsole()
    }

    override fun onEnable() {
        instance = this

        if (!Vault.setupEconomy()) {
            "§9§lMiyukiSystem  §cO vault nao foi encontrado no servidor.".sendToConsole()
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        ConfigManager.load()
        CommandService.CommandRegistry.load()
        ListenerRegistry.load()
        ManagerService.load()
        Database.dataSource

    }

    override fun onDisable() {
        try {
            Database.dataSource.closeConnection()
        } catch (ignored: Exception) { }
    }


}

fun String?.sendToConsole() {
    if (this == null || this == "") return
    Bukkit.getConsoleSender().sendMessage(this)
}
