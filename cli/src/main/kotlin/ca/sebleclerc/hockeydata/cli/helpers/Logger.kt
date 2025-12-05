package ca.sebleclerc.hockeydata.cli.helpers

import com.andreapivetta.kolor.green
import com.andreapivetta.kolor.yellow

object Logger {
  var enabled = true

  fun taskTitle(title: String) {
    debug("")
    debug("###########################################################".green())
    debug("########     $title     ########".green())
    debug("###########################################################".green())
    debug("")
    debug("")
  }

  fun taskSubtitle(subtitle: String) {
    debug(subtitle.green())
    debug("##############################".green())
    debug("")
  }

  fun taskEnd() {
    debug("")
    debug("")
    debug("###########################################################".green())
    debug("")
  }

  fun completed() {
    debug("")
    debug("      #")
    debug("  #  #")
    debug("   ##")
    debug("")
  }

  fun header(vararg headers: LoggerColumn) {
    var header = ""

    headers.forEach {
      header += it.title.padStart(it.padding, ' ')
    }

    header = header.yellow()

    debug(header)
  }

  fun row(vararg rows: LoggerColumn) {
    var rowText = ""

    rows.forEach {
      rowText += it.title.padStart(it.padding, ' ')
    }

    debug(rowText)
  }

  fun debug(text: String) {
    logMessage("🟩", text)
  }

  fun warning(text: String) {
    logMessage("⚠️ ", text)
  }

  fun error(text: String) {
    logMessage("❗", text)
  }

  private fun logMessage(
    prefix: String,
    text: String,
  ) {
    if (enabled) println("$prefix [HD] $text")
  }
}
