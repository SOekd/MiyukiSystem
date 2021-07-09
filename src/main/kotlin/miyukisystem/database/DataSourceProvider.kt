package miyukisystem.database

import miyukisystem.model.User
import java.sql.Connection
import java.util.concurrent.CompletableFuture

interface DataSourceProvider<T> {

    fun createTable()

    fun has(key: String) : CompletableFuture<Boolean>

    fun insertOrUpdate(value: T)

    fun insertOrUpdateAll(value: List<T>)

    fun get(key: String) : CompletableFuture<User>

    fun getAll() : List<T>

    fun truncate()

    fun close()

}