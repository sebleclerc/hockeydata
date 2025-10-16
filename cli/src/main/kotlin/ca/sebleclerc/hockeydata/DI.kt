package ca.sebleclerc.hockeydata

import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.services.CacheService
import ca.sebleclerc.hockeydata.services.ImportService

object DI {
  val database = DatabaseService()
  val import = ImportService(database)
  val cache = CacheService(import)
}
