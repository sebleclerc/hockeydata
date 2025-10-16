plugins {
  kotlin("multiplatform")
}

kotlin {
  jvm()

  sourceSets {
    commonMain.dependencies {
      implementation(project(":core"))
    }

    jvmMain.dependencies {
      implementation(libs.maria.db)
      implementation(libs.slf4j)
    }
  }
}
