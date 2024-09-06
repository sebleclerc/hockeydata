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

    if (force == true) {
      Logger.warning("Asking missing salaries...")
      salaries.forEach {
        if (it.value.salary == null && it.value.player != null) {
          askMissingPlayerSalary(
            player = it.value.player!!,
            season = Constants.currentSeason
          )
        }
      }
    }

    Logger.taskEnd()
  }

  private fun askMissingPlayerSalary(player: Player, season: Int) {
    Logger.info("Quel est le salaire de ${player.fullName} (${player.positionCode})(${player.id}) pour $season?")
    val salary = readln()

    val sanitizedSalary = if (salary.isNotEmpty()) {
      salary.replace(
        oldValue = ",",
        newValue = ""
      ).toInt()
    } else {
      0
    }

    Logger.debug("Sanitized salary $sanitizedSalary")

    if (sanitizedSalary > 0) {
      di.database.insertPlayerSalary(
        playerId = player.id,
        season = season,
        salary = sanitizedSalary
      )

      Logger.info("Pour combien d'années encore?")
      val nbYear = readlnOrNull()?.toIntOrNull()

      nbYear?.also {
        if (it > 0) {
          var currentSeason = season

          repeat(it) {
            val nextSeason = calculateNextSeason(currentSeason)
            Logger.debug("Next season: $nextSeason")
            di.database.insertPlayerSalary(
              playerId = player.id,
              season = nextSeason,
              salary = sanitizedSalary
            )

            currentSeason = nextSeason
          }
        }
      }
    }
  }

  private fun calculateNextSeason(current: Int) : Int {
    Logger.debug("Next from which? $current")
    val currentStr = current.toString()
    val lastPart = currentStr.takeLast(4)
    val nextYearLast = lastPart.toInt() + 1

    val nextSeason = "$lastPart$nextYearLast"
    Logger.debug("Next season $nextSeason")
    return nextSeason.toInt()
  }
}

private data class PlayerSeasonSalary(
  var player: Player?,
  var salary: PlayerSalarySeason?
)