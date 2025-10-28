package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.core.domain.Player
import ca.sebleclerc.hockeydata.core.domain.PoolMePlayer
import ca.sebleclerc.hockeydata.core.helpers.Constants
import ca.sebleclerc.hockeydata.core.helpers.Formatter
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.helpers.LoggerColumn
import ca.sebleclerc.hockeydata.models.CacheStep

class PoolMeCommand(
  di: DI,
) : BaseCommand(di, name = "me") {
  private var totalSalary = 0

  override fun run() {
    super.run()

    Logger.taskTitle("Pool ME ${Constants.currentSeason}")

    Logger.enabled = false
    val players = di.database.getPoolMePlayers()
    val steps = players.map { CacheStep.Player(it.id) }
    di.cache.cache(steps, force = true)
    di.import.importPlayers(steps)
    Logger.enabled = true

    val forwards = players.filter { it.positionCode != "D" }
    val defenses = players.filter { it.positionCode == "D" }

    val columns =
      listOf(
        LoggerColumn.ID(),
        LoggerColumn.Name(),
        LoggerColumn.Games(),
        LoggerColumn.Goals(),
        LoggerColumn.Assists(),
        LoggerColumn.Points(),
        LoggerColumn.PoolPts(),
        LoggerColumn.Salary(),
      )

    Logger.taskSubtitle("Forwards")
    Logger.header(*columns.toTypedArray())
    forwards.forEach { displayPlayer(it) }

    Logger.info("")
    Logger.info("")

    Logger.taskSubtitle("Defense")
    Logger.header(*columns.toTypedArray())
    defenses.forEach { displayPlayer(it) }

    Logger.info("")
    Logger.info("")
    Logger.info("Salaire total:      ${Formatter.intToSalary(totalSalary)}")

    Logger.taskEnd()
  }

  private fun displayPlayer(player: Player) {
    val stats = di.database.getSingleSeasonForSkateId(player.id, Constants.currentSeason)
    val salary = di.database.getPlayerSeasonSalary(Constants.currentSeason, player.id)

    val mePlayer = PoolMePlayer(
      player = player,
      stats = stats,
      salary = salary
    )

    totalSalary += salary?.salary ?: 0

    Logger.row(
      LoggerColumn.ID(player.id),
      LoggerColumn.Name(player.fullName),
      LoggerColumn.Games(mePlayer.games),
      LoggerColumn.Goals(mePlayer.goals),
      LoggerColumn.Assists(mePlayer.assists),
      LoggerColumn.Points(mePlayer.points),
      LoggerColumn.PoolPts(mePlayer.poolPoints),
      LoggerColumn.Salary(mePlayer.avv),
    )
  }
}
