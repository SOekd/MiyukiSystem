plugins {
    kotlin("jvm") version "1.5.21"
    `java-library`
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.github.slimjar") version "1.2.1"
}

group = "miyukisystem"
version = "1.0-SNAPSHOT"


repositories {
    google()
    jcenter()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://libraries.minecraft.net/")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.codemc.org/repository/maven-public/")
    flatDir {
        dirs("libs", "implementation")
    }
}

dependencies {
    slim(kotlin("stdlib-jdk8"))
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(fileTree(mapOf("dir" to "implementation", "include" to listOf("*.jar"))))
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.mojang:authlib:1.5.21")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
    slim("com.zaxxer:HikariCP:3.4.5")
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("io.github.bananapuncher714:nbteditor:7.17.0")
    slim("com.github.cryptomorin:XSeries:8.2.0")
    slim("com.h2database:h2:1.4.200")
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    slimJar {
        relocate("com.zaxxer", "miyukisystem.database")
        relocate("org.slf4j", "miyukisystem.util.slf4j")
        relocate("kotlin", "miyukisystem.kotlin")
        relocate("com.cryptomorin.xseries", "miyukisystem.util.xseries")
        relocate("org.h2", "miyukisystem.database.h2")
        relocate("org.intellij.lang.annotations", "miyukisystem.util.annotations")
        relocate("org.jetbrains.annotations", "miyukisystem.util.annotations")
        relocate("com.google.protobuf", "miyukisystem.util.google.protobuf")
    }
    shadowJar {
        relocate("io.github.bananapuncher714.nbteditor", "miyukisystem.util")
        relocate("io.github.slimjar", "miyukisystem.util.slimjar")
        relocate("net.wesjd.anvilgui", "miyukisystem.util.anvil")
        classifier = null
    }
}