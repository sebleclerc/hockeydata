package ca.sebleclerc.hockeydata.shared.ui

import ca.sebleclerc.hockeydata.cache.CacheService
import ca.sebleclerc.hockeydata.cache.ImportService
import ca.sebleclerc.hockeydata.core.helpers.Progress
import ca.sebleclerc.hockeydata.database.DatabaseService

object DI {
  val database = DatabaseService()

  val import = ImportService(dbService = database)
  val progress = Progress(runner = ComposeProgressRunner())
  val cache = CacheService(
    import = import,
    progress = progress,
  )
}
