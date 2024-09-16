package ca.sebleclerc.hockeydata.commands.subcommands

import ca.sebleclerc.hockeydata.DI
import ca.sebleclerc.hockeydata.commands.BaseCommand
import ca.sebleclerc.hockeydata.helpers.Logger
import ca.sebleclerc.hockeydata.models.CacheStep

class CacheTeamsCommand(di: DI) : BaseCommand(di = di, name = "teams") {
  override fun run() {
    Logger.taskTitle("Cache Teams rosters")

    val steps = di.database.getAllTeams().map { CacheStep.CacheTeamRoster(it) }
    di.cache.cache(steps, force ?: false)
    di.import.importRosters()

    Logger.taskEnd()
  }
}