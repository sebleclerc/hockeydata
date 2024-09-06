package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Constants
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.helpers.LoggerColumn

class SalaryAllCommand(di: DI) : BaseCommand(di, name = "all") {
  override fun run() {
    super.run()

    Logger.taskTitle("Salary Summary")
    Logger.info("Showing a summary of salary informations from all teams in season ${Constants.currentSeason}")
    Logger.info("")
    Logger.info("")

    val customPadding = 15
    Logger.header(
      LoggerColumn.ID(),
      LoggerColumn.Name(),
      LoggerColumn.Custom("P %", customPadding)
    )

    di.database.getAllTeams().forEach { team ->
      val roster = di.database.getRosterForTeam(team.id)
      val nbPlayersInRoster = roster.count()
      var nbPlayerInDB = 0

      roster.forEach { playerId ->
        val salary = di.database.getPlayerSeasonSalary(Constants.currentSeason, playerId)
        if (salary != null) nbPlayerInDB += 1
      }

      val proportion = "$nbPlayerInDB / $nbPlayersInRoster"

      Logger.row(
        LoggerColumn.ID(team.id),
        LoggerColumn.Name(team.name),
        LoggerColumn.Custom(proportion, customPadding)
      )
    }

    Logger.taskEnd()
  }
}