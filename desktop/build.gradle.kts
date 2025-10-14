import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("multiplatform")

  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.compose.multiplatform)
}

kotlin {
  jvm()

  sourceSets {
    jvmMain.dependencies {
      implementation(compose.desktop.currentOs)
      implementation(compose.ui)
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.components.resources)

      implementation(project(":shared-ui"))
    }
  }
}

compose.desktop {
  application {
    mainClass = "MainKt"
  }
}


//compose.desktop {
//  application {
//    mainClass = "org.example.project.MainKt"
//
//    nativeDistributions {
//      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
//      packageName = "org.example.project"
//      packageVersion = "1.0.0"
//    }
//  }
//}
