package miyukisystem.listener

import com.google.common.reflect.ClassPath
import miyukisystem.Main
import miyukisystem.manager.ManagerService
import miyukisystem.sendToConsole
import org.bukkit.Bukkit
import org.bukkit.event.Listener

class ListenerRegistry {

    companion object : ManagerService {

        override fun load() {
            val classPath = ClassPath.from(Main.instance.javaClass.classLoader)
            var i = 0
            classPath.getTopLevelClassesRecursive("miyukisystem.listener.impl").forEach { classInfo ->
                try {
                    val listener = classInfo.load().newInstance()
                    if (listener is Listener) {
                        Bukkit.getPluginManager().registerEvents(listener, Main.instance)
                        i++
                    }
                } catch (exception: Exception) {  }
            }
            "§9§lMiyukiSystem  §aForam registrados §7$i §aeventos.".sendToConsole()
        }

        override fun reload() { }

    }

}