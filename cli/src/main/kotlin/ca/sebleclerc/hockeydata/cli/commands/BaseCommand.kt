package ca.sebleclerc.hockeydata.cli.commands

import ca.sebleclerc.hockeydata.cli.DI
import ca.sebleclerc.hockeydata.cli.helpers.Logger
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option

open class BaseCommand(
  val di: DI,
  name: String,
) : CliktCommand(name = name) {
  val force: Boolean? by option("-f", "--force").flag()
  private val silent: Boolean? by option("-s", "--silent").flag()

  override fun run() {
    if (silent == true) {
      Logger.enabled = false
    }
  }
}
