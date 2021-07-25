package miyukisystem.manager.impl

import miyukisystem.Main
import miyukisystem.manager.CachedDataService
import miyukisystem.manager.ManagerService
import miyukisystem.model.Warp
import miyukisystem.util.Config
import miyukisystem.util.toCustomString
import miyukisystem.util.toLocation
import java.io.File

class WarpsManager {

    companion object : CachedDataService<Warp>(), ManagerService {

        fun saveFile(warp: Warp) {
            val warpConfig = Config("warps${File.separator}${warp.name}.yml")
            warpConfig.saveDefaultConfig()
            val config = warpConfig.config
            config.set("Name", warp.name)
            config.set("Permission", warp.permission)
            config.set("Location", warp.location.toCustomString())
            config.set("Title.Title", warp.title?.first)
            config.set("Title.Subtitle", warp.title?.second)
            config.set("ActionBar", warp.actionBar)
            if (warp.message.size == 1)
                config.set("Message", warp.message[0])
            else
                config.set("Message", warp.message.toList())
            warpConfig.saveConfig()
        }

        override fun load() {
            val warps = File(Main.instance.dataFolder, "warps").listFiles()
            if (warps.isNullOrEmpty()) return
            for (warpName in warps.map { it.nameWithoutExtension }) {
                val warpConfig = Config("warps${File.separator}$warpName.yml")
                warpConfig.saveDefaultConfig()
                val config = warpConfig.config
                set(
                    Warp(
                        config.getString("Name")!!,
                        config.getString("Location").toLocation()!!,
                        config.getString("Permission")!!,
                        if (config.contains("Title"))
                            Pair(config.getString("Title.Title")!!, config.getString("Title.Subtitle")!!)
                        else null,
                        if (config.contains("ActionBar")) config.getString("ActionBar")!! else null,
                        if (config.isString("Message"))
                            arrayOf(config.getString("Message")!!)
                        else
                            config.getStringList("Message").toTypedArray()
                    )
                )


            }
        }

        override fun reload() {
            clear()
            load()
        }

    }

}
