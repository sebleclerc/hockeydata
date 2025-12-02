package ca.sebleclerc.hockeydata

import ca.sebleclerc.hockeydata.cache.CacheService
import ca.sebleclerc.hockeydata.database.DatabaseService
import ca.sebleclerc.hockeydata.cache.ImportService
import ca.sebleclerc.hockeydata.core.helpers.Progress
import ca.sebleclerc.hockeydata.helpers.CLIProgressRunner

object DI {
  val database = DatabaseService()
  val import = ImportService(database)
  val progress = Progress(CLIProgressRunner())
  val cache = CacheService(
    import = import,
    progress = progress
  )
}
