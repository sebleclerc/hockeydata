package ca.sebleclerc.hockeydata.helpers

import com.andreapivetta.kolor.green
import com.andreapivetta.kolor.yellow

object Logger {
  var enabled = true

  fun taskTitle(title: String) {
    info("")
    info("###########################################################".green())
    info("########     $title     ########".green())
    info("###########################################################".green())
    info("")
    info("")
  }

  fun taskEnd() {
    info("")
    info("")
    info("###########################################################".green())
    info("")
  }

  fun completed() {
    info("")
    info("      #")
    info("  #  #")
    info("   ##")
    info("")
  }

  fun header(vararg headers: LoggerColumn) {
    var header = ""

    headers.forEach {
      header += it.title.padStart(it.padding, ' ')
    }

    header = header.yellow()

    info(header)
  }

  fun row(vararg rows: LoggerColumn) {
    var rowText = ""

    rows.forEach {
      rowText += it.title.padStart(it.padding, ' ')
    }

    info(rowText)
  }

  fun debug(text: String) {
    logMessage("🟩", text)
  }

  fun info(text: String) {
    logMessage("🔷", text)
  }

  fun warning(text: String) {
    logMessage("⚠️ ", text)
  }

  fun error(text: String) {
    logMessage("❗", text)
  }

  private fun logMessage(prefix: String, text: String) {
    if (enabled) println("$prefix [HD] $text")
  }
}