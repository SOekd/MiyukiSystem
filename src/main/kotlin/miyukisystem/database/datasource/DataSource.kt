package miyukisystem.database.datasource

import java.sql.Connection

interface DataSource {

    fun getConnection(): Connection

    fun closeConnection()


}