package ca.sebleclerc.hockeydata.cli.commands.subcommands

import ca.sebleclerc.hockeydata.cli.DI
import ca.sebleclerc.hockeydata.cli.commands.BaseCommand
import ca.sebleclerc.hockeydata.cli.helpers.Logger
import ca.sebleclerc.hockeydata.core.cache.CacheStep
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int

class CacheTeamCommand(
  di: DI,
) : BaseCommand(
  di = di,
  name = "team",
  help = "Cache single team rosters and all missing players. (Can force)"
) {
  val teamId by argument().int()

  override fun run() {
    super.run()

    Logger.taskTitle("Cache Team $teamId rosters")
    Logger.enabled = false

    val team = di.database.getTeamForId(teamId) ?: return
    val roster = di.database.getRosterForTeam(team.id)

    val nbSteps = roster.count().toFloat()
    di.progress.startProgress("Players", nbSteps)

    val playerSteps = roster.map { CacheStep.Player(it) }
    di.cache.cache(playerSteps, force ?: false, showProgress = true)
    di.import.importPlayers(playerSteps)

    di.progress.endProgress()

    Logger.enabled = true
    Logger.taskEnd()
  }
}
