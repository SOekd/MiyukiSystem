package miyukisystem.commands

import miyukisystem.manager.impl.ConfigManager
import org.bukkit.command.CommandSender

abstract class SubCommand(val name: String) {

    val aliases: List<String>
    val enabled : Boolean

    init {
        val section = ConfigManager.subCommands.config.getConfigurationSection(name)!!

        aliases = section.getString("Aliases")!!.split("|")
        enabled = section.getBoolean("Enabled")
    }

    fun execute(sender: CommandSender, args: Array<String>): Boolean {
        val command = args[0]
        val correctArgs = args.sliceArray(1 until args.size)
        return execute(sender, correctArgs, command)
    }

    abstract fun execute(sender: CommandSender, args: Array<String>, command: String): Boolean

}