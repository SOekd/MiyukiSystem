package miyukisystem.manager.impl

import com.cryptomorin.xseries.ReflectionUtils
import com.cryptomorin.xseries.messages.ActionBar
import com.cryptomorin.xseries.messages.Titles
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import java.lang.reflect.Field
import java.lang.reflect.Method

fun Player.isInventoryEmpty(checkScroll: Boolean = false): Boolean {
    return when {
        inventory.contents.any { it != null && it.type != Material.AIR } -> false
        inventory.armorContents.any { it != null && it.type != Material.AIR } -> false
        checkScroll -> itemOnCursor == null || itemOnCursor.type == Material.AIR
        else -> true
    }
}

fun Player.toSpawn() {
    val location =
        if (LocationManager.has("Spawn") && LocationManager.get("Spawn").location != null)
            LocationManager.get("Spawn").location!!
        else Bukkit.getWorlds()[0].spawnLocation
    teleport(location)
}

fun Player.clearInventory() {
    inventory.boots = null
    inventory.helmet = null
    inventory.chestplate = null
    inventory.leggings = null
    setItemOnCursor(null)
    inventory.clear()
}

private object PingReflection {
    var getHandleMethod: Method? = null
    var pingField: Field? = null
}

fun Player.getServerPing(): Int {
    return try {
        if (PingReflection.getHandleMethod == null) {
            val method = javaClass.getDeclaredMethod("getHandle")
            method.isAccessible = true
            PingReflection.getHandleMethod = method
        }
        val entityPlayer = PingReflection.getHandleMethod!!.invoke(this)
        if (PingReflection.pingField == null) {
            val field = entityPlayer.javaClass.getDeclaredField("ping")
            field.isAccessible = true
            PingReflection.pingField = field
        }
        val ping = PingReflection.pingField!!.getInt(entityPlayer)
        if (ping > 0) ping else 0
    } catch (exception: Exception) {
        0
    }
}

fun Player.clearPotionEffects() {
    activePotionEffects.forEach { removePotionEffect(it.type) }
}

fun Player.title(title: String, subtitle: String) {
    Titles.sendTitle(this, title, subtitle)
}

fun Player.title(title: String, subtitle: String, fadeIn: Int, stay: Int, fadeOut: Int) {
    Titles.sendTitle(this, fadeIn, stay, fadeOut, title, subtitle)
}

fun Player.clearTitle() {
    Titles.clearTitle(this)
}

fun Player.sendActionBar(message: String) {
    ActionBar.sendActionBar(this, message)
}

fun Player.clearActionBar() {
    ActionBar.clearActionBar(this)
}