package ca.sebleclerc.hockeydata.commands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.helpers.LoggerColumn
import ca.sebleclerc.hockeydata.models.CacheStep
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument

class TeamsCommand(private val di: DI) : CliktCommand(name = "teams") {
  override fun run() {
    Logger.taskTitle("Teams")

    val steps: MutableList<CacheStep> = di.database.getAllTeams().map { CacheStep.CacheTeamRoster(it) }.toMutableList()
    steps.add(0, CacheStep.Teams())

    Logger.enabled = false

    di.cache.cache(
      steps,
      force = false
    )

    Logger.enabled = true

    val proportionPadding = 10
    Logger.header(
      LoggerColumn.ID(),
      LoggerColumn.Name(),
      LoggerColumn.Custom("P %", proportionPadding)
    )

    di.database.getAllTeams().forEach { team ->
      val roster = di.database.getRosterForTeam(team.id)
      val nbPlayersInRoster = roster.count()
      var nbPlayerInDB = 0

      roster.forEach { playerId ->
        val player = di.database.getPlayerForId(playerId)
        if (player != null) nbPlayerInDB += 1
      }

      val proportion = "$nbPlayerInDB / $nbPlayersInRoster"

      Logger.row(
        LoggerColumn.ID(team.id),
        LoggerColumn.Name(team.name),
        LoggerColumn.Custom(proportion, proportionPadding)
      )
    }

    Logger.taskEnd()
  }
}
