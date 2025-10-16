plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "hockeydata"

include("core")
include("domain")

include("cli")

include("shared-ui")
include("desktop")