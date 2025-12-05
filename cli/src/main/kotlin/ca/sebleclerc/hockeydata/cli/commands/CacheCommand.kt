package ca.sebleclerc.hockeydata.cli.commands

import ca.sebleclerc.hockeydata.cli.DI

class CacheCommand(
  di: DI,
) : BaseCommand(di = di, name = "cache") {
  override fun run() = Unit
}
