package miyukisystem.database.datasource.impl

import miyukisystem.Main
import miyukisystem.database.datasource.DataSource
import java.io.File
import java.sql.Connection
import java.sql.DriverManager

class SQLite : DataSource {

    private val url: String = "jdbc:sqlite:${File(Main.instance.dataFolder, "database.sqlite.db")}"

    init {
        Class.forName("org.sqlite.JDBC")
    }

    override fun getConnection(): Connection = DriverManager.getConnection(url)

    override fun closeConnection() { }


}