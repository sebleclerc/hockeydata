plugins {
    kotlin("jvm") version "2.0.0"
    id("application")
}

group = "ca.sebleclerc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:4.4.0")

    implementation("org.mariadb.jdbc:mariadb-java-client:3.4.1")
    implementation("org.slf4j:slf4j-simple:2.0.16")

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
