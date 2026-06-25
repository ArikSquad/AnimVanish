import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.gradleup.shadow") version "9.4.2"
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.1.2.build.+")
    implementation(project(":common"))

    implementation("org.bstats:bstats-bukkit:3.2.1") {
        exclude("org.spigotmc", "spigot-api")
    }
    implementation("io.github.revxrsal:lamp.common:4.0.0-rc.17")
    implementation("io.github.revxrsal:lamp.bukkit:4.0.0-rc.17") {
        exclude("org.spigotmc", "spigot-api")
    }
    implementation("net.william278:PagineDown:1.1") {
        exclude("org.spigotmc", "spigot-api")
    }
    implementation("net.william278:DesertWell:2.0.4") {
        exclude("org.spigotmc", "spigot-api")
    }
    implementation("xyz.xenondevs.invui:invui:2.2.0")

    compileOnly("net.citizensnpcs:citizens-main:2.0.33-SNAPSHOT") {
        exclude("org.spigotmc", "spigot-api")
    }
    compileOnly("me.quantiom:advancedvanish:1.2.6") {
        exclude("org.spigotmc", "spigot-api")
    }
    compileOnly("com.github.LeonMangler:PremiumVanishAPI:2.7.11-2") {
        exclude("org.spigotmc", "spigot-api")
    }
}

tasks.named<ShadowJar>("shadowJar") {
    archiveFileName.set("AnimVanish-Paper-${project.version}.jar")
    destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
    minimize()
    relocate("org.bstats", "eu.mikart.animvanish")
    relocate("xyz.xenondevs.invui", "eu.mikart.animvanish.invui")
    relocate("xyz.xenondevs.inventoryaccess", "eu.mikart.animvanish.inventoryaccess")
    exclude("META-INF/**")
}

tasks.build {
    dependsOn(tasks.named("shadowJar"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}