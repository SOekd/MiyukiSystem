package miyukisystem.database.impl

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import miyukisystem.database.DataSourceProvider
import miyukisystem.database.adapter.LocationAdapter
import miyukisystem.manager.impl.ConfigManager
import miyukisystem.model.User
import org.apache.commons.lang3.BooleanUtils
import org.bukkit.Bukkit
import org.bukkit.Location
import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.CompletableFuture

class UserMySQL : DataSourceProvider<User> {

    private val connection: Connection
    private val gson: Gson


    init {
        val config = ConfigManager.config.config
        val section = config.getConfigurationSection("Database")!!
        val url =
            "jdbc:mysql://${section.getString("Host")}:${section.getString("Port")}/${section.getString("Database")}?autoReconnect=true"
        Class.forName(section.getString("MySQLDriver"))
        connection = DriverManager.getConnection(url, section.getString("Username"), section.getString("Password"))

        gson = GsonBuilder()
            .disableHtmlEscaping()
            .enableComplexMapKeySerialization()
            .registerTypeAdapter(Location::class.java, LocationAdapter())
            .create()
    }

    @Synchronized
    override fun createTable() {
        connection
            .prepareStatement("""
                CREATE TABLE `miyukisystem_users`
                (`player` VARCHAR(20) NOT NULL,
                `name` VARCHAR (20) NOT NULL,
                `tpa` TINYINT(1) NOT NULL,
                `homes` TEXT NOT NULL,
                `kits` TEXT NOT NULL)
            """.trimIndent())
            .use { it.executeUpdate() }
    }

    override fun has(key: String): CompletableFuture<Boolean> = CompletableFuture.supplyAsync {
        connection
            .prepareStatement("SELECT `player` from `miyukisystem_users` where `player` = ?")
            .use { stm ->
                stm.setString(1, key.toLowerCase())
                stm.executeQuery().use { rs ->
                    rs.next()
                }
            }
    }

    override fun insertOrUpdate(value: User) {
        CompletableFuture.runAsync {
            connection
                .prepareStatement("""
                INSERT INTO `miyukisystem_users` (`player`, `name`, `tpa`, `homes`, `kits`) 
                VALUES (?, ?, ?, ?, ?, ?) 
                ON DUPLICATE KEY UPDATE 
                `player` = VALUES(`player`), 
                `name` = VALUES(`name`), 
                `tpa` = VALUES(`tpa`), 
                `homes` = VALUES(`homes`), 
                `kits` = VALUES(`kits`)
            """.trimIndent()).use {
                    it.setString(1, value.playerName.toLowerCase())
                    it.setString(2, value.playerName)
                    it.setInt(3, BooleanUtils.toInteger(value.tpaEnabled))
                    it.setString(4, gson.toJson(value.homes))
                    it.setString(5, gson.toJson(value.kits))
                    it.executeUpdate()
                }
        }
    }

    override fun insertOrUpdateAll(value: List<User>) {
        CompletableFuture.runAsync {
            value.forEach { insertOrUpdate(it) }
        }

    }

    override fun get(key: String): CompletableFuture<User> = CompletableFuture.supplyAsync {
        connection
            .prepareStatement("SELECT * FROM `miyukisystem_users` WHERE `player` = ?")
            .use { stm ->
                stm.setString(1, key.toLowerCase())
                stm.executeQuery().use { rs ->
                    if (rs.next()) {
                        var kits: Map<String, Long> = HashMap()
                        kits = gson.fromJson(rs.getString("kits"), kits.javaClass)
                            .filter { it.value > System.currentTimeMillis() }
                        var homes: Map<String, Location> = HashMap()
                        homes = gson.fromJson(rs.getString("homes"), homes.javaClass)
                        User(
                            rs.getString("name"),
                            rs.getInt("tpa") != 0,
                            homes.toMutableMap(),
                            kits.toMutableMap(),
                        )
                    } else null
                }
            }
    }

    override fun getAll(): CompletableFuture<List<User>> = CompletableFuture.supplyAsync {
        val users = mutableListOf<User>()
        connection
            .prepareStatement("SELECT * FROM `miyukisystem_users`")
            .use { stm ->
                stm.executeQuery().use { rs ->
                    while (rs.next()) {
                        var kits: Map<String, Long> = HashMap()
                        kits = gson.fromJson(rs.getString("kits"), kits.javaClass)
                            .filter { it.value > System.currentTimeMillis() }
                        var homes: Map<String, Location> = HashMap()
                        homes = gson.fromJson(rs.getString("homes"), homes.javaClass)
                        User(
                            rs.getString("name"),
                            rs.getInt("tpa") != 0,
                            homes.toMutableMap(),
                            kits.toMutableMap(),
                        )
                    }
                }
            }
        users.toList()
    }

    @Synchronized
    override fun truncate() {
        connection
            .prepareStatement("TRUNCATE TABLE `miyukisystem_users`")
            .use { stm ->
                stm.executeUpdate()
            }
    }

    @Synchronized
    override fun close() {
        if(!connection.isClosed)
            connection.close()
    }

}