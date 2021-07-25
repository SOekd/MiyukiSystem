package miyukisystem.database

import miyukisystem.Main
import miyukisystem.database.impl.UserHikari
import miyukisystem.database.impl.UserMySQL
import miyukisystem.database.impl.UserSQLite
import miyukisystem.manager.impl.ConfigManager
import miyukisystem.model.User
import org.bukkit.Bukkit

class Database {

    companion object {

        lateinit var USERS: DataSourceProvider<User>

        init {
            val mode = ConfigManager.config.config.getString("Database.Mode")!!.toUpperCase()
            try {
                when (mode) {
                    "HIKARI", "HIKARICP", "MYSQL_POOLING" -> {
                        USERS = UserHikari()
                    }
                    "MYSQL" -> {
                        USERS = UserMySQL()
                    }
                    else -> {
                        USERS = UserSQLite()
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                Bukkit.getConsoleSender().sendMessage("§9§lMiyukiSystem  §cNao foi possivel estabelecer uma conexao com o banco de dados.")
                Bukkit.getPluginManager().disablePlugin(Main.instance)
            }


        }


    }

}