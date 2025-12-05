plugins {
  kotlin("multiplatform")

  alias(libs.plugins.kotlinx.serialization)
}

kotlin {
  jvm()

  sourceSets {
    jvmMain.dependencies {
      implementation(project(":core"))
      implementation(project(":database"))

      implementation(libs.androidx.lifecycle.viewmodelCompose)
    }
  }
}
