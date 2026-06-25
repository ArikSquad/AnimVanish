import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.gradleup.shadow") version "9.4.2"
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation(project(":spigot"))
    implementation(project(":common"))
}

tasks.named<ShadowJar>("shadowJar") {
    archiveFileName.set("AnimVanish-Paper-${project.version}.jar")
    destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
    minimize()
    relocate("org.bstats", "eu.mikart.animvanish")
    relocate("com.github.stefvanschie.inventoryframework", "eu.mikart.animvanish.inventoryframework")
    exclude("META-INF/**")
}

tasks.build {
    dependsOn(tasks.named("shadowJar"))
}