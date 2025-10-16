plugins {
    kotlin("jvm")
    id("application")
    kotlin("plugin.serialization") version "2.0.20"
}

group = "ca.sebleclerc"
version = "1.0-SNAPSHOT"

dependencies {
  implementation(project(":core"))
  implementation(project(":database"))

  implementation(libs.clikt)

  implementation(libs.okhttp)
  implementation(libs.kotlinx.serialization)

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
