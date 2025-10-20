package ca.sebleclerc.hockeydata.core.helpers

object Logger {
  fun debug(text: String) {
    logMessage(
      text = text,
      level = LoggerLevel.DEBUG
    )
  }

  fun warning(text: String) {
    logMessage(
      text = text,
      level = LoggerLevel.WARNING
    )
  }

  fun error(text: String) {
    logMessage(
      text = text,
      level = LoggerLevel.ERROR
    )
  }

  private fun logMessage(text: String, level: LoggerLevel) {
    val message = "${level.prefix} [HD] $text"
    platformLogMessage(message)
  }
}

expect fun platformLogMessage(text: String)
