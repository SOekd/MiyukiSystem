package miyukisystem.commands

import com.google.common.reflect.ClassPath
import miyukisystem.Main
import miyukisystem.manager.ManagerService
import miyukisystem.manager.impl.ConfigManager
import miyukisystem.manager.impl.sendCustomMessage
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender


abstract class CommandService(
    name: String,
    val perm: String
) : Command(name) {

    object CommandRegistry : ManagerService {

        override fun load() {
            val classPath = ClassPath.from(Main.instance.javaClass.classLoader)
            classPath.getTopLevelClassesRecursive("miyukisystem.commands.impl").forEach { classInfo ->
                try {
                    val cmd = classInfo.load().newInstance()
                    if (cmd is CommandService && cmd.canRegister()) {
                        val field = Bukkit.getPluginManager().javaClass.getDeclaredField("commandMap")
                        field.isAccessible = true
                        val commandMap = field.get(Bukkit.getPluginManager()) as CommandMap
                        commandMap.register("miyukisystem", cmd)
                    }
                } catch (exception: Exception) {  }
            }
        }

        override fun reload() { }


    }

    private val commandConfig = ConfigManager.commands.config

    private val enabled: Boolean
    private val commandName: String
    private val commandAliases: List<String>
    private val commandDescription: String

    init {
        val section = commandConfig.getConfigurationSection(name)!!
        enabled = section.getBoolean("Enabled")
        commandName = section.getString("Command")!!
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

    abstract fun onTabComplete(sender: CommandSender, args: Array<String>): List<String>

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>): MutableList<String> {
        return onTabComplete(sender, args).toMutableList();
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (!sender.hasPermission(perm)) {
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