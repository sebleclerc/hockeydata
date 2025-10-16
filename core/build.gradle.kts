plugins {
  kotlin("multiplatform")
}

kotlin {
  jvm()

  sourceSets {
    commonMain.dependencies {
      implementation(libs.kotlinx.serialization)
    }
  }
}
