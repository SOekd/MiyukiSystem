package miyukisystem.manager

import miyukisystem.manager.impl.ConfigManager
import miyukisystem.manager.impl.MessageManager
import miyukisystem.manager.impl.UserManager

interface ManagerService {

    companion object {

        private val managerList = mutableListOf<ManagerService>()

        init {
            managerList.addAll(
                arrayOf(
                    ConfigManager,
                    MessageManager,
                    UserManager
                )
            )
        }

        fun loadAll() {
            managerList.forEach { it.load() }
        }

        fun reloadAll() {
            managerList.forEach { it.reload() }
        }

    }

    fun load()

    fun reload()

}