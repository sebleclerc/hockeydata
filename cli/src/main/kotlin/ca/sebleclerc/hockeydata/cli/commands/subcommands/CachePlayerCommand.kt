package ca.sebleclerc.hockeydata.cli.commands.subcommands

import ca.sebleclerc.hockeydata.cli.DI
import ca.sebleclerc.hockeydata.cli.commands.BaseCommand
import ca.sebleclerc.hockeydata.cli.helpers.Logger
import ca.sebleclerc.hockeydata.core.cache.CacheStep
import ca.sebleclerc.hockeydata.core.helpers.Constants
import com.github.ajalt.clikt.parameters.arguments.argument
import kotlin.io.path.Path
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

class CachePlayerCommand(
  di: DI,
) : BaseCommand(
  di = di,
  name = "player",
  help = "Cache a single player or all players (always force)"
) {
  private val playerId: String by argument()

  override fun run() {
    super.run()
    Logger.taskTitle("Caching player with option $playerId")

    if (playerId == "all") {
      cacheAllPlayers()
    } else {
      cachePlayerWithId(playerId.toInt())
    }

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
