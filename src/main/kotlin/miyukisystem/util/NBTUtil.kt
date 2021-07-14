package miyukisystem.util

import io.github.bananapuncher714.nbteditor.NBTEditor
import org.bukkit.inventory.ItemStack

fun ItemStack.containsNBT(tag: String): Boolean = NBTEditor.contains(this, tag)

fun ItemStack.getNBT(tag: String): String? = NBTEditor.getString(this, tag)

fun ItemStack.setNBT(tag: String, value: String): ItemStack = NBTEditor.set(this.clone(), value, tag)