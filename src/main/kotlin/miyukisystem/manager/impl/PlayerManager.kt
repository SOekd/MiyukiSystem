package miyukisystem.manager.impl

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

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

fun Player.clearPotionEffects() {
    activePotionEffects.forEach { removePotionEffect(it.type) }
}