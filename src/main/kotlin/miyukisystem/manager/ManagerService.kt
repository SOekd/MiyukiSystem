package miyukisystem.manager

import com.google.common.reflect.ClassPath
import miyukisystem.Main
import miyukisystem.manager.impl.ConfigManager
import miyukisystem.manager.impl.MessageManager
import miyukisystem.manager.impl.UserManager
import org.bukkit.Bukkit
import org.bukkit.event.Listener

interface ManagerService {

    companion object : ManagerService {


        // Provavelmente irá quebrar o plugin, mas vale a pena tentar
        override fun load() {
            val classPath = ClassPath.from(Main.instance.javaClass.classLoader)
            var i = 0
            classPath.getTopLevelClassesRecursive("miyukisystem.manager.impl").forEach { classInfo ->
                try {
                    val manager = Class.forName(classInfo.name).getDeclaredField("INSTANCE").get(null)
                    if (manager is ManagerService) {
                        manager.load()
                        i++
                    }
                } catch (exception: Exception) {  }
            }
            Bukkit.getConsoleSender().sendMessage("§9§lMiyukiSystem  §aForam carregados §7$i §amodulos.")
        }

        override fun reload() { }


    }

    fun load()

    fun reload()

}