package miyukisystem.database.impl

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import miyukisystem.Main
import miyukisystem.database.DataSourceProvider
import miyukisystem.database.adapter.LocationAdapter
import miyukisystem.manager.impl.ConfigManager
import miyukisystem.model.User
import org.apache.commons.lang3.BooleanUtils
import org.bukkit.Location
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.CompletableFuture

class UserSQLite : DataSourceProvider<User> {

    private val url: String = "jdbc:sqlite:${File(Main.instance.dataFolder, "database${File.separator}users.db")}"
    private val gson: Gson

    init {
        Class.forName("org.sqlite.JDBC")

        gson = GsonBuilder()
            .disableHtmlEscaping()
            .enableComplexMapKeySerialization()
            .registerTypeAdapter(Location::class.java, LocationAdapter())
            .create()
    }

    @Synchronized
    override fun createTable() {
        DriverManager.getConnection(url).use { connection ->
            connection.prepareStatement(
                """
                CREATE TABLE `miyukisystem_users`
                (`player` VARCHAR(20) NOT NULL,
                `name` VARCHAR (20) NOT NULL,
                `tpa` TINYINT(1) NOT NULL,
                `homes` TEXT NOT NULL,
                `kits` TEXT NOT NULL)
            """.trimIndent()
            )
                .use { it.executeUpdate() }
        }
    }

    override fun has(key: String): CompletableFuture<Boolean> = CompletableFuture.supplyAsync {
        DriverManager.getConnection(url).use { connection ->
            connection
                .prepareStatement("SELECT `player` from `miyukisystem_users` where `player` = ?")
                .use { stm ->
                    stm.setString(1, key.toLowerCase())
                    stm.executeQuery().use { rs ->
                        rs.next()
                    }
                }
        }
    }

    override fun insertOrUpdate(value: User) {
        CompletableFuture.runAsync {
            DriverManager.getConnection(url).use { connection ->
                connection
                    .prepareStatement("INSERT OR REPLACE INTO  `miyukisystem_users` (`player`, `name`, `tpa`, `homes`, `kits`) VALUES (?, ?, ?, ?, ?, ?) ")
                    .use {
                        it.setString(1, value.playerName.toLowerCase())
                        it.setString(2, value.playerName)
                        it.setInt(3, BooleanUtils.toInteger(value.tpaEnabled))
                        it.setString(4, gson.toJson(value.homes))
                        it.setString(5, gson.toJson(value.kits))
                        it.executeUpdate()
                    }
            }
        }
    }

    override fun insertOrUpdateAll(value: List<User>) {
        CompletableFuture.runAsync {
            value.forEach { insertOrUpdate(it) }
        }

    }

    override fun get(key: String): CompletableFuture<User> = CompletableFuture.supplyAsync {
        DriverManager.getConnection(url).use { connection ->
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
    }

    override fun getAll(): CompletableFuture<List<User>> = CompletableFuture.supplyAsync {
        val users = mutableListOf<User>()
        DriverManager.getConnection(url).use { connection ->
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
        }
        users.toList()
    }

    @Synchronized
    override fun truncate() {
        DriverManager.getConnection(url).use { connection ->
            connection
                .prepareStatement("DELETE FROM `miyukisystem_users`")
                .use { stm ->
                    stm.executeUpdate()
                }
        }
    }

    @Synchronized
    override fun close() {
        DriverManager.getConnection(url).use { connection ->
            if (!connection.isClosed)
                connection.close()
        }
    }

}