package miyukisystem.database.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import miyukisystem.util.toCustomString
import miyukisystem.util.toLocation
import org.bukkit.Bukkit
import org.bukkit.Location

class LocationAdapter : TypeAdapter<Location>() {

    override fun write(out: JsonWriter, value: Location) {
        out.beginObject()
        out.name("loc").value(value.toCustomString())
        out.endObject()
    }

    override fun read(`in`: JsonReader): Location {
        `in`.beginObject()
        var loc: Location? = null
        while (`in`.hasNext()) {
            if (`in`.nextName() == "loc") {
                loc = `in`.nextString().toLocation()
            }
        }
        `in`.endObject()
        return loc!!
    }

}
