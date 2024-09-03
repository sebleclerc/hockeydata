package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.models.CacheStep
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

class CachePlayerCommand(di: DI) : BaseCommand(di = di, name = "player") {
  private val force: Boolean? by option("-f", "--force").flag()
  private val playerId: Int by argument().int()

  override fun run() {
    Logger.taskTitle("Cache Player $playerId")

    val step = CacheStep.Player(playerId)

    di.cache.cache(listOf(step), force ?: false)
    di.import.importPlayers(listOf(step))

    Logger.taskEnd()
  }
}