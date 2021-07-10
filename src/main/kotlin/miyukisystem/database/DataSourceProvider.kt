package miyukisystem.database

import java.util.concurrent.CompletableFuture

interface DataSourceProvider<T> {

    fun createTable()

    fun has(key: String) : CompletableFuture<Boolean>

    fun insertOrUpdate(value: T)

    fun insertOrUpdateAll(value: List<T>)

    fun get(key: String) : CompletableFuture<T>

    fun getAll() : CompletableFuture<List<T>>

    fun truncate()

    fun close()

}