plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.11"
}

group = "io.github.vlouboos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven(url = "https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("org.github.paperspigot:paperspigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("io.github.floatingpointmc:sanctionmanager-minecraft:1.0-SNAPSHOT")
    implementation("fr.minuskube.inv:smart-invs:1.2.7")
    compileOnly("org.projectlombok:lombok:1.18.48")
    annotationProcessor("org.projectlombok:lombok:1.18.48")
}

tasks.shadowJar {
    relocate("fr.minuskube.inv", "io.github.vlouboos.lib.inv")
}