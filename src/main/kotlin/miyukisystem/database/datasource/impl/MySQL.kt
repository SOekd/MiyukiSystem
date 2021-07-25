package miyukisystem.database.datasource.impl

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import miyukisystem.database.datasource.DataSource
import miyukisystem.manager.impl.ConfigManager
import java.lang.Exception
import java.sql.Connection

class MySQL : DataSource {

    private val connection: HikariDataSource

    init {

        val config = ConfigManager.config.config
        var section = config.getConfigurationSection("Database")!!

        val mode = section.getString("Mode")!!.lowercase()

        val hikariConfig = HikariConfig().apply {
            jdbcUrl =
                "jdbc:$mode://${section.getString("Host")}:${section.getString("Port")}/${section.getString("Database")}"
            username = section.getString("Username")
            password = section.getString("Password")

            driverClassName = if (mode == "mysql") {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver")
                    "com.mysql.cj.jdbc.Driver"
                } catch (exception: Exception) {
                    "com.mysql.jdbc.Driver"
                }
            } else
                "org.mariadb.jdbc.Driver"

            section = section.getConfigurationSection("MySQLConfiguration")!!

            poolName = section.getString("PoolName")
            minimumIdle = section.getInt("MiniumIdle")
            maximumPoolSize = section.getInt("MaximumPoolSize")
            connectionTimeout = section.getLong("ConnectionTimeout")
            addDataSourceProperty("cachePrepStmts", section.getBoolean("CachePrepStmts"))
            addDataSourceProperty("prepStmtCacheSize", section.getInt("PrepStmtCacheSize"))
            addDataSourceProperty("prepStmtCacheSqlLimit", section.getInt("PrepStmtCacheSqlLimit"))
            addDataSourceProperty("useServerPrepStmts", section.getBoolean("UseServerPrepStmts"))
            addDataSourceProperty("useLocalSessionState", section.getBoolean("UseLocalSessionState"))
            addDataSourceProperty("rewriteBatchedStatements", section.getBoolean("RewriteBatchedStatements"))
            addDataSourceProperty("cacheResultSetMetadata", section.getBoolean("CacheResultSetMetadata"))
            addDataSourceProperty("cacheServerConfiguration", section.getBoolean("CacheServerConfiguration"))
            addDataSourceProperty("elideSetAutoCommits", section.getBoolean("ElideSetAutoCommits"))
            addDataSourceProperty("maintainTimeStats", section.getBoolean("MaintainTimeStats"))
        }

        connection = HikariDataSource(hikariConfig)

    }

    override fun getConnection(): Connection = connection.connection

    override fun closeConnection() {
        if (!connection.isClosed) {
            connection.close()
        }
    }

}