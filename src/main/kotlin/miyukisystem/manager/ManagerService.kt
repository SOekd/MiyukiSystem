package miyukisystem.manager

import com.google.common.reflect.ClassPath
import miyukisystem.Main
import miyukisystem.sendToConsole

interface ManagerService {

    companion object : ManagerService {


        override fun load() {
            val classPath = ClassPath.from(Main.instance.javaClass.classLoader)
            var i = 0
            classPath.getTopLevelClassesRecursive("miyukisystem.manager.impl").forEach { classInfo ->
                try {
                    val manager = Class.forName(classInfo.name).getDeclaredField("Companion").get(null)
                    if (manager is ManagerService) {
                        manager.load()
                        i++
                    }
                } catch (exception: Exception) { }
            }
            "§9§lMiyukiSystem  §aForam carregados §7$i §amodulos.".sendToConsole()
        }

        override fun reload() { }


    }

    fun load()

    fun reload()

}