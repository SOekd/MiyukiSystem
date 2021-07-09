package miyukisystem.manager

interface DataService<V : Cacheable> {

    fun has(key: String): Boolean

    fun get(key: String): V

    fun set(value: V)

    fun set(vararg value: V)

    fun remove(key: String)

    fun getAll(): List<V>

}