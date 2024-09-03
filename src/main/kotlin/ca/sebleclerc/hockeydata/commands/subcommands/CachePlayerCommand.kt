package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Constants
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.models.CacheStep
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import kotlin.io.path.Path
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

class CachePlayerCommand(di: DI) : BaseCommand(di = di, name = "player") {
  private val force: Boolean? by option("-f", "--force").flag()
  private val playerId: String by argument()

  override fun run() {
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

    di.cache.cache(listOf(step), force ?: false)
    di.import.importPlayers(listOf(step))
  }

  private fun cacheAllPlayers() {
    Path(Constants.jsonFolder).listDirectoryEntries("*-player.json").forEach {
      val playerId = it.name.split("-")[0].toInt()
      Logger.info("Caching player $playerId from ${it.name}")

      cachePlayerWithId(playerId)
    }

  }
}