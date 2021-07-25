package miyukisystem.database.tables

import java.util.concurrent.CompletableFuture

interface Table<T> {

    fun createTable()

    fun has(key: String): CompletableFuture<Boolean>

    fun insert(value: T)

    fun update(value: T)

    fun get(key: String): CompletableFuture<T>

    fun getAll(): CompletableFuture<List<T>>

}