package ca.sebleclerc.hockeydata

import ca.sebleclerc.hockeydata.services.DatabaseService

object DI {
  val database = DatabaseService()
}
