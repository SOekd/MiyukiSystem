package miyukisystem

import miyukisystem.commands.CommandService
import miyukisystem.hook.Vault
import miyukisystem.manager.impl.ConfigManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {

        lateinit var instance: Main

    }

    private val cs = Bukkit.getConsoleSender()
    private val pm = Bukkit.getPluginManager()

    override fun onEnable() {
        instance = this
        if (!Vault.setupEconomy()) {
            cs.sendMessage("§9§lMiyukiSystem §cO vault nao foi encontrado no servidor.")
            pm.disablePlugin(this)
            return
        }
        ConfigManager.load()
        CommandService.CommandRegistry.load()
    }

    override fun onDisable() {

    }

    fun registerEvents() {

    }


}
