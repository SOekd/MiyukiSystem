plugins {
    kotlin("jvm") version "1.4.32"
    `java-library`
    id("com.github.johnrengelman.shadow") version "6.1.0"
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
    maven("https://repo.aikar.co/content/groups/aikar/")
//    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    flatDir {
        dirs("libs")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
//    compileOnly("me.clip:placeholderapi:2.10.9")
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.mojang:authlib:1.5.21")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    shadowJar {
        minimize {
            exclude(dependency("org.jetbrains.kotlin:.*"))
//            exclude(dependency("org.jetbrains.kotlinx:.*"))
//            exclude(dependency("com.github.shynixn.*:.*"))
        }
        relocate("kotlin", "miyukisystem.kotlin")
        classifier = null
    }
}
