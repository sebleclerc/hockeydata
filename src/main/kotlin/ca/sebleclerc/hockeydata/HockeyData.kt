package ca.sebleclerc.hockeydata

import ca.sebleclerc.hockeydata.services.DatabaseService
import com.github.ajalt.clikt.core.CliktCommand

class HockeyData : CliktCommand() {
  override fun run() {
    echo("HockeyData")
    DatabaseService().some()
  }
}
