plugins {
  kotlin("multiplatform")
  kotlin("plugin.serialization") version "2.0.20"
}

kotlin {
  jvm()

  sourceSets.jvmMain.dependencies {
    implementation(project(":core"))
    implementation(project(":database"))

    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization)
  }
}
