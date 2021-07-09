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
    private val perm: String
) : Command(name) {

    object CommandRegistry : ManagerService {

        override fun load() {
            val classPath = ClassPath.from(Main.instance.javaClass.classLoader)
            var i = 0
            classPath.getTopLevelClassesRecursive("miyukisystem.commands.impl").forEach { classInfo ->
                try {
                    val command = classInfo.load().newInstance()
                    if (command is CommandService && command.canRegister()) {
                        val field = Bukkit.getPluginManager().javaClass.getDeclaredField("commandMap")
                        field.isAccessible = true
                        val commandMap = field.get(Bukkit.getPluginManager()) as CommandMap
                        commandMap.register("miyukisystem", command)
                        i++
                    }
                } catch (exception: Exception) {  }
            }
            Bukkit.getConsoleSender().sendMessage("§9§lMiyukiSystem  §aForam registrados §7$i §acomandos.")
        }

        override fun reload() { }


    }

    private val commandConfig = ConfigManager.commands.config

    private val enabled: Boolean
    private val commandName: String
    private val commandDescription: String

    init {
        val section = commandConfig.getConfigurationSection(name)!!
        enabled = section.getBoolean("Enabled")
        commandName = section.getString("Command")!!
        commandDescription = section.getString("Description")!!
    }

    fun canRegister() : Boolean {
        if (!enabled) return false
        val commands: Array<String> =
            if(commandName.contains("|"))
                commandName.split("|").toTypedArray()
            else
                arrayOf(commandName)
        if (commands.isNullOrEmpty()) return false;
        setDescription(description)
        val register = setName(commands[0])
        aliases = commands.drop(1)
        return register
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