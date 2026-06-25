import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.gradleup.shadow") version "9.4.2"
}

dependencies {
    implementation(project(":common"))

    compileOnly("org.spigotmc:spigot-api:26.1.2-R0.1-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:3.2.1")
    compileOnly("com.github.LeonMangler:PremiumVanishAPI:2.7.11-2")
    implementation("net.kyori:adventure-platform-bukkit:4.4.1")
    implementation("net.kyori:adventure-text-minimessage:4.26.1")
    compileOnly("net.citizensnpcs:citizens-main:2.0.33-SNAPSHOT")
    implementation("net.william278:PagineDown:1.1")
    implementation("net.william278:DesertWell:2.0.4")
    implementation("net.kyori:adventure-key:4.26.1")
    compileOnly("me.quantiom:advancedvanish:1.2.6")
    implementation("com.github.stefvanschie.inventoryframework:IF:0.12.0")
    implementation("io.github.revxrsal:lamp.common:4.0.0-rc.17")
    implementation("io.github.revxrsal:lamp.bukkit:4.0.0-rc.17")
}

tasks.named<ShadowJar>("shadowJar") {
    archiveFileName.set("AnimVanish-Spigot-${project.version}.jar")
    destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
    minimize()
    relocate("org.bstats", "eu.mikart.animvanish")
    relocate("com.github.stefvanschie.inventoryframework", "eu.mikart.animvanish.inventoryframework")
    exclude("META-INF/**")
}

tasks.build {
    dependsOn(tasks.named("shadowJar"))
}
