package miyukisystem.database.tables.impl

import com.google.gson.GsonBuilder
import miyukisystem.database.Database
import miyukisystem.database.adapter.LocationAdapter
import miyukisystem.database.tables.Table
import miyukisystem.model.User
import org.bukkit.Location
import java.util.concurrent.CompletableFuture

class Users : Table<User> {

    private val gson = GsonBuilder()
        .disableHtmlEscaping()
        .enableComplexMapKeySerialization()
        .registerTypeAdapter(Location::class.java, LocationAdapter())
        .create()

    override fun createTable() {
        Database.dataSource.getConnection().use { conn ->
            conn.prepareStatement("""
                CREATE TABLE `miyukisystem_users` (
                	`id` INT NOT NULL AUTO_INCREMENT,
                	`name` VARCHAR(40) NOT NULL,
                	`playername` VARCHAR(40) NOT NULL,
                	`tpaEnabled` TINYINT(1) NOT NULL,
                	`homes` TEXT NOT NULL,
                	`kits` TEXT NOT NULL,
                	PRIMARY KEY (`id`)
                )
            """.trimIndent()).use { it.executeUpdate() }
        }
    }

    override fun has(key: String): CompletableFuture<Boolean> = CompletableFuture.supplyAsync {
        Database.dataSource.getConnection().use { conn ->
            conn.prepareStatement("SELECT * FROM `miyukisystem_users` WHERE `name` = ?")
                .use { stm ->
                    stm.setString(1, key.lowercase())
                    stm.executeQuery().use { it.next() }
                }
        }
    }

    override fun insert(value: User) {
        CompletableFuture.runAsync {
            Database.dataSource.getConnection().use { conn ->
                conn.prepareStatement("INSERT INTO `miyukisystem_users` (`name`,`playername`,`tpaEnabled`, `homes`,`kits`) VALUES (?,?,?,?,?)")
                    .use {
                        it.setString(1, value.playerName.lowercase())
                        it.setString(2, value.playerName)
                        it.setInt(3, if (value.tpaEnabled) 1 else 0)
                        it.setString(4, gson.toJson(value.homes))
                        it.setString(5, gson.toJson(value.kits))
                        it.executeUpdate()
                    }
            }
        }
    }

    override fun update(value: User) {
        CompletableFuture.runAsync {
            Database.dataSource.getConnection().use { conn ->
                conn.prepareStatement("""
                    UPDATE `miyukisystem_users` SET
                    `tpaEnabled` = ?,
                    `homes` = ?,
                    `kits` = ?,
                    WHERE `name` = ?
                """.trimIndent())
                    .use {
                        it.setInt(1, if (value.tpaEnabled) 1 else 0)
                        it.setString(2, gson.toJson(value.homes))
                        it.setString(3, gson.toJson(value.kits))
                        it.setString(4, value.playerName.lowercase())
                        it.executeUpdate()
                    }
            }
        }
    }

    override fun get(key: String): CompletableFuture<User> = CompletableFuture.supplyAsync {
        Database.dataSource.getConnection().use { conn ->
            conn.prepareStatement("SELECT * FROM `miyukisystem_users` WHERE `name` = ?")
                .use { stm ->
                    stm.setString(1, key.lowercase())
                    stm.executeQuery().use {
                        if (it.next()) {
                            val homesMap = mutableMapOf<String, Location>().javaClass
                            val kitsMap = mutableMapOf<String, Long>().javaClass

                            User(
                                it.getString("playername"),
                                it.getInt("tpaEnabled") == 1,
                                gson.fromJson(it.getString("homes"), homesMap),
                                gson.fromJson(it.getString("kits"), kitsMap)
                            )
                        }
                        else
                            null
                    }
                }
        }
    }


    override fun getAll(): CompletableFuture<List<User>> = CompletableFuture.supplyAsync {
        val users = mutableListOf<User>()

        val homesMap = mutableMapOf<String, Location>().javaClass
        val kitsMap = mutableMapOf<String, Long>().javaClass

        Database.dataSource.getConnection().use { conn ->
            conn.prepareStatement("SELECT * FROM `miyukisystem_users`")
                .use { stm ->
                    stm.executeQuery().use {
                        while (it.next()) {
                            users.add(
                                User(
                                    it.getString("playername"),
                                    it.getInt("tpaEnabled") == 1,
                                    gson.fromJson(it.getString("homes"), homesMap),
                                    gson.fromJson(it.getString("kits"), kitsMap)
                                )
                            )
                        }
                    }
                }
        }
        users
    }


}