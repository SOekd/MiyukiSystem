package miyukisystem.util

import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.net.URI
import java.nio.file.Files


class JsonFile : File {

    private var parent: JsonFile? = null

    companion object {
        private const val seriaalVersionUID = 1L
    }

    constructor(pathname: String) : super(pathname) {
        if (getParent() != null) {
            parent = JsonFile(getParent())
        }
    }

    constructor(parent: String, child: String) : super(parent, child) {
        if (getParent() != null) {
            this.parent = JsonFile(getParent())
        }
    }

    constructor(parent: File, child: String) : super(parent, child) {
        if (getParent() != null) {
            this.parent = JsonFile(getParent())
        }
    }

    constructor(uri: URI) : super(uri) {
        if (getParent() != null) {
            parent = JsonFile(getParent())
        }
    }

    override fun getParentFile(): JsonFile {
        return parent!!
    }

    fun getFileInDir(name: String, create: Boolean, dir: Boolean): JsonFile? {
        return if (this.isDirectory) {
            val file = JsonFile(this, name)
            if (create && !file.exists()) {
                if (dir) {
                    file.mkdirs()
                } else {
                    if (file.parentFile.exists()) {
                        file.parentFile.mkdirs()
                    }
                    try {
                        file.createNewFile()
                    } catch (var6: IOException) {
                        var6.printStackTrace()
                    }
                }
            }
            file
        } else {
            null
        }
    }

    fun readerToString(defaultString: String): String {
        return try {
            String(Files.readAllBytes(toPath()))
        } catch (var3: IOException) {
            defaultString
        }
    }

    fun whiter(result: String) {
        try {
            if (!exists()) {
                createNewFile()
            }
            val fw = FileWriter(this)
            fw.write(result)
            fw.close()
        } catch (var3: IOException) {
        }
    }

}