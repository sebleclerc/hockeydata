package ca.sebleclerc.hockeydata.cli.commands

import ca.sebleclerc.hockeydata.cli.DI
import ca.sebleclerc.hockeydata.cli.helpers.Logger
import ca.sebleclerc.hockeydata.core.cache.CacheStep
import ca.sebleclerc.hockeydata.core.helpers.Constants
import kotlin.io.path.Path
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

class CacheCommand(
  di: DI,
) : BaseCommand(
  di = di,
  name = "cache",
  help = "Cache all players (always force)"
) {
  override fun run() {
    super.run()
    Logger.taskTitle("Caching all players")

    cacheAllPlayers()

    Logger.taskEnd()
  }

  private fun cachePlayerWithId(playerId: Int) {
    val step = CacheStep.Player(playerId)

    di.cache.cache(listOf(step), true)
    di.import.importPlayers(listOf(step))
  }

  private fun cacheAllPlayers() {
    Logger.enabled = false

    val players = Path(Constants.JSON_FOLDER).listDirectoryEntries("*-player.json")

    di.progress.startProgress("Players", players.count().toFloat())
    players.forEach {
      val playerId = it.name.split("-")[0].toInt()
      Logger.debug("Caching player $playerId from ${it.name}")

      cachePlayerWithId(playerId)
      di.progress.step()
    }
  }
}