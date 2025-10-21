plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "hockeydata"

include("core")

include("database")

include("shared")
include("shared-ui")

include("cli")

include("desktop")