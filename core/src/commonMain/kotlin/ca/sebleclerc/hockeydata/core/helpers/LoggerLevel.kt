package ca.sebleclerc.hockeydata.core.helpers

enum class LoggerLevel(
  val prefix: String,
) {
  DEBUG("🟩"),
  WARNING("⚠️ "),
  ERROR("❗"),
}
