plugins {
    kotlin("jvm")
    id("application")
    kotlin("plugin.serialization") version "2.0.20"
}

group = "ca.sebleclerc"
version = "1.0-SNAPSHOT"

dependencies {
  implementation(project(":core"))
  implementation(project(":cache"))
  implementation(project(":database"))

  implementation(project(":shared"))
  implementation(libs.androidx.lifecycle.viewmodelCompose)

  implementation(libs.clikt)

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

distributions {
  main {
    contents {
      duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
  }
}


tasks.withType<Tar>().configureEach {
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
tasks.withType<Zip>().configureEach {
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
