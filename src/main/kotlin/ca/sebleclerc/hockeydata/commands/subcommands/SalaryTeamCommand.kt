package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Constants
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.helpers.LoggerColumn
import ca.sebleclerc.hockeydata.models.Player
import ca.sebleclerc.hockeydata.models.PlayerSalarySeason
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int

class SalaryTeamCommand(di: DI) : BaseCommand(di, name = "team") {
  private val teamId by argument().int()

  override fun run() {
    super.run()

    Logger.taskTitle("Missing Team $teamId salary for ${Constants.currentSeason}")

    val team = di.database.getTeamForId(teamId) ?: return
    val roster = di.database.getRosterForTeam(teamId)

    Logger.info("Salaries for team ${team.name}")
    val salaries = mutableMapOf<String, PlayerSeasonSalary>()

    roster.forEach { playerId ->
      val player = di.database.getPlayerForId(playerId)
      val salary = di.database.getPlayerSeasonSalary(
        season = Constants.currentSeason,
        playerId = playerId
      )

      salaries[playerId.toString()] = PlayerSeasonSalary(player, salary)
    }

    val padding = 15
    Logger.header(
      LoggerColumn.ID(),
      LoggerColumn.Name(),
      LoggerColumn.Custom("Salary", padding)
    )

    salaries.forEach {
      Logger.row(
        LoggerColumn.ID(it.value.player?.id ?: 0),
        LoggerColumn.Name(it.value.player?.fullName ?: ""),
        LoggerColumn.Custom(it.value.salary?.avv ?: "", padding)
      )
    }

    Logger.taskEnd()
  }
}

private data class PlayerSeasonSalary(
  var player: Player?,
  var salary: PlayerSalarySeason?
)