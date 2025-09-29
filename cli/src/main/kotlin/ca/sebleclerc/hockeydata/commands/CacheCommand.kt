package ca.sebleclerc.hockeydata.commands

import ca.sebleclerc.hockeydata.DI

class CacheCommand(di: DI) : BaseCommand(di = di, name = "cache") {
  override fun run() = Unit
}