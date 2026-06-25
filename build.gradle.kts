plugins {
    java
    id("com.gradleup.shadow") version "9.4.2" apply false
}

group = "eu.mikart"
version = "1.2.0"

tasks.jar {
    enabled = false
}

allprojects {
    group = "eu.mikart.animvanish"
    version = rootProject.version
}

subprojects {
    apply(plugin = "java-library")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    tasks.processResources {
        filteringCharset = "UTF-8"
        filesMatching(listOf("plugin.yml", "paper-plugin.yml")) {
            expand("project" to mapOf("version" to project.version.toString()))
        }
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.38")
        annotationProcessor("org.projectlombok:lombok:1.18.38")
        implementation("org.jetbrains:annotations:26.0.2")
    }
}
