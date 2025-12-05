package ca.sebleclerc.hockeydata.cli.helpers

import ca.sebleclerc.hockeydata.cli.helpers.Logger.completed
import ca.sebleclerc.hockeydata.cli.helpers.Logger.debug
import ca.sebleclerc.hockeydata.cli.helpers.Logger.enabled
import ca.sebleclerc.hockeydata.core.helpers.ProgressRunner

class CLIProgressRunner : ProgressRunner {
  private var isInProgress = false
  private var currentStep: Float = 0F
  private var maxSteps: Float = 0F

  override fun startProgress(
    title: String,
    max: Float,
  ) {
    isInProgress = true
    currentStep = 0F
    maxSteps = max
    debug(title)
    logProgressMessage("[0%]${progressBar(0)}")
  }

  override fun step() {
    if (isInProgress) {
      currentStep += 1
      val percent = (currentStep / maxSteps * 100).toInt()
      val displayPercent = "$percent%"

      logProgressMessage("[$displayPercent]${progressBar(percent)}")
    }
  }

  override fun endProgress() {
    if (isInProgress) {
      val oldEnabled = enabled
      enabled = true

      logProgressMessage("Done!                                                                   ")
      debug("")
      debug("")
      debug("")
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
}
