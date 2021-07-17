package miyukisystem.manager.impl

import miyukisystem.manager.CachedDataService
import miyukisystem.manager.ManagerService
import miyukisystem.model.Warp

class WarpsManager {

    companion object : CachedDataService<Warp>(), ManagerService {

        override fun load() {

        }

        override fun reload() {
        }

    }

}
