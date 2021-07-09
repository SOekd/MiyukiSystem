package miyukisystem.manager

abstract class CachedDataService<V : Cacheable> : DataService<V> {

    open val cached = hashMapOf<String, V>()

    override fun has(key: String): Boolean = cached.containsKey(key)

    override fun get(key: String): V = cached[key]!!

    override fun set(value: V) {
        cached[value.getKey()] = value
    }

    override fun set(vararg value: V) {
        value.forEach { cached[it.getKey()] = it }
    }

    override fun getAll(): List<V> = cached.values.toList()


}