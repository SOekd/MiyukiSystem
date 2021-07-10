package miyukisystem.manager

abstract class CachedDataService<V : Cacheable> : DataService<V> {

    open val cached = mutableMapOf<String, V>()

    override fun has(key: String): Boolean = cached.containsKey(key.toLowerCase())

    override fun get(key: String): V = cached[key.toLowerCase()]!!

    override fun set(value: V) {
        cached[value.getKey()] = value
    }

    override fun set(vararg value: V) {
        value.forEach { cached[it.getKey()] = it }
    }

    override fun remove(key: String) {
        cached.remove(key.toLowerCase())
    }

    override fun getAll(): List<V> = cached.values.toList()


}