package ca.sebleclerc.hockeydata.commands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.helpers.Constants
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.helpers.LoggerColumn
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int

class PlayerCommand(di: DI): BaseCommand(di, name = "player") {
  val playerId by argument().int()

  override fun run() {
    super.run()

    Logger.taskTitle("Player $playerId")

    val player = di.database.getPlayerForId(playerId) ?: return

    Logger.info("Name: ${player.fullName}")
    Logger.info("Birthday: ${player.birthDate.display}")
    Logger.info("Primary number: ${player.primaryNumber}")
    Logger.info("")

    Logger.header(
      LoggerColumn.Season(),
      LoggerColumn.League(),
      LoggerColumn.TeamName(),
      LoggerColumn.Games(),
      LoggerColumn.Goals(),
      LoggerColumn.Assists(),
      LoggerColumn.Points()
    )

    val seasons = di.database.getAllSeasonsForSkaterId(playerId)

    seasons.forEach { season ->
      Logger.row(
        LoggerColumn.Season(season.season.intValue),
        LoggerColumn.League(season.league),
        LoggerColumn.TeamName(season.team.take(Constants.paddingTeamName - 2)),
        LoggerColumn.Games(season.games),
        LoggerColumn.Goals(season.goals),
        LoggerColumn.Assists(season.assists),
        LoggerColumn.Points(season.points)
      )
    }

    Logger.info("")
    Logger.taskEnd()
  }
}