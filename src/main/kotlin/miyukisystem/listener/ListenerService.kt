package miyukisystem.listener

import com.google.common.reflect.ClassPath
import miyukisystem.Main
import miyukisystem.commands.CommandService
import miyukisystem.manager.ManagerService
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.event.Listener

abstract class ListenerService : Listener {

    object ListenerRegistry : ManagerService {

        override fun load() {
            val classPath = ClassPath.from(Main.instance.javaClass.classLoader)
            var i = 0
            classPath.getTopLevelClassesRecursive("miyukisystem.listener.impl").forEach { classInfo ->
                try {
                    val listener = classInfo.load().newInstance()
                    if (listener is ListenerService) {
                        listener.register()
                        i++
                    }
                } catch (exception: Exception) {  }
            }
            Bukkit.getConsoleSender().sendMessage("§9§lMiyukiSystem  §aForam registrados §7$i §aeventos.")
        }

        override fun reload() { }

    }

    fun register() {
        Bukkit.getPluginManager().registerEvents(this, Main.instance)
    }

}