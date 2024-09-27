package ca.sebleclerc.hockeydata.helpers

import com.andreapivetta.kolor.green
import com.andreapivetta.kolor.yellow
import kotlin.math.max

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

  private var isInProgress = false
  private var currentStep: Float = 0F
  private var maxSteps: Float = 0F

  fun startProgress(title: String, max: Float) {
    isInProgress = true
    currentStep = 0F
    maxSteps = max
    debug(title)
    logProgressMessage("[0%]${progressBar(0)}")
  }

  fun step() {
    if (isInProgress) {
      currentStep += 1
      val percent = (currentStep / maxSteps * 100).toInt()
      val displayPercent = "$percent%"

      logProgressMessage("[$displayPercent]${progressBar(percent)}")
    }
  }

  fun endProgress() {
    if (isInProgress) {
      val oldEnabled = enabled
      enabled = true

      logProgressMessage("Done!                                                                   ")
      info("")
      info("")
      info("")
      completed()

      enabled = oldEnabled
    }

    isInProgress = false
  }

  private fun logProgressMessage(text: String) {
    print("\uD83D\uDFE9 [HD]     $text\r")
  }

  private fun progressBar(percent: Int): String {
    val maxBars = 25
    val completed = ((percent.toFloat() / 100) * maxBars).toInt()
    val empty = maxBars - completed

    return "  [ ${"|".repeat(completed)}${".".repeat(empty)} ]"
  }

  private fun logMessage(prefix: String, text: String) {
    if (enabled) println("$prefix [HD] $text")
  }
}