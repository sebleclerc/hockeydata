plugins {
    kotlin("jvm") version "2.0.0"
    id("application")
    kotlin("plugin.serialization") version "2.0.20"
}

group = "ca.sebleclerc"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("com.github.ajalt.clikt:clikt:4.4.0")

    implementation("org.mariadb.jdbc:mariadb-java-client:3.4.1")
    implementation("org.slf4j:slf4j-simple:2.0.16")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.2")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass = "AppKt"
}
