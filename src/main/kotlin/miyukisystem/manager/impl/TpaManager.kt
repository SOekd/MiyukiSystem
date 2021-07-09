package miyukisystem.manager.impl

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import miyukisystem.manager.Cacheable
import miyukisystem.manager.DataService
import miyukisystem.manager.ManagerService
import java.util.concurrent.TimeUnit

class TpaManager {

    companion object : DataService<TPA>, ManagerService {

        private lateinit var cached: Cache<String, TPA>

        override fun has(key: String): Boolean = cached.asMap().containsKey(key)

        override fun get(key: String): TPA = cached.asMap()[key]!!

        override fun set(value: TPA) {
            cached.put(value.getKey(), value)
        }

        override fun set(vararg value: TPA) {
            value.forEach { cached.put(it.getKey(), it) }
        }

        override fun getAll(): List<TPA> = cached.asMap().values.toList()

        override fun load() {
            val expireTime = ConfigManager.config.config.getLong("TpaExpire")
            cached = CacheBuilder.newBuilder()
                .expireAfterWrite(expireTime, TimeUnit.SECONDS)
                .build()
        }

        override fun reload() {
            load()
        }

    }

}

data class TPA(val from: String, val to: String) : Cacheable {

    override fun getKey(): String = from

}