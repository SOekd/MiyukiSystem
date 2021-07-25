package miyukisystem.database

import miyukisystem.database.datasource.DataSource
import miyukisystem.database.datasource.impl.H2
import miyukisystem.database.datasource.impl.MySQL
import miyukisystem.database.datasource.impl.SQLite
import miyukisystem.database.tables.Table
import miyukisystem.database.tables.impl.Users
import miyukisystem.manager.impl.ConfigManager
import miyukisystem.model.User
import miyukisystem.sendToConsole


class Database {

    companion object {

        lateinit var dataSource: DataSource

        lateinit var USERS: Table<User>

        init {

            "§9§lMiyukiSystem  §cConectando ao banco de dados.".sendToConsole()
            try {
                dataSource = when (ConfigManager.config.config.getString("Database.Mode")!!.uppercase()) {
                    "MYSQL", "MARIADB" -> MySQL()
                    "SQLITE" -> SQLite()
                    else -> H2()
                }

                dataSource.getConnection()

                USERS = Users()

                "§9§lMiyukiSystem  §aA conexao com o banco de dados foi estabelecida com sucesso.".sendToConsole()
            } catch (exception: Exception) {
                exception.printStackTrace()
                "§9§lMiyukiSystem  §cOcorreu um erro ao tentar estabelecer uma conexao com o banco de dados.".sendToConsole()
            }

        }




    }

}