package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.models.CacheStep
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int

class CacheTeamCommand(di: DI) : BaseCommand(di, name = "team") {
  val teamId by argument().int()

  override fun run() {
    super.run()

    Logger.taskTitle("Cache Team $teamId rosters")
    Logger.enabled = false

    val team = di.database.getTeamForId(teamId) ?: return
    val roster = di.database.getRosterForTeam(team.id)

    val nbSteps = roster.count().toFloat()
    Logger.startProgress("Players", nbSteps)

    val playerSteps = roster.map { CacheStep.Player(it) }
    di.cache.cache(playerSteps, force ?: false, showProgress = true)
    di.import.importPlayers(playerSteps)

    Logger.endProgress()

    Logger.enabled = true
    Logger.taskEnd()
  }
}