package ca.sebleclerc.hockeydata.commands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.helpers.Logger
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option

open class BaseCommand(val di: DI, name: String) : CliktCommand(name = name) {
  private val silent: Boolean? by option("-s", "--silent").flag()

  override fun run() {
    if (silent == true) {
      Logger.enabled = false
    }
  }
}