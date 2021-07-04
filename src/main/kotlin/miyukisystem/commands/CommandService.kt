package miyukisystem.commands

import com.google.common.reflect.ClassPath
import miyukisystem.Main
import miyukisystem.manager.ManagerService
import miyukisystem.manager.impl.ConfigManager
import miyukisystem.manager.impl.sendCustomMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

abstract class CommandService(
    name: String,
) : Command(name){

    object CommandRegistry : ManagerService {

        override fun load() {
            val classPath = ClassPath.from(Main.javaClass.classLoader)
            classPath.getTopLevelClassesRecursive("miyukisystem.commands.impl").forEach { classInfo ->
                val obj = Class.forName(classInfo.name).newInstance()
                if (obj is CommandService && obj.canRegister()) {
                    val simpleCommandMap = Main.instance.server
                }
            }
        }

        override fun reload() { }


    }

    private val commandConfig = ConfigManager.commands.config

    private val enabled: Boolean
    private val commandPermission: String
    private val commandName: String
    private val commandAliases: List<String>
    private val commandDescription: String

    init {
        val section = commandConfig.getConfigurationSection(name)!!
        enabled = section.getBoolean("Enabled")
        commandName = section.getString("Name")!!
        commandPermission = section.getString("Permission")!!
        commandAliases = section.getStringList("Aliases")
        commandDescription = section.getString("Description")!!
    }

    fun canRegister() : Boolean {
        var register: Boolean = setName(commandName)
        setDescription(description)
        setAliases(commandAliases)
        return register && enabled
    }

    abstract fun execute(sender: CommandSender, args: Array<String>): Boolean

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (!sender.hasPermission(commandPermission)) {
            sender.sendCustomMessage("NoPermission")
            return false
        }
        return try {
            execute(sender, args)
        } catch (exception: Exception) {
            exception.printStackTrace()
            sender.sendMessage("§dMiyukiSystem ➡ §cOcorreu um erro ao tentar executar o comando. Contate o administrador.")
            false;
        }
    }

}