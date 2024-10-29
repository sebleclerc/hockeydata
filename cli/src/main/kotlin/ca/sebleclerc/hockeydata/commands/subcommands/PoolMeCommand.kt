package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Constants
import ca.sebleclerc.hockeydata.helpers.Formatter
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.helpers.LoggerColumn
import ca.sebleclerc.hockeydata.models.CacheStep
import ca.sebleclerc.hockeydata.models.Player

class PoolMeCommand(di: DI) : BaseCommand(di, name = "me") {
  private var totalSalary = 11900000
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

    val columns = listOf(
      LoggerColumn.ID(),
      LoggerColumn.Name(),
      LoggerColumn.Games(),
      LoggerColumn.Goals(),
      LoggerColumn.Assists(),
      LoggerColumn.Points(),
      LoggerColumn.PoolPts(),
      LoggerColumn.Salary()
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
    totalSalary += salary?.salary ?: 0

    Logger.row(
      LoggerColumn.ID(player.id),
      LoggerColumn.Name(player.fullName),
      LoggerColumn.Games(stats?.games ?: 0),
      LoggerColumn.Goals(stats?.goals ?: 0),
      LoggerColumn.Assists(stats?.assists ?: 0),
      LoggerColumn.Points(stats?.points ?: 0),
      LoggerColumn.PoolPts(stats?.poolPoints ?: 0F),
      LoggerColumn.Salary(salary?.avv ?: "N/A")
    )
  }
}