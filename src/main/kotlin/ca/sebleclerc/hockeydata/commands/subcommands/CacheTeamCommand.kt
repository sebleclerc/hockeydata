package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.models.CacheStep
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int

class CacheTeamCommand(di: DI) : BaseCommand(di = di, name = "team") {
  private val teamId: Int by argument().int()

  override fun run() {
    Logger.taskTitle("Cache Team $teamId")

    val team = di.database.getTeamForId(teamId)

    if (team != null) {
      di.cache.cache(listOf(
        CacheStep.CacheTeamRoster(team)
      ),
        true
      )

      di.import.importRosters()

      val playerIds = di.database.getRosterForTeam(teamId)
      val playerSteps = playerIds.map { CacheStep.Player(it) }
      di.cache.cache(playerSteps, force ?: false)
      di.import.importPlayers(playerSteps)
    } else {
      Logger.error("No team found for ID $teamId")
    }

    Logger.taskEnd()
  }
}