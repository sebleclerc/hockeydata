plugins {
  kotlin("multiplatform")

  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.kotlinx.serialization)
}

kotlin {
  jvm()

  sourceSets {
    commonMain.dependencies {
      implementation(project(":core"))
      implementation(project(":database"))

      implementation(libs.compose.navigation)

      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)

      implementation(libs.androidx.lifecycle.viewmodelCompose)
      implementation(libs.androidx.lifecycle.runtimeCompose)
    }
  }
}