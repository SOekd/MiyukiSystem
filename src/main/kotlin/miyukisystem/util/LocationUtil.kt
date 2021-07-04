package miyukisystem.util

import org.bukkit.Bukkit
import org.bukkit.Location

fun Location?.toCustomString(): String? {
    if (this == null) return null
    if (world == null) return null
    return "${world?.name};${x};${y};${z};${yaw};${pitch}"
}

fun String?.toLocation(): Location? {
    if (this == null) return null
    val locSplit = split(";")
    if (locSplit.size != 6) return null
    return Location(
        Bukkit.getWorld(locSplit[0]),
        locSplit[1].toDouble(),
        locSplit[2].toDouble(),
        locSplit[3].toDouble(),
        locSplit[4].toFloat(),
        locSplit[5].toFloat()
    )
}