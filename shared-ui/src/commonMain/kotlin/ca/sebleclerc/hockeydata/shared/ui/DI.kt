package ca.sebleclerc.hockeydata.shared.ui

import ca.sebleclerc.hockeydata.database.DatabaseService

object DI {
  val database = DatabaseService()
}