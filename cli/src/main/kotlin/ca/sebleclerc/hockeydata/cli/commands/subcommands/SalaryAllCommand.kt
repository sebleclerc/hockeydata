package ca.sebleclerc.hockeydata.cli.commands.subcommands

import ca.sebleclerc.hockeydata.cli.DI
import ca.sebleclerc.hockeydata.cli.commands.BaseCommand
import ca.sebleclerc.hockeydata.cli.helpers.Logger
import ca.sebleclerc.hockeydata.cli.helpers.LoggerColumn
import ca.sebleclerc.hockeydata.core.helpers.Constants

class SalaryAllCommand(
  di: DI,
) : BaseCommand(di, name = "all") {
  override fun run() {
    super.run()

    Logger.taskTitle("Salary Summary")
    Logger.debug("Showing a summary of salary informations from all teams in season ${Constants.currentSeason}")
    Logger.debug("")
    Logger.debug("")

    val customPadding = 15
    Logger.header(
      LoggerColumn.ID(),
      LoggerColumn.Name(),
      LoggerColumn.Custom("P %", customPadding),
    )

    var totalPlayers = 0
    var totalInDbPlayers = 0

    di.database.getAllTeams().forEach { team ->
      val roster = di.database.getRosterForTeam(team.id)
      val nbPlayersInRoster = roster.count()
      totalPlayers += roster.count()

      var nbPlayerInDB = 0

      roster.forEach { playerId ->
        val salary = di.database.getPlayerSeasonSalary(Constants.currentSeason, playerId)
        if (salary != null) nbPlayerInDB += 1
      }

      totalInDbPlayers += nbPlayerInDB
      val proportion = "$nbPlayerInDB / $nbPlayersInRoster"

      Logger.row(
        LoggerColumn.ID(team.id),
        LoggerColumn.Name(team.name),
        LoggerColumn.Custom(proportion, customPadding),
      )
    }

    Logger.debug("")
    Logger.debug("Total players proportion: $totalInDbPlayers / $totalPlayers")

    Logger.taskEnd()
  }
}
