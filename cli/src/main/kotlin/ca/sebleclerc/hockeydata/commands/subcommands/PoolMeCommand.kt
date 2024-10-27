package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Constants
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.helpers.LoggerColumn
import ca.sebleclerc.hockeydata.models.CacheStep
import ca.sebleclerc.hockeydata.models.Player

class PoolMeCommand(di: DI) : BaseCommand(di, name = "me") {
  override fun run() {
    super.run()

    Logger.taskTitle("Pool ME ${Constants.currentSeason}")

    val players = di.database.getPoolMePlayers()
    val steps = players.map { CacheStep.Player(it.id) }
    di.cache.cache(steps, force = true)
    di.import.importPlayers(steps)

    val forwards = players.filter { it.positionCode != "D" }
    val defenses = players.filter { it.positionCode == "D" }

    val columns = listOf(
      LoggerColumn.ID(),
      LoggerColumn.Name(),
      LoggerColumn.Games(),
      LoggerColumn.Goals(),
      LoggerColumn.Assists(),
      LoggerColumn.Points(),
      LoggerColumn.PoolPts()
    )

    Logger.info("Forwards")
    Logger.header(*columns.toTypedArray())
    forwards.forEach { displayPlayer(it) }

    Logger.info("")
    Logger.info("")

    Logger.info("Defense")
    Logger.header(*columns.toTypedArray())
    defenses.forEach { displayPlayer(it) }


    Logger.taskEnd()
  }

  private fun displayPlayer(player: Player) {
    val stats = di.database.getSingleSeasonForSkateId(player.id, Constants.currentSeason)

    Logger.row(
      LoggerColumn.ID(player.id),
      LoggerColumn.Name(player.fullName),
      LoggerColumn.Games(stats?.games ?: 0),
      LoggerColumn.Goals(stats?.goals ?: 0),
      LoggerColumn.Assists(stats?.assists ?: 0),
      LoggerColumn.Points(stats?.points ?: 0),
      LoggerColumn.PoolPts(stats?.poolPoints ?: 0F)
    )
  }
}