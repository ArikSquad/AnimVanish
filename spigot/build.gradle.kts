import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.gradleup.shadow") version "9.4.2"
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
    implementation(project(":common"))
    implementation("org.bstats:bstats-bukkit:3.1.0")
    compileOnly("com.github.LeonMangler:PremiumVanishAPI:2.7.11-2")
    implementation("net.kyori:adventure-platform-bukkit:4.3.4")
    implementation("net.kyori:adventure-text-minimessage:4.21.0")
    compileOnly("net.citizensnpcs:citizens-main:2.0.33-SNAPSHOT")
    implementation("net.william278:PagineDown:1.1")
    implementation("net.william278:DesertWell:2.0.4")
    implementation("net.kyori:adventure-key:4.21.0")
    compileOnly("dev.jorel:commandapi-bukkit-core:9.7.0")
    compileOnly("me.quantiom:advancedvanish:1.2.6")
    implementation("com.github.stefvanschie.inventoryframework:IF:0.11.3")
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