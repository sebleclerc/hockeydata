package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.models.CacheStep

class CacheTeamsCommand(di: DI) : BaseCommand(di = di, name = "teams") {
  override fun run() {
    Logger.taskTitle("Cache Teams rosters")
    Logger.enabled = false

    val teams = di.database.getAllTeams()
    val nbSteps = teams.count().toFloat() * 2
    Logger.startProgress("Teams", nbSteps)

    val steps = teams.map { CacheStep.CacheTeamRoster(it) }
    di.cache.cache(steps, true, showProgress = true)
    di.import.importRosters()

    teams.forEach { team ->
      val roster = di.database.getRosterForTeam(team.id)
      val playerSteps = roster.map { CacheStep.Player(it) }
      di.cache.cache(playerSteps, false)
      di.import.importPlayers(playerSteps)
      Logger.step()
    }

    Logger.endProgress()

    Logger.enabled = true

    Logger.taskEnd()
  }
}