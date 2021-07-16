package miyukisystem.manager.impl


import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import miyukisystem.Main
import miyukisystem.manager.ManagerService
import miyukisystem.model.User
import miyukisystem.util.JsonFile
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.chat.ComponentSerializer
import net.md_5.bungee.chat.TextComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.File
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern

class MessageManager {

    companion object : ManagerService {

        private val messages = CacheBuilder
            .newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(object : CacheLoader<String, Array<String>>() {

                override fun load(key: String): Array<String> {
                    val config = ConfigManager.messages.config
                    return when {
                        config.isString(key) ->
                            return arrayOf(config.getString(key)!!.translateColorCodes())
                        config.isList(key) -> config.getStringList(key).map { it.translateColorCodes() }.toTypedArray()
                        else -> arrayOf("§9§lMiyukiSystem  §cNao foi possivel encontrar a mensagem com o PATH: §7$key§c.")
                    }
                }

            })

        private val jsonMessages = CacheBuilder
            .newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build(object : CacheLoader<String, String>() {

                override fun load(key: String): String {
                    val file = JsonFile(Main.instance.dataFolder, "json${File.separator}$key.json")
                    if (file.exists()) {
                        val json: String = file.readerToString("[]")
                        return if (json == "[]") "§9§lMiyukiSystem  §cNao foi possivel encontrar a mensagem com o PATH: §7$key§c."
                        else
                            json.translateColorCodes()
                    }
                    return "§9§lMiyukiSystem  §cNao foi possivel encontrar a mensagem com o PATH: §7$key§c."
                }

            })


        val hexColorPattern: Pattern = Pattern.compile("&#(\\w{5}[0-9a-f])")
        val supportHexColor = try {
            net.md_5.bungee.api.ChatColor::class.java.getDeclaredMethod("of", String::class.java)
            true
        } catch (e: NoSuchMethodException) {
            false
        }

        val gson: Gson =
            GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(BaseComponent::class.java, ComponentSerializer())
                .registerTypeAdapter(TextComponent::class.java, TextComponentSerializer())
                .create()

        private fun getPathList(): Set<String> {
            val pathList = HashSet<String>()
            // geral
            pathList.add("NoPermission")
            pathList.add("NoConsole")
            pathList.add("Offline")
            // alert
            pathList.add("IncorrectAlertCommand")
            pathList.add("AlertSent")
            // feed
            pathList.add("IncorrectFeedCommand")
            pathList.add("FullFood")
            pathList.add("FullFoodPlayer")
            pathList.add("Feeded")
            pathList.add("FeededPlayer")
            // flashlight
            pathList.add("FlashLightActivated")
            pathList.add("FlashLightDisabled")
            // gamemode
            // hat
            pathList.add("HatPlaced")
            pathList.add("InvalidHat")
            // heal
            pathList.add("IncorrectHealCommand")
            pathList.add("FullLife")
            pathList.add("FullLifePlayer")
            pathList.add("Healed")
            pathList.add("HealedPlayer")
            // teleport
            pathList.add("IncorrectTeleportCommand")
            pathList.add("TeleportedToYourself")
            pathList.add("TeleportedSuccess")
            pathList.add("TeleportedSuccessWithCoords")
            pathList.add("PlayerTeleportedSuccessWithCoords")
            pathList.add("InvalidCoords")
            // clearchat
            pathList.add("ClearedChat")
            // fly
            pathList.add("FlyActivated")
            pathList.add("FlyDisabled")
            // vanish
            pathList.add("VanishLeaved")
            pathList.add("VanishJoined")
            // ping
            pathList.add("Ping")
            pathList.add("PingTarget")
            // craft
            // trash
            pathList.add("OpeningTrash")
            // enderchest
            // invsee
            pathList.add("IncorrectInvSeeCommand")
            pathList.add("OpeningInventory")
            // TpAll
            pathList.add("IncorrectTpAllCommand")
            pathList.add("AllPlayersTeleportedToYou")
            pathList.add("ForcedTpAll")
            // Tpa
            pathList.add("IncorrectTpaCommand")
            pathList.add("TpaYourself")
            pathList.add("TpaOther")
            pathList.add("TpaPlayer")
            pathList.add("NoHaveRequests")
            pathList.add("ExpiredTpa")
            // Tpaccept
            pathList.add("AcceptTpaPlayer")
            pathList.add("AcceptTpaOther")
            pathList.add("TpAcceptYourself")
            // Tpdeny
            pathList.add("DenyTpaOther")
            pathList.add("DenyTpaPlayer")
            pathList.add("TpDenyYourself")
            // Spawn
            pathList.add("IncorrectSpawnCommand")
            pathList.add("IncorrectSpawnAdminCommand")
            pathList.add("TeleportedSpawnSuccess")
            pathList.add("SentToSpawn")
            pathList.add("ForcedTeleportSpawn")
            // SetSpawn
            pathList.add("SettedSpawnSuccess")
            // Clear
            pathList.add("IncorrectClearCommand")
            pathList.add("AlreadyTargetCleared")
            pathList.add("TargetCleared")
            pathList.add("Cleared")
            pathList.add("AlreadyCleared")
            // title
            pathList.add("IncorrectTitleCommand")
            pathList.add("TitleSent")
            // anvil
            pathList.add("IncorrectAnvilCommand")
            // speed
            pathList.add("IncorrectSpeedCommand")
            // god
            pathList.add("DisabledGodMode")
            pathList.add("EnabledGodMode")
            // repair
            pathList.add("NoReparable")
            pathList.add("MaxDurability")
            pathList.add("BlockedRepairItem")
            pathList.add("SuccessRepair")
            // gamemode
            pathList.add("GameModeNotFound")
            pathList.add("GameModeTargetChanged")
            pathList.add("GameModeChanged")
            pathList.add("AlreadyGameMode");
            // tphere
            pathList.add("IncorrectTpHereCommand")
            pathList.add("TpHereSuccess")
            pathList.add("GameModeNull")
            // actionbar
            pathList.add("IncorrectActionBarCommand")
            pathList.add("ActionBarSent")

            return pathList
        }

        override fun reload() {
            ConfigManager.messages.saveDefaultConfig()
            ConfigManager.messages.reloadConfig()
            messages.cleanUp()
            load()
        }

        override fun load() {
            for (path in getPathList()) {
                val config = ConfigManager.messages.config
                when {
                    config.isString(path) -> {
                        val message = arrayOf(config.getString(path)!!.translateColorCodes())
                        if (message[0] == "json") {
                            val file = JsonFile(Main.instance.dataFolder, "json${File.separator}$path.json")
                            if (file.exists()) {
                                val json: String = file.readerToString("[]")
                                if (json == "[]")
                                    messages.put(
                                        path,
                                        arrayOf("§9§lMiyukiSystem  §cNão foi possível ler o arquivo §7json/$path.json§c.")
                                    )
                                else {
                                    jsonMessages.put(path, json.translateColorCodes())
                                    messages.put(path, message)
                                }
                            } else
                                messages.put(
                                    path,
                                    arrayOf("§9§lMiyukiSystem  §cNão foi encontrado o arquivo §7json/$path.json§c.")
                                )
                        } else
                            messages.put(path, message)
                    }
                    config.isList(path) ->
                        messages.put(path, config.getStringList(path).map { it.translateColorCodes() }.toTypedArray())
                    else -> {
                        Bukkit.getConsoleSender()
                            .sendMessage("§9§lMiyukiSystem  §cNao foi possivel encontrar a mensagem com o PATH: §7$path§c.")
                    }
                }
            }
            Bukkit.getConsoleSender()
                .sendMessage("§9§lMiyukiSystem  §aTodas as mensagens foram carregadas com sucesso.")
        }

        fun getMessage(path: String): Array<String> {
            return messages.get(path)
        }

        fun getMessage(path: String, user: User): Array<String> {
            return messages.get(path).map { formatMessage(it, user) }.toTypedArray()
        }

        fun getJson(path: String): String {
            return jsonMessages.get(path)
        }

        fun getJson(path: String, placeholder: MutableMap<String, String>): String {
            return formatMessage(jsonMessages.get(path), placeholder)
        }

        fun getJson(path: String, user: User): String {
            return formatMessage(jsonMessages.get(path), user)
        }

        fun getJson(path: String, user: User, placeholder: MutableMap<String, String>): String {
            return formatMessage(formatMessage(jsonMessages.get(path), user), placeholder)
        }

        fun sendMessage(sender: CommandSender, path: String) {
            sender.sendCustomMessage(path)
        }

        fun formatMessage(message: String, user: User): String {
            return message
                .replace("{player}", user.playerName)
                .translateColorCodes()
        }

        fun formatMessage(message: String, placeholder: MutableMap<String, String>): String {
            var msg = message
            placeholder.forEach { (key, value) -> msg = msg.replace(key, value) }
            return msg.translateColorCodes()
        }

    }
}

fun CommandSender.sendCustomMessage(path: String) {
    val message = MessageManager.getMessage(path)
    when {
        this !is Player && message[0] == "json" ->
            sendMessage("§9§lMiyukiSystem  §cA mensagem com path §7json/$path.json §cnao pode ser usada em json.")
        message[0] == "json" -> {
            val textComponent = TextComponent("")
            val json = MessageManager.getJson(path)
            if (json.startsWith("§9§lMiyukiSystem"))
                textComponent.addExtra(json)
            else {
                MessageManager.gson.fromJson(
                    MessageManager.getJson(path),
                    Array<BaseComponent>::class.java
                ).forEach { textComponent.addExtra(it) }
            }
            (this as Player).spigot().sendMessage(textComponent)
        }
        else -> sendMessage(message)
    }
}


fun CommandSender.sendCustomMessage(path: String, editor: MutableMap<String, String>.() -> Unit) {
    val message = MessageManager.getMessage(path)
    when {
        this !is Player && message[0] == "json" ->
            sendMessage("§9§lMiyukiSystem  §cA mensagem com path §7json/$path.json §cnao pode ser usada em json.")
        message[0] == "json" -> {
            val textComponent = TextComponent("")
            val json = MessageManager.getJson(path)
            if (json.startsWith("§9§lMiyukiSystem"))
                textComponent.addExtra(json)
            else {
                MessageManager.gson.fromJson(
                    MessageManager.getJson(path, mutableMapOf<String, String>().apply(editor)),
                    Array<BaseComponent>::class.java
                ).forEach { textComponent.addExtra(it) }
            }
            (this as Player).spigot().sendMessage(textComponent)
        }
        else -> sendMessage(message.map { it.formatPlaceholder(editor) }.toTypedArray())
    }
}

fun CommandSender.sendCustomMessage(path: String, placeHolders: MutableMap<String, String>) {
    val message = MessageManager.getMessage(path)
    when {
        this !is Player && message[0] == "json" ->
            sendMessage("§9§lMiyukiSystem  §cA mensagem com path §7json/$path.json §cnao pode ser usada em json.")
        message[0] == "json" -> {
            val textComponent = TextComponent("")
            val json = MessageManager.getJson(path)
            if (json.startsWith("§9§lMiyukiSystem"))
                textComponent.addExtra(json)
            else {
                MessageManager.gson.fromJson(
                    MessageManager.getJson(path, placeHolders),
                    Array<BaseComponent>::class.java
                ).forEach { textComponent.addExtra(it) }
            }
            (this as Player).spigot().sendMessage(textComponent)
        }
        else -> sendMessage(message.map { it.formatPlaceholder(placeHolders) }.toTypedArray())
    }
}

fun CommandSender.sendCustomMessage(path: String, user: User) {
    val message = MessageManager.getMessage(path)
    when {
        this !is Player && message[0] == "json" ->
            sendMessage("§9§lMiyukiSystem  §cA mensagem com path §7json/$path.json §cnao pode ser usada em json.")
        message[0] == "json" -> {
            val textComponent = TextComponent("")
            val json = MessageManager.getJson(path)
            if (json.startsWith("§9§lMiyukiSystem"))
                textComponent.addExtra(json)
            else {
                MessageManager.gson.fromJson(
                    MessageManager.getJson(path, user),
                    Array<BaseComponent>::class.java
                ).forEach { textComponent.addExtra(it) }
            }
            (this as Player).spigot().sendMessage(textComponent)
        }
        else -> sendMessage(message)
    }
}

fun CommandSender.sendCustomMessage(path: String, user: User, editor: MutableMap<String, String>.() -> Unit) {
    val message = MessageManager.getMessage(path)
    when {
        this !is Player && message[0] == "json" ->
            sendMessage("§9§lMiyukiSystem  §cA mensagem com path §7json/$path.json §cnao pode ser usada em json.")
        message[0] == "json" -> {
            val textComponent = TextComponent("")
            val json = MessageManager.getJson(path)
            if (json.startsWith("§9§lMiyukiSystem"))
                textComponent.addExtra(json)
            else {
                MessageManager.gson.fromJson(
                    MessageManager.getJson(path, user, mutableMapOf<String, String>().apply(editor)),
                    Array<BaseComponent>::class.java
                ).forEach { textComponent.addExtra(it) }
            }
            (this as Player).spigot().sendMessage(textComponent)
        }
        else -> sendMessage(message.map { it.formatPlaceholder(editor) }.toTypedArray())
    }
}

fun CommandSender.sendCustomMessage(path: String, user: User, placeholders: MutableMap<String, String>) {
    val message = MessageManager.getMessage(path)
    when {
        this !is Player && message[0] == "json" ->
            sendMessage("§9§lMiyukiSystem  §cA mensagem com path §7json/$path.json §cnao pode ser usada em json.")
        message[0] == "json" -> {
            val textComponent = TextComponent("")
            val json = MessageManager.getJson(path)
            if (json.startsWith("§9§lMiyukiSystem"))
                textComponent.addExtra(json)
            else {
                MessageManager.gson.fromJson(
                    MessageManager.getJson(path, user, placeholders),
                    Array<BaseComponent>::class.java
                ).forEach { textComponent.addExtra(it) }
            }
            (this as Player).spigot().sendMessage(textComponent)
        }
        else -> sendMessage(message.map { it.formatPlaceholder(placeholders) }.toTypedArray())
    }
}


fun String.formatPlaceholder(editor: MutableMap<String, String>.() -> Unit): String {
    var msg = this
    mutableMapOf<String, String>().apply(editor).forEach { (key, value) -> msg = msg.replace(key, value) }
    return msg.translateColorCodes()
}

fun String.formatPlaceholder(placeholders: MutableMap<String, String>): String {
    var msg = this
    placeholders.forEach { (key, value) -> msg = msg.replace(key, value) }
    return msg.translateColorCodes()
}

fun String.translateColorCodes(): String {
    var msg = this
    if (MessageManager.supportHexColor) {
        val matcher: Matcher = MessageManager.hexColorPattern.matcher(msg)
        val buffer = StringBuffer()
        while (matcher.find()) {
            try {
                matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of("#" + matcher.group(1)).toString())
            } catch (ignored: Exception) {
            }
        }
        msg = matcher.appendTail(buffer).toString()
    }
    return ChatColor.translateAlternateColorCodes('&', msg)
}

fun String?.stripColors(): String? {
    var msg = this ?: return null
    if (MessageManager.supportHexColor) {
        val matcher = MessageManager.hexColorPattern.matcher(msg)
        val buffer = StringBuffer()
        while (matcher.find()) {
            matcher.appendReplacement(buffer, "")
        }
        msg = matcher.appendTail(buffer).toString()
    }
    return ChatColor.stripColor(msg)
}

