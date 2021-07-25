package miyukisystem.database.datasource.impl

import miyukisystem.Main
import miyukisystem.database.datasource.DataSource
import java.io.File
import java.sql.Connection
import java.sql.DriverManager

class H2 : DataSource {

    private val url: String = "jdbc:h2:./${File(Main.instance.dataFolder, "database")}"

    init {
        Class.forName("org.h2.Driver")
    }

    override fun getConnection(): Connection = DriverManager.getConnection(url)

    override fun closeConnection() { }

}